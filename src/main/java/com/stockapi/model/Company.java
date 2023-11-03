package com.stockapi.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "company")
    private Stock stock;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    private String summary;


    public Company(String name, String summary){
        this.name = name;
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Company{" +
                "stock=" + stock.getSymbol() +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
