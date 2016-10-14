package com.sth.gpweb.service;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sth.gpweb.domain.Filial;
import com.sth.gpweb.domain.ProdutoFilial;

/**
 * Service Interface for managing ProdutoFilial.
 */
public interface ProdutoFilialService {

    /**
     * Save a produtoFilial.
     * 
     * @param produtoFilial the entity to save
     * @return the persisted entity
     */
    ProdutoFilial save(ProdutoFilial produtoFilial);

    /**
     *  Get all the produtoFilials.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProdutoFilial> findAll(Pageable pageable);

    /**
     *  Get the "id" produtoFilial.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ProdutoFilial findOne(Long id);

    /**
     *  Delete the "id" produtoFilial.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the produtoFilial corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<ProdutoFilial> search(String query, Pageable pageable);

	/**
	 * Get all the produtoFilials based on produto_id.
	 * 
	 * @param produto_id
	 * @return the list of entities
	 */
	Set<ProdutoFilial> findProdutoIdExists(Long idProduto);

	/**
	 * Get the count of produtoFilials based on produto_id and filial_id.
	 * 
	 * @param produto_id,
	 *            filial_id
	 * @return count of entities
	 */
	Long findFilialIdExists(Long idProduto, Long idFilial);

	/**
	 * Get all the Filials based on produto_id.
	 * 
	 * @param produto_id
	 * @return the list of entities
	 */
	ArrayList<Filial> findFiliaisByIdProduto(Long idProduto);

	/**
	 * Delete the produtoFilial where produto_id not in.
	 * 
	 * @param id
	 * 
	 */
	void deleteWhereProdutoId(Long id);

}
