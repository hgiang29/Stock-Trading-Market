package com.stockapi.service;

import com.stockapi.dto.StockDiagramDTO;
import com.stockapi.dto.StockPriceDTO;
import com.stockapi.model.StockPrice;
import com.stockapi.repository.StockPriceRepository;
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

    // hard code stock change
    public StockDiagramDTO getStockDiagramLastXDays(int x, String symbol){
        List<StockPriceDTO> stockPriceDTOS = this.getStockPriceLastXDays(x, symbol);
        StockDiagramDTO stockDiagramDTO = new StockDiagramDTO();

        stockDiagramDTO.setStockPriceList(stockPriceDTOS);
        stockDiagramDTO.setChange(4);
        stockDiagramDTO.setChangePercent(3);

        return stockDiagramDTO;
    }




}
