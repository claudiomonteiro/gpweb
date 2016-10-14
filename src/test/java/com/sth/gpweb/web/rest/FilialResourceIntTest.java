package com.sth.gpweb.web.rest;

import com.sth.gpweb.GpwebApp;
import com.sth.gpweb.domain.Filial;
import com.sth.gpweb.repository.FilialRepository;
import com.sth.gpweb.service.FilialService;
import com.sth.gpweb.repository.search.FilialSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FilialResource REST controller.
 *
 * @see FilialResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GpwebApp.class)
@WebAppConfiguration
@IntegrationTest
public class FilialResourceIntTest {

    private static final String DEFAULT_NM_FILIAL = "A";
    private static final String UPDATED_NM_FILIAL = "B";

    private static final Integer DEFAULT_NN_NUMERO = 1;
    private static final Integer UPDATED_NN_NUMERO = 2;
    private static final String DEFAULT_DS_COMPLEMENTO = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DS_COMPLEMENTO = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_CNPJ = "AAAAAAAAAAAAAA";
    private static final String UPDATED_CD_CNPJ = "BBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_IE = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_IE = "BBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DS_SITE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DS_SITE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_FL_INATIVO = false;
    private static final Boolean UPDATED_FL_INATIVO = true;
    private static final String DEFAULT_NM_RAZAO = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NM_RAZAO = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_TEL = "AAAAAAAAAAAAA";
    private static final String UPDATED_CD_TEL = "BBBBBBBBBBBBB";
    private static final String DEFAULT_CD_TEL_1 = "AAAAAAAAAAAAA";
    private static final String UPDATED_CD_TEL_1 = "BBBBBBBBBBBBB";
    private static final String DEFAULT_CD_TEL_2 = "AAAAAAAAAAAAA";
    private static final String UPDATED_CD_TEL_2 = "BBBBBBBBBBBBB";
    private static final String DEFAULT_CD_FAX = "AAAAAAAAAAAAA";
    private static final String UPDATED_CD_FAX = "BBBBBBBBBBBBB";

    private static final LocalDate DEFAULT_DT_OPERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_OPERACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NN_VERSAO = 1;
    private static final Integer UPDATED_NN_VERSAO = 2;

    private static final Boolean DEFAULT_FL_TPREC = false;
    private static final Boolean UPDATED_FL_TPREC = true;
    private static final String DEFAULT_DS_PIS_COFINS = "AA";
    private static final String UPDATED_DS_PIS_COFINS = "BB";

    private static final Integer DEFAULT_NN_MDF_VERSAO = 1;
    private static final Integer UPDATED_NN_MDF_VERSAO = 2;
    
    private static final String DEFAULT_DS_EMAIL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DS_EMAIL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final Boolean DEFAULT_FL_ENVIA_EMAIL = false;
    private static final Boolean UPDATED_FL_ENVIA_EMAIL = true;
    private static final Boolean DEFAULT_FL_MATRIZ = false;
    private static final Boolean UPDATED_FL_MATRIZ = true;
    private static final String DEFAULT_DS_OBS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DS_OBS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private FilialRepository filialRepository;

    @Inject
    private FilialService filialService;

    @Inject
    private FilialSearchRepository filialSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFilialMockMvc;

    private Filial filial;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FilialResource filialResource = new FilialResource();
        ReflectionTestUtils.setField(filialResource, "filialService", filialService);
        this.restFilialMockMvc = MockMvcBuilders.standaloneSetup(filialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        filialSearchRepository.deleteAll();
        filial = new Filial();
        filial.setNmFilial(DEFAULT_NM_FILIAL);
        filial.setNnNumero(DEFAULT_NN_NUMERO);
        filial.setDsComplemento(DEFAULT_DS_COMPLEMENTO);
        filial.setCdCnpj(DEFAULT_CD_CNPJ);
        filial.setCdIe(DEFAULT_CD_IE);
        filial.setDsSite(DEFAULT_DS_SITE);
        filial.setFlInativo(DEFAULT_FL_INATIVO);
        filial.setNmRazao(DEFAULT_NM_RAZAO);
        filial.setCdTel(DEFAULT_CD_TEL);
        filial.setCdTel1(DEFAULT_CD_TEL_1);
        filial.setCdTel2(DEFAULT_CD_TEL_2);
        filial.setCdFax(DEFAULT_CD_FAX);
        filial.setDtOperacao(DEFAULT_DT_OPERACAO);
        filial.setFlTprec(DEFAULT_FL_TPREC);
        filial.setDsPisCofins(DEFAULT_DS_PIS_COFINS);
        filial.setDsEmail(DEFAULT_DS_EMAIL);
        filial.setFlEnviaEmail(DEFAULT_FL_ENVIA_EMAIL);
        filial.setFlMatriz(DEFAULT_FL_MATRIZ);
        filial.setDsObs(DEFAULT_DS_OBS);
    }

    @Test
    @Transactional
    public void createFilial() throws Exception {
        int databaseSizeBeforeCreate = filialRepository.findAll().size();

        // Create the Filial

        restFilialMockMvc.perform(post("/api/filials")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filial)))
                .andExpect(status().isCreated());

        // Validate the Filial in the database
        List<Filial> filials = filialRepository.findAll();
        assertThat(filials).hasSize(databaseSizeBeforeCreate + 1);
        Filial testFilial = filials.get(filials.size() - 1);
        assertThat(testFilial.getNmFilial()).isEqualTo(DEFAULT_NM_FILIAL);
        assertThat(testFilial.getNnNumero()).isEqualTo(DEFAULT_NN_NUMERO);
        assertThat(testFilial.getDsComplemento()).isEqualTo(DEFAULT_DS_COMPLEMENTO);
        assertThat(testFilial.getCdCnpj()).isEqualTo(DEFAULT_CD_CNPJ);
        assertThat(testFilial.getCdIe()).isEqualTo(DEFAULT_CD_IE);
        assertThat(testFilial.getDsSite()).isEqualTo(DEFAULT_DS_SITE);
        assertThat(testFilial.isFlInativo()).isEqualTo(DEFAULT_FL_INATIVO);
        assertThat(testFilial.getNmRazao()).isEqualTo(DEFAULT_NM_RAZAO);
        assertThat(testFilial.getCdTel()).isEqualTo(DEFAULT_CD_TEL);
        assertThat(testFilial.getCdTel1()).isEqualTo(DEFAULT_CD_TEL_1);
        assertThat(testFilial.getCdTel2()).isEqualTo(DEFAULT_CD_TEL_2);
        assertThat(testFilial.getCdFax()).isEqualTo(DEFAULT_CD_FAX);
        assertThat(testFilial.getDtOperacao()).isEqualTo(DEFAULT_DT_OPERACAO);
        assertThat(testFilial.isFlTprec()).isEqualTo(DEFAULT_FL_TPREC);
        assertThat(testFilial.getDsPisCofins()).isEqualTo(DEFAULT_DS_PIS_COFINS);
        assertThat(testFilial.getDsEmail()).isEqualTo(DEFAULT_DS_EMAIL);
        assertThat(testFilial.isFlEnviaEmail()).isEqualTo(DEFAULT_FL_ENVIA_EMAIL);
        assertThat(testFilial.isFlMatriz()).isEqualTo(DEFAULT_FL_MATRIZ);
        assertThat(testFilial.getDsObs()).isEqualTo(DEFAULT_DS_OBS);

        // Validate the Filial in ElasticSearch
        Filial filialEs = filialSearchRepository.findOne(testFilial.getId());
        assertThat(filialEs).isEqualToComparingFieldByField(testFilial);
    }

    @Test
    @Transactional
    public void checkNmFilialIsRequired() throws Exception {
        int databaseSizeBeforeTest = filialRepository.findAll().size();
        // set the field null
        filial.setNmFilial(null);

        // Create the Filial, which fails.

        restFilialMockMvc.perform(post("/api/filials")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filial)))
                .andExpect(status().isBadRequest());

        List<Filial> filials = filialRepository.findAll();
        assertThat(filials).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilials() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        // Get all the filials
        restFilialMockMvc.perform(get("/api/filials?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(filial.getId().intValue())))
                .andExpect(jsonPath("$.[*].nmFilial").value(hasItem(DEFAULT_NM_FILIAL.toString())))
                .andExpect(jsonPath("$.[*].nnNumero").value(hasItem(DEFAULT_NN_NUMERO)))
                .andExpect(jsonPath("$.[*].dsComplemento").value(hasItem(DEFAULT_DS_COMPLEMENTO.toString())))
                .andExpect(jsonPath("$.[*].cdCnpj").value(hasItem(DEFAULT_CD_CNPJ.toString())))
                .andExpect(jsonPath("$.[*].cdIe").value(hasItem(DEFAULT_CD_IE.toString())))
                .andExpect(jsonPath("$.[*].dsSite").value(hasItem(DEFAULT_DS_SITE.toString())))
                .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].nmRazao").value(hasItem(DEFAULT_NM_RAZAO.toString())))
                .andExpect(jsonPath("$.[*].cdTel").value(hasItem(DEFAULT_CD_TEL.toString())))
                .andExpect(jsonPath("$.[*].cdTel1").value(hasItem(DEFAULT_CD_TEL_1.toString())))
                .andExpect(jsonPath("$.[*].cdTel2").value(hasItem(DEFAULT_CD_TEL_2.toString())))
                .andExpect(jsonPath("$.[*].cdFax").value(hasItem(DEFAULT_CD_FAX.toString())))
                .andExpect(jsonPath("$.[*].dtOperacao").value(hasItem(DEFAULT_DT_OPERACAO.toString())))
                .andExpect(jsonPath("$.[*].flTprec").value(hasItem(DEFAULT_FL_TPREC.booleanValue())))
                .andExpect(jsonPath("$.[*].dsPisCofins").value(hasItem(DEFAULT_DS_PIS_COFINS.toString())))
                .andExpect(jsonPath("$.[*].dsEmail").value(hasItem(DEFAULT_DS_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].flEnviaEmail").value(hasItem(DEFAULT_FL_ENVIA_EMAIL.booleanValue())))
                .andExpect(jsonPath("$.[*].flMatriz").value(hasItem(DEFAULT_FL_MATRIZ.booleanValue())))
                .andExpect(jsonPath("$.[*].dsObs").value(hasItem(DEFAULT_DS_OBS.toString())));
    }

    @Test
    @Transactional
    public void getFilial() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        // Get the filial
        restFilialMockMvc.perform(get("/api/filials/{id}", filial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(filial.getId().intValue()))
            .andExpect(jsonPath("$.nmFilial").value(DEFAULT_NM_FILIAL.toString()))
            .andExpect(jsonPath("$.nnNumero").value(DEFAULT_NN_NUMERO))
            .andExpect(jsonPath("$.dsComplemento").value(DEFAULT_DS_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.cdCnpj").value(DEFAULT_CD_CNPJ.toString()))
            .andExpect(jsonPath("$.cdIe").value(DEFAULT_CD_IE.toString()))
            .andExpect(jsonPath("$.dsSite").value(DEFAULT_DS_SITE.toString()))
            .andExpect(jsonPath("$.flInativo").value(DEFAULT_FL_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.nmRazao").value(DEFAULT_NM_RAZAO.toString()))
            .andExpect(jsonPath("$.cdTel").value(DEFAULT_CD_TEL.toString()))
            .andExpect(jsonPath("$.cdTel1").value(DEFAULT_CD_TEL_1.toString()))
            .andExpect(jsonPath("$.cdTel2").value(DEFAULT_CD_TEL_2.toString()))
            .andExpect(jsonPath("$.cdFax").value(DEFAULT_CD_FAX.toString()))
            .andExpect(jsonPath("$.dtOperacao").value(DEFAULT_DT_OPERACAO.toString()))
            .andExpect(jsonPath("$.flTprec").value(DEFAULT_FL_TPREC.booleanValue()))
            .andExpect(jsonPath("$.dsPisCofins").value(DEFAULT_DS_PIS_COFINS.toString()))
            .andExpect(jsonPath("$.dsEmail").value(DEFAULT_DS_EMAIL.toString()))
            .andExpect(jsonPath("$.flEnviaEmail").value(DEFAULT_FL_ENVIA_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.flMatriz").value(DEFAULT_FL_MATRIZ.booleanValue()))
            .andExpect(jsonPath("$.dsObs").value(DEFAULT_DS_OBS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilial() throws Exception {
        // Get the filial
        restFilialMockMvc.perform(get("/api/filials/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilial() throws Exception {
        // Initialize the database
        filialService.save(filial);

        int databaseSizeBeforeUpdate = filialRepository.findAll().size();

        // Update the filial
        Filial updatedFilial = new Filial();
        updatedFilial.setId(filial.getId());
        updatedFilial.setNmFilial(UPDATED_NM_FILIAL);
        updatedFilial.setNnNumero(UPDATED_NN_NUMERO);
        updatedFilial.setDsComplemento(UPDATED_DS_COMPLEMENTO);
        updatedFilial.setCdCnpj(UPDATED_CD_CNPJ);
        updatedFilial.setCdIe(UPDATED_CD_IE);
        updatedFilial.setDsSite(UPDATED_DS_SITE);
        updatedFilial.setFlInativo(UPDATED_FL_INATIVO);
        updatedFilial.setNmRazao(UPDATED_NM_RAZAO);
        updatedFilial.setCdTel(UPDATED_CD_TEL);
        updatedFilial.setCdTel1(UPDATED_CD_TEL_1);
        updatedFilial.setCdTel2(UPDATED_CD_TEL_2);
        updatedFilial.setCdFax(UPDATED_CD_FAX);
        updatedFilial.setDtOperacao(UPDATED_DT_OPERACAO);
        updatedFilial.setFlTprec(UPDATED_FL_TPREC);
        updatedFilial.setDsPisCofins(UPDATED_DS_PIS_COFINS);
        updatedFilial.setDsEmail(UPDATED_DS_EMAIL);
        updatedFilial.setFlEnviaEmail(UPDATED_FL_ENVIA_EMAIL);
        updatedFilial.setFlMatriz(UPDATED_FL_MATRIZ);
        updatedFilial.setDsObs(UPDATED_DS_OBS);

        restFilialMockMvc.perform(put("/api/filials")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFilial)))
                .andExpect(status().isOk());

        // Validate the Filial in the database
        List<Filial> filials = filialRepository.findAll();
        assertThat(filials).hasSize(databaseSizeBeforeUpdate);
        Filial testFilial = filials.get(filials.size() - 1);
        assertThat(testFilial.getNmFilial()).isEqualTo(UPDATED_NM_FILIAL);
        assertThat(testFilial.getNnNumero()).isEqualTo(UPDATED_NN_NUMERO);
        assertThat(testFilial.getDsComplemento()).isEqualTo(UPDATED_DS_COMPLEMENTO);
        assertThat(testFilial.getCdCnpj()).isEqualTo(UPDATED_CD_CNPJ);
        assertThat(testFilial.getCdIe()).isEqualTo(UPDATED_CD_IE);
        assertThat(testFilial.getDsSite()).isEqualTo(UPDATED_DS_SITE);
        assertThat(testFilial.isFlInativo()).isEqualTo(UPDATED_FL_INATIVO);
        assertThat(testFilial.getNmRazao()).isEqualTo(UPDATED_NM_RAZAO);
        assertThat(testFilial.getCdTel()).isEqualTo(UPDATED_CD_TEL);
        assertThat(testFilial.getCdTel1()).isEqualTo(UPDATED_CD_TEL_1);
        assertThat(testFilial.getCdTel2()).isEqualTo(UPDATED_CD_TEL_2);
        assertThat(testFilial.getCdFax()).isEqualTo(UPDATED_CD_FAX);
        assertThat(testFilial.getDtOperacao()).isEqualTo(UPDATED_DT_OPERACAO);
        assertThat(testFilial.isFlTprec()).isEqualTo(UPDATED_FL_TPREC);
        assertThat(testFilial.getDsPisCofins()).isEqualTo(UPDATED_DS_PIS_COFINS);
        assertThat(testFilial.getDsEmail()).isEqualTo(UPDATED_DS_EMAIL);
        assertThat(testFilial.isFlEnviaEmail()).isEqualTo(UPDATED_FL_ENVIA_EMAIL);
        assertThat(testFilial.isFlMatriz()).isEqualTo(UPDATED_FL_MATRIZ);
        assertThat(testFilial.getDsObs()).isEqualTo(UPDATED_DS_OBS);

        // Validate the Filial in ElasticSearch
        Filial filialEs = filialSearchRepository.findOne(testFilial.getId());
        assertThat(filialEs).isEqualToComparingFieldByField(testFilial);
    }

    @Test
    @Transactional
    public void deleteFilial() throws Exception {
        // Initialize the database
        filialService.save(filial);

        int databaseSizeBeforeDelete = filialRepository.findAll().size();

        // Get the filial
        restFilialMockMvc.perform(delete("/api/filials/{id}", filial.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean filialExistsInEs = filialSearchRepository.exists(filial.getId());
        assertThat(filialExistsInEs).isFalse();

        // Validate the database is empty
        List<Filial> filials = filialRepository.findAll();
        assertThat(filials).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFilial() throws Exception {
        // Initialize the database
        filialService.save(filial);

        // Search the filial
        restFilialMockMvc.perform(get("/api/_search/filials?query=id:" + filial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmFilial").value(hasItem(DEFAULT_NM_FILIAL.toString())))
            .andExpect(jsonPath("$.[*].nnNumero").value(hasItem(DEFAULT_NN_NUMERO)))
            .andExpect(jsonPath("$.[*].dsComplemento").value(hasItem(DEFAULT_DS_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].cdCnpj").value(hasItem(DEFAULT_CD_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].cdIe").value(hasItem(DEFAULT_CD_IE.toString())))
            .andExpect(jsonPath("$.[*].dsSite").value(hasItem(DEFAULT_DS_SITE.toString())))
            .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].nmRazao").value(hasItem(DEFAULT_NM_RAZAO.toString())))
            .andExpect(jsonPath("$.[*].cdTel").value(hasItem(DEFAULT_CD_TEL.toString())))
            .andExpect(jsonPath("$.[*].cdTel1").value(hasItem(DEFAULT_CD_TEL_1.toString())))
            .andExpect(jsonPath("$.[*].cdTel2").value(hasItem(DEFAULT_CD_TEL_2.toString())))
            .andExpect(jsonPath("$.[*].cdFax").value(hasItem(DEFAULT_CD_FAX.toString())))
            .andExpect(jsonPath("$.[*].dtOperacao").value(hasItem(DEFAULT_DT_OPERACAO.toString())))
            .andExpect(jsonPath("$.[*].flTprec").value(hasItem(DEFAULT_FL_TPREC.booleanValue())))
            .andExpect(jsonPath("$.[*].dsPisCofins").value(hasItem(DEFAULT_DS_PIS_COFINS.toString())))
            .andExpect(jsonPath("$.[*].dsEmail").value(hasItem(DEFAULT_DS_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].flEnviaEmail").value(hasItem(DEFAULT_FL_ENVIA_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].flMatriz").value(hasItem(DEFAULT_FL_MATRIZ.booleanValue())))
            .andExpect(jsonPath("$.[*].dsObs").value(hasItem(DEFAULT_DS_OBS.toString())));
    }
}
