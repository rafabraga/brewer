package com.algaworks.brewer.dto;

public class VendaMes {

    private String mes;
    private Integer total;

    public VendaMes() {
    }

    public VendaMes(final String mes, final Integer total) {
        this.mes = mes;
        this.total = total;
    }

    public String getMes() {
        return this.mes;
    }

    public void setMes(final String mes) {
        this.mes = mes;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

}