package com.stockapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockCreationDTO {

    private String symbol;

    private String name;

    private String about;

    private int quantity;

    private double price;

}
