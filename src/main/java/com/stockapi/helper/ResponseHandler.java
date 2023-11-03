package com.stockapi.helper;

import com.stockapi.dto.CompanyDTO;
import com.stockapi.dto.StockDTO;
import com.stockapi.dto.StockDiagramDTO;
import com.stockapi.dto.StockPriceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateDiagramResponse(
            StockDTO stockDTO, CompanyDTO companyDTO, List<StockDiagramDTO> stockDiagramDTOs){

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("diagramdetail", stockDiagramDTOs);
        map.put("stock", stockDTO);
        map.put("company", companyDTO);

        return ResponseEntity.ok(map);
    }
}
