package com.sth.gpweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gpweb.domain.Marca;

/**
 * Spring Data JPA repository for the Marca entity.
 */
public interface MarcaRepository extends JpaRepository<Marca,Long> {
    
    //Verify if nmMarca already exits on database before insert	
 	@Query("SELECT f.nmMarca FROM Marca f where f.nmMarca = :nmMarca") 
 	String findNmMarcaExists(@Param("nmMarca") String nmMarca);
	
	Page<Marca> findByNmMarcaStartingWithOrderByNmMarcaAsc(String descricao, Pageable pageable);
	
	@Query(value = "select m from Marca m where cast(m.id as string) like :id || '%'") 
	Page<Marca> findByIdStartingWithOrderByIdAsc(@Param("id") String id, Pageable pageable);
	
	@Query(value = "select m from Marca m order by m.nmMarca")
	Page<Marca> findAllOrderByNmMarca(Pageable pageable);
	
	@Query(value = "select m from Marca m order by m.id")
	Page<Marca> findAllOrderById(Pageable pageable);	
	
}
