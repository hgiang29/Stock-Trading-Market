package com.stockapi.repository;

import com.stockapi.model.Stock;
import com.stockapi.model.StockOHLC;
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
public class StockOHLCRepositoryTest {

    @Autowired
    StockOHLCRepository ohlcRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void createOHLC(){
        Stock stock = entityManager.find(Stock.class, "appl");
        StockOHLC stockOHLC = new StockOHLC(stock, new Date(), 60, 30, 35, 55);

        ohlcRepository.save(stockOHLC);
        System.out.println(stockOHLC);
    }

    @Test
    public void findOHLCByStock(){
        Stock stock = entityManager.find(Stock.class, "appl");
        List<StockOHLC> OHLCs = ohlcRepository.findStockOHLCByStock(stock);

        OHLCs.forEach(System.out::println);
    }

}
