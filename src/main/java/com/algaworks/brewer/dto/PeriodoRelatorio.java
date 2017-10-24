package com.algaworks.brewer.dto;

import java.time.LocalDate;

public class PeriodoRelatorio {

    private LocalDate dataInicio;
    private LocalDate dataFim;

    /**
     * @return the dataInicio
     */
    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    /**
     * @param dataInicio the dataInicio to set
     */
    public void setDataInicio(final LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * @return the dataFim
     */
    public LocalDate getDataFim() {
        return this.dataFim;
    }

    /**
     * @param dataFim the dataFim to set
     */
    public void setDataFim(final LocalDate dataFim) {
        this.dataFim = dataFim;
    }

}
