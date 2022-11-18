package br.blog.smarti.processor.enums;

public enum ArgumentsOptionsEnum {
    
    PATH;
    
    public String getValue() {
        return this.name().toLowerCase();
    }
    
}
