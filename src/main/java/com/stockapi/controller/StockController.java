package com.stockapi.controller;

import com.stockapi.dto.StockBuySellDTO;
import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockSummaryDTO;
import com.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {

    @Autowired
    StockService stockService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/stock")
    public List<StockSummaryDTO> getStockTable(){
        return stockService.getAllStockSummary();
    }

    @GetMapping("/stock/buy")
    public String buyStock(@RequestBody StockBuySellDTO stockBuySellDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return stockService.buyStock(userDetails.getUsername(), stockBuySellDTO.getSymbol(), stockBuySellDTO.getQuantity());
    }


}
