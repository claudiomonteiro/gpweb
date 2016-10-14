package com.sth.gpweb.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.domain.Filial;
import com.sth.gpweb.domain.ProdutoFilial;
import com.sth.gpweb.repository.ProdutoFilialRepository;
import com.sth.gpweb.repository.search.ProdutoFilialSearchRepository;
import com.sth.gpweb.service.ProdutoFilialService;

/**
 * Service Implementation for managing ProdutoFilial.
 */
@Service
@Transactional
public class ProdutoFilialServiceImpl implements ProdutoFilialService{

    private final Logger log = LoggerFactory.getLogger(ProdutoFilialServiceImpl.class);
    
    @Inject
    private ProdutoFilialRepository produtoFilialRepository;
    
    @Inject
    private ProdutoFilialSearchRepository produtoFilialSearchRepository;
    
    /**
     * Save a produtoFilial.
     * 
     * @param produtoFilial the entity to save
     * @return the persisted entity
     */
    @Override
	public ProdutoFilial save(ProdutoFilial produtoFilial) {
        log.debug("Request to save ProdutoFilial : {}", produtoFilial);
        ProdutoFilial result = produtoFilialRepository.save(produtoFilial);
        produtoFilialSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the produtoFilials.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true) 
    public Page<ProdutoFilial> findAll(Pageable pageable) {
        log.debug("Request to get all ProdutoFilials");        
        Page<ProdutoFilial> result = produtoFilialRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one produtoFilial by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
	@Transactional(readOnly = true) 
    public ProdutoFilial findOne(Long id) {
        log.debug("Request to get ProdutoFilial : {}", id);
        ProdutoFilial produtoFilial = produtoFilialRepository.findOne(id);
        return produtoFilial;
    }

    /**
     *  Delete the  produtoFilial by id.
     *  
     *  @param id the id of the entity
     */
    @Override
	public void delete(Long id) {
        log.debug("Request to delete ProdutoFilial : {}", id);
        produtoFilialRepository.delete(id);
        produtoFilialSearchRepository.delete(id);
    }

    /**
     * Search for the produtoFilial corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true)
    public Page<ProdutoFilial> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProdutoFilials for query {}", query);
        return produtoFilialSearchRepository.search(queryStringQuery(query), pageable);
    }

	/**
	 * Get all the produtoFilials based on produto_id.
	 * 
	 * @param produto_id
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<ProdutoFilial> findProdutoIdExists(Long idProduto) {
		log.debug("Request to get all ProdutoFilials where produto_id equals: " + idProduto);
		Set<ProdutoFilial> result = produtoFilialRepository.findProdutoIdExists(idProduto);
		return result;
	}

	/**
	 * Get the count of produtoFilials based on produto_id and filial_id.
	 * 
	 * @param produto_id,
	 *            filial_id
	 * @return count of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Long findFilialIdExists(Long idProduto, Long idFilial) {
		log.debug("Request to get the count of ProdutoFilials where produto_id = " + idProduto + " and filial_id = "
				+ idFilial);
		Long result = produtoFilialRepository.findFilialIdExists(idProduto, idFilial);
		return result;
	}

	/**
	 * Get all the produtoFilials based on produto_id.
	 * 
	 * @param produto_id
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public ArrayList<Filial> findFiliaisByIdProduto(Long idProduto) {
		log.debug("Request to get all Filials where produto_id equals: " + idProduto);
		ArrayList<Filial> result = produtoFilialRepository.findFiliaisByIdProduto(idProduto);
		return result;
	}
	
	/**
	 * Delete the produtoFilial by id not in.
	 * 
	 * @param id
	 * 
	 */
	@Override
	public void deleteWhereProdutoId(Long id) {
		log.debug("Request to delete ProdutoFilial where produto_id = {}", id);
		produtoFilialRepository.deleteWhereProdutoId(id);
	}
}
