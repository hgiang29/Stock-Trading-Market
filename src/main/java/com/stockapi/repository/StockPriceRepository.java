package com.stockapi.repository;

import com.stockapi.model.Stock;
import com.stockapi.model.StockPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockPriceRepository extends CrudRepository<StockPrice, Integer> {

    @Query("SELECT s FROM StockPrice s WHERE s.stock = ?1")
    List<StockPrice> findStockPricesByStock(Stock stock);

    @Query(nativeQuery = true, value = "SELECT * FROM stock_price " +
            "WHERE stock_price.date >  NOW() - INTERVAL :x DAY" +
            " AND stock_price.stock = :stockSymbol" +
            " ORDER BY stock_price.date")
    List<StockPrice> findStockPriceLastXDays(@Param("x") int x, @Param("stockSymbol") String symbol);

}
