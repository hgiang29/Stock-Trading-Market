package com.stockapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionHistoryDTO {

    private String symbol;

    private int quantity;

    private double price;

    private String type;

    private Date date;
}
