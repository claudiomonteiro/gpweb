package com.sth.gpweb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sth.gpweb.domain.Grupo;
import com.sth.gpweb.domain.Subgrupo;

/**
 * Service Interface for managing Subgrupo.
 */
public interface SubgrupoService {

    /**
     * Save a subgrupo.
     * 
     * @param subgrupo the entity to save
     * @return the persisted entity
     */
    Subgrupo save(Subgrupo subgrupo);

    /**
     *  Get all the subgrupos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Subgrupo> findAll(Pageable pageable);

    /**
     *  Get the "id" subgrupo.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Subgrupo findOne(Long id);

    /**
     *  Delete the "id" subgrupo.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the subgrupo corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Subgrupo> search(String query, Pageable pageable);
    
    /**
     * Search for the subgrupo corresponding to the query name.
     * 
     *  @param description query for the name
     *  @return the list of entities
     */
     Page<Subgrupo> findByNmSubgrupoStartingWithOrderByNmSubgrupoAsc(String descricao, Pageable pageable);
     
     /**
      * Search for the Subgrupo corresponding to the query name.
      * Used on product page
      * 
      *  @param description query for the name
      *  @return the list of entities
      */
     Page<Subgrupo> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable);
     
     /**
      *  Get all the Subgrupos ordered by name.
      *  
      *  @param pageable the pagination information
      *  @return the list of entities
      */
     Page<Subgrupo> findAllOrderByNmSubgrupo(Pageable pageable);
     
     /**
      *  Get all the Subgrupos ordered by id.
      *  
      *  @param pageable the pagination information
      *  @return the list of entities
      */
     Page<Subgrupo> findAllOrderById(Pageable pageable);	
}
