package com.stockapi.repository;

import com.stockapi.model.Stock;
import com.stockapi.model.User;
import com.stockapi.model.UserStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserStockRepository extends CrudRepository<UserStock, Integer> {

    @Query("SELECT s FROM UserStock s WHERE s.user=?1 AND  s.stock=?2")
    public UserStock findUserStockByUserAndStock(User user, Stock stock);

    public List<UserStock> findUserStocksByUser(User user);

}
