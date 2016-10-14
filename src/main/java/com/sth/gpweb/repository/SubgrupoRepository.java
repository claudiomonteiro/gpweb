package com.sth.gpweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.Subgrupo;

/**
 * Spring Data JPA repository for the Subgrupo entity.
 */
public interface SubgrupoRepository extends JpaRepository<Subgrupo,Long> {
		
	Page<Subgrupo> findByNmSubgrupoStartingWithOrderByNmSubgrupoAsc(String descricao, Pageable pageable);
	
	@Query(value = "select s from Subgrupo s where cast(s.id as string) like :id || '%'") 
	Page<Subgrupo> findByIdStartingWithOrderByIdAsc(@Param("id") String id, Pageable pageable);
	
	@Query(value = "select s from Subgrupo s order by s.nmSubgrupo")
	Page<Subgrupo> findAllOrderByNmSubgrupo(Pageable pageable);
	
	@Query(value = "select s from Subgrupo s order by s.id")
	Page<Subgrupo> findAllOrderById(Pageable pageable);	
}