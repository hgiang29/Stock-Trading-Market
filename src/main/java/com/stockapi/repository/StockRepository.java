package com.stockapi.repository;

import com.stockapi.model.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, String> {

    @Query("SELECT s FROM Stock s WHERE s.symbol = ?1")
    public Stock findBySymbol(String symbol);

    @Query("SELECT s FROM Stock s WHERE s.symbol LIKE %?1%" +
            "OR s.company.name LIKE %?1%")
    public List<Stock> searchStock(String keyword);


}
