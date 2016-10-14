package com.sth.gpweb.repository.search;

import com.sth.gpweb.domain.ProdutoFilial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProdutoFilial entity.
 */
public interface ProdutoFilialSearchRepository extends ElasticsearchRepository<ProdutoFilial, Long> {
}
