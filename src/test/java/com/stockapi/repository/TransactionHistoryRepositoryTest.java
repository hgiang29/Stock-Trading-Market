package com.stockapi.repository;


import com.stockapi.model.Stock;
import com.stockapi.model.TransactionHistory;
import com.stockapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionHistoryRepositoryTest {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StockRepository stockRepository;

    @Test
    public void createTransactionHistory() {
        User user = userRepository.findUserByEmail("meme@gmail.com");
        Stock stock = stockRepository.findBySymbol("appl");

        TransactionHistory transactionHistory = new TransactionHistory(user, stock, 6, 400, true);
        transactionHistoryRepository.save(transactionHistory);

        System.out.println(transactionHistory);
    }

    @Test
    public void readUserTransactionHistory() {
        User user = userRepository.findUserByEmail("meme@gmail.com");
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findTransactionHistoriesByUser(user);
        System.out.println(transactionHistoryList);
    }

}
