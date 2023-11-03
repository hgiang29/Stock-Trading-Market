package com.stockapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class StockOHLC {

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
    private double high;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private double low;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private double open;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private double close;

    public StockOHLC(Stock stock, Date date, double high, double low, double open, double close) {
        this.stock = stock;
        this.date = date;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
    }

    public StockOHLC() {

    }

    @Override
    public String toString() {
        return stock.getSymbol() + " " +
                date + " " +
                " open: " + open +
                " high: " + high +
                " low: " + low +
                " close: " + close;
    }
}
