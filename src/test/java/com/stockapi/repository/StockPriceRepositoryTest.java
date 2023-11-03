package com.stockapi.repository;

import com.stockapi.model.Stock;
import com.stockapi.model.StockOHLC;
import com.stockapi.model.StockPrice;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockPriceRepositoryTest {

    @Autowired
    StockPriceRepository priceRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void createStockPrice(){
        Stock stock = entityManager.find(Stock.class, "mcsf");
        StockPrice stockPrice = new StockPrice(stock, new Date(), 200.4);

        priceRepository.save(stockPrice);
        System.out.println(stockPrice);
    }

    @Test
    public void findStockPriceByStock(){
        Stock stock = entityManager.find(Stock.class, "appl");
        List<StockPrice> stockPrices = priceRepository.findStockPricesByStock(stock);

        stockPrices.forEach(System.out::println);
    }

    @Test
    public void findStockPriceLastOneDay(){
        List<StockPrice> stockPrices = priceRepository.findStockPriceLastXDays(2, "appl");

        stockPrices.forEach(System.out::println);
    }

}
