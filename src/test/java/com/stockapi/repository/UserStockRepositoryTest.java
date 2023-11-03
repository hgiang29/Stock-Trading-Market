package com.stockapi.repository;

import com.stockapi.model.Stock;
import com.stockapi.model.User;
import com.stockapi.model.UserStock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserStockRepositoryTest {

    @Autowired
    UserStockRepository userStockRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void createUserStock(){
        User user = entityManager.find(User.class, 2);
        Stock stock = entityManager.find(Stock.class, "mcsf");

        UserStock userStock = new UserStock(user, stock, 3);
        userStockRepository.save(userStock);
    }

    @Test
    public void readUserInventory(){
        User user = entityManager.find(User.class, 1);
        System.out.println(user.getInventory());
    }



}
