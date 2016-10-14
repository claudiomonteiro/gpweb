package com.sth.gpweb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gpweb.domain.ProdutoFilial;
import com.sth.gpweb.service.ProdutoFilialService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProdutoFilial.
 */
@RestController
@RequestMapping("/api")
public class ProdutoFilialResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoFilialResource.class);
        
    @Inject
    private ProdutoFilialService produtoFilialService;
    
    /**
     * POST  /produto-filials : Create a new produtoFilial.
     *
     * @param produtoFilial the produtoFilial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produtoFilial, or with status 400 (Bad Request) if the produtoFilial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/produto-filials",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProdutoFilial> createProdutoFilial(@RequestBody ProdutoFilial produtoFilial) throws URISyntaxException {
        log.debug("REST request to save ProdutoFilial : {}", produtoFilial);
        if (produtoFilial.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("produtoFilial", "idexists", "A new produtoFilial cannot already have an ID")).body(null);
        }
        ProdutoFilial result = produtoFilialService.save(produtoFilial);
        return ResponseEntity.created(new URI("/api/produto-filials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("produtoFilial", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produto-filials : Updates an existing produtoFilial.
     *
     * @param produtoFilial the produtoFilial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produtoFilial,
     * or with status 400 (Bad Request) if the produtoFilial is not valid,
     * or with status 500 (Internal Server Error) if the produtoFilial couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/produto-filials",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProdutoFilial> updateProdutoFilial(@RequestBody ProdutoFilial produtoFilial) throws URISyntaxException {
        log.debug("REST request to update ProdutoFilial : {}", produtoFilial);
        if (produtoFilial.getId() == null) {
            return createProdutoFilial(produtoFilial);
        }
        ProdutoFilial result = produtoFilialService.save(produtoFilial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("produtoFilial", produtoFilial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produto-filials : get all the produtoFilials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of produtoFilials in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/produto-filials",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProdutoFilial>> getAllProdutoFilials(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProdutoFilials");
        Page<ProdutoFilial> page = produtoFilialService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produto-filials");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produto-filials/:id : get the "id" produtoFilial.
     *
     * @param id the id of the produtoFilial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produtoFilial, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/produto-filials/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProdutoFilial> getProdutoFilial(@PathVariable Long id) {
        log.debug("REST request to get ProdutoFilial : {}", id);
        ProdutoFilial produtoFilial = produtoFilialService.findOne(id);
        return Optional.ofNullable(produtoFilial)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /produto-filials/:id : delete the "id" produtoFilial.
     *
     * @param id the id of the produtoFilial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/produto-filials/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProdutoFilial(@PathVariable Long id) {
        log.debug("REST request to delete ProdutoFilial : {}", id);
        produtoFilialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("produtoFilial", id.toString())).build();
    }

    /**
     * SEARCH  /_search/produto-filials?query=:query : search for the produtoFilial corresponding
     * to the query.
     *
     * @param query the query of the produtoFilial search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/produto-filials",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProdutoFilial>> searchProdutoFilials(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ProdutoFilials for query {}", query);
        Page<ProdutoFilial> page = produtoFilialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/produto-filials");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
