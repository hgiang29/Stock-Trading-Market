package com.stockapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StockSummaryDTO {

    private String symbol;

    private String name;

    private String about;

    private double price;

    private double change;

    private double changePercent;
}
