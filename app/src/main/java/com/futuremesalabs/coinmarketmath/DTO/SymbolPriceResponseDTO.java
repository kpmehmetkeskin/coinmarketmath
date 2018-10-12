package com.futuremesalabs.coinmarketmath.DTO;

import java.util.List;

public class SymbolPriceResponseDTO {
    public List<SymbolPriceDTO> data;

    public List<SymbolPriceDTO> getData() {
        return data;
    }

    public void setData(List<SymbolPriceDTO> data) {
        this.data = data;
    }
}
