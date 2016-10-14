package com.sth.gpweb.web.rest;

import com.sth.gpweb.GpwebApp;
import com.sth.gpweb.domain.ClassProduto;
import com.sth.gpweb.repository.ClassProdutoRepository;
import com.sth.gpweb.service.ClassProdutoService;
import com.sth.gpweb.repository.search.ClassProdutoSearchRepository;

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
 * Test class for the ClassProdutoResource REST controller.
 *
 * @see ClassProdutoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GpwebApp.class)
@WebAppConfiguration
@IntegrationTest
public class ClassProdutoResourceIntTest {

    private static final String DEFAULT_CD_CLASS_PRODUTO = "AA";
    private static final String UPDATED_CD_CLASS_PRODUTO = "BB";
    private static final String DEFAULT_NM_CLASS_PRODUTO = "A";
    private static final String UPDATED_NM_CLASS_PRODUTO = "B";

    @Inject
    private ClassProdutoRepository classProdutoRepository;

    @Inject
    private ClassProdutoService classProdutoService;

    @Inject
    private ClassProdutoSearchRepository classProdutoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClassProdutoMockMvc;

    private ClassProduto classProduto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassProdutoResource classProdutoResource = new ClassProdutoResource();
        ReflectionTestUtils.setField(classProdutoResource, "classProdutoService", classProdutoService);
        this.restClassProdutoMockMvc = MockMvcBuilders.standaloneSetup(classProdutoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        classProdutoSearchRepository.deleteAll();
        classProduto = new ClassProduto();
        classProduto.setCdClassProduto(DEFAULT_CD_CLASS_PRODUTO);
        classProduto.setnmClassProduto(DEFAULT_NM_CLASS_PRODUTO);
    }

    @Test
    @Transactional
    public void createClassProduto() throws Exception {
        int databaseSizeBeforeCreate = classProdutoRepository.findAll().size();

        // Create the ClassProduto

        restClassProdutoMockMvc.perform(post("/api/class-produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classProduto)))
                .andExpect(status().isCreated());

        // Validate the ClassProduto in the database
        List<ClassProduto> classProdutos = classProdutoRepository.findAll();
        assertThat(classProdutos).hasSize(databaseSizeBeforeCreate + 1);
        ClassProduto testClassProduto = classProdutos.get(classProdutos.size() - 1);
        assertThat(testClassProduto.getCdClassProduto()).isEqualTo(DEFAULT_CD_CLASS_PRODUTO);
        assertThat(testClassProduto.getnmClassProduto()).isEqualTo(DEFAULT_NM_CLASS_PRODUTO);

        // Validate the ClassProduto in ElasticSearch
        ClassProduto classProdutoEs = classProdutoSearchRepository.findOne(testClassProduto.getId());
        assertThat(classProdutoEs).isEqualToComparingFieldByField(testClassProduto);
    }

    @Test
    @Transactional
    public void checkCdClassProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = classProdutoRepository.findAll().size();
        // set the field null
        classProduto.setCdClassProduto(null);

        // Create the ClassProduto, which fails.

        restClassProdutoMockMvc.perform(post("/api/class-produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classProduto)))
                .andExpect(status().isBadRequest());

        List<ClassProduto> classProdutos = classProdutoRepository.findAll();
        assertThat(classProdutos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checknmClassProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = classProdutoRepository.findAll().size();
        // set the field null
        classProduto.setnmClassProduto(null);

        // Create the ClassProduto, which fails.

        restClassProdutoMockMvc.perform(post("/api/class-produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classProduto)))
                .andExpect(status().isBadRequest());

        List<ClassProduto> classProdutos = classProdutoRepository.findAll();
        assertThat(classProdutos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassProdutos() throws Exception {
        // Initialize the database
        classProdutoRepository.saveAndFlush(classProduto);

        // Get all the classProdutos
        restClassProdutoMockMvc.perform(get("/api/class-produtos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(classProduto.getId().intValue())))
                .andExpect(jsonPath("$.[*].cdClassProduto").value(hasItem(DEFAULT_CD_CLASS_PRODUTO.toString())))
                .andExpect(jsonPath("$.[*].nmClassProduto").value(hasItem(DEFAULT_NM_CLASS_PRODUTO.toString())));
    }

    @Test
    @Transactional
    public void getClassProduto() throws Exception {
        // Initialize the database
        classProdutoRepository.saveAndFlush(classProduto);

        // Get the classProduto
        restClassProdutoMockMvc.perform(get("/api/class-produtos/{id}", classProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(classProduto.getId().intValue()))
            .andExpect(jsonPath("$.cdClassProduto").value(DEFAULT_CD_CLASS_PRODUTO.toString()))
            .andExpect(jsonPath("$.nmClassProduto").value(DEFAULT_NM_CLASS_PRODUTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassProduto() throws Exception {
        // Get the classProduto
        restClassProdutoMockMvc.perform(get("/api/class-produtos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassProduto() throws Exception {
        // Initialize the database
        classProdutoService.save(classProduto);

        int databaseSizeBeforeUpdate = classProdutoRepository.findAll().size();

        // Update the classProduto
        ClassProduto updatedClassProduto = new ClassProduto();
        updatedClassProduto.setId(classProduto.getId());
        updatedClassProduto.setCdClassProduto(UPDATED_CD_CLASS_PRODUTO);
        updatedClassProduto.setnmClassProduto(UPDATED_NM_CLASS_PRODUTO);

        restClassProdutoMockMvc.perform(put("/api/class-produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClassProduto)))
                .andExpect(status().isOk());

        // Validate the ClassProduto in the database
        List<ClassProduto> classProdutos = classProdutoRepository.findAll();
        assertThat(classProdutos).hasSize(databaseSizeBeforeUpdate);
        ClassProduto testClassProduto = classProdutos.get(classProdutos.size() - 1);
        assertThat(testClassProduto.getCdClassProduto()).isEqualTo(UPDATED_CD_CLASS_PRODUTO);
        assertThat(testClassProduto.getnmClassProduto()).isEqualTo(UPDATED_NM_CLASS_PRODUTO);

        // Validate the ClassProduto in ElasticSearch
        ClassProduto classProdutoEs = classProdutoSearchRepository.findOne(testClassProduto.getId());
        assertThat(classProdutoEs).isEqualToComparingFieldByField(testClassProduto);
    }

    @Test
    @Transactional
    public void deleteClassProduto() throws Exception {
        // Initialize the database
        classProdutoService.save(classProduto);

        int databaseSizeBeforeDelete = classProdutoRepository.findAll().size();

        // Get the classProduto
        restClassProdutoMockMvc.perform(delete("/api/class-produtos/{id}", classProduto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean classProdutoExistsInEs = classProdutoSearchRepository.exists(classProduto.getId());
        assertThat(classProdutoExistsInEs).isFalse();

        // Validate the database is empty
        List<ClassProduto> classProdutos = classProdutoRepository.findAll();
        assertThat(classProdutos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchClassProduto() throws Exception {
        // Initialize the database
        classProdutoService.save(classProduto);

        // Search the classProduto
        restClassProdutoMockMvc.perform(get("/api/_search/class-produtos?query=id:" + classProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cdClassProduto").value(hasItem(DEFAULT_CD_CLASS_PRODUTO.toString())))
            .andExpect(jsonPath("$.[*].nmClassProduto").value(hasItem(DEFAULT_NM_CLASS_PRODUTO.toString())));
    }
}
