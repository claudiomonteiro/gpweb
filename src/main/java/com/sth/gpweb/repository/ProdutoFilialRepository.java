package com.sth.gpweb.repository;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.Filial;
import com.sth.gpweb.domain.ProdutoFilial;

/**
 * Spring Data JPA repository for the ProdutoFilial entity.
 */
public interface ProdutoFilialRepository extends JpaRepository<ProdutoFilial,Long> {
	
	@Modifying
	@Query(value = "delete from ProdutoFilial pf where pf.produto.id = :id")
	void deleteWhereProdutoId(@Param("id") Long id);	
	
	@Query(value = "SELECT pf FROM ProdutoFilial pf where pf.produto.id = :idProduto")
	Set<ProdutoFilial> findProdutoIdExists(@Param("idProduto") Long idProduto);

	@Query(value = "select COUNT(pf) from ProdutoFilial pf where pf.produto.id = :idProduto and pf.filial.id = :idFilial")
	Long findFilialIdExists(@Param("idProduto") Long idProduto, @Param("idFilial") Long idFilial);
	
	@Query(value = "SELECT pf.filial FROM ProdutoFilial pf where pf.produto.id = :idProduto")
	ArrayList<Filial> findFiliaisByIdProduto(@Param("idProduto") Long idProduto);
}