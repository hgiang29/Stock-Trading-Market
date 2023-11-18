package com.stockapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserStock {

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

    public UserStock(User user, Stock stock, int quantity) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "user: " + user.getName() +
                ", stock: " + stock.getSymbol() +
                ", quantity: " + quantity;
    }
    
    public void addUserStockQuantity(int quantity) {
        this.quantity += quantity;
    }
    
    public void subtractUserStockQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
