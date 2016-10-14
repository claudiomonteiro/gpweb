package com.sth.gpweb.web.rest;

import com.sth.gpweb.GpwebApp;
import com.sth.gpweb.domain.Marca;
import com.sth.gpweb.repository.MarcaRepository;
import com.sth.gpweb.service.MarcaService;
import com.sth.gpweb.repository.search.MarcaSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MarcaResource REST controller.
 *
 * @see MarcaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GpwebApp.class)
@WebAppConfiguration
@IntegrationTest
public class MarcaResourceIntTest {

    private static final String DEFAULT_NM_FABRICANTE = "A";
    private static final String UPDATED_NM_FABRICANTE = "B";
    private static final String DEFAULT_CD_CNPJ = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_CNPJ = "BBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_IE = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_IE = "BBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_NN_NUMERO = 1;
    private static final Integer UPDATED_NN_NUMERO = 2;
    private static final String DEFAULT_DS_COMPLEMENTO = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DS_COMPLEMENTO = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_TEL = "AAAAAAAAAAAAA";
    private static final String UPDATED_CD_TEL = "BBBBBBBBBBBBB";
    private static final String DEFAULT_CD_FAX = "AAAAAAAAAAAAA";
    private static final String UPDATED_CD_FAX = "BBBBBBBBBBBBB";

    private static final Boolean DEFAULT_FL_INATIVO = false;
    private static final Boolean UPDATED_FL_INATIVO = true;
    private static final String DEFAULT_NM_FANTASIA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NM_FANTASIA = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_VL_COMISSAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_COMISSAO = new BigDecimal(2);

    private static final LocalDate DEFAULT_DT_OPERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_OPERACAO = LocalDate.now(ZoneId.systemDefault());    

    @Inject
    private MarcaRepository marcaRepository;

    @Inject
    private MarcaService marcaService;

    @Inject
    private MarcaSearchRepository marcaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMarcaMockMvc;

    private Marca marca;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarcaResource marcaResource = new MarcaResource();
        ReflectionTestUtils.setField(marcaResource, "marcaService", marcaService);
        this.restMarcaMockMvc = MockMvcBuilders.standaloneSetup(marcaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        marcaSearchRepository.deleteAll();
        marca = new Marca();
        marca.setNmMarca(DEFAULT_NM_FABRICANTE);
        marca.setCdCnpj(DEFAULT_CD_CNPJ);
        marca.setCdIe(DEFAULT_CD_IE);
        marca.setNnNumero(DEFAULT_NN_NUMERO);
        marca.setDsComplemento(DEFAULT_DS_COMPLEMENTO);
        marca.setCdTel(DEFAULT_CD_TEL);
        marca.setCdFax(DEFAULT_CD_FAX);
        marca.setFlInativo(DEFAULT_FL_INATIVO);
        marca.setNmFantasia(DEFAULT_NM_FANTASIA);
        marca.setVlComissao(DEFAULT_VL_COMISSAO);
        marca.setDtOperacao(DEFAULT_DT_OPERACAO);        
    }

    @Test
    @Transactional
    public void createMarca() throws Exception {
        int databaseSizeBeforeCreate = marcaRepository.findAll().size();

        // Create the Marca

        restMarcaMockMvc.perform(post("/api/marcas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(marca)))
                .andExpect(status().isCreated());

        // Validate the Marca in the database
        List<Marca> marcas = marcaRepository.findAll();
        assertThat(marcas).hasSize(databaseSizeBeforeCreate + 1);
        Marca testMarca = marcas.get(marcas.size() - 1);
        assertThat(testMarca.getNmMarca()).isEqualTo(DEFAULT_NM_FABRICANTE);
        assertThat(testMarca.getCdCnpj()).isEqualTo(DEFAULT_CD_CNPJ);
        assertThat(testMarca.getCdIe()).isEqualTo(DEFAULT_CD_IE);
        assertThat(testMarca.getNnNumero()).isEqualTo(DEFAULT_NN_NUMERO);
        assertThat(testMarca.getDsComplemento()).isEqualTo(DEFAULT_DS_COMPLEMENTO);
        assertThat(testMarca.getCdTel()).isEqualTo(DEFAULT_CD_TEL);
        assertThat(testMarca.getCdFax()).isEqualTo(DEFAULT_CD_FAX);
        assertThat(testMarca.isFlInativo()).isEqualTo(DEFAULT_FL_INATIVO);
        assertThat(testMarca.getNmFantasia()).isEqualTo(DEFAULT_NM_FANTASIA);
        assertThat(testMarca.getVlComissao()).isEqualTo(DEFAULT_VL_COMISSAO);
        assertThat(testMarca.getDtOperacao()).isEqualTo(DEFAULT_DT_OPERACAO);        

        // Validate the Marca in ElasticSearch
        Marca marcaEs = marcaSearchRepository.findOne(testMarca.getId());
        assertThat(marcaEs).isEqualToComparingFieldByField(testMarca);
    }

    @Test
    @Transactional
    public void checkNmMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = marcaRepository.findAll().size();
        // set the field null
        marca.setNmMarca(null);

        // Create the Marca, which fails.

        restMarcaMockMvc.perform(post("/api/marcas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(marca)))
                .andExpect(status().isBadRequest());

        List<Marca> marcas = marcaRepository.findAll();
        assertThat(marcas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarcas() throws Exception {
        // Initialize the database
        marcaRepository.saveAndFlush(marca);

        // Get all the marcas
        restMarcaMockMvc.perform(get("/api/marcas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(marca.getId().intValue())))
                .andExpect(jsonPath("$.[*].nmMarca").value(hasItem(DEFAULT_NM_FABRICANTE.toString())))
                .andExpect(jsonPath("$.[*].cdCnpj").value(hasItem(DEFAULT_CD_CNPJ.toString())))
                .andExpect(jsonPath("$.[*].cdIe").value(hasItem(DEFAULT_CD_IE.toString())))
                .andExpect(jsonPath("$.[*].nnNumero").value(hasItem(DEFAULT_NN_NUMERO)))
                .andExpect(jsonPath("$.[*].dsComplemento").value(hasItem(DEFAULT_DS_COMPLEMENTO.toString())))
                .andExpect(jsonPath("$.[*].cdTel").value(hasItem(DEFAULT_CD_TEL.toString())))
                .andExpect(jsonPath("$.[*].cdFax").value(hasItem(DEFAULT_CD_FAX.toString())))
                .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].nmFantasia").value(hasItem(DEFAULT_NM_FANTASIA.toString())))
                .andExpect(jsonPath("$.[*].vlComissao").value(hasItem(DEFAULT_VL_COMISSAO.intValue())))
                .andExpect(jsonPath("$.[*].dtOperacao").value(hasItem(DEFAULT_DT_OPERACAO.toString())));
    }

    @Test
    @Transactional
    public void getMarca() throws Exception {
        // Initialize the database
        marcaRepository.saveAndFlush(marca);

        // Get the marca
        restMarcaMockMvc.perform(get("/api/marcas/{id}", marca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(marca.getId().intValue()))
            .andExpect(jsonPath("$.nmMarca").value(DEFAULT_NM_FABRICANTE.toString()))
            .andExpect(jsonPath("$.cdCnpj").value(DEFAULT_CD_CNPJ.toString()))
            .andExpect(jsonPath("$.cdIe").value(DEFAULT_CD_IE.toString()))
            .andExpect(jsonPath("$.nnNumero").value(DEFAULT_NN_NUMERO))
            .andExpect(jsonPath("$.dsComplemento").value(DEFAULT_DS_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.cdTel").value(DEFAULT_CD_TEL.toString()))
            .andExpect(jsonPath("$.cdFax").value(DEFAULT_CD_FAX.toString()))
            .andExpect(jsonPath("$.flInativo").value(DEFAULT_FL_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.nmFantasia").value(DEFAULT_NM_FANTASIA.toString()))
            .andExpect(jsonPath("$.vlComissao").value(DEFAULT_VL_COMISSAO.intValue()))
            .andExpect(jsonPath("$.dtOperacao").value(DEFAULT_DT_OPERACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarca() throws Exception {
        // Get the marca
        restMarcaMockMvc.perform(get("/api/marcas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarca() throws Exception {
        // Initialize the database
        marcaService.save(marca);

        int databaseSizeBeforeUpdate = marcaRepository.findAll().size();

        // Update the marca
        Marca updatedMarca = new Marca();
        updatedMarca.setId(marca.getId());
        updatedMarca.setNmMarca(UPDATED_NM_FABRICANTE);
        updatedMarca.setCdCnpj(UPDATED_CD_CNPJ);
        updatedMarca.setCdIe(UPDATED_CD_IE);
        updatedMarca.setNnNumero(UPDATED_NN_NUMERO);
        updatedMarca.setDsComplemento(UPDATED_DS_COMPLEMENTO);
        updatedMarca.setCdTel(UPDATED_CD_TEL);
        updatedMarca.setCdFax(UPDATED_CD_FAX);
        updatedMarca.setFlInativo(UPDATED_FL_INATIVO);
        updatedMarca.setNmFantasia(UPDATED_NM_FANTASIA);
        updatedMarca.setVlComissao(UPDATED_VL_COMISSAO);
        updatedMarca.setDtOperacao(UPDATED_DT_OPERACAO);        

        restMarcaMockMvc.perform(put("/api/marcas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMarca)))
                .andExpect(status().isOk());

        // Validate the Marca in the database
        List<Marca> marcas = marcaRepository.findAll();
        assertThat(marcas).hasSize(databaseSizeBeforeUpdate);
        Marca testMarca = marcas.get(marcas.size() - 1);
        assertThat(testMarca.getNmMarca()).isEqualTo(UPDATED_NM_FABRICANTE);
        assertThat(testMarca.getCdCnpj()).isEqualTo(UPDATED_CD_CNPJ);
        assertThat(testMarca.getCdIe()).isEqualTo(UPDATED_CD_IE);
        assertThat(testMarca.getNnNumero()).isEqualTo(UPDATED_NN_NUMERO);
        assertThat(testMarca.getDsComplemento()).isEqualTo(UPDATED_DS_COMPLEMENTO);
        assertThat(testMarca.getCdTel()).isEqualTo(UPDATED_CD_TEL);
        assertThat(testMarca.getCdFax()).isEqualTo(UPDATED_CD_FAX);
        assertThat(testMarca.isFlInativo()).isEqualTo(UPDATED_FL_INATIVO);
        assertThat(testMarca.getNmFantasia()).isEqualTo(UPDATED_NM_FANTASIA);
        assertThat(testMarca.getVlComissao()).isEqualTo(UPDATED_VL_COMISSAO);
        assertThat(testMarca.getDtOperacao()).isEqualTo(UPDATED_DT_OPERACAO);        

        // Validate the Marca in ElasticSearch
        Marca marcaEs = marcaSearchRepository.findOne(testMarca.getId());
        assertThat(marcaEs).isEqualToComparingFieldByField(testMarca);
    }

    @Test
    @Transactional
    public void deleteMarca() throws Exception {
        // Initialize the database
        marcaService.save(marca);

        int databaseSizeBeforeDelete = marcaRepository.findAll().size();

        // Get the marca
        restMarcaMockMvc.perform(delete("/api/marcas/{id}", marca.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean marcaExistsInEs = marcaSearchRepository.exists(marca.getId());
        assertThat(marcaExistsInEs).isFalse();

        // Validate the database is empty
        List<Marca> marcas = marcaRepository.findAll();
        assertThat(marcas).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarca() throws Exception {
        // Initialize the database
        marcaService.save(marca);

        // Search the marca
        restMarcaMockMvc.perform(get("/api/_search/marcas?query=id:" + marca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmMarca").value(hasItem(DEFAULT_NM_FABRICANTE.toString())))
            .andExpect(jsonPath("$.[*].cdCnpj").value(hasItem(DEFAULT_CD_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].cdIe").value(hasItem(DEFAULT_CD_IE.toString())))
            .andExpect(jsonPath("$.[*].nnNumero").value(hasItem(DEFAULT_NN_NUMERO)))
            .andExpect(jsonPath("$.[*].dsComplemento").value(hasItem(DEFAULT_DS_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].cdTel").value(hasItem(DEFAULT_CD_TEL.toString())))
            .andExpect(jsonPath("$.[*].cdFax").value(hasItem(DEFAULT_CD_FAX.toString())))
            .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].nmFantasia").value(hasItem(DEFAULT_NM_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].vlComissao").value(hasItem(DEFAULT_VL_COMISSAO.intValue())))
            .andExpect(jsonPath("$.[*].dtOperacao").value(hasItem(DEFAULT_DT_OPERACAO.toString())));
    }
}
