package com.sth.gpweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.sth.gpweb.domain.Filial;
import com.sth.gpweb.domain.Produto;
import com.sth.gpweb.domain.ProdutoFilial;
import com.sth.gpweb.service.ProdutoFilialService;
import com.sth.gpweb.service.ProdutoService;
import com.sth.gpweb.web.rest.util.HeaderUtil;
import com.sth.gpweb.web.rest.util.PaginationUtil;
import com.sth.gpweb.web.rest.util.ScSelect;

/**
 * REST controller for managing Produto.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);
        
    @Inject
    private ProdutoService produtoService;
    @Inject
	private ProdutoFilialService produtoFilialService;

    /**
     * POST  /produtos : Create a new produto.
     *
     * @param produto the produto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produto, or with status 400 (Bad Request) if the produto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/produtos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produto> createProduto(@Valid @RequestBody Produto produto) throws URISyntaxException {
        log.debug("REST request to save Produto : {}", produto);        
        if (produto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("catalogo", "idexists", "A new produto cannot already have an ID")).body(null);
        }
        
        if (produtoService.findNmProdutoExists(produto.getNmProduto()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("catalogo", "nmexists", "A new produto already used!")).body(null);
        } 
        
        Produto result = produtoService.save(produto);
        return ResponseEntity.created(new URI("/api/produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("produto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produtos : Updates an existing produto.
     *
     * @param produto the produto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produto,
     * or with status 400 (Bad Request) if the produto is not valid,
     * or with status 500 (Internal Server Error) if the produto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/produtos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto) throws URISyntaxException {
		Boolean productSaved = false;
		try {
			log.debug("REST request to update Produto : {}", produto);

			if (produto.getId() == null) {
				return createProduto(produto);
			}

			Produto result = produtoService.save(produto);
			productSaved = true;

			// Deleting all filiais that were'nt setted ...
			
			//Deletar todas as filiais lincadas a este id onde não estão dentro da lista de filiais passadas 
			if (produto.getFilials().size() > 0) {
				Set<ProdutoFilial> produtoFilialDatabase = produtoFilialService.findProdutoIdExists(produto.getId());
				for (ProdutoFilial produtoFilial : produtoFilialDatabase) {
					if(!(produto.getFilials().contains(produtoFilial.getFilial()))){						
						produtoFilialService.delete(produtoFilial.getId());
					}
				}				
			} else {
				// quando apagar tudo no front end então apaga tudo na base				
				produtoFilialService.deleteWhereProdutoId(produto.getId());
			}

			// Salvando relação na produto_filial
			for (Filial filiaisFront : produto.getFilials()) {				
				if (!(produtoFilialService.findFilialIdExists(produto.getId(), filiaisFront.getId()) > 0)) {
					ProdutoFilial produtoFilialAux = new ProdutoFilial();
					produtoFilialAux.setFilial(filiaisFront);
					produtoFilialAux.setProduto(produto);					
					produtoFilialService.save(produtoFilialAux);
				}
			}
			
			return ResponseEntity.ok()
					.headers(HeaderUtil.createEntityUpdateAlert("produto", produto.getId().toString())).body(result);
        
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert(productSaved ? "filial" : "produto", "create",
							productSaved ? "Can not create Filiais" : "Can not create Produto"))
					.body(null);
		}
	}

	/**
	 * GET /produtos : get all the produtos.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of produtos
	 *         in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
    @RequestMapping(value = "/produtos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Produto>> getAllProdutos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Produtos");
        Page<Produto> page = produtoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produtos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produtos/:id : get the "id" produto.
     *
     * @param id the id of the produto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produto, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/produtos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
        log.debug("REST request to get Produto : {}", id);
        Produto produto = produtoService.findOne(id);
		return Optional.ofNullable(produto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /produtos/:id : delete the "id" produto.
     *
     * @param id the id of the produto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/produtos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        log.debug("REST request to delete Produto : {}", id);
        produtoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("produto", id.toString())).build();
    }

    /**
     * SEARCH  /_search/produtos?query=:query : search for the produto corresponding
     * to the query.
     *
     * @param query the query of the produto search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/produtos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Produto>> searchProdutos(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Produtos for query {}", query);
        Page<Produto> page = produtoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/produtos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * SEARCH  /_search/produtos/select?query=:query : search for the produto corresponding
     * to the query.
     *
     * @param query the query of the produto search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/produtos/select",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> searchProdutoSelect(@RequestParam String query, @RequestParam String field, Pageable pageable)
        throws URISyntaxException {
    	
    	try{
    		Page<Produto> page;
    		
    		if(query.trim().equalsIgnoreCase("*")){
    			//Find all
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = produtoService.findAllOrderById(pageable); 
    			}else{
    				//Find by name
    				page = produtoService.findAllOrderByNmProduto(pageable);
    			} 
    		}else{    			
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = produtoService.findByIdStartingWithOrderByIdAsc(query, pageable); 
    			}else{
    				//Find by name
    				page = produtoService.findByNomeStartingWithOrderByNomeAsc(query, pageable);
    			}
    		};
    		
	    	HttpHeaders headers = new HttpHeaders();
	    	headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/produtos/select");
	    	
	    	//Json modified to supply sc-select component on frontend
	        ScSelect<Produto> scSelect = new ScSelect<>(
	        			// Query params:
		        			"", 
		        			"request", 
		        			Integer.toString(page.getNumber()),
	        			// ScTrackmatches params:	        
		        			page.getContent(),
	        			// Results params:
		        			Long.toString(page.getTotalElements()), 
							Integer.toString(page.getNumber() * page.getSize()), 
		        			Integer.toString(page.getSize()) 
					);
	        
	        return new ResponseEntity<String>(new Gson().toJson(scSelect), headers, HttpStatus.OK);
	        
    	}catch(Exception e){
    		log.error(e.getMessage());
    		return ResponseEntity.badRequest().header("Falha", e.getMessage()).body(null);
    	}		
    }
}