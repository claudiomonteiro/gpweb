package com.sth.gpweb.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Unidade.
 */
@Entity
@Table(name = "unidade")
@Document(indexName = "unidade")
public class Unidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nm_unidade", length = 25, nullable = false, unique = true)
    private String nmUnidade;

    @Size(max = 2)
    @Column(name = "sg_unidade", length = 2, nullable = false, unique = true)
    private String sgUnidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmUnidade() {
        return nmUnidade;
    }

    public void setNmUnidade(String nmUnidade) {
        this.nmUnidade = nmUnidade;
    }

    public String getSgUnidade() {
        return sgUnidade;
    }

    public void setSgUnidade(String sgUnidade) {
        this.sgUnidade = sgUnidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Unidade unidade = (Unidade) o;
        if(unidade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, unidade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Unidade{" +
            "id=" + id +
            ", nmUnidade='" + nmUnidade + "'" +
            ", sgUnidade='" + sgUnidade + "'" +
            '}';
    }
}
