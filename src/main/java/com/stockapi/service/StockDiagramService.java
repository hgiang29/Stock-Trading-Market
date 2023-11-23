package com.stockapi.service;

import com.stockapi.dto.StockDiagramDTO;
import com.stockapi.dto.StockPriceDTO;
import com.stockapi.model.Stock;
import com.stockapi.model.StockPrice;
import com.stockapi.repository.StockPriceRepository;
import com.stockapi.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockDiagramService {

    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StockPriceDTO> getStockPriceLastXDays(int x, String symbol){
        List<StockPrice> stockPrices = stockPriceRepository.findStockPriceLastXDays(x, symbol);
        List<StockPriceDTO> stockPriceDTOs = new ArrayList<>();

        stockPrices.forEach( (s) -> {
            StockPriceDTO stockPriceDTO = modelMapper.map(s, StockPriceDTO.class);
            stockPriceDTOs.add(stockPriceDTO);
        });

        return stockPriceDTOs;
    }

    public double getStockPriceChange(int x, String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol);
        double priceNow = stock.getPrice();
        // because primitive type can not be null, so we need to convert from Double to double, in case the db return null
        // we return 0
        Double priceFirstChange = stockPriceRepository.findFirstStockPriceChange(x, symbol);
        if(priceFirstChange == null) {
            return 0;
        }

        return priceNow - priceFirstChange;
    }

    public double getStockPriceChangePercent(int x, String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol);
        double priceNow = stock.getPrice();

        Double priceFirstChange = stockPriceRepository.findFirstStockPriceChange(x, symbol);
        if(priceFirstChange == null) {
            return 0;
        }

        double change = priceNow - priceFirstChange;
        double changePercent = (change / priceFirstChange) * 100;

        return changePercent;
    }

    // hard code stock change
    public StockDiagramDTO getStockDiagramLastXDays(int x, String symbol) {
        List<StockPriceDTO> stockPriceDTOS = this.getStockPriceLastXDays(x, symbol);
        StockDiagramDTO stockDiagramDTO = new StockDiagramDTO();

        stockDiagramDTO.setStockPriceList(stockPriceDTOS);
        stockDiagramDTO.setChange(this.getStockPriceChange(x, symbol));
        stockDiagramDTO.setChangePercent(this.getStockPriceChangePercent(x, symbol));

        return stockDiagramDTO;
    }


}
