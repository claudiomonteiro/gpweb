package com.sth.gpweb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ProdutoFilial.
 */
@Entity
@Table(name = "produto_filial")
@Document(indexName = "produtofilial")
public class ProdutoFilial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qt_embalagem", precision=18, scale=4)
    private BigDecimal qtEmbalagem;

    @Column(name = "qt_min", precision=18, scale=4)
    private BigDecimal qtMin;

    @Column(name = "qt_max", precision=18, scale=4)
    private BigDecimal qtMax;

    @Column(name = "qt_saldo", precision=18, scale=4)
    private BigDecimal qtSaldo;

    @Column(name = "dt_vencimento")
    private LocalDate dtVencimento;

    @Column(name = "vl_compra", precision=18, scale=6)
    private BigDecimal vlCompra;

    @Column(name = "vl_custo", precision=18, scale=6)
    private BigDecimal vlCusto;

    @Column(name = "vl_custo_medio", precision=18, scale=6)
    private BigDecimal vlCustoMedio;

    @Column(name = "vl_markp_venda", precision=18, scale=6)
    private BigDecimal vlMarkpVenda;

    @Column(name = "vl_markp_atacado", precision=18, scale=6)
    private BigDecimal vlMarkpAtacado;

    @Column(name = "vl_ata_vista", precision=18, scale=6)
    private BigDecimal vlAtaVista;

    @Column(name = "vl_markp_aprazo", precision=18, scale=6)
    private BigDecimal vlMarkpAprazo;

    @Column(name = "vl_aprazo", precision=18, scale=6)
    private BigDecimal vlAprazo;

    @Column(name = "vl_markp_ata_prazo", precision=18, scale=6)
    private BigDecimal vlMarkpAtaPrazo;

    @Column(name = "vl_ata_a_prazo", precision=18, scale=6)
    private BigDecimal vlAtaAPrazo;

    @Column(name = "qt_atacado", precision=18, scale=4)
    private BigDecimal qtAtacado;

    @Column(name = "qt_bonificacao", precision=18, scale=4)
    private BigDecimal qtBonificacao;

    @Column(name = "fl_inativo")
    private Boolean flInativo;

    @Column(name = "fl_materia_prima")
    private Boolean flMateriaPrima;

    @Column(name = "fl_composto")
    private Boolean flComposto;

    @Column(name = "nn_pontos")
    private Integer nnPontos;

    @OneToOne
    private Produto produto;     
    
    @OneToOne
    private Filial filial;

	@OneToOne
    private Promocao promocao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

    public BigDecimal getQtEmbalagem() {
        return qtEmbalagem;
    }

    public void setQtEmbalagem(BigDecimal qtEmbalagem) {
        this.qtEmbalagem = qtEmbalagem;
    }

    public BigDecimal getQtMin() {
        return qtMin;
    }

    public void setQtMin(BigDecimal qtMin) {
        this.qtMin = qtMin;
    }

    public BigDecimal getQtMax() {
        return qtMax;
    }

    public void setQtMax(BigDecimal qtMax) {
        this.qtMax = qtMax;
    }

    public BigDecimal getQtSaldo() {
        return qtSaldo;
    }

    public void setQtSaldo(BigDecimal qtSaldo) {
        this.qtSaldo = qtSaldo;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public BigDecimal getVlCompra() {
        return vlCompra;
    }

    public void setVlCompra(BigDecimal vlCompra) {
        this.vlCompra = vlCompra;
    }

    public BigDecimal getVlCusto() {
        return vlCusto;
    }

    public void setVlCusto(BigDecimal vlCusto) {
        this.vlCusto = vlCusto;
    }

    public BigDecimal getVlCustoMedio() {
        return vlCustoMedio;
    }

    public void setVlCustoMedio(BigDecimal vlCustoMedio) {
        this.vlCustoMedio = vlCustoMedio;
    }

    public BigDecimal getVlMarkpVenda() {
        return vlMarkpVenda;
    }

    public void setVlMarkpVenda(BigDecimal vlMarkpVenda) {
        this.vlMarkpVenda = vlMarkpVenda;
    }

    public BigDecimal getVlMarkpAtacado() {
        return vlMarkpAtacado;
    }

    public void setVlMarkpAtacado(BigDecimal vlMarkpAtacado) {
        this.vlMarkpAtacado = vlMarkpAtacado;
    }

    public BigDecimal getVlAtaVista() {
        return vlAtaVista;
    }

    public void setVlAtaVista(BigDecimal vlAtaVista) {
        this.vlAtaVista = vlAtaVista;
    }

    public BigDecimal getVlMarkpAprazo() {
        return vlMarkpAprazo;
    }

    public void setVlMarkpAprazo(BigDecimal vlMarkpAprazo) {
        this.vlMarkpAprazo = vlMarkpAprazo;
    }

    public BigDecimal getVlAprazo() {
        return vlAprazo;
    }

    public void setVlAprazo(BigDecimal vlAprazo) {
        this.vlAprazo = vlAprazo;
    }

    public BigDecimal getVlMarkpAtaPrazo() {
        return vlMarkpAtaPrazo;
    }

    public void setVlMarkpAtaPrazo(BigDecimal vlMarkpAtaPrazo) {
        this.vlMarkpAtaPrazo = vlMarkpAtaPrazo;
    }

    public BigDecimal getVlAtaAPrazo() {
        return vlAtaAPrazo;
    }

    public void setVlAtaAPrazo(BigDecimal vlAtaAPrazo) {
        this.vlAtaAPrazo = vlAtaAPrazo;
    }

    public BigDecimal getQtAtacado() {
        return qtAtacado;
    }

    public void setQtAtacado(BigDecimal qtAtacado) {
        this.qtAtacado = qtAtacado;
    }

    public BigDecimal getQtBonificacao() {
        return qtBonificacao;
    }

    public void setQtBonificacao(BigDecimal qtBonificacao) {
        this.qtBonificacao = qtBonificacao;
    }

    public Boolean isFlInativo() {
        return flInativo;
    }

    public void setFlInativo(Boolean flInativo) {
        this.flInativo = flInativo;
    }

    public Boolean isFlMateriaPrima() {
        return flMateriaPrima;
    }

    public void setFlMateriaPrima(Boolean flMateriaPrima) {
        this.flMateriaPrima = flMateriaPrima;
    }

    public Boolean isFlComposto() {
        return flComposto;
    }

    public void setFlComposto(Boolean flComposto) {
        this.flComposto = flComposto;
    }

    public Integer getNnPontos() {
        return nnPontos;
    }

    public void setNnPontos(Integer nnPontos) {
        this.nnPontos = nnPontos;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProdutoFilial produtoFilial = (ProdutoFilial) o;
        if(produtoFilial.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, produtoFilial.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProdutoFilial{" +
            "id=" + id +
            ", qtEmbalagem='" + qtEmbalagem + "'" +
            ", qtMin='" + qtMin + "'" +
            ", qtMax='" + qtMax + "'" +
            ", qtSaldo='" + qtSaldo + "'" +
            ", dtVencimento='" + dtVencimento + "'" +
            ", vlCompra='" + vlCompra + "'" +
            ", vlCusto='" + vlCusto + "'" +
            ", vlCustoMedio='" + vlCustoMedio + "'" +
            ", vlMarkpVenda='" + vlMarkpVenda + "'" +
            ", vlMarkpAtacado='" + vlMarkpAtacado + "'" +
            ", vlAtaVista='" + vlAtaVista + "'" +
            ", vlMarkpAprazo='" + vlMarkpAprazo + "'" +
            ", vlAprazo='" + vlAprazo + "'" +
            ", vlMarkpAtaPrazo='" + vlMarkpAtaPrazo + "'" +
            ", vlAtaAPrazo='" + vlAtaAPrazo + "'" +
            ", qtAtacado='" + qtAtacado + "'" +
            ", qtBonificacao='" + qtBonificacao + "'" +
            ", flInativo='" + flInativo + "'" +
            ", flMateriaPrima='" + flMateriaPrima + "'" +
            ", flComposto='" + flComposto + "'" +
            ", nnPontos='" + nnPontos + "'" +
            '}';
    }
}
