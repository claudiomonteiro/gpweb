package com.sth.gpweb.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Filial.
 */
@Entity
@Table(name = "filial")
@Document(indexName = "filial")
public class Filial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nm_filial", length = 60, nullable = false, unique = true)
    private String nmFilial;

    @Column(name = "nn_numero")
    private Integer nnNumero;

    @Size(max = 20)
    @Column(name = "ds_complemento", length = 20)
    private String dsComplemento;

    @Size(max = 14)
    @Column(name = "cd_cnpj", length = 14)
    private String cdCnpj;

    @Size(max = 18)
    @Column(name = "cd_ie", length = 18)
    private String cdIe;
    
    @Size(max = 30)
    @Column(name = "ds_site", length = 30)
    private String dsSite;

    @Column(name = "fl_inativo")
    private Boolean flInativo;

    @Size(max = 150)
    @Column(name = "nm_razao", length = 150)
    private String nmRazao;

    @Size(max = 13)
    @Column(name = "cd_tel", length = 13)
    private String cdTel;

    @Size(max = 13)
    @Column(name = "cd_tel_1", length = 13)
    private String cdTel1;

    @Size(max = 13)
    @Column(name = "cd_tel_2", length = 13)
    private String cdTel2;

    @Size(max = 13)
    @Column(name = "cd_fax", length = 13)
    private String cdFax;

    @Column(name = "dt_operacao")
    private LocalDate dtOperacao;

    @Column(name = "fl_tprec")
    private Boolean flTprec;

    @Size(max = 2)
    @Column(name = "ds_pis_cofins", length = 2)
    private String dsPisCofins;
    
    @Size(max = 100)
    @Column(name = "ds_email", length = 100)
    private String dsEmail;

    @Column(name = "fl_envia_email")
    private Boolean flEnviaEmail;
    
    @Column(name = "fl_matriz")
    private Boolean flMatriz;

    @Size(max = 200)
    @Column(name = "ds_obs", length = 200)
    private String dsObs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmFilial() {
        return nmFilial;
    }

    public void setNmFilial(String nmFilial) {
        this.nmFilial = nmFilial;
    }

    public Integer getNnNumero() {
        return nnNumero;
    }

    public void setNnNumero(Integer nnNumero) {
        this.nnNumero = nnNumero;
    }

    public String getDsComplemento() {
        return dsComplemento;
    }

    public void setDsComplemento(String dsComplemento) {
        this.dsComplemento = dsComplemento;
    }

    public String getCdCnpj() {
        return cdCnpj;
    }

    public void setCdCnpj(String cdCnpj) {
        this.cdCnpj = cdCnpj;
    }

    public String getCdIe() {
        return cdIe;
    }

    public void setCdIe(String cdIe) {
        this.cdIe = cdIe;
    }
    
    public String getDsSite() {
        return dsSite;
    }

    public void setDsSite(String dsSite) {
        this.dsSite = dsSite;
    }

    public Boolean isFlInativo() {
        return flInativo;
    }

    public void setFlInativo(Boolean flInativo) {
        this.flInativo = flInativo;
    }

    public String getNmRazao() {
        return nmRazao;
    }

    public void setNmRazao(String nmRazao) {
        this.nmRazao = nmRazao;
    }

    public String getCdTel() {
        return cdTel;
    }

    public void setCdTel(String cdTel) {
        this.cdTel = cdTel;
    }

    public String getCdTel1() {
        return cdTel1;
    }

    public void setCdTel1(String cdTel1) {
        this.cdTel1 = cdTel1;
    }

    public String getCdTel2() {
        return cdTel2;
    }

    public void setCdTel2(String cdTel2) {
        this.cdTel2 = cdTel2;
    }

    public String getCdFax() {
        return cdFax;
    }

    public void setCdFax(String cdFax) {
        this.cdFax = cdFax;
    }

    public LocalDate getDtOperacao() {
        return dtOperacao;
    }

    public void setDtOperacao(LocalDate dtOperacao) {
        this.dtOperacao = dtOperacao;
    }

    public Boolean isFlTprec() {
        return flTprec;
    }

    public void setFlTprec(Boolean flTprec) {
        this.flTprec = flTprec;
    }

    public String getDsPisCofins() {
        return dsPisCofins;
    }

    public void setDsPisCofins(String dsPisCofins) {
        this.dsPisCofins = dsPisCofins;
    }
    
    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public Boolean isFlEnviaEmail() {
        return flEnviaEmail;
    }

    public void setFlEnviaEmail(Boolean flEnviaEmail) {
        this.flEnviaEmail = flEnviaEmail;
    }
    
    public Boolean isFlMatriz() {
        return flMatriz;
    }

    public void setFlMatriz(Boolean flMatriz) {
        this.flMatriz = flMatriz;
    }

    public String getDsObs() {
        return dsObs;
    }

    public void setDsObs(String dsObs) {
        this.dsObs = dsObs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Filial filial = (Filial) o;
        if(filial.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, filial.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Filial{" +
            "id=" + id +
            ", nmFilial='" + nmFilial + "'" +
            ", nnNumero='" + nnNumero + "'" +
            ", dsComplemento='" + dsComplemento + "'" +
            ", cdCnpj='" + cdCnpj + "'" +
            ", cdIe='" + cdIe + "'" +            
            ", dsSite='" + dsSite + "'" +
            ", flInativo='" + flInativo + "'" +
            ", nmRazao='" + nmRazao + "'" +
            ", cdTel='" + cdTel + "'" +
            ", cdTel1='" + cdTel1 + "'" +
            ", cdTel2='" + cdTel2 + "'" +
            ", cdFax='" + cdFax + "'" +
            ", dtOperacao='" + dtOperacao + "'" +
            ", flTprec='" + flTprec + "'" +
            ", dsPisCofins='" + dsPisCofins + "'" +
            ", dsEmail='" + dsEmail + "'" +
            ", flEnviaEmail='" + flEnviaEmail + "'" +
            ", flMatriz='" + flMatriz + "'" +
            ", dsObs='" + dsObs + "'" +
            '}';
    }
}
