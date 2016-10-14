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
import com.sth.gpweb.domain.Grupo;
import com.sth.gpweb.service.GrupoService;
import com.sth.gpweb.web.rest.util.HeaderUtil;
import com.sth.gpweb.web.rest.util.PaginationUtil;
import com.sth.gpweb.web.rest.util.ScSelect;

/**
 * REST controller for managing Grupo.
 */
@RestController
@RequestMapping("/api")
public class GrupoResource {

    private final Logger log = LoggerFactory.getLogger(GrupoResource.class);
        
    @Inject
    private GrupoService grupoService;
    
    /**
     * POST  /grupos : Create a new grupo.
     *
     * @param grupo the grupo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grupo, or with status 400 (Bad Request) if the grupo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/grupos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grupo> createGrupo(@Valid @RequestBody Grupo grupo) throws URISyntaxException {
        log.debug("REST request to save Grupo : {}", grupo);
        
        if (grupo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("grupo", "idexists", "A new grupo cannot already have an ID")).body(null);
        }
        
        if (grupoService.findNmGrupoExists(grupo.getNmGrupo()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("grupo", "nmexists", "A new grupo already used!")).body(null);
        }    
        
        grupo.setDtOperacao(LocalDate.now()); //Always use the operation date from server
        
        Grupo result = grupoService.save(grupo);
        return ResponseEntity.created(new URI("/api/grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("grupo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grupos : Updates an existing grupo.
     *
     * @param grupo the grupo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grupo,
     * or with status 400 (Bad Request) if the grupo is not valid,
     * or with status 500 (Internal Server Error) if the grupo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/grupos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grupo> updateGrupo(@Valid @RequestBody Grupo grupo) throws URISyntaxException {
        log.debug("REST request to update Grupo : {}", grupo);
        if (grupo.getId() == null) {
            return createGrupo(grupo);
        }
        
        grupo.setDtOperacao(LocalDate.now()); //Always use the operation date from server
        
        Grupo result = grupoService.save(grupo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("grupo", grupo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grupos : get all the grupos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of grupos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/grupos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Grupo>> getAllGrupos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Grupos");
        Page<Grupo> page = grupoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grupos/:id : get the "id" grupo.
     *
     * @param id the id of the grupo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grupo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/grupos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grupo> getGrupo(@PathVariable Long id) {
        log.debug("REST request to get Grupo : {}", id);        
        Grupo grupo = grupoService.findOne(id);        
        return Optional.ofNullable(grupo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /grupos/:id : delete the "id" grupo.
     *
     * @param id the id of the grupo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/grupos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        log.debug("REST request to delete Grupo : {}", id);
        grupoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grupo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/grupos?query=:query : search for the grupo corresponding
     * to the query.
     *
     * @param query the query of the grupo search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/grupos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Grupo>> searchGrupos(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Grupos for query {}", query);
        Page<Grupo> page = grupoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/grupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * SEARCH  /_search/grupos/select?query=:query : search for the grupo corresponding
     * to the query.
     *
     * @param query the query of the grupo search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/grupos/select",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> searchGrupoSelect(@RequestParam String query, @RequestParam String field, Pageable pageable)
        throws URISyntaxException {
    	
    	try{
    		Page<Grupo> page;
    		
    		if(query.trim().equalsIgnoreCase("*")){
    			//Find all
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = grupoService.findAllOrderById(pageable); 
    			}else{
    				//Find by name
    				page = grupoService.findAllOrderByNmGrupo(pageable);
    			} 
    		}else{    			
    			if(field.trim().equalsIgnoreCase("id")){
    				//Find by id
    				page = grupoService.findByIdStartingWithOrderByIdAsc(query, pageable); 
    			}else{
    				//Find by name
    				page = grupoService.findByNomeStartingWithOrderByNomeAsc(query, pageable);
    			}
    		};
    		
	    	HttpHeaders headers = new HttpHeaders();
	    	headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/grupos/select");	        
	        
	        //Json modified to supply sc-select component on frontend
	        ScSelect<Grupo> scSelect = new ScSelect<>(
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