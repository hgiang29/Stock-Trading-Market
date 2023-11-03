package com.stockapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "stock", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private double price;

    public StockPrice(Stock stock, Date date, double price) {
        this.stock = stock;
        this.date = date;
        this.price = price;
    }

    public StockPrice() {

    }

    @Override
    public String toString() {
        return "StockPrice{" +
                "stock=" + stock.getSymbol() +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
