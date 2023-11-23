package com.stockapi.repository;

import com.stockapi.model.TransactionHistory;
import com.stockapi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {

    @Query("SELECT t FROM TransactionHistory t WHERE t.user=?1 ORDER BY t.time DESC")
    public List<TransactionHistory> findTransactionHistoriesByUser(User user);

}
