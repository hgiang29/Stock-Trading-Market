package com.stockapi.service;

import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockSummaryDTO;
import com.stockapi.model.*;
import com.stockapi.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    UserStockRepository userStockRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    StockPriceRepository stockPriceRepository;

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

    public void increaseStockPrice(Stock stock, int quantity) {
        double newPrice = stock.getPrice() + quantity * 0.1;
        stock.setPrice(newPrice);
    }

    public void decreaseStockPrice(Stock stock, int quantity) {
        double newPrice = stock.getPrice() - quantity * 0.1;
        stock.setPrice(newPrice);
    }

    @Transactional
    public String buyStock(String email, String symbol, int quantity) {
        /*
        1. Check if user have enough money and if stock have enough stock remaining to buy
        2. Subtract user money, stock quantity, add new UserStock entity, if user
        already have this stock, add quantity
        3. Update the price value of stock, the new price is calculated by: price + quantity*0.001
        4. Create new transaction history
         */

        Stock stock = stockRepository.findBySymbol(symbol);
        if(stock.getQuantity() < quantity) {
            return "Not enough stock remaining";
        }

        User user = userRepository.findUserByEmail(email);
        double totalPrice = stock.getPrice() * quantity;
        if(user.getMoney() < totalPrice) {
            return "User dont have enough money";
        }

        // start

        // add user stock inventory
        UserStock userStock = userStockRepository.findUserStockByUserAndStock(user, stock);
        if(userStock == null) {
            userStock = new UserStock(user, stock, 0);
        }
        userStock.setQuantity(userStock.getQuantity() + quantity);

        // create new transaction history
        Date now = new Date();
        TransactionHistory transactionHistory = new TransactionHistory(user, stock, quantity, totalPrice, now, true);

        user.setMoney(user.getMoney() - totalPrice);
        stock.setQuantity(stock.getQuantity() - quantity);
        this.increaseStockPrice(stock, quantity);

        // save stock price history
        StockPrice stockPrice = new StockPrice(stock, now, stock.getPrice());

        // save to database
        userRepository.save(user);
        stockRepository.save(stock);
        transactionHistoryRepository.save(transactionHistory);
        userStockRepository.save(userStock);
        stockPriceRepository.save(stockPrice);

        return "buying completed";
    }

    @Transactional
    public String sellStock(String email, String symbol, int quantity) {
        /*
        1. Check if user has this stock, quantity must <= the quantity user has
        2. Add user money, total stock quantity, decrease stock price
        3. If this stock user has is 0, remove the stock in user's inventory
        4. Create new transaction history, stock price history
         */

        User user = userRepository.findUserByEmail(email);
        Stock stock = stockRepository.findBySymbol(symbol);
        UserStock userStock = userStockRepository.findUserStockByUserAndStock(user, stock);

        if(userStock == null) {
            return "You dont have this stock!";
        }

        if(quantity > userStock.getQuantity()) {
            return "You dont have this amount of stock!";
        }

        // change user stock, delete if the amount is 0
        userStock.setQuantity(userStock.getQuantity() - quantity);
        if(userStock.getQuantity() == 0){
            userStockRepository.delete(userStock);
        } else{
            userStockRepository.save(userStock);
        }

        // subtract user money
        double totalPrice = stock.getPrice() * quantity;
        user.setMoney(user.getMoney() + totalPrice);

        // change stock
        stock.setQuantity(stock.getQuantity() - quantity);
        this.decreaseStockPrice(stock, quantity);

        // create transaction history
        Date now = new Date();
        TransactionHistory transactionHistory = new TransactionHistory(user, stock, quantity, totalPrice, now, false);

        // add stock price history
        StockPrice stockPrice = new StockPrice(stock, now, stock.getPrice());

        // save to database
        userRepository.save(user);
        stockRepository.save(stock);
        transactionHistoryRepository.save(transactionHistory);
        stockPriceRepository.save(stockPrice);

        return "selling completed";

    }





}
