package com.sth.gpweb.repository;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.Filial;

/**
 * Spring Data JPA repository for the Filial entity.
 */
public interface FilialRepository extends JpaRepository<Filial,Long> {
    
    //Verify if nmFilial already exists on database before insert	
	@Query("SELECT f.nmFilial FROM Filial f where f.nmFilial = :nmFilial") 
	String findNmFilialExists(@Param("nmFilial") String nmFilial);

	@Query(value = "SELECT f FROM Filial f WHERE f.id not in(:id)")
	ArrayList<Filial> findFiliaisByIdProdutoWhereNotUsed(@Param("id") Collection<Long> ids);
	
	@Query(value = "SELECT f FROM Filial f")
	ArrayList<Filial> findAllFilials();
}
