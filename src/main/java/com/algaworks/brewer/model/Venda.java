package com.algaworks.brewer.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "venda")
@DynamicUpdate
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "VENDA_SEQ")
    @SequenceGenerator(name = "VENDA_SEQ", sequenceName = "venda_codigo_seq", allocationSize = 1)
    private Long codigo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;

    private String observacao;

    @Column(name = "data_hora_entrega")
    private LocalDateTime dataHoraEntrega;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private StatusVenda status = StatusVenda.ORCAMENTO;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens = new ArrayList<>();

    @Transient
    private String uuid;

    @Transient
    private LocalDate dataEntrega;

    @Transient
    private LocalTime horaEntrega;

    public boolean isNova() {
        return this.codigo == null;
    }

    public BigDecimal getValorTotalItens() {
        return this.getItens().stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public void calcularValorTotal() {
        this.valorTotal = this.calcularValorTotal(this.getValorTotalItens(), this.getValorFrete(), this.getValorDesconto());
    }

    private BigDecimal calcularValorTotal(final BigDecimal valorTotalItens, final BigDecimal valorFrete, final BigDecimal valorDesconto) {
        return valorTotalItens.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
                .subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
    }

    public void adicionarItens(final List<ItemVenda> itensVenda) {
        this.itens = itensVenda;
        itensVenda.forEach(i -> i.setVenda(this));
    }

    public Long getDiasCriacao() {
        final LocalDate dataInicio = this.dataCriacao == null ? LocalDate.now() : this.dataCriacao.toLocalDate();
        return ChronoUnit.DAYS.between(dataInicio, LocalDate.now());
    }

    public boolean isSalvarPermitido() {
        return !StatusVenda.CANCELADA.equals(this.status);
    }

    public boolean isSalvarProibido() {
        return !this.isSalvarPermitido();
    }

    /**
     * @return the dataEntrega
     */
    public LocalDate getDataEntrega() {
        return this.dataEntrega;
    }

    /**
     * @param dataEntrega the dataEntrega to set
     */
    public void setDataEntrega(final LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    /**
     * @return the horaEntrega
     */
    public LocalTime getHoraEntrega() {
        return this.horaEntrega;
    }

    /**
     * @param horaEntrega the horaEntrega to set
     */
    public void setHoraEntrega(final LocalTime horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BigDecimal getValorFrete() {
        return this.valorFrete;
    }

    public void setValorFrete(final BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorDesconto() {
        return this.valorDesconto;
    }

    public void setValorDesconto(final BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(final BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public void setObservacao(final String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getDataHoraEntrega() {
        return this.dataHoraEntrega;
    }

    public void setDataHoraEntrega(final LocalDateTime dataHoraEntrega) {
        this.dataHoraEntrega = dataHoraEntrega;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

    public StatusVenda getStatus() {
        return this.status;
    }

    public void setStatus(final StatusVenda status) {
        this.status = status;
    }

    public List<ItemVenda> getItens() {
        return this.itens;
    }

    public void setItens(final List<ItemVenda> itens) {
        this.itens = itens;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.codigo == null) ? 0 : this.codigo.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (this.codigo == null) {
            if (other.codigo != null) {
                return false;
            }
        } else if (!this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

}