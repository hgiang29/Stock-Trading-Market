package com.stockapi.service;

import com.stockapi.dto.OwningStockDTO;
import com.stockapi.dto.UserDTO;
import com.stockapi.dto.UserTransactionHistoryDTO;
import com.stockapi.model.Stock;
import com.stockapi.model.TransactionHistory;
import com.stockapi.model.User;
import com.stockapi.model.UserStock;
import com.stockapi.repository.TransactionHistoryRepository;
import com.stockapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;


    public UserDTO getUserDTOFromEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setOwningStocks(this.getUserOwningStock(email));

        return userDTO;
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public List<OwningStockDTO> getUserOwningStock(String email) {
        User user = userRepository.findUserByEmail(email);
        List<UserStock> userStockList = new ArrayList<>(user.getInventory());
        List<OwningStockDTO> owningStocks = new ArrayList<>();

        userStockList.forEach((s) -> {
            OwningStockDTO owningStockDTO =
                    new OwningStockDTO(s.getStock().getSymbol(), s.getStock().getCompany().getName(), s.getQuantity());
            owningStocks.add(owningStockDTO);
        });

        return owningStocks;
    }

    public String getUserOwningStockWithHighestPrice(String email) {
        User user = userRepository.findUserByEmail(email);
        List<UserStock> owningStocks =  new ArrayList<>(user.getInventory());

        String symbol = "";
        double highestPrice = 0;

        for(UserStock s : owningStocks) {
            if (s.getStock().getPrice() > highestPrice) {
                symbol = s.getStock().getSymbol();
                highestPrice = s.getStock().getPrice();
            }
        }

        return  symbol;
    }

    public List<UserTransactionHistoryDTO> getUserTransactionHistory(String email) {
        User user = userRepository.findUserByEmail(email);
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findTransactionHistoriesByUser(user);
        List<UserTransactionHistoryDTO> userTransactionHistoryDTOS = new ArrayList<>();

        transactionHistoryList.forEach((t) -> {
            String type = (t.isType()) ? "buy" : "sell";
            UserTransactionHistoryDTO userTransactionHistory =
                    new UserTransactionHistoryDTO(t.getStock().getSymbol(), t.getQuantity(), t.getPrice(), type, t.getTime());

            userTransactionHistoryDTOS.add(userTransactionHistory);
        });

        return userTransactionHistoryDTOS;
    }

    public double getUserMoney(String email) {
        return userRepository.findUserByEmail(email).getMoney();
    }

}
