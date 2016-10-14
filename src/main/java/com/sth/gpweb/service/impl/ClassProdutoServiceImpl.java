package com.sth.gpweb.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.domain.ClassProduto;
import com.sth.gpweb.repository.ClassProdutoRepository;
import com.sth.gpweb.repository.search.ClassProdutoSearchRepository;
import com.sth.gpweb.service.ClassProdutoService;

/**
 * Service Implementation for managing ClassProduto.
 */
@Service
@Transactional
public class ClassProdutoServiceImpl implements ClassProdutoService{

    private final Logger log = LoggerFactory.getLogger(ClassProdutoServiceImpl.class);
    
    @Inject
    private ClassProdutoRepository classProdutoRepository;
    
    @Inject
    private ClassProdutoSearchRepository classProdutoSearchRepository;
    
    /**
     * Save a classProduto.
     * 
     * @param classProduto the entity to save
     * @return the persisted entity
     */
    public ClassProduto save(ClassProduto classProduto) {
        log.debug("Request to save ClassProduto : {}", classProduto);
        ClassProduto result = classProdutoRepository.save(classProduto);
        classProdutoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the classProdutos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ClassProduto> findAll(Pageable pageable) {
        log.debug("Request to get all ClassProdutos");
        Page<ClassProduto> result = classProdutoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one classProduto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClassProduto findOne(Long id) {
        log.debug("Request to get ClassProduto : {}", id);
        ClassProduto classProduto = classProdutoRepository.findOne(id);
        return classProduto;
    }

    /**
     *  Delete the  classProduto by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassProduto : {}", id);
        classProdutoRepository.delete(id);
        classProdutoSearchRepository.delete(id);
    }

    /**
     * Search for the classProduto corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClassProduto> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClassProdutos for query {}", query);
        return classProdutoSearchRepository.search(queryStringQuery(query), pageable);
    }
    
    /**
     * Search for the cdClassProduto already exists.
     *
     *  @param query the cdClassProduto
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public String findCdClassProdutoExists(String cdClassProduto){
    	log.debug("Request to search if the cdClassProduto: {} already exists", cdClassProduto);
        return classProdutoRepository.findCdClassProdutoExists(cdClassProduto);
    }
    
    /**
     * Search for the nmClassProduto already exists.
     *
     *  @param query the nmClassProduto
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public String findnmClassProdutoExists(String nmClassProduto){
    	log.debug("Request to search if the nmClassProduto: {} already exists", nmClassProduto);
        return classProdutoRepository.findnmClassProdutoExists(nmClassProduto);
    }    
    
    /**
     * Search for the nmClassProduto
     *
     *  @param query the nmMarca
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClassProduto> findBynmClassProdutoStartingWithOrderBynmClassProdutoAsc(String descricao, Pageable pageable){
	    log.debug("Request to...", descricao);
	    return classProdutoRepository.findByNmClassProdutoStartingWithOrderByNmClassProdutoAsc(descricao, pageable);
    }
    
    /**
     * Search for the id
     * Used on product page
     *
     *  @param query the nmClassProduto
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClassProduto> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable){
    	log.debug("Request to...", id);
        return classProdutoRepository.findByIdStartingWithOrderByIdAsc(id, pageable);
    }
    
    /**
     *  Get all the ClassProduto ordered by name.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ClassProduto> findAllOrderByNmClassProduto(Pageable pageable) {
        log.debug("Request to get all ClassProdutos ordered by name");
        Page<ClassProduto> result = classProdutoRepository.findAllOrderByNmClassProduto(pageable); 
        return result;
    }
    
    /**
     *  Get all the ClassProduto ordered by id.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ClassProduto> findAllOrderById(Pageable pageable) {
        log.debug("Request to get all ClassProdutos ordered by id");
        Page<ClassProduto> result = classProdutoRepository.findAllOrderById(pageable); 
        return result;
    }
}
