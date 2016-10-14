package com.sth.gpweb.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.domain.Grupo;
import com.sth.gpweb.repository.GrupoRepository;
import com.sth.gpweb.repository.search.GrupoSearchRepository;
import com.sth.gpweb.service.GrupoService;

/**
 * Service Implementation for managing Grupo.
 */
@Service
@Transactional
public class GrupoServiceImpl implements GrupoService{

    private final Logger log = LoggerFactory.getLogger(GrupoServiceImpl.class);
    
    @Inject
    private GrupoRepository grupoRepository;
    
    @Inject
    private GrupoSearchRepository grupoSearchRepository;
    
    /**
     * Save a grupo.
     * 
     * @param grupo the entity to save
     * @return the persisted entity
     */
    public Grupo save(Grupo grupo) {
        log.debug("Request to save Grupo : {}", grupo);
        Grupo result = grupoRepository.save(grupo);
        grupoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the grupos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Grupo> findAll(Pageable pageable) {
        log.debug("Request to get all Grupos");
        Page<Grupo> result = grupoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one grupo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Grupo findOne(Long id) {
        log.debug("Request to get Grupo : {}", id);
        Grupo grupo = grupoRepository.findOne(id);
        return grupo;
    }

    /**
     *  Delete the  grupo by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Grupo : {}", id);
        grupoRepository.delete(id);
        grupoSearchRepository.delete(id);
    }

    /**
     * Search for the grupo corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Grupo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Grupos for query {}", query);
        return grupoSearchRepository.search(queryStringQuery(query), pageable);
    }
    
    /**
     * Search for the nmGrupo already exists.
     *
     *  @param query the nmGrupo
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public String findNmGrupoExists(String nmGrupo){
    	log.debug("Request to search if the nmGrupo: {} already exists", nmGrupo);
        return grupoRepository.findNmGrupoExists(nmGrupo);
    }
    
    /**
     * Search for the nmGrupo
     * Used on product page
     *
     *  @param query the nmGrupo
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Grupo> findByNomeStartingWithOrderByNomeAsc(String descricao, Pageable pageable){
    	log.debug("Request to...", descricao);
        return grupoRepository.findByNmGrupoStartingWithOrderByNmGrupoAsc(descricao, pageable);
    }
    
    /**
     * Search for the id
     * Used on product page
     *
     *  @param query the nmGrupo
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Grupo> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable){
    	log.debug("Request to...", id);
        return grupoRepository.findByIdStartingWithOrderByIdAsc(id, pageable);
    }
    
    /**
     *  Get all the grupos ordered by name.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Grupo> findAllOrderByNmGrupo(Pageable pageable) {
        log.debug("Request to get all Grupos ordered by name");
        Page<Grupo> result = grupoRepository.findAllOrderByNmGrupo(pageable); 
        return result;
    }
    
    /**
     *  Get all the grupos ordered by id.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Grupo> findAllOrderById(Pageable pageable) {
        log.debug("Request to get all Grupos ordered by id");
        Page<Grupo> result = grupoRepository.findAllOrderById(pageable); 
        return result;
    }
}
