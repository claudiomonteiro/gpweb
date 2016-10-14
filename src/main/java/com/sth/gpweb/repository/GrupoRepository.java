package com.sth.gpweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.Grupo;

/**
 * Spring Data JPA repository for the Grupo entity.
 */
@SuppressWarnings("unused")
public interface GrupoRepository extends JpaRepository<Grupo,Long> {
	
	//Verify if nmGrupo already exist on database before insert	
	@Query("SELECT g.nmGrupo FROM Grupo g where g.nmGrupo = :nmGrupo") 
	String findNmGrupoExists(@Param("nmGrupo") String nmGrupo);
	
	Page<Grupo> findByNmGrupoStartingWithOrderByNmGrupoAsc(String descricao, Pageable pageable);	
	
	@Query(value = "select g from Grupo g where cast(g.id as string) like :id || '%'") 
	Page<Grupo> findByIdStartingWithOrderByIdAsc(@Param("id") String id, Pageable pageable);
	
	@Query(value = "select g from Grupo g order by g.nmGrupo")
	Page<Grupo> findAllOrderByNmGrupo(Pageable pageable);
	
	@Query(value = "select g from Grupo g order by g.id")
	Page<Grupo> findAllOrderById(Pageable pageable);
}
