package com.stockapi.dto;

import com.stockapi.model.StockPrice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class StockDiagramDTO {

    private double change;

    private double changePercent;

    private List<StockPriceDTO> stockPriceList;

}
