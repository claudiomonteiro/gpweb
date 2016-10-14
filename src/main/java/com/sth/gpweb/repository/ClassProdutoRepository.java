package com.sth.gpweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.ClassProduto;

/**
 * Spring Data JPA repository for the ClassProduto entity.
 */
public interface ClassProdutoRepository extends JpaRepository<ClassProduto,Long> {
    
    //Verify if cdClassProduto already exits on database before insert	
 	@Query("SELECT c.cdClassProduto FROM ClassProduto c where c.cdClassProduto = :cdClassProduto") 
 	String findCdClassProdutoExists(@Param("cdClassProduto") String cdClassProduto);
    
    //Verify if nmClassProduto already exits on database before insert	
 	@Query("SELECT c.nmClassProduto FROM ClassProduto c where c.nmClassProduto = :nmClassProduto") 
 	String findnmClassProdutoExists(@Param("nmClassProduto") String nmClassProduto);

	Page<ClassProduto> findByNmClassProdutoStartingWithOrderByNmClassProdutoAsc(String descricao, Pageable pageable);
	
	@Query(value = "select c from ClassProduto c where cast(c.id as string) like :id || '%'") 
	Page<ClassProduto> findByIdStartingWithOrderByIdAsc(@Param("id") String id, Pageable pageable);
	
	@Query(value = "select c from ClassProduto c order by c.nmClassProduto")
	Page<ClassProduto> findAllOrderByNmClassProduto(Pageable pageable);
	
	@Query(value = "select c from ClassProduto c order by c.id")
	Page<ClassProduto> findAllOrderById(Pageable pageable);	
		
}