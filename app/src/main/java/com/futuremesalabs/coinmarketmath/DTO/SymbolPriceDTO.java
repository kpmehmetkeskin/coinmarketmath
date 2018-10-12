package com.futuremesalabs.coinmarketmath.DTO;

public class SymbolPriceDTO {
    public String Symbol;
    public String Price;
    public String PricePower;

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPricePower() {
        return PricePower;
    }

    public void setPricePower(String pricePower) {
        PricePower = pricePower;
    }
}
