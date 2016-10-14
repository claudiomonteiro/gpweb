package com.sth.gpweb.service;

import com.sth.gpweb.domain.Promocao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Promocao.
 */
public interface PromocaoService {

    /**
     * Save a promocao.
     * 
     * @param promocao the entity to save
     * @return the persisted entity
     */
    Promocao save(Promocao promocao);

    /**
     *  Get all the promocaos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Promocao> findAll(Pageable pageable);

    /**
     *  Get the "id" promocao.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Promocao findOne(Long id);

    /**
     *  Delete the "id" promocao.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the promocao corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Promocao> search(String query, Pageable pageable);
    
    /**
     * Search for the nmPromocao already exists.
     * 
     *  @param query the nmPromocao
     *  @return the list of entities
     */
    String findNmPromocaoExists(String nmPromocao);
}
