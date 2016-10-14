package com.sth.gpweb.web.rest;

import com.sth.gpweb.GpwebApp;
import com.sth.gpweb.domain.Produto;
import com.sth.gpweb.repository.ProdutoRepository;
import com.sth.gpweb.service.ProdutoService;
import com.sth.gpweb.repository.search.ProdutoSearchRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProdutoResource REST controller.
 *
 * @see ProdutoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GpwebApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProdutoResourceIntTest {


    private static final Long DEFAULT_CD_PRODUTO = 1L;
    private static final Long UPDATED_CD_PRODUTO = 2L;
    private static final String DEFAULT_CD_BARRAS = "AAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_BARRAS = "BBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NM_PRODUTO = "A";
    private static final String UPDATED_NM_PRODUTO = "B";
    private static final String DEFAULT_CD_NCM = "AAAAAAAA";
    private static final String UPDATED_CD_NCM = "BBBBBBBB";
    private static final String DEFAULT_CD_GTIN = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_GTIN = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_ANP = "AAAAAAAAA";
    private static final String UPDATED_CD_ANP = "BBBBBBBBB";
    private static final String DEFAULT_DS_ANP = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DS_ANP = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_CONTA_CONTABIL = "AAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_CONTA_CONTABIL = "BBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_FL_BALANCA = false;
    private static final Boolean UPDATED_FL_BALANCA = true;

    private static final Boolean DEFAULT_FL_INATIVO = false;
    private static final Boolean UPDATED_FL_INATIVO = true;

    private static final Boolean DEFAULT_FL_SNGPC = false;
    private static final Boolean UPDATED_FL_SNGPC = true;

    private static final Boolean DEFAULT_FL_USO_PROLONGADO = false;
    private static final Boolean UPDATED_FL_USO_PROLONGADO = true;

    private static final BigDecimal DEFAULT_VL_VENDA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_VENDA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_ESTOQUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_ESTOQUE = new BigDecimal(2);
    private static final String DEFAULT_DS_INFORMACOES = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DS_INFORMACOES = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final byte[] DEFAULT_BL_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BL_IMAGEM = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BL_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BL_IMAGEM_CONTENT_TYPE = "image/png";

    @Inject
    private ProdutoRepository produtoRepository;

    @Inject
    private ProdutoService produtoService;

    @Inject
    private ProdutoSearchRepository produtoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProdutoResource produtoResource = new ProdutoResource();
        ReflectionTestUtils.setField(produtoResource, "produtoService", produtoService);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        produtoSearchRepository.deleteAll();
        produto = new Produto();
        produto.setCdProduto(DEFAULT_CD_PRODUTO);
        produto.setCdBarras(DEFAULT_CD_BARRAS);
        produto.setNmProduto(DEFAULT_NM_PRODUTO);
        produto.setCdNcm(DEFAULT_CD_NCM);
        produto.setCdGtin(DEFAULT_CD_GTIN);
        produto.setCdAnp(DEFAULT_CD_ANP);
        produto.setDsAnp(DEFAULT_DS_ANP);
        produto.setCdContaContabil(DEFAULT_CD_CONTA_CONTABIL);
        produto.setFlBalanca(DEFAULT_FL_BALANCA);
        produto.setFlInativo(DEFAULT_FL_INATIVO);
        produto.setFlSngpc(DEFAULT_FL_SNGPC);
        produto.setFlUsoProlongado(DEFAULT_FL_USO_PROLONGADO);
        produto.setVlVenda(DEFAULT_VL_VENDA);
        produto.setVlEstoque(DEFAULT_VL_ESTOQUE);
        produto.setDsInformacoes(DEFAULT_DS_INFORMACOES);
        produto.setBlImagem(DEFAULT_BL_IMAGEM);
        produto.setBlImagemContentType(DEFAULT_BL_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto

        restProdutoMockMvc.perform(post("/api/produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produto)))
                .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtos.get(produtos.size() - 1);
        assertThat(testProduto.getCdProduto()).isEqualTo(DEFAULT_CD_PRODUTO);
        assertThat(testProduto.getCdBarras()).isEqualTo(DEFAULT_CD_BARRAS);
        assertThat(testProduto.getNmProduto()).isEqualTo(DEFAULT_NM_PRODUTO);
        assertThat(testProduto.getCdNcm()).isEqualTo(DEFAULT_CD_NCM);
        assertThat(testProduto.getCdGtin()).isEqualTo(DEFAULT_CD_GTIN);
        assertThat(testProduto.getCdAnp()).isEqualTo(DEFAULT_CD_ANP);
        assertThat(testProduto.getDsAnp()).isEqualTo(DEFAULT_DS_ANP);
        assertThat(testProduto.getCdContaContabil()).isEqualTo(DEFAULT_CD_CONTA_CONTABIL);
        assertThat(testProduto.isFlBalanca()).isEqualTo(DEFAULT_FL_BALANCA);
        assertThat(testProduto.isFlInativo()).isEqualTo(DEFAULT_FL_INATIVO);
        assertThat(testProduto.isFlSngpc()).isEqualTo(DEFAULT_FL_SNGPC);
        assertThat(testProduto.isFlUsoProlongado()).isEqualTo(DEFAULT_FL_USO_PROLONGADO);
        assertThat(testProduto.getVlVenda()).isEqualTo(DEFAULT_VL_VENDA);
        assertThat(testProduto.getVlEstoque()).isEqualTo(DEFAULT_VL_ESTOQUE);
        assertThat(testProduto.getDsInformacoes()).isEqualTo(DEFAULT_DS_INFORMACOES);
        assertThat(testProduto.getBlImagem()).isEqualTo(DEFAULT_BL_IMAGEM);
        assertThat(testProduto.getBlImagemContentType()).isEqualTo(DEFAULT_BL_IMAGEM_CONTENT_TYPE);

        // Validate the Produto in ElasticSearch
        Produto produtoEs = produtoSearchRepository.findOne(testProduto.getId());
        assertThat(produtoEs).isEqualToComparingFieldByField(testProduto);
    }

    @Test
    @Transactional
    public void checkNmProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNmProduto(null);

        // Create the Produto, which fails.

        restProdutoMockMvc.perform(post("/api/produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produto)))
                .andExpect(status().isBadRequest());

        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtos
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
                .andExpect(jsonPath("$.[*].cdProduto").value(hasItem(DEFAULT_CD_PRODUTO.intValue())))
                .andExpect(jsonPath("$.[*].cdBarras").value(hasItem(DEFAULT_CD_BARRAS.toString())))
                .andExpect(jsonPath("$.[*].nmProduto").value(hasItem(DEFAULT_NM_PRODUTO.toString())))
                .andExpect(jsonPath("$.[*].cdNcm").value(hasItem(DEFAULT_CD_NCM.toString())))
                .andExpect(jsonPath("$.[*].cdGtin").value(hasItem(DEFAULT_CD_GTIN.toString())))
                .andExpect(jsonPath("$.[*].cdAnp").value(hasItem(DEFAULT_CD_ANP.toString())))
                .andExpect(jsonPath("$.[*].dsAnp").value(hasItem(DEFAULT_DS_ANP.toString())))
                .andExpect(jsonPath("$.[*].cdContaContabil").value(hasItem(DEFAULT_CD_CONTA_CONTABIL.toString())))
                .andExpect(jsonPath("$.[*].flBalanca").value(hasItem(DEFAULT_FL_BALANCA.booleanValue())))
                .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].flSngpc").value(hasItem(DEFAULT_FL_SNGPC.booleanValue())))
                .andExpect(jsonPath("$.[*].flUsoProlongado").value(hasItem(DEFAULT_FL_USO_PROLONGADO.booleanValue())))
                .andExpect(jsonPath("$.[*].vlVenda").value(hasItem(DEFAULT_VL_VENDA.intValue())))
                .andExpect(jsonPath("$.[*].vlEstoque").value(hasItem(DEFAULT_VL_ESTOQUE.intValue())))
                .andExpect(jsonPath("$.[*].dsInformacoes").value(hasItem(DEFAULT_DS_INFORMACOES.toString())))
                .andExpect(jsonPath("$.[*].blImagemContentType").value(hasItem(DEFAULT_BL_IMAGEM_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].blImagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_BL_IMAGEM))));
    }

    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.cdProduto").value(DEFAULT_CD_PRODUTO.intValue()))
            .andExpect(jsonPath("$.cdBarras").value(DEFAULT_CD_BARRAS.toString()))
            .andExpect(jsonPath("$.nmProduto").value(DEFAULT_NM_PRODUTO.toString()))
            .andExpect(jsonPath("$.cdNcm").value(DEFAULT_CD_NCM.toString()))
            .andExpect(jsonPath("$.cdGtin").value(DEFAULT_CD_GTIN.toString()))
            .andExpect(jsonPath("$.cdAnp").value(DEFAULT_CD_ANP.toString()))
            .andExpect(jsonPath("$.dsAnp").value(DEFAULT_DS_ANP.toString()))
            .andExpect(jsonPath("$.cdContaContabil").value(DEFAULT_CD_CONTA_CONTABIL.toString()))
            .andExpect(jsonPath("$.flBalanca").value(DEFAULT_FL_BALANCA.booleanValue()))
            .andExpect(jsonPath("$.flInativo").value(DEFAULT_FL_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.flSngpc").value(DEFAULT_FL_SNGPC.booleanValue()))
            .andExpect(jsonPath("$.flUsoProlongado").value(DEFAULT_FL_USO_PROLONGADO.booleanValue()))
            .andExpect(jsonPath("$.vlVenda").value(DEFAULT_VL_VENDA.intValue()))
            .andExpect(jsonPath("$.vlEstoque").value(DEFAULT_VL_ESTOQUE.intValue()))
            .andExpect(jsonPath("$.dsInformacoes").value(DEFAULT_DS_INFORMACOES.toString()))
            .andExpect(jsonPath("$.blImagemContentType").value(DEFAULT_BL_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.blImagem").value(Base64Utils.encodeToString(DEFAULT_BL_IMAGEM)));
    }

    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoService.save(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = new Produto();
        updatedProduto.setId(produto.getId());
        updatedProduto.setCdProduto(UPDATED_CD_PRODUTO);
        updatedProduto.setCdBarras(UPDATED_CD_BARRAS);
        updatedProduto.setNmProduto(UPDATED_NM_PRODUTO);
        updatedProduto.setCdNcm(UPDATED_CD_NCM);
        updatedProduto.setCdGtin(UPDATED_CD_GTIN);
        updatedProduto.setCdAnp(UPDATED_CD_ANP);
        updatedProduto.setDsAnp(UPDATED_DS_ANP);
        updatedProduto.setCdContaContabil(UPDATED_CD_CONTA_CONTABIL);
        updatedProduto.setFlBalanca(UPDATED_FL_BALANCA);
        updatedProduto.setFlInativo(UPDATED_FL_INATIVO);
        updatedProduto.setFlSngpc(UPDATED_FL_SNGPC);
        updatedProduto.setFlUsoProlongado(UPDATED_FL_USO_PROLONGADO);
        updatedProduto.setVlVenda(UPDATED_VL_VENDA);
        updatedProduto.setVlEstoque(UPDATED_VL_ESTOQUE);
        updatedProduto.setDsInformacoes(UPDATED_DS_INFORMACOES);
        updatedProduto.setBlImagem(UPDATED_BL_IMAGEM);
        updatedProduto.setBlImagemContentType(UPDATED_BL_IMAGEM_CONTENT_TYPE);

        restProdutoMockMvc.perform(put("/api/produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProduto)))
                .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtos.get(produtos.size() - 1);
        assertThat(testProduto.getCdProduto()).isEqualTo(UPDATED_CD_PRODUTO);
        assertThat(testProduto.getCdBarras()).isEqualTo(UPDATED_CD_BARRAS);
        assertThat(testProduto.getNmProduto()).isEqualTo(UPDATED_NM_PRODUTO);
        assertThat(testProduto.getCdNcm()).isEqualTo(UPDATED_CD_NCM);
        assertThat(testProduto.getCdGtin()).isEqualTo(UPDATED_CD_GTIN);
        assertThat(testProduto.getCdAnp()).isEqualTo(UPDATED_CD_ANP);
        assertThat(testProduto.getDsAnp()).isEqualTo(UPDATED_DS_ANP);
        assertThat(testProduto.getCdContaContabil()).isEqualTo(UPDATED_CD_CONTA_CONTABIL);
        assertThat(testProduto.isFlBalanca()).isEqualTo(UPDATED_FL_BALANCA);
        assertThat(testProduto.isFlInativo()).isEqualTo(UPDATED_FL_INATIVO);
        assertThat(testProduto.isFlSngpc()).isEqualTo(UPDATED_FL_SNGPC);
        assertThat(testProduto.isFlUsoProlongado()).isEqualTo(UPDATED_FL_USO_PROLONGADO);
        assertThat(testProduto.getVlVenda()).isEqualTo(UPDATED_VL_VENDA);
        assertThat(testProduto.getVlEstoque()).isEqualTo(UPDATED_VL_ESTOQUE);
        assertThat(testProduto.getDsInformacoes()).isEqualTo(UPDATED_DS_INFORMACOES);
        assertThat(testProduto.getBlImagem()).isEqualTo(UPDATED_BL_IMAGEM);
        assertThat(testProduto.getBlImagemContentType()).isEqualTo(UPDATED_BL_IMAGEM_CONTENT_TYPE);

        // Validate the Produto in ElasticSearch
        Produto produtoEs = produtoSearchRepository.findOne(testProduto.getId());
        assertThat(produtoEs).isEqualToComparingFieldByField(testProduto);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoService.save(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Get the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean produtoExistsInEs = produtoSearchRepository.exists(produto.getId());
        assertThat(produtoExistsInEs).isFalse();

        // Validate the database is empty
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProduto() throws Exception {
        // Initialize the database
        produtoService.save(produto);

        // Search the produto
        restProdutoMockMvc.perform(get("/api/_search/produtos?query=id:" + produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cdProduto").value(hasItem(DEFAULT_CD_PRODUTO.intValue())))
            .andExpect(jsonPath("$.[*].cdBarras").value(hasItem(DEFAULT_CD_BARRAS.toString())))
            .andExpect(jsonPath("$.[*].nmProduto").value(hasItem(DEFAULT_NM_PRODUTO.toString())))
            .andExpect(jsonPath("$.[*].cdNcm").value(hasItem(DEFAULT_CD_NCM.toString())))
            .andExpect(jsonPath("$.[*].cdGtin").value(hasItem(DEFAULT_CD_GTIN.toString())))
            .andExpect(jsonPath("$.[*].cdAnp").value(hasItem(DEFAULT_CD_ANP.toString())))
            .andExpect(jsonPath("$.[*].dsAnp").value(hasItem(DEFAULT_DS_ANP.toString())))
            .andExpect(jsonPath("$.[*].cdContaContabil").value(hasItem(DEFAULT_CD_CONTA_CONTABIL.toString())))
            .andExpect(jsonPath("$.[*].flBalanca").value(hasItem(DEFAULT_FL_BALANCA.booleanValue())))
            .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].flSngpc").value(hasItem(DEFAULT_FL_SNGPC.booleanValue())))
            .andExpect(jsonPath("$.[*].flUsoProlongado").value(hasItem(DEFAULT_FL_USO_PROLONGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].vlVenda").value(hasItem(DEFAULT_VL_VENDA.intValue())))
            .andExpect(jsonPath("$.[*].vlEstoque").value(hasItem(DEFAULT_VL_ESTOQUE.intValue())))
            .andExpect(jsonPath("$.[*].dsInformacoes").value(hasItem(DEFAULT_DS_INFORMACOES.toString())))
            .andExpect(jsonPath("$.[*].blImagemContentType").value(hasItem(DEFAULT_BL_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blImagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_BL_IMAGEM))));
    }
}
