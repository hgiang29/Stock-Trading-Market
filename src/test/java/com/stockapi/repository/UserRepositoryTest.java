package com.stockapi.repository;

import com.stockapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() {
        User user = new User("mimi", "$2a$12$cSAZpNc/RVZ38xuByxB.cetwOlDrlE1.Jg5LCmG6APSdUqxkSVtnO", "meme@gmail.com");
        userRepository.save(user);
    }

    @Test
    public void findUserByEmail() {
        User user = userRepository.findUserByEmail("joe@gmail.com");
        System.out.println(user);
    }

}
