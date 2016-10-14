package com.sth.gpweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.Unidade;

/**
 * Spring Data JPA repository for the Unidade entity.
 */
public interface UnidadeRepository extends JpaRepository<Unidade,Long> {

	//Verify if nmUnidade already exits on database before insert	
	@Query("SELECT u.nmUnidade FROM Unidade u where u.nmUnidade = :nmUnidade") 
	String findNmUnidadeExists(@Param("nmUnidade") String nmUnidade);
    
    //Verify if sgUnidade already exits on database before insert	
	@Query("SELECT u.sgUnidade FROM Unidade u where u.sgUnidade = :sgUnidade") 
	String findSgUnidadeExists(@Param("sgUnidade") String sgUnidade);

	Page<Unidade> findByNmUnidadeStartingWithOrderByNmUnidadeAsc(String descricao, Pageable pageable);
	
	@Query(value = "select u from Unidade u where cast(u.id as string) like :id || '%'") 
	Page<Unidade> findByIdStartingWithOrderByIdAsc(@Param("id") String id, Pageable pageable);
	
	@Query(value = "select u from Unidade u order by u.nmUnidade")
	Page<Unidade> findAllOrderByNmUnidade(Pageable pageable);
	
	@Query(value = "select u from Unidade u order by u.id")
	Page<Unidade> findAllOrderById(Pageable pageable);	
	
}
