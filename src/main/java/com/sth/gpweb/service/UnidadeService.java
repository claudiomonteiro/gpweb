package com.sth.gpweb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sth.gpweb.domain.Unidade;

/**
 * Service Interface for managing Unidade.
 */
public interface UnidadeService {

    /**
     * Save a unidade.
     * 
     * @param unidade the entity to save
     * @return the persisted entity
     */
    Unidade save(Unidade unidade);

    /**
     *  Get all the unidades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Unidade> findAll(Pageable pageable);

    /**
     *  Get the "id" unidade.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Unidade findOne(Long id);

    /**
     *  Delete the "id" unidade.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the unidade corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Unidade> search(String query, Pageable pageable);
    
    /**
     * Search for the nmUnidade already exists.
     * 
     *  @param query the nmUnidade
     *  @return the list of entities
     */
    String findNmUnidadeExists(String nmUnidade);
    
    /**
     * Search for the sgUnidade already exists.
     * 
     *  @param query the sgUnidade
     *  @return the list of entities
     */
    String findSgUnidadeExists(String sgUnidade);
    
    /**
     * Search for the unidade corresponding to the query name.
     * 
     *  @param description query for the name
     *  @return the list of entities
     */
     Page<Unidade> findByNmUnidadeStartingWithOrderByNmUnidadeAsc(String descricao, Pageable pageable);   
     
     /**
      * Search for the Unidade corresponding to the query name.
      * Used on product page
      * 
      *  @param description query for the name
      *  @return the list of entities
      */
     Page<Unidade> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable);
     
     /**
      *  Get all the Unidades ordered by name.
      *  
      *  @param pageable the pagination information
      *  @return the list of entities
      */
     Page<Unidade> findAllOrderByNmUnidade(Pageable pageable);
     
     /**
      *  Get all the Unidades ordered by id.
      *  
      *  @param pageable the pagination information
      *  @return the list of entities
      */
     Page<Unidade> findAllOrderById(Pageable pageable);	
     
}
