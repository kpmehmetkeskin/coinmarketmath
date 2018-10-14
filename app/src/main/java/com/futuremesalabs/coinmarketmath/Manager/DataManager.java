package com.futuremesalabs.coinmarketmath.Manager;

import com.futuremesalabs.coinmarketmath.Connector.Connection;
import com.futuremesalabs.coinmarketmath.DTO.SymbolPriceDTO;

import java.util.List;

public class DataManager {

    Connection connection;

    public  DataManager() {
        connection = new Connection();
    }

    public List<SymbolPriceDTO> getSymbolPriceData() {
        List<SymbolPriceDTO> symbolPriceDTOList = null;
        try {
            symbolPriceDTOList = connection.getSymbolPriceData();
        } catch (Exception e) {
            // TODO
        }
        return symbolPriceDTOList;
    }
}
