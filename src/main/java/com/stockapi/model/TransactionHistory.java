package com.stockapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock")
    private Stock stock;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private int quantity;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private double price;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    // true is buy, false is sell
    private boolean type;

    public TransactionHistory(User user, Stock stock, int quantity, double price, boolean type) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.time = new Date();
        this.type = type;
    }

    public TransactionHistory(User user, Stock stock, int quantity, double price, Date time, boolean type) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.time = time;
        this.type = type;
    }

    @Override
    public String toString() {
        String transactionType = (type) ? "buy" : "sell";

        return "TransactionHistory{" +
                "user: " + user.getName() +
                ", stock: " + stock.getSymbol() +
                ", quantity: " + quantity +
                ", total price: " + price +
                ", time: " + time +
                ", type: " + transactionType +
                '}';
    }
}
