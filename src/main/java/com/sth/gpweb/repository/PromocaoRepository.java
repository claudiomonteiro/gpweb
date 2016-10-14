package com.sth.gpweb.repository;

import com.sth.gpweb.domain.Promocao;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Promocao entity.
 */
@SuppressWarnings("unused")
public interface PromocaoRepository extends JpaRepository<Promocao,Long> {
    
    //Verify if nmPromocao already exits on database before insert	
  	@Query("SELECT p.nmPromocao FROM Promocao p where p.nmPromocao = :nmPromocao") 
 	String findNmPromocaoExists(@Param("nmPromocao") String nmPromocao);

}
