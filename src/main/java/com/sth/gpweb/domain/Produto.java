package com.sth.gpweb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Document(indexName = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cd_produto")
    private Long cdProduto;

    @Size(max = 25)
    @Column(name = "cd_barras", length = 25)
    private String cdBarras;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nm_produto", length = 50, nullable = false, unique = true)
    private String nmProduto;

    @Size(max = 8)
    @Column(name = "cd_ncm", length = 8)
    private String cdNcm;

    @Size(max = 20)
    @Column(name = "cd_gtin", length = 20)
    private String cdGtin;

    @Size(max = 9)
    @Column(name = "cd_anp", length = 9)
    private String cdAnp;

    @Size(max = 30)
    @Column(name = "ds_anp", length = 30)
    private String dsAnp;

    @Size(max = 25)
    @Column(name = "cd_conta_contabil", length = 25)
    private String cdContaContabil;

    @Column(name = "fl_balanca")
    private Boolean flBalanca;

    @Column(name = "fl_inativo")
    private Boolean flInativo;

    @Column(name = "fl_sngpc")
    private Boolean flSngpc;

    @Column(name = "fl_uso_prolongado")
    private Boolean flUsoProlongado;

    @Column(name = "vl_Venda", precision=10, scale=2)
    private BigDecimal vlVenda;

    @Column(name = "vl_estoque", precision=10, scale=2)
    private BigDecimal vlEstoque;

    @Size(max = 200)
    @Column(name = "ds_informacoes", length = 200)
    private String dsInformacoes;

    @Lob
    @Column(name = "bl_imagem")
    private byte[] blImagem;

    @Column(name = "bl_imagem_content_type")
    private String blImagemContentType;

    @OneToOne
    private Grupo grupo;

    @OneToOne
    private Marca marca;

    @OneToOne
    private Unidade unidade;

    @OneToOne
    private ClassProduto classProduto;
    
    @OneToOne
    private Subgrupo subgrupo;
    
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private List<Filial> filials;
    
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private List<Filial> filialsNotUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
	}   

    public List<Filial> getFilials() {
        return filials;
    }

    public List<Filial> getFilialsNotUsed() {
		return filialsNotUsed;
	}

	public void setFilialsNotUsed(List<Filial> filialsNotUsed) {
		this.filialsNotUsed = filialsNotUsed;
	}

	public void setFilials(List<Filial> filials) {
        this.filials = filials;
    }

	public Boolean getFlBalanca() {
		return flBalanca;
	}

	public Boolean getFlInativo() {
		return flInativo;
	}

	public Boolean getFlSngpc() {
		return flSngpc;
	}

	public Boolean getFlUsoProlongado() {
		return flUsoProlongado;
	}

	public Long getCdProduto() {
        return cdProduto;
    }

    public void setCdProduto(Long cdProduto) {
        this.cdProduto = cdProduto;
    }

    public String getCdBarras() {
        return cdBarras;
    }

    public void setCdBarras(String cdBarras) {
        this.cdBarras = cdBarras;
    }

    public String getNmProduto() {
        return nmProduto;
    }

    public void setNmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
    }

    public String getCdNcm() {
        return cdNcm;
    }

    public void setCdNcm(String cdNcm) {
        this.cdNcm = cdNcm;
    }

    public String getCdGtin() {
        return cdGtin;
    }

    public void setCdGtin(String cdGtin) {
        this.cdGtin = cdGtin;
    }

    public String getCdAnp() {
        return cdAnp;
    }

    public void setCdAnp(String cdAnp) {
        this.cdAnp = cdAnp;
    }

    public String getDsAnp() {
        return dsAnp;
    }

    public void setDsAnp(String dsAnp) {
        this.dsAnp = dsAnp;
    }

    public String getCdContaContabil() {
        return cdContaContabil;
    }

    public void setCdContaContabil(String cdContaContabil) {
        this.cdContaContabil = cdContaContabil;
    }

    public Boolean isFlBalanca() {
        return flBalanca;
    }

    public void setFlBalanca(Boolean flBalanca) {
        this.flBalanca = flBalanca;
    }

    public Boolean isFlInativo() {
        return flInativo;
    }

    public void setFlInativo(Boolean flInativo) {
        this.flInativo = flInativo;
    }

    public Boolean isFlSngpc() {
        return flSngpc;
    }

    public void setFlSngpc(Boolean flSngpc) {
        this.flSngpc = flSngpc;
    }

    public Boolean isFlUsoProlongado() {
        return flUsoProlongado;
    }

    public void setFlUsoProlongado(Boolean flUsoProlongado) {
        this.flUsoProlongado = flUsoProlongado;
    }

    public BigDecimal getVlVenda() {
        return vlVenda;
    }

    public void setVlVenda(BigDecimal vlVenda) {
        this.vlVenda = vlVenda;
    }

    public BigDecimal getVlEstoque() {
        return vlEstoque;
    }

    public void setVlEstoque(BigDecimal vlEstoque) {
        this.vlEstoque = vlEstoque;
    }

    public String getDsInformacoes() {
        return dsInformacoes;
    }

    public void setDsInformacoes(String dsInformacoes) {
        this.dsInformacoes = dsInformacoes;
    }

    public byte[] getBlImagem() {
        return blImagem;
    }

    public void setBlImagem(byte[] blImagem) {
        this.blImagem = blImagem;
    }

    public String getBlImagemContentType() {
        return blImagemContentType;
    }

    public void setBlImagemContentType(String blImagemContentType) {
        this.blImagemContentType = blImagemContentType;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public ClassProduto getClassProduto() {
        return classProduto;
    }

    public void setClassProduto(ClassProduto classProduto) {
        this.classProduto = classProduto;
    }
    
    public Subgrupo getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(Subgrupo subgrupo) {
        this.subgrupo = subgrupo;
    }    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Produto produto = (Produto) o;
        if(produto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "Produto [id=" + id + ", cdProduto=" + cdProduto + ", cdBarras=" + cdBarras + ", nmProduto=" + nmProduto
				+ ", cdNcm=" + cdNcm + ", cdGtin=" + cdGtin + ", cdAnp=" + cdAnp + ", dsAnp=" + dsAnp
				+ ", cdContaContabil=" + cdContaContabil + ", flBalanca=" + flBalanca + ", flInativo=" + flInativo
				+ ", flSngpc=" + flSngpc + ", flUsoProlongado=" + flUsoProlongado + ", vlVenda=" + vlVenda
				+ ", vlEstoque=" + vlEstoque + ", dsInformacoes=" + dsInformacoes + ", blImagem="
				+ Arrays.toString(blImagem) + ", blImagemContentType=" + blImagemContentType + ", grupo=" + grupo
				+ ", marca=" + marca + ", unidade=" + unidade + ", classProduto=" + classProduto + ", subgrupo="
				+ subgrupo + ", filials=" + filials + "]";
	}    
}
