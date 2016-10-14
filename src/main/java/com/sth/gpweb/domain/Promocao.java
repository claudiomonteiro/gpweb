package com.sth.gpweb.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Promocao.
 */
@Entity
@Table(name = "promocao")
@Document(indexName = "promocao")
public class Promocao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nm_promocao", length = 30, nullable = false, unique = true)
    private String nmPromocao;

    @Column(name = "dt_vencimento")
    private LocalDate dtVencimento;

    @Column(name = "nn_dia_data")
    private Integer nnDiaData;

    @Column(name = "nn_dia_semana")
    private String nnDiaSemana;

    @Column(name = "fl_inativo")
    private Boolean flInativo;

    @Column(name = "vl_promocao", precision=10, scale=2)
    private BigDecimal vlPromocao;    

    @Column(name = "nn_tipo")
    private Integer nnTipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmPromocao() {
        return nmPromocao;
    }

    public void setNmPromocao(String nmPromocao) {
        this.nmPromocao = nmPromocao;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Integer getNnDiaData() {
        return nnDiaData;
    }

    public void setNnDiaData(Integer nnDiaData) {
        this.nnDiaData = nnDiaData;
    }

    public String getNnDiaSemana() {
        return nnDiaSemana;
    }

    public void setNnDiaSemana(String nnDiaSemana) {
        this.nnDiaSemana = nnDiaSemana;
    }

    public Boolean isFlInativo() {
        return flInativo;
    }

    public void setFlInativo(Boolean flInativo) {
        this.flInativo = flInativo;
    }

    public BigDecimal getVlPromocao() {
        return vlPromocao;
    }

    public void setVlPromocao(BigDecimal vlPromocao) {
        this.vlPromocao = vlPromocao;
    }
    
    public Integer getNnTipo() {
        return nnTipo;
    }

    public void setNnTipo(Integer nnTipo) {
        this.nnTipo = nnTipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promocao promocao = (Promocao) o;
        if(promocao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, promocao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Promocao{" +
            "id=" + id +
            ", nmPromocao='" + nmPromocao + "'" +
            ", dtVencimento='" + dtVencimento + "'" +
            ", nnDiaData='" + nnDiaData + "'" +
            ", nnDiaSemana='" + nnDiaSemana + "'" +
            ", flInativo='" + flInativo + "'" +
            ", vlPromocao='" + vlPromocao + "'" +     
            ", nnTipo='" + nnTipo + "'" +
            '}';
    }
}
