package com.stockapi.helper;

import com.stockapi.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateDiagramResponse(
            StockDTO stockDTO, List<StockDiagramDTO> stockDiagramDTOs){

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("diagramdetail", stockDiagramDTOs);
        map.put("stock", stockDTO);

        return ResponseEntity.ok(map);
    }

    public static ResponseEntity<Object> generateAuthenticationResponse(UserDTO userDTO, String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", userDTO);
        map.put("token", token);

        return ResponseEntity.ok(map);
    }
}
