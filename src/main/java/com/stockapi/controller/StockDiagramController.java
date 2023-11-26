package com.stockapi.controller;

import com.stockapi.dto.CompanyDTO;
import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockDiagramDTO;
import com.stockapi.dto.StockPriceDTO;
import com.stockapi.helper.ResponseHandler;
import com.stockapi.model.Stock;
import com.stockapi.service.CompanyService;
import com.stockapi.service.StockDiagramService;
import com.stockapi.service.StockService;
import com.stockapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StockDiagramController {

    @Autowired
    StockDiagramService stockDiagramService;

    @Autowired
    StockService stockService;

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

//    @GetMapping("/stock/{symbol}/diagram/{days}")
//    public List<StockPriceDTO> getStockPriceDiagram(@PathVariable String symbol, @PathVariable int days){
//        return stockDiagramService.priceLastXDays(days, symbol);
//    }

//    @GetMapping("/stock/{symbol}/diagram/{days}")
//    public ResponseEntity<Object> getAllDiagrams(@PathVariable String symbol, @PathVariable int days){
//        try {
//            List<StockPriceDTO> stockPriceDTOs = stockDiagramService.getStockPriceLastXDays(days, symbol);
//
//            return ResponseHandler.generateResponse(message, stockPriceDTOs);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("wtf");
//        }
//    }

    @GetMapping("/stock/{symbol}")
    public ResponseEntity<Object> getStockDiagram(@PathVariable String symbol) {
        List<StockDiagramDTO> stockDiagramDTOs = new ArrayList<>();
        stockDiagramDTOs.add(stockDiagramService.getStockDiagramLastXDays(1, symbol)); // 1 day
        stockDiagramDTOs.add(stockDiagramService.getStockDiagramLastXDays(7, symbol)); // 7 days
        stockDiagramDTOs.add(stockDiagramService.getStockDiagramLastXDays(30, symbol)); // 30 days

        StockDTO stockDTO = stockService.getStockDTO(symbol);

        return ResponseHandler.generateDiagramResponse(stockDTO, stockDiagramDTOs);
    }

    @GetMapping("/user/inventory/highest_stock")
    public ResponseEntity<Object> getStockDiagramByHighestPrice() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        String symbol = userService.getUserOwningStockWithHighestPrice(userDetails.getUsername());
        return this.getStockDiagram(symbol);
    }

}
