package com.stockapi.controller;

import com.stockapi.dto.StockBuySellDTO;
import com.stockapi.dto.StockCreationDTO;
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

    @PostMapping("/stock/buy")
    public String buyStock(@RequestBody StockBuySellDTO stockBuySellDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return stockService.buyStock(userDetails.getUsername(), stockBuySellDTO.getSymbol(), stockBuySellDTO.getQuantity());
    }


    @PostMapping("/stock/sell")
    public String sellStock(@RequestBody StockBuySellDTO stockBuySellDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return stockService.sellStock(userDetails.getUsername(), stockBuySellDTO.getSymbol(), stockBuySellDTO.getQuantity());
    }

    @GetMapping(value = "/stock", params = "keyword")
    public List<StockSummaryDTO> searchStock(@RequestParam String keyword) {
        return stockService.searchStock(keyword);
    }

    @PostMapping("/stock/new")
    public String createStock(@RequestBody StockCreationDTO stockCreationDTO) {
        return stockService.createStock(stockCreationDTO);
    }

    @PutMapping("/stock/{symbol}/update")
    public String updateStock(@PathVariable String symbol, @RequestBody StockCreationDTO stockCreationDTO) {
        return stockService.updateStock(symbol, stockCreationDTO);
    }

}
