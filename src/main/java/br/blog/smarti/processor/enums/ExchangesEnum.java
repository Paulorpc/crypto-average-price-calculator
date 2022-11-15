package br.blog.smarti.processor.enums;

public enum ExchangesEnum {
    
    BINANCE("Binance", "http://www.binance.com"), 
    BITFINEX("Bitfinex", "http://bitfinext.com");
    
    private String site;
    private String name;
    
    ExchangesEnum(String name, String site) {
        this.name = name;
        this.site = site;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSite() {
        return this.site;
    }
    
}
