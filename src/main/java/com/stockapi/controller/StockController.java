package com.stockapi.controller;

import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockSummaryDTO;
import com.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/stock")
    public List<StockSummaryDTO> getStockTable(){
        return stockService.getAllStockSummary();
    }


    @GetMapping("/stock/{symbol}/buy/{quantity}")
    public String buyStock(@PathVariable String symbol, @PathVariable int  quantity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return stockService.buyStock(userDetails.getUsername(), symbol, quantity);
    }


}
