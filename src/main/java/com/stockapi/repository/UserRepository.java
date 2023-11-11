package com.stockapi.repository;

import com.stockapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    public User findUserByEmail(String email);

}
