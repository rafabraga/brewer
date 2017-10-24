package com.algaworks.brewer.repository.filter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.brewer.model.StatusVenda;

public class VendaFilter implements Serializable {

    private static final long serialVersionUID = -7236804256153110604L;

    private Long codigo;

    private LocalDate dataCriacaoDe;

    private LocalDate dataCriacaoAte;

    private BigDecimal valorTotalDe;

    private BigDecimal valorTotalAte;

    private StatusVenda status;

    private String nomeCliente;

    private String cpfOuCnpjCliente;

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return this.codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the dataCriacaoDe
     */
    public LocalDate getDataCriacaoDe() {
        return this.dataCriacaoDe;
    }

    /**
     * @param dataCriacaoDe the dataCriacaoDe to set
     */
    public void setDataCriacaoDe(final LocalDate dataCriacaoDe) {
        this.dataCriacaoDe = dataCriacaoDe;
    }

    /**
     * @return the dataCriacaoAte
     */
    public LocalDate getDataCriacaoAte() {
        return this.dataCriacaoAte;
    }

    /**
     * @param dataCriacaoAte the dataCriacaoAte to set
     */
    public void setDataCriacaoAte(final LocalDate dataCriacaoAte) {
        this.dataCriacaoAte = dataCriacaoAte;
    }

    /**
     * @return the valorTotalDe
     */
    public BigDecimal getValorTotalDe() {
        return this.valorTotalDe;
    }

    /**
     * @param valorTotalDe the valorTotalDe to set
     */
    public void setValorTotalDe(final BigDecimal valorTotalDe) {
        this.valorTotalDe = valorTotalDe;
    }

    /**
     * @return the valorTotalAte
     */
    public BigDecimal getValorTotalAte() {
        return this.valorTotalAte;
    }

    /**
     * @param valorTotalAte the valorTotalAte to set
     */
    public void setValorTotalAte(final BigDecimal valorTotalAte) {
        this.valorTotalAte = valorTotalAte;
    }

    /**
     * @return the status
     */
    public StatusVenda getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final StatusVenda status) {
        this.status = status;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return this.nomeCliente;
    }

    /**
     * @param nomeCliente the nomeCliente to set
     */
    public void setNomeCliente(final String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    /**
     * @return the cpfOuCnpjCliente
     */
    public String getCpfOuCnpjCliente() {
        return this.cpfOuCnpjCliente;
    }

    /**
     * @param cpfOuCnpjCliente the cpfOuCnpjCliente to set
     */
    public void setCpfOuCnpjCliente(final String cpfOuCnpjCliente) {
        this.cpfOuCnpjCliente = cpfOuCnpjCliente;
    }

}
