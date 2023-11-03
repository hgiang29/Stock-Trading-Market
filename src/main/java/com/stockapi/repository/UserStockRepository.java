package com.stockapi.repository;

import com.stockapi.model.UserStock;
import org.springframework.data.repository.CrudRepository;

public interface UserStockRepository extends CrudRepository<UserStock, Integer> {
}
