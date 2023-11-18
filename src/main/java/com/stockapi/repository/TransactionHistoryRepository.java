package com.stockapi.repository;

import com.stockapi.model.TransactionHistory;
import com.stockapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {

    public List<TransactionHistory> findTransactionHistoriesByUser(User user);

}
