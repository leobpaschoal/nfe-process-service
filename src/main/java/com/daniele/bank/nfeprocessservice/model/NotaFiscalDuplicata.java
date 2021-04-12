package com.daniele.bank.nfeprocessservice.model;

import com.daniele.bank.nfeprocessservice.adapter.LocalDateAdapter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@XmlRootElement(name = "duplicata")
public class NotaFiscalDuplicata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int parcela;
    private BigDecimal valor;
    private LocalDate vencimento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nota_fiscal_id", insertable = false, updatable = false)
    private NotaFiscal notaFiscal;

    public Integer getId() {
        return id;
    }

    @XmlTransient
    public void setId(Integer id) {
        this.id = id;
    }

    public int getParcela() {
        return parcela;
    }

    @XmlElement
    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @XmlElement
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
}