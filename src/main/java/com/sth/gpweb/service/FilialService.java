package com.sth.gpweb.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sth.gpweb.domain.Filial;

/**
 * Service Interface for managing Filial.
 */
public interface FilialService {

    /**
     * Save a filial.
     * 
     * @param filial the entity to save
     * @return the persisted entity
     */
    Filial save(Filial filial);

    /**
     *  Get all the filials.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Filial> findAll(Pageable pageable);

    /**
     *  Get the "id" filial.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Filial findOne(Long id);

    /**
     *  Delete the "id" filial.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the filial corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Filial> search(String query, Pageable pageable);
    
    /**
     * Search for the nmFilial already exists.
     * 
     *  @param query the nmFilial
     *  @return the list of entities
     */
    String findNmFilialExists(String nmFilial);
    
	/**
	 * Get all the entities not in produto_id.
	 * 
	 * @param produto_id
	 * @return the list of entities
	 */
	ArrayList<Filial> findFiliaisByIdProdutoWhereNotUsed(Collection<Long> ids);
	
	/**
	 * Get all the Filials as list
	 *
	 * @return the list of entities
	 */
	ArrayList<Filial> findAllFilials();	
	
}
