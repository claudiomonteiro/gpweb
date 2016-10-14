package com.sth.gpweb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gpweb.domain.Promocao;
import com.sth.gpweb.service.PromocaoService;
import com.sth.gpweb.web.rest.util.HeaderUtil;
import com.sth.gpweb.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Promocao.
 */
@RestController
@RequestMapping("/api")
public class PromocaoResource {

    private final Logger log = LoggerFactory.getLogger(PromocaoResource.class);
        
    @Inject
    private PromocaoService promocaoService;
    
    /**
     * POST  /promocaos : Create a new promocao.
     *
     * @param promocao the promocao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new promocao, or with status 400 (Bad Request) if the promocao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/promocaos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Promocao> createPromocao(@Valid @RequestBody Promocao promocao) throws URISyntaxException {
        log.debug("REST request to save Promocao : {}", promocao);
        
        if (promocao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("promocao", "idexists", "A new promocao cannot already have an ID")).body(null);
        }
        
        if (promocaoService.findNmPromocaoExists(promocao.getNmPromocao()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("promocao", "nmexists", "A new promocao cannot already have an ID")).body(null);
        }
        
        Promocao result = promocaoService.save(promocao);
        return ResponseEntity.created(new URI("/api/promocaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("promocao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /promocaos : Updates an existing promocao.
     *
     * @param promocao the promocao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated promocao,
     * or with status 400 (Bad Request) if the promocao is not valid,
     * or with status 500 (Internal Server Error) if the promocao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/promocaos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Promocao> updatePromocao(@Valid @RequestBody Promocao promocao) throws URISyntaxException {
        log.debug("REST request to update Promocao : {}", promocao);
        if (promocao.getId() == null) {
            return createPromocao(promocao);
        }
        Promocao result = promocaoService.save(promocao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("promocao", promocao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /promocaos : get all the promocaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of promocaos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/promocaos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Promocao>> getAllPromocaos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Promocaos");
        Page<Promocao> page = promocaoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/promocaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /promocaos/:id : get the "id" promocao.
     *
     * @param id the id of the promocao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the promocao, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/promocaos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Promocao> getPromocao(@PathVariable Long id) {
        log.debug("REST request to get Promocao : {}", id);
        Promocao promocao = promocaoService.findOne(id);
        return Optional.ofNullable(promocao)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /promocaos/:id : delete the "id" promocao.
     *
     * @param id the id of the promocao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/promocaos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePromocao(@PathVariable Long id) {
        log.debug("REST request to delete Promocao : {}", id);
        promocaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("promocao", id.toString())).build();
    }

    /**
     * SEARCH  /_search/promocaos?query=:query : search for the promocao corresponding
     * to the query.
     *
     * @param query the query of the promocao search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/promocaos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Promocao>> searchPromocaos(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Promocaos for query {}", query);
        Page<Promocao> page = promocaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/promocaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
