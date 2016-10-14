package com.sth.gpweb.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.domain.Filial;
import com.sth.gpweb.domain.Produto;
import com.sth.gpweb.repository.ProdutoRepository;
import com.sth.gpweb.repository.search.ProdutoSearchRepository;
import com.sth.gpweb.service.FilialService;
import com.sth.gpweb.service.ProdutoFilialService;
import com.sth.gpweb.service.ProdutoService;

/**
 * Service Implementation for managing Produto.
 */
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService{

    private final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);
    
    @Inject
    private ProdutoRepository produtoRepository;
    
    @Inject
    private ProdutoSearchRepository produtoSearchRepository;    

	@Inject
	private ProdutoFilialService produtoFilialService;	
	
	@Inject
	private FilialService filialService;	
    
    /**
     * Save a produto.
     * 
     * @param produto the entity to save
     * @return the persisted entity
     */
    @Override
	public Produto save(Produto produto) {
        log.debug("Request to save Produto : {}", produto);
        Produto result = produtoRepository.save(produto);
        produtoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the produtos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true) 
    public Page<Produto> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        Page<Produto> result = produtoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one produto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
	@Transactional(readOnly = true) 
    public Produto findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        Produto produto = produtoRepository.findOne(id);

        //Find filiais that belongs to produto
     	ArrayList<Filial> filials = produtoFilialService.findFiliaisByIdProduto(produto.getId());
     	produto.setFilials(filials);
     	
     	//Find filiais that not belongs to produto
     	if(filials.size() > 0){
	        Set<Long> ids = new HashSet<>();
	     	for (Filial filial : filials) {
				ids.add(filial.getId());
			}	     	
	     	produto.setFilialsNotUsed(filialService.findFiliaisByIdProdutoWhereNotUsed(ids));	
     	}else{
     		produto.setFilialsNotUsed(filialService.findAllFilials()); //Find All
     	}
        
        return produto;
    }

    /**
     *  Delete the  produto by id.
     *  
     *  @param id the id of the entity
     */
    @Override
	public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoFilialService.deleteWhereProdutoId(id);
        produtoRepository.delete(id);
        produtoSearchRepository.delete(id);
    }

    /**
     * Search for the produto corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true)
    public Page<Produto> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Produtos for query {}", query);
        return produtoSearchRepository.search(queryStringQuery(query), pageable);
    }
    
    /**
     * Search for the nmProduto already exists.
     *
     *  @param query the nmProduto
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true)    
    public String findNmProdutoExists(String nmProduto){
    	log.debug("Request to search if the nmProduto: {} already exists", nmProduto);
        return produtoRepository.findNmProdutoExists(nmProduto);
    }
    
    /**
     * Search for the nmProduto
     * Used on productfilial page
     *
     *  @param query the nmProduto
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true)
    public Page<Produto> findByNomeStartingWithOrderByNomeAsc(String descricao, Pageable pageable){
    	log.debug("Request to...", descricao);
        return produtoRepository.findByNmProdutoStartingWithOrderByNmProdutoAsc(descricao, pageable);
    }
    
    /**
     * Search for the id
     *
     *  @param id to find where id starting by
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true)
    public Page<Produto> findByIdStartingWithOrderByIdAsc(String id, Pageable pageable){
    	log.debug("Request to...", id);
        return produtoRepository.findByIdStartingWithOrderByIdAsc(id, pageable);
    }
    
    /**
     *  Get all the produtos ordered by name.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true) 
    public Page<Produto> findAllOrderByNmProduto(Pageable pageable) {
        log.debug("Request to get all Products ordered by name");
        Page<Produto> result = produtoRepository.findAllOrderByNmProduto(pageable); 
        return result;
    }
    
    /**
     *  Get all the grupos ordered by id.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true) 
    public Page<Produto> findAllOrderById(Pageable pageable) {
        log.debug("Request to get all Products ordered by id");
        Page<Produto> result = produtoRepository.findAllOrderById(pageable); 
        return result;
    }
}
