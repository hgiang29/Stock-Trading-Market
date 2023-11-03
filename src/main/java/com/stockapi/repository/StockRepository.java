package com.stockapi.repository;

import com.stockapi.model.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, String> {

    @Query("SELECT s FROM Stock s WHERE s.symbol = ?1")
    public Stock findBySymbol(String symbol);



}
