package com.stockapi.controller;

import com.stockapi.dto.StockBuySellDTO;
import com.stockapi.dto.StockCreationDTO;
import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockSummaryDTO;
import com.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@RestController
public class StockController {

    @Autowired
    StockService stockService;

    private final Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();

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

        String message = stockService.buyStock(userDetails.getUsername(), stockBuySellDTO.getSymbol(), stockBuySellDTO.getQuantity());

        // update live price
        StockSummaryDTO liveStock = stockService.getStockSummary(stockBuySellDTO.getSymbol());
        sink.tryEmitNext(liveStock);

        return message;
    }


    @PostMapping("/stock/sell")
    public String sellStock(@RequestBody StockBuySellDTO stockBuySellDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        String message =  stockService.sellStock(userDetails.getUsername(), stockBuySellDTO.getSymbol(), stockBuySellDTO.getQuantity());

        // update live price
        StockSummaryDTO liveStock = stockService.getStockSummary(stockBuySellDTO.getSymbol());
        sink.tryEmitNext(liveStock);

        return message;
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

    @GetMapping(value = "/stock/live",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<StockSummaryDTO>> sse() {
        return sink.asFlux().map(e -> ServerSentEvent.builder((StockSummaryDTO) e).build());
    }

}
