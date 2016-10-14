package com.sth.gpweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.Produto;

/**
 * Spring Data JPA repository for the Produto entity.
 */
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
	
	//Verify if nmProduto already exists on database before insert	
	@Query("SELECT g.nmProduto FROM Produto g where g.nmProduto = :nmProduto") 
	String findNmProdutoExists(@Param("nmProduto") String nmProduto);
	
	Page<Produto> findByNmProdutoStartingWithOrderByNmProdutoAsc(String descricao, Pageable pageable);
	
	@Query(value = "select p from Produto p where cast(p.id as string) like :id || '%'") 
	Page<Produto> findByIdStartingWithOrderByIdAsc(@Param("id") String id, Pageable pageable);
	
	@Query(value = "select p from Produto p order by p.nmProduto")
	Page<Produto> findAllOrderByNmProduto(Pageable pageable);
	
	@Query(value = "select p from Produto p order by p.id")
	Page<Produto> findAllOrderById(Pageable pageable);
}
