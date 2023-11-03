package com.stockapi.repository;

import com.stockapi.model.Stock;
import com.stockapi.model.StockOHLC;
import com.stockapi.model.StockPrice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockOHLCRepository extends CrudRepository<StockOHLC, Integer> {

    @Query("SELECT s FROM StockOHLC s WHERE s.stock = ?1")
    public List<StockOHLC> findStockOHLCByStock(Stock stock);

}
