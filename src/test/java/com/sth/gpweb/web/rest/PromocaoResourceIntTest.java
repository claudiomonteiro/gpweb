package com.sth.gpweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gpweb.GpwebApp;
import com.sth.gpweb.domain.Promocao;
import com.sth.gpweb.repository.PromocaoRepository;
import com.sth.gpweb.repository.search.PromocaoSearchRepository;
import com.sth.gpweb.service.PromocaoService;


/**
 * Test class for the PromocaoResource REST controller.
 *
 * @see PromocaoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GpwebApp.class)
@WebAppConfiguration
@IntegrationTest
public class PromocaoResourceIntTest {

    private static final String DEFAULT_NM_PROMOCAO = "A";
    private static final String UPDATED_NM_PROMOCAO = "B";

    private static final LocalDate DEFAULT_DT_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NN_DIA_DATA = 1;
    private static final Integer UPDATED_NN_DIA_DATA = 2;

    private static final String DEFAULT_NN_DIA_SEMANA = "AAAAA";
    private static final String UPDATED_NN_DIA_SEMANA = "BBBBB";

    private static final Boolean DEFAULT_FL_INATIVO = false;
    private static final Boolean UPDATED_FL_INATIVO = true;

    private static final BigDecimal DEFAULT_VL_PROMOCAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_PROMOCAO = new BigDecimal(2);

    private static final Integer DEFAULT_NN_TIPO = 1;
    private static final Integer UPDATED_NN_TIPO = 2;

    @Inject
    private PromocaoRepository promocaoRepository;

    @Inject
    private PromocaoService promocaoService;

    @Inject
    private PromocaoSearchRepository promocaoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPromocaoMockMvc;

    private Promocao promocao;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromocaoResource promocaoResource = new PromocaoResource();
        ReflectionTestUtils.setField(promocaoResource, "promocaoService", promocaoService);
        this.restPromocaoMockMvc = MockMvcBuilders.standaloneSetup(promocaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        promocaoSearchRepository.deleteAll();
        promocao = new Promocao();
        promocao.setNmPromocao(DEFAULT_NM_PROMOCAO);
        promocao.setDtVencimento(DEFAULT_DT_VENCIMENTO);
        promocao.setNnDiaData(DEFAULT_NN_DIA_DATA);
        promocao.setNnDiaSemana(DEFAULT_NN_DIA_SEMANA);
        promocao.setFlInativo(DEFAULT_FL_INATIVO);
        promocao.setVlPromocao(DEFAULT_VL_PROMOCAO);        
        promocao.setNnTipo(DEFAULT_NN_TIPO);
    }

    @Test
    @Transactional
    public void createPromocao() throws Exception {
        int databaseSizeBeforeCreate = promocaoRepository.findAll().size();

        // Create the Promocao

        restPromocaoMockMvc.perform(post("/api/promocaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promocao)))
                .andExpect(status().isCreated());

        // Validate the Promocao in the database
        List<Promocao> promocaos = promocaoRepository.findAll();
        assertThat(promocaos).hasSize(databaseSizeBeforeCreate + 1);
        Promocao testPromocao = promocaos.get(promocaos.size() - 1);
        assertThat(testPromocao.getNmPromocao()).isEqualTo(DEFAULT_NM_PROMOCAO);
        assertThat(testPromocao.getDtVencimento()).isEqualTo(DEFAULT_DT_VENCIMENTO);
        assertThat(testPromocao.getNnDiaData()).isEqualTo(DEFAULT_NN_DIA_DATA);
        assertThat(testPromocao.getNnDiaSemana()).isEqualTo(DEFAULT_NN_DIA_SEMANA);
        assertThat(testPromocao.isFlInativo()).isEqualTo(DEFAULT_FL_INATIVO);
        assertThat(testPromocao.getVlPromocao()).isEqualTo(DEFAULT_VL_PROMOCAO);        
        assertThat(testPromocao.getNnTipo()).isEqualTo(DEFAULT_NN_TIPO);

        // Validate the Promocao in ElasticSearch
        Promocao promocaoEs = promocaoSearchRepository.findOne(testPromocao.getId());
        assertThat(promocaoEs).isEqualToComparingFieldByField(testPromocao);
    }

    @Test
    @Transactional
    public void checkNmPromocaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = promocaoRepository.findAll().size();
        // set the field null
        promocao.setNmPromocao(null);

        // Create the Promocao, which fails.

        restPromocaoMockMvc.perform(post("/api/promocaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promocao)))
                .andExpect(status().isBadRequest());

        List<Promocao> promocaos = promocaoRepository.findAll();
        assertThat(promocaos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPromocaos() throws Exception {
        // Initialize the database
        promocaoRepository.saveAndFlush(promocao);

        // Get all the promocaos
        restPromocaoMockMvc.perform(get("/api/promocaos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promocao.getId().intValue())))
                .andExpect(jsonPath("$.[*].nmPromocao").value(hasItem(DEFAULT_NM_PROMOCAO.toString())))
                .andExpect(jsonPath("$.[*].dtVencimento").value(hasItem(DEFAULT_DT_VENCIMENTO.toString())))
                .andExpect(jsonPath("$.[*].nnDiaData").value(hasItem(DEFAULT_NN_DIA_DATA)))
                .andExpect(jsonPath("$.[*].nnDiaSemana").value(hasItem(DEFAULT_NN_DIA_SEMANA)))
                .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].vlPromocao").value(hasItem(DEFAULT_VL_PROMOCAO.intValue())))                
                .andExpect(jsonPath("$.[*].nnTipo").value(hasItem(DEFAULT_NN_TIPO)));
    }

    @Test
    @Transactional
    public void getPromocao() throws Exception {
        // Initialize the database
        promocaoRepository.saveAndFlush(promocao);

        // Get the promocao
        restPromocaoMockMvc.perform(get("/api/promocaos/{id}", promocao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(promocao.getId().intValue()))
            .andExpect(jsonPath("$.nmPromocao").value(DEFAULT_NM_PROMOCAO.toString()))
            .andExpect(jsonPath("$.dtVencimento").value(DEFAULT_DT_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.nnDiaData").value(DEFAULT_NN_DIA_DATA))
            .andExpect(jsonPath("$.nnDiaSemana").value(DEFAULT_NN_DIA_SEMANA))
            .andExpect(jsonPath("$.flInativo").value(DEFAULT_FL_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.vlPromocao").value(DEFAULT_VL_PROMOCAO.intValue()))
            .andExpect(jsonPath("$.nnTipo").value(DEFAULT_NN_TIPO));
    }

    @Test
    @Transactional
    public void getNonExistingPromocao() throws Exception {
        // Get the promocao
        restPromocaoMockMvc.perform(get("/api/promocaos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromocao() throws Exception {
        // Initialize the database
        promocaoService.save(promocao);

        int databaseSizeBeforeUpdate = promocaoRepository.findAll().size();

        // Update the promocao
        Promocao updatedPromocao = new Promocao();
        updatedPromocao.setId(promocao.getId());
        updatedPromocao.setNmPromocao(UPDATED_NM_PROMOCAO);
        updatedPromocao.setDtVencimento(UPDATED_DT_VENCIMENTO);
        updatedPromocao.setNnDiaData(UPDATED_NN_DIA_DATA);
        updatedPromocao.setNnDiaSemana(UPDATED_NN_DIA_SEMANA);
        updatedPromocao.setFlInativo(UPDATED_FL_INATIVO);
        updatedPromocao.setVlPromocao(UPDATED_VL_PROMOCAO);        
        updatedPromocao.setNnTipo(UPDATED_NN_TIPO);

        restPromocaoMockMvc.perform(put("/api/promocaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPromocao)))
                .andExpect(status().isOk());

        // Validate the Promocao in the database
        List<Promocao> promocaos = promocaoRepository.findAll();
        assertThat(promocaos).hasSize(databaseSizeBeforeUpdate);
        Promocao testPromocao = promocaos.get(promocaos.size() - 1);
        assertThat(testPromocao.getNmPromocao()).isEqualTo(UPDATED_NM_PROMOCAO);
        assertThat(testPromocao.getDtVencimento()).isEqualTo(UPDATED_DT_VENCIMENTO);
        assertThat(testPromocao.getNnDiaData()).isEqualTo(UPDATED_NN_DIA_DATA);
        assertThat(testPromocao.getNnDiaSemana()).isEqualTo(UPDATED_NN_DIA_SEMANA);
        assertThat(testPromocao.isFlInativo()).isEqualTo(UPDATED_FL_INATIVO);
        assertThat(testPromocao.getVlPromocao()).isEqualTo(UPDATED_VL_PROMOCAO);        
        assertThat(testPromocao.getNnTipo()).isEqualTo(UPDATED_NN_TIPO);

        // Validate the Promocao in ElasticSearch
        Promocao promocaoEs = promocaoSearchRepository.findOne(testPromocao.getId());
        assertThat(promocaoEs).isEqualToComparingFieldByField(testPromocao);
    }

    @Test
    @Transactional
    public void deletePromocao() throws Exception {
        // Initialize the database
        promocaoService.save(promocao);

        int databaseSizeBeforeDelete = promocaoRepository.findAll().size();

        // Get the promocao
        restPromocaoMockMvc.perform(delete("/api/promocaos/{id}", promocao.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean promocaoExistsInEs = promocaoSearchRepository.exists(promocao.getId());
        assertThat(promocaoExistsInEs).isFalse();

        // Validate the database is empty
        List<Promocao> promocaos = promocaoRepository.findAll();
        assertThat(promocaos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPromocao() throws Exception {
        // Initialize the database
        promocaoService.save(promocao);

        // Search the promocao
        restPromocaoMockMvc.perform(get("/api/_search/promocaos?query=id:" + promocao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promocao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmPromocao").value(hasItem(DEFAULT_NM_PROMOCAO.toString())))
            .andExpect(jsonPath("$.[*].dtVencimento").value(hasItem(DEFAULT_DT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nnDiaData").value(hasItem(DEFAULT_NN_DIA_DATA)))
            .andExpect(jsonPath("$.[*].nnDiaSemana").value(hasItem(DEFAULT_NN_DIA_SEMANA)))
            .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].vlPromocao").value(hasItem(DEFAULT_VL_PROMOCAO.intValue())))            
            .andExpect(jsonPath("$.[*].nnTipo").value(hasItem(DEFAULT_NN_TIPO)));
    }
}
