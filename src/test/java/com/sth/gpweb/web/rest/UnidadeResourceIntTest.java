package com.sth.gpweb.web.rest;

import com.sth.gpweb.GpwebApp;
import com.sth.gpweb.domain.Unidade;
import com.sth.gpweb.repository.UnidadeRepository;
import com.sth.gpweb.service.UnidadeService;
import com.sth.gpweb.repository.search.UnidadeSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UnidadeResource REST controller.
 *
 * @see UnidadeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GpwebApp.class)
@WebAppConfiguration
@IntegrationTest
public class UnidadeResourceIntTest {

    private static final String DEFAULT_DS_UNIDADE = "A";
    private static final String UPDATED_DS_UNIDADE = "B";
    private static final String DEFAULT_SG_UNIDADE = "AA";
    private static final String UPDATED_SG_UNIDADE = "BB";

    @Inject
    private UnidadeRepository unidadeRepository;

    @Inject
    private UnidadeService unidadeService;

    @Inject
    private UnidadeSearchRepository unidadeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUnidadeMockMvc;

    private Unidade unidade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UnidadeResource unidadeResource = new UnidadeResource();
        ReflectionTestUtils.setField(unidadeResource, "unidadeService", unidadeService);
        this.restUnidadeMockMvc = MockMvcBuilders.standaloneSetup(unidadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        unidadeSearchRepository.deleteAll();
        unidade = new Unidade();
        unidade.setNmUnidade(DEFAULT_DS_UNIDADE);
        unidade.setSgUnidade(DEFAULT_SG_UNIDADE);
    }

    @Test
    @Transactional
    public void createUnidade() throws Exception {
        int databaseSizeBeforeCreate = unidadeRepository.findAll().size();

        // Create the Unidade

        restUnidadeMockMvc.perform(post("/api/unidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unidade)))
                .andExpect(status().isCreated());

        // Validate the Unidade in the database
        List<Unidade> unidades = unidadeRepository.findAll();
        assertThat(unidades).hasSize(databaseSizeBeforeCreate + 1);
        Unidade testUnidade = unidades.get(unidades.size() - 1);
        assertThat(testUnidade.getNmUnidade()).isEqualTo(DEFAULT_DS_UNIDADE);
        assertThat(testUnidade.getSgUnidade()).isEqualTo(DEFAULT_SG_UNIDADE);

        // Validate the Unidade in ElasticSearch
        Unidade unidadeEs = unidadeSearchRepository.findOne(testUnidade.getId());
        assertThat(unidadeEs).isEqualToComparingFieldByField(testUnidade);
    }

    @Test
    @Transactional
    public void checkNmUnidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeRepository.findAll().size();
        // set the field null
        unidade.setNmUnidade(null);

        // Create the Unidade, which fails.

        restUnidadeMockMvc.perform(post("/api/unidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unidade)))
                .andExpect(status().isBadRequest());

        List<Unidade> unidades = unidadeRepository.findAll();
        assertThat(unidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidades() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        // Get all the unidades
        restUnidadeMockMvc.perform(get("/api/unidades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(unidade.getId().intValue())))
                .andExpect(jsonPath("$.[*].nmUnidade").value(hasItem(DEFAULT_DS_UNIDADE.toString())))
                .andExpect(jsonPath("$.[*].sgUnidade").value(hasItem(DEFAULT_SG_UNIDADE.toString())));
    }

    @Test
    @Transactional
    public void getUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        // Get the unidade
        restUnidadeMockMvc.perform(get("/api/unidades/{id}", unidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(unidade.getId().intValue()))
            .andExpect(jsonPath("$.nmUnidade").value(DEFAULT_DS_UNIDADE.toString()))
            .andExpect(jsonPath("$.sgUnidade").value(DEFAULT_SG_UNIDADE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidade() throws Exception {
        // Get the unidade
        restUnidadeMockMvc.perform(get("/api/unidades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidade() throws Exception {
        // Initialize the database
        unidadeService.save(unidade);

        int databaseSizeBeforeUpdate = unidadeRepository.findAll().size();

        // Update the unidade
        Unidade updatedUnidade = new Unidade();
        updatedUnidade.setId(unidade.getId());
        updatedUnidade.setNmUnidade(UPDATED_DS_UNIDADE);
        updatedUnidade.setSgUnidade(UPDATED_SG_UNIDADE);

        restUnidadeMockMvc.perform(put("/api/unidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUnidade)))
                .andExpect(status().isOk());

        // Validate the Unidade in the database
        List<Unidade> unidades = unidadeRepository.findAll();
        assertThat(unidades).hasSize(databaseSizeBeforeUpdate);
        Unidade testUnidade = unidades.get(unidades.size() - 1);
        assertThat(testUnidade.getNmUnidade()).isEqualTo(UPDATED_DS_UNIDADE);
        assertThat(testUnidade.getSgUnidade()).isEqualTo(UPDATED_SG_UNIDADE);

        // Validate the Unidade in ElasticSearch
        Unidade unidadeEs = unidadeSearchRepository.findOne(testUnidade.getId());
        assertThat(unidadeEs).isEqualToComparingFieldByField(testUnidade);
    }

    @Test
    @Transactional
    public void deleteUnidade() throws Exception {
        // Initialize the database
        unidadeService.save(unidade);

        int databaseSizeBeforeDelete = unidadeRepository.findAll().size();

        // Get the unidade
        restUnidadeMockMvc.perform(delete("/api/unidades/{id}", unidade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean unidadeExistsInEs = unidadeSearchRepository.exists(unidade.getId());
        assertThat(unidadeExistsInEs).isFalse();

        // Validate the database is empty
        List<Unidade> unidades = unidadeRepository.findAll();
        assertThat(unidades).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUnidade() throws Exception {
        // Initialize the database
        unidadeService.save(unidade);

        // Search the unidade
        restUnidadeMockMvc.perform(get("/api/_search/unidades?query=id:" + unidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmUnidade").value(hasItem(DEFAULT_DS_UNIDADE.toString())))
            .andExpect(jsonPath("$.[*].sgUnidade").value(hasItem(DEFAULT_SG_UNIDADE.toString())));
    }
}
