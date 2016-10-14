package com.sth.gpweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
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
import com.sth.gpweb.domain.Subgrupo;
import com.sth.gpweb.service.SubgrupoService;
import com.sth.gpweb.web.rest.util.HeaderUtil;
import com.sth.gpweb.web.rest.util.PaginationUtil;
import com.sth.gpweb.web.rest.util.ScSelect;

/**
 * REST controller for managing Subgrupo.
 */
@RestController
@RequestMapping("/api")
public class SubgrupoResource {

    private final Logger log = LoggerFactory.getLogger(SubgrupoResource.class);
        
    @Inject
    private SubgrupoService subgrupoService;
    
    /**
     * POST  /subgrupos : Create a new subgrupo.
     *
     * @param subgrupo the subgrupo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subgrupo, or with status 400 (Bad Request) if the subgrupo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subgrupos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subgrupo> createSubgrupo(@Valid @RequestBody Subgrupo subgrupo) throws URISyntaxException {
        log.debug("REST request to save Subgrupo : {}", subgrupo);
        if (subgrupo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subgrupo", "idexists", "A new subgrupo cannot already have an ID")).body(null);
        }
        
        subgrupo.setDtOperacao(LocalDate.now()); //Always use the operation date from server
        
        Subgrupo result = subgrupoService.save(subgrupo);
        return ResponseEntity.created(new URI("/api/subgrupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subgrupo", result.getId().toString()))
            .body(result);               
    }

    /**
     * PUT  /subgrupos : Updates an existing subgrupo.
     *
     * @param subgrupo the subgrupo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subgrupo,
     * or with status 400 (Bad Request) if the subgrupo is not valid,
     * or with status 500 (Internal Server Error) if the subgrupo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subgrupos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subgrupo> updateSubgrupo(@Valid @RequestBody Subgrupo subgrupo) throws URISyntaxException {
        log.debug("REST request to update Subgrupo : {}", subgrupo);
        if (subgrupo.getId() == null) {
            return createSubgrupo(subgrupo);
        }
        
        subgrupo.setDtOperacao(LocalDate.now()); //Always use the operation date from server
        
        Subgrupo result = subgrupoService.save(subgrupo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subgrupo", subgrupo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subgrupos : get all the subgrupos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subgrupos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/subgrupos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Subgrupo>> getAllSubgrupos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Subgrupos");
        Page<Subgrupo> page = subgrupoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subgrupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subgrupos/:id : get the "id" subgrupo.
     *
     * @param id the id of the subgrupo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subgrupo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/subgrupos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subgrupo> getSubgrupo(@PathVariable Long id) {
        log.debug("REST request to get Subgrupo : {}", id);
        Subgrupo subgrupo = subgrupoService.findOne(id);
        return Optional.ofNullable(subgrupo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subgrupos/:id : delete the "id" subgrupo.
     *
     * @param id the id of the subgrupo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/subgrupos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubgrupo(@PathVariable Long id) {
        log.debug("REST request to delete Subgrupo : {}", id);
        subgrupoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subgrupo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/subgrupos?query=:query : search for the subgrupo corresponding
     * to the query.
     *
     * @param query the query of the subgrupo search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/subgrupos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Subgrupo>> searchSubgrupos(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Subgrupos for query {}", query);
        Page<Subgrupo> page = subgrupoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/subgrupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * SEARCH  /_search/subgrupos/select?query=:query : search for the subgrupo corresponding
     * to the query.
     *
     * @param query the query of the subgrupo search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/subgrupos/select",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> searchSubgrupoSelect(@RequestParam String query, @RequestParam String field, Pageable pageable)
        throws URISyntaxException {
    	
    	try{
    		Page<Subgrupo> page;
    		
    		if(query.trim().equalsIgnoreCase("*")){
    			//Find all
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = subgrupoService.findAllOrderById(pageable); 
    			}else{
    				//Find by name
    				page = subgrupoService.findAllOrderByNmSubgrupo(pageable);
    			} 
    		}else{    			
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = subgrupoService.findByIdStartingWithOrderByIdAsc(query, pageable); 
    			}else{
    				//Find by name
    				page = subgrupoService.findByNmSubgrupoStartingWithOrderByNmSubgrupoAsc(query, pageable);
    			}
    		};
    		
	    	HttpHeaders headers = new HttpHeaders();
	    	headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/subgrupos/select");	        
	        
	        //Json modified to supply sc-select component on frontend
	        ScSelect<Subgrupo> scSelect = new ScSelect<>(
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