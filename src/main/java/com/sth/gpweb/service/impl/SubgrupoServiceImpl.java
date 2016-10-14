package com.sth.gpweb.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.domain.Subgrupo;
import com.sth.gpweb.repository.SubgrupoRepository;
import com.sth.gpweb.repository.search.SubgrupoSearchRepository;
import com.sth.gpweb.service.SubgrupoService;

/**
 * Service Implementation for managing Subgrupo.
 */
@Service
@Transactional
public class SubgrupoServiceImpl implements SubgrupoService{

    private final Logger log = LoggerFactory.getLogger(SubgrupoServiceImpl.class);
    
    @Inject
    private SubgrupoRepository subgrupoRepository;
    
    @Inject
    private SubgrupoSearchRepository subgrupoSearchRepository;
    
    /**
     * Save a subgrupo.
     * 
     * @param subgrupo the entity to save
     * @return the persisted entity
     */
    public Subgrupo save(Subgrupo subgrupo) {
        log.debug("Request to save Subgrupo : {}", subgrupo);
        Subgrupo result = subgrupoRepository.save(subgrupo);
        subgrupoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the subgrupos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Subgrupo> findAll(Pageable pageable) {
        log.debug("Request to get all Subgrupos");
        Page<Subgrupo> result = subgrupoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one subgrupo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Subgrupo findOne(Long id) {
        log.debug("Request to get Subgrupo : {}", id);
        Subgrupo subgrupo = subgrupoRepository.findOne(id);
        return subgrupo;
    }

    /**
     *  Delete the  subgrupo by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Subgrupo : {}", id);
        subgrupoRepository.delete(id);
        subgrupoSearchRepository.delete(id);
    }

    /**
     * Search for the subgrupo corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Subgrupo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Subgrupos for query {}", query);
        return subgrupoSearchRepository.search(queryStringQuery(query), pageable);
    }
    
    /**
     * Search for the NmSubgrupo
     *
     *  @param query the NmSubgrupo
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Subgrupo> findByNmSubgrupoStartingWithOrderByNmSubgrupoAsc(String descricao, Pageable pageable){
	    log.debug("Request to...", descricao);
	    return subgrupoRepository.findByNmSubgrupoStartingWithOrderByNmSubgrupoAsc(descricao, pageable);
    }
    
    /**
     * Search for the id
     * Used on product page
     *
     *  @param query the nmSubgrupo
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Subgrupo> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable){
    	log.debug("Request to...", id);
        return subgrupoRepository.findByIdStartingWithOrderByIdAsc(id, pageable);
    }
    
    /**
     *  Get all the Subgrupos ordered by name.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Subgrupo> findAllOrderByNmSubgrupo(Pageable pageable) {
        log.debug("Request to get all Subgrupos ordered by name");
        Page<Subgrupo> result = subgrupoRepository.findAllOrderByNmSubgrupo(pageable); 
        return result;
    }
    
    /**
     *  Get all the Subgrupos ordered by id.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Subgrupo> findAllOrderById(Pageable pageable) {
        log.debug("Request to get all Subgrupos ordered by id");
        Page<Subgrupo> result = subgrupoRepository.findAllOrderById(pageable); 
        return result;
    }
}
