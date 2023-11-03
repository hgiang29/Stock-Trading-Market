package com.stockapi.controller;

import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockSummaryDTO;
import com.stockapi.model.Stock;
import com.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {

    @Autowired
    StockService stockService;


    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/stock/{symbol}")
    public StockDTO getStock(@PathVariable String symbol){
        return stockService.getStockDTO(symbol);
    }

    @GetMapping("/stock/all")
    public List<StockSummaryDTO> getStockTable(){
        return stockService.getAllStockSummary();
    }


}
