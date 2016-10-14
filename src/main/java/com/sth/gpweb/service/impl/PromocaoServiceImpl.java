package com.sth.gpweb.service.impl;

import com.sth.gpweb.service.PromocaoService;
import com.sth.gpweb.domain.Promocao;
import com.sth.gpweb.repository.PromocaoRepository;
import com.sth.gpweb.repository.search.PromocaoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Promocao.
 */
@Service
@Transactional
public class PromocaoServiceImpl implements PromocaoService{

    private final Logger log = LoggerFactory.getLogger(PromocaoServiceImpl.class);
    
    @Inject
    private PromocaoRepository promocaoRepository;
    
    @Inject
    private PromocaoSearchRepository promocaoSearchRepository;
    
    /**
     * Save a promocao.
     * 
     * @param promocao the entity to save
     * @return the persisted entity
     */
    public Promocao save(Promocao promocao) {
        log.debug("Request to save Promocao : {}", promocao);
        Promocao result = promocaoRepository.save(promocao);
        promocaoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the promocaos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Promocao> findAll(Pageable pageable) {
        log.debug("Request to get all Promocaos");
        Page<Promocao> result = promocaoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one promocao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Promocao findOne(Long id) {
        log.debug("Request to get Promocao : {}", id);
        Promocao promocao = promocaoRepository.findOne(id);
        return promocao;
    }

    /**
     *  Delete the  promocao by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Promocao : {}", id);
        promocaoRepository.delete(id);
        promocaoSearchRepository.delete(id);
    }

    /**
     * Search for the promocao corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Promocao> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Promocaos for query {}", query);
        return promocaoSearchRepository.search(queryStringQuery(query), pageable);
    }
    
    /**
     * Search for the nmPromocao already exists.
     *
     *  @param query the nmPromocao
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public String findNmPromocaoExists(String nmPromocao){
    	log.debug("Request to search if the nmPromocao: {} already exists", nmPromocao);
        return promocaoRepository.findNmPromocaoExists(nmPromocao);
    }
}
