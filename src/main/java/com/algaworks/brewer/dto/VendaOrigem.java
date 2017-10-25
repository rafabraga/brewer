package com.algaworks.brewer.dto;

public class VendaOrigem {

    private String mes;
    private Integer totalNacional;
    private Integer totalInternacional;

    public VendaOrigem() {

    }

    public VendaOrigem(final String mes, final Integer totalNacional, final Integer totalInternacional) {
        this.mes = mes;
        this.totalNacional = totalNacional;
        this.totalInternacional = totalInternacional;
    }

    public String getMes() {
        return this.mes;
    }

    public Integer getTotalNacional() {
        return this.totalNacional;
    }

    public void setTotalNacional(final Integer totalNacional) {
        this.totalNacional = totalNacional;
    }

    public Integer getTotalInternacional() {
        return this.totalInternacional;
    }

    public void setTotalInternacional(final Integer totalInternacional) {
        this.totalInternacional = totalInternacional;
    }

    public void setMes(final String mes) {
        this.mes = mes;
    }

}