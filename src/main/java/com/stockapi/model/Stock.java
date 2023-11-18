package com.stockapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Stock {

    @Id
    private String symbol;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private double price;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private int quantity;

    public Stock() {

    }
    public Stock(String symbol, Double price, int quantity){
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return symbol + " " + company.getName() + " price:" + price + " quantity:" + quantity;
    }


}
