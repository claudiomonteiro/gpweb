package com.sth.gpweb.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.domain.Unidade;
import com.sth.gpweb.repository.UnidadeRepository;
import com.sth.gpweb.repository.search.UnidadeSearchRepository;
import com.sth.gpweb.service.UnidadeService;

/**
 * Service Implementation for managing Unidade.
 */
@Service
@Transactional
public class UnidadeServiceImpl implements UnidadeService{

    private final Logger log = LoggerFactory.getLogger(UnidadeServiceImpl.class);
    
    @Inject
    private UnidadeRepository unidadeRepository;
    
    @Inject
    private UnidadeSearchRepository unidadeSearchRepository;
    
    /**
     * Save a unidade.
     * 
     * @param unidade the entity to save
     * @return the persisted entity
     */
    public Unidade save(Unidade unidade) {
        log.debug("Request to save Unidade : {}", unidade);
        Unidade result = unidadeRepository.save(unidade);
        unidadeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the unidades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Unidade> findAll(Pageable pageable) {
        log.debug("Request to get all Unidades");
        Page<Unidade> result = unidadeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one unidade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Unidade findOne(Long id) {
        log.debug("Request to get Unidade : {}", id);
        Unidade unidade = unidadeRepository.findOne(id);
        return unidade;
    }

    /**
     *  Delete the  unidade by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Unidade : {}", id);
        unidadeRepository.delete(id);
        unidadeSearchRepository.delete(id);
    }

    /**
     * Search for the unidade corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Unidade> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Unidades for query {}", query);
        return unidadeSearchRepository.search(queryStringQuery(query), pageable);
    }
    
    /**
     * Search for the nmUnidade already exists.
     *
     *  @param query the nmUnidade
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public String findNmUnidadeExists(String nmUnidade){
    	log.debug("Request to search if the nmUnidade: {} already exists", nmUnidade);
        return unidadeRepository.findNmUnidadeExists(nmUnidade);
    }
    
    /**
     * Search for the sgUnidade already exists.
     *
     *  @param query the sgUnidade
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public String findSgUnidadeExists(String sgUnidade){
    	log.debug("Request to search if the sgUnidade: {} already exists", sgUnidade);
        return unidadeRepository.findSgUnidadeExists(sgUnidade);
    }
    
    /**
     * Search for the nmUnidade
     *
     *  @param query the nmMarca
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Unidade> findByNmUnidadeStartingWithOrderByNmUnidadeAsc(String descricao, Pageable pageable){
	    log.debug("Request to...", descricao);
	    return unidadeRepository.findByNmUnidadeStartingWithOrderByNmUnidadeAsc(descricao, pageable);
    }
    
    /**
     * Search for the id
     * Used on product page
     *
     *  @param query the nmUnidade
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Unidade> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable){
    	log.debug("Request to...", id);
        return unidadeRepository.findByIdStartingWithOrderByIdAsc(id, pageable);
    }
    
    /**
     *  Get all the Unidades ordered by name.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Unidade> findAllOrderByNmUnidade(Pageable pageable) {
        log.debug("Request to get all Unidades ordered by name");
        Page<Unidade> result = unidadeRepository.findAllOrderByNmUnidade(pageable); 
        return result;
    }
    
    /**
     *  Get all the Unidades ordered by id.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Unidade> findAllOrderById(Pageable pageable) {
        log.debug("Request to get all Unidades ordered by id");
        Page<Unidade> result = unidadeRepository.findAllOrderById(pageable); 
        return result;
    }
}
