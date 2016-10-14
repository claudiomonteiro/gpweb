package com.sth.gpweb.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClassProduto.
 */
@Entity
@Table(name = "class_produto")
@Document(indexName = "classproduto")
public class ClassProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "cd_class_produto", length = 2, nullable = false, unique = true)
    private String cdClassProduto;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nm_class_produto", length = 80, nullable = false, unique = true)
    private String nmClassProduto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCdClassProduto() {
        return cdClassProduto;
    }

    public void setCdClassProduto(String cdClassProduto) {
        this.cdClassProduto = cdClassProduto;
    }

    public String getnmClassProduto() {
        return nmClassProduto;
    }

    public void setnmClassProduto(String nmClassProduto) {
        this.nmClassProduto = nmClassProduto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassProduto classProduto = (ClassProduto) o;
        if(classProduto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, classProduto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClassProduto{" +
            "id=" + id +
            ", cdClassProduto='" + cdClassProduto + "'" +
            ", nmClassProduto='" + nmClassProduto + "'" +
            '}';
    }
}
