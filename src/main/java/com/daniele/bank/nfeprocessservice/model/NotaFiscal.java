package com.daniele.bank.nfeprocessservice.model;

import com.daniele.bank.nfeprocessservice.adapter.LocalDateTimeAdapter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@XmlRootElement(name = "nfe")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String chave;
    private int numero;
    private LocalDateTime dhRegistro;
    private String nomeEmitente;
    private String nomeDestinatario;
    private BigDecimal valorNota;
    private String nomeArquivoOriginal;
    private String arquivoId;
    private int statusId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "nota_fiscal_id")
    private List<NotaFiscalDuplicata> duplicatas;

    public Integer getId() {
        return id;
    }

    @XmlTransient
    public void setId(Integer id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    @XmlElement
    public void setChave(String chave) {
        this.chave = chave;
    }

    public int getNumero() {
        return numero;
    }

    @XmlElement
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDateTime getDhRegistro() {
        return dhRegistro;
    }

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public void setDhRegistro(LocalDateTime dhRegistro) {
        this.dhRegistro = dhRegistro;
    }

    public String getNomeEmitente() {
        return nomeEmitente;
    }

    @XmlElement
    public void setNomeEmitente(String nomeEmitente) {
        this.nomeEmitente = nomeEmitente;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    @XmlElement
    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public BigDecimal getValorNota() {
        return valorNota;
    }

    @XmlElement
    public void setValorNota(BigDecimal valorNota) {
        this.valorNota = valorNota;
    }

    public String getNomeArquivoOriginal() {
        return nomeArquivoOriginal;
    }

    @XmlTransient
    public void setNomeArquivoOriginal(String nomeArquivoOriginal) {
        this.nomeArquivoOriginal = nomeArquivoOriginal;
    }

    public String getArquivoId() {
        return arquivoId;
    }

    @XmlTransient
    public void setArquivoId(String arquivoId) {
        this.arquivoId = arquivoId;
    }

    public int getStatusId() {
        return statusId;
    }

    @XmlTransient
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public List<NotaFiscalDuplicata> getDuplicatas() {
        return duplicatas;
    }

    @XmlElementWrapper(name = "duplicatas")
    @XmlElement(name = "duplicata")
    public void setDuplicatas(List<NotaFiscalDuplicata> duplicatas) {
        this.duplicatas = duplicatas;
    }
}
