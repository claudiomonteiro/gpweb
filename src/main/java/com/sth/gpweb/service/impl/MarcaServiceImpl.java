package com.sth.gpweb.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.domain.Marca;
import com.sth.gpweb.repository.MarcaRepository;
import com.sth.gpweb.repository.search.MarcaSearchRepository;
import com.sth.gpweb.service.MarcaService;

/**
 * Service Implementation for managing Marca.
 */
@Service
@Transactional
public class MarcaServiceImpl implements MarcaService{

    private final Logger log = LoggerFactory.getLogger(MarcaServiceImpl.class);
    
    @Inject
    private MarcaRepository marcaRepository;
    
    @Inject
    private MarcaSearchRepository marcaSearchRepository;
    
    /**
     * Save a marca.
     * 
     * @param marca the entity to save
     * @return the persisted entity
     */
    public Marca save(Marca marca) {
        log.debug("Request to save Marca : {}", marca);
        Marca result = marcaRepository.save(marca);
        marcaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the marcas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Marca> findAll(Pageable pageable) {
        log.debug("Request to get all Marcas");
        Page<Marca> result = marcaRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one marca by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Marca findOne(Long id) {
        log.debug("Request to get Marca : {}", id);
        Marca marca = marcaRepository.findOne(id);
        return marca;
    }

    /**
     *  Delete the  marca by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Marca : {}", id);
        marcaRepository.delete(id);
        marcaSearchRepository.delete(id);
    }

    /**
     * Search for the marca corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Marca> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Marcas for query {}", query);
        return marcaSearchRepository.search(queryStringQuery(query), pageable);
    }
    
    /**
     * Search for the nmMarca already exists.
     *
     *  @param query the nmMarca
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public String findNmMarcaExists(String nmMarca){
    	log.debug("Request to search if the nmMarca: {} already exists", nmMarca);
        return marcaRepository.findNmMarcaExists(nmMarca);
    }
    
    /**
     * Search for the nmMarca
     *
     *  @param query the nmMarca
     *  @return the list of entities
     */
     @Transactional(readOnly = true)
     public Page<Marca> findByNmMarcaStartingWithOrderByNmMarcaAsc(String descricao, Pageable pageable){
    	log.debug("Request to...", descricao);
        return marcaRepository.findByNmMarcaStartingWithOrderByNmMarcaAsc(descricao, pageable);
     }
     
     /**
      * Search for the id
      * Used on marca page
      *
      *  @param query the nmMarca
      *  @return the list of entities
      */
     @Transactional(readOnly = true)
     public Page<Marca> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable){
     	log.debug("Request to...", id);
         return marcaRepository.findByIdStartingWithOrderByIdAsc(id, pageable);
     }
     
     /**
      *  Get all the Marcas ordered by name.
      *  
      *  @param pageable the pagination information
      *  @return the list of entities
      */
     @Transactional(readOnly = true) 
     public Page<Marca> findAllOrderByNmMarca(Pageable pageable) {
         log.debug("Request to get all Marcas ordered by name");
         Page<Marca> result = marcaRepository.findAllOrderByNmMarca(pageable); 
         return result;
     }
     
     /**
      *  Get all the Marcas ordered by id.
      *  
      *  @param pageable the pagination information
      *  @return the list of entities
      */
     @Transactional(readOnly = true) 
     public Page<Marca> findAllOrderById(Pageable pageable) {
         log.debug("Request to get all Marcas ordered by id");
         Page<Marca> result = marcaRepository.findAllOrderById(pageable); 
         return result;
     }     
}
