package com.sth.gpweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import com.sth.gpweb.domain.Unidade;
import com.sth.gpweb.service.UnidadeService;
import com.sth.gpweb.web.rest.util.HeaderUtil;
import com.sth.gpweb.web.rest.util.PaginationUtil;
import com.sth.gpweb.web.rest.util.ScSelect;

/**
 * REST controller for managing Unidade.
 */
@RestController
@RequestMapping("/api")
public class UnidadeResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeResource.class);
        
    @Inject
    private UnidadeService unidadeService;
    
    /**
     * POST  /unidades : Create a new unidade.
     *
     * @param unidade the unidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidade, or with status 400 (Bad Request) if the unidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/unidades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Unidade> createUnidade(@Valid @RequestBody Unidade unidade) throws URISyntaxException {
        log.debug("REST request to save Unidade : {}", unidade);
        
        if (unidade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("unidade", "idexists", "A new unidade cannot already have an ID")).body(null);
        }
        
        if (unidadeService.findNmUnidadeExists(unidade.getNmUnidade()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("unidade", "nmexists", "A new unidade cannot already have an ID")).body(null);
        }
        
        if (unidadeService.findSgUnidadeExists(unidade.getSgUnidade()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("unidade", "sgexists", "A new unidade cannot already have an ID")).body(null);
        }
        
        Unidade result = unidadeService.save(unidade);
        return ResponseEntity.created(new URI("/api/unidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("unidade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unidades : Updates an existing unidade.
     *
     * @param unidade the unidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidade,
     * or with status 400 (Bad Request) if the unidade is not valid,
     * or with status 500 (Internal Server Error) if the unidade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/unidades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Unidade> updateUnidade(@Valid @RequestBody Unidade unidade) throws URISyntaxException {
        log.debug("REST request to update Unidade : {}", unidade);
        if (unidade.getId() == null) {
            return createUnidade(unidade);
        }
        Unidade result = unidadeService.save(unidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("unidade", unidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unidades : get all the unidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of unidades in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/unidades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Unidade>> getAllUnidades(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Unidades");
        Page<Unidade> page = unidadeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /unidades/:id : get the "id" unidade.
     *
     * @param id the id of the unidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unidade, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/unidades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Unidade> getUnidade(@PathVariable Long id) {
        log.debug("REST request to get Unidade : {}", id);
        Unidade unidade = unidadeService.findOne(id);
        return Optional.ofNullable(unidade)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /unidades/:id : delete the "id" unidade.
     *
     * @param id the id of the unidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/unidades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUnidade(@PathVariable Long id) {
        log.debug("REST request to delete Unidade : {}", id);
        unidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("unidade", id.toString())).build();
    }

    /**
     * SEARCH  /_search/unidades?query=:query : search for the unidade corresponding
     * to the query.
     *
     * @param query the query of the unidade search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/unidades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Unidade>> searchUnidades(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Unidades for query {}", query);
        Page<Unidade> page = unidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/unidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * SEARCH  /_search/unidade/select?query=:query : search for the unidade corresponding
     * to the query.
     *
     * @param query the query of the unidade search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/unidades/select",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> searchUnidadeSelect(@RequestParam String query, @RequestParam String field, Pageable pageable)
        throws URISyntaxException {
    	
    	try{
    		Page<Unidade> page;
    		
    		if(query.trim().equalsIgnoreCase("*")){
    			//Find all
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = unidadeService.findAllOrderById(pageable); 
    			}else{
    				//Find by name
    				page = unidadeService.findAllOrderByNmUnidade(pageable);
    			} 
    		}else{    			
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = unidadeService.findByIdStartingWithOrderByIdAsc(query, pageable); 
    			}else{
    				//Find by name
    				page = unidadeService.findByNmUnidadeStartingWithOrderByNmUnidadeAsc(query, pageable);
    			}
    		};
    		
	    	HttpHeaders headers = new HttpHeaders();
	    	headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/unidades/select");	        
	        
	        //Json modified to supply sc-select component on frontend
	        ScSelect<Unidade> scSelect = new ScSelect<>(
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
	        
	        Gson gson = new Gson();
	        return new ResponseEntity<String>(gson.toJson(scSelect), headers, HttpStatus.OK);	        
    	}catch(Exception e){
    		log.error(e.getMessage());    		
    		return ResponseEntity.badRequest().header("Falha", e.getMessage()).body(null);
    	}
    }    
}