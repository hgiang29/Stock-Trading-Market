package com.stockapi.service;

import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockSummaryDTO;
import com.stockapi.model.Company;
import com.stockapi.model.Stock;
import com.stockapi.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    private ModelMapper modelMapper;

    public StockDTO getStockDTO(String symbol){
        Stock stock = stockRepository.findBySymbol(symbol);
        return modelMapper.map(stock, StockDTO.class);
    }
    public Stock createStock(String symbol, Company company, double price, int quantity){
        Stock stock = new Stock(symbol, company, price, quantity);
        return stockRepository.save(stock);
    }

    public Stock editStock(String symbol, Company company, double price, int quantity){

        Stock stock = stockRepository.findBySymbol(symbol);

        stock.setCompany(company);
        stock.setQuantity(quantity);
        stock.setPrice(price);

        return stockRepository.save(stock);
    }

    // hard code for stock change
    public List<StockSummaryDTO> getAllStockSummary(){
        List<Stock> stocks = (List<Stock>) stockRepository.findAll();
        List<StockSummaryDTO> stockSummaryDTOs = new ArrayList<>();

        stocks.forEach( (s) -> {
            StockSummaryDTO stockSummaryDTO = modelMapper.map(s, StockSummaryDTO.class);
            stockSummaryDTO.setName(s.getCompany().getName());
            stockSummaryDTO.setAbout(s.getCompany().getSummary());
            stockSummaryDTO.setChange(4);
            stockSummaryDTO.setChangePercent(3);

            stockSummaryDTOs.add(stockSummaryDTO);
        });

        return stockSummaryDTOs;
    }





}
