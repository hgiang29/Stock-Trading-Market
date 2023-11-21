package com.stockapi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;

    private int id;

    private String email;

    private String role;

    private double money;

    private List<OwningStockDTO> owningStocks;

}
