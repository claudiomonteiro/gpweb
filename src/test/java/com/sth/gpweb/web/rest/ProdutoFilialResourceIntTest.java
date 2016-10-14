package com.sth.gpweb.web.rest;

import com.sth.gpweb.GpwebApp;
import com.sth.gpweb.domain.ProdutoFilial;
import com.sth.gpweb.repository.ProdutoFilialRepository;
import com.sth.gpweb.service.ProdutoFilialService;
import com.sth.gpweb.repository.search.ProdutoFilialSearchRepository;

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
 * Test class for the ProdutoFilialResource REST controller.
 *
 * @see ProdutoFilialResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GpwebApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProdutoFilialResourceIntTest {


    private static final BigDecimal DEFAULT_QT_EMBALAGEM = new BigDecimal(1);
    private static final BigDecimal UPDATED_QT_EMBALAGEM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QT_MIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_QT_MIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QT_MAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_QT_MAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QT_SALDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_QT_SALDO = new BigDecimal(2);

    private static final LocalDate DEFAULT_DT_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VL_COMPRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_COMPRA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_CUSTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_CUSTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_CUSTO_MEDIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_CUSTO_MEDIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_MARKP_VENDA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_MARKP_VENDA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_MARKP_ATACADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_MARKP_ATACADO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_ATA_VISTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_ATA_VISTA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_MARKP_APRAZO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_MARKP_APRAZO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_APRAZO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_APRAZO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_MARKP_ATA_PRAZO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_MARKP_ATA_PRAZO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_ATA_A_PRAZO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_ATA_A_PRAZO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QT_ATACADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_QT_ATACADO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QT_BONIFICACAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_QT_BONIFICACAO = new BigDecimal(2);

    private static final Boolean DEFAULT_FL_INATIVO = false;
    private static final Boolean UPDATED_FL_INATIVO = true;

    private static final Boolean DEFAULT_FL_MATERIA_PRIMA = false;
    private static final Boolean UPDATED_FL_MATERIA_PRIMA = true;

    private static final Boolean DEFAULT_FL_COMPOSTO = false;
    private static final Boolean UPDATED_FL_COMPOSTO = true;

    private static final Integer DEFAULT_NN_PONTOS = 1;
    private static final Integer UPDATED_NN_PONTOS = 2;

    @Inject
    private ProdutoFilialRepository produtoFilialRepository;

    @Inject
    private ProdutoFilialService produtoFilialService;

    @Inject
    private ProdutoFilialSearchRepository produtoFilialSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProdutoFilialMockMvc;

    private ProdutoFilial produtoFilial;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProdutoFilialResource produtoFilialResource = new ProdutoFilialResource();
        ReflectionTestUtils.setField(produtoFilialResource, "produtoFilialService", produtoFilialService);
        this.restProdutoFilialMockMvc = MockMvcBuilders.standaloneSetup(produtoFilialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        produtoFilialSearchRepository.deleteAll();
        produtoFilial = new ProdutoFilial();
        produtoFilial.setQtEmbalagem(DEFAULT_QT_EMBALAGEM);
        produtoFilial.setQtMin(DEFAULT_QT_MIN);
        produtoFilial.setQtMax(DEFAULT_QT_MAX);
        produtoFilial.setQtSaldo(DEFAULT_QT_SALDO);
        produtoFilial.setDtVencimento(DEFAULT_DT_VENCIMENTO);
        produtoFilial.setVlCompra(DEFAULT_VL_COMPRA);
        produtoFilial.setVlCusto(DEFAULT_VL_CUSTO);
        produtoFilial.setVlCustoMedio(DEFAULT_VL_CUSTO_MEDIO);
        produtoFilial.setVlMarkpVenda(DEFAULT_VL_MARKP_VENDA);
        produtoFilial.setVlMarkpAtacado(DEFAULT_VL_MARKP_ATACADO);
        produtoFilial.setVlAtaVista(DEFAULT_VL_ATA_VISTA);
        produtoFilial.setVlMarkpAprazo(DEFAULT_VL_MARKP_APRAZO);
        produtoFilial.setVlAprazo(DEFAULT_VL_APRAZO);
        produtoFilial.setVlMarkpAtaPrazo(DEFAULT_VL_MARKP_ATA_PRAZO);
        produtoFilial.setVlAtaAPrazo(DEFAULT_VL_ATA_A_PRAZO);
        produtoFilial.setQtAtacado(DEFAULT_QT_ATACADO);
        produtoFilial.setQtBonificacao(DEFAULT_QT_BONIFICACAO);
        produtoFilial.setFlInativo(DEFAULT_FL_INATIVO);
        produtoFilial.setFlMateriaPrima(DEFAULT_FL_MATERIA_PRIMA);
        produtoFilial.setFlComposto(DEFAULT_FL_COMPOSTO);
        produtoFilial.setNnPontos(DEFAULT_NN_PONTOS);
    }

    @Test
    @Transactional
    public void createProdutoFilial() throws Exception {
        int databaseSizeBeforeCreate = produtoFilialRepository.findAll().size();

        // Create the ProdutoFilial

        restProdutoFilialMockMvc.perform(post("/api/produto-filials")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produtoFilial)))
                .andExpect(status().isCreated());

        // Validate the ProdutoFilial in the database
        List<ProdutoFilial> produtoFilials = produtoFilialRepository.findAll();
        assertThat(produtoFilials).hasSize(databaseSizeBeforeCreate + 1);
        ProdutoFilial testProdutoFilial = produtoFilials.get(produtoFilials.size() - 1);
        assertThat(testProdutoFilial.getQtEmbalagem()).isEqualTo(DEFAULT_QT_EMBALAGEM);
        assertThat(testProdutoFilial.getQtMin()).isEqualTo(DEFAULT_QT_MIN);
        assertThat(testProdutoFilial.getQtMax()).isEqualTo(DEFAULT_QT_MAX);
        assertThat(testProdutoFilial.getQtSaldo()).isEqualTo(DEFAULT_QT_SALDO);
        assertThat(testProdutoFilial.getDtVencimento()).isEqualTo(DEFAULT_DT_VENCIMENTO);
        assertThat(testProdutoFilial.getVlCompra()).isEqualTo(DEFAULT_VL_COMPRA);
        assertThat(testProdutoFilial.getVlCusto()).isEqualTo(DEFAULT_VL_CUSTO);
        assertThat(testProdutoFilial.getVlCustoMedio()).isEqualTo(DEFAULT_VL_CUSTO_MEDIO);
        assertThat(testProdutoFilial.getVlMarkpVenda()).isEqualTo(DEFAULT_VL_MARKP_VENDA);
        assertThat(testProdutoFilial.getVlMarkpAtacado()).isEqualTo(DEFAULT_VL_MARKP_ATACADO);
        assertThat(testProdutoFilial.getVlAtaVista()).isEqualTo(DEFAULT_VL_ATA_VISTA);
        assertThat(testProdutoFilial.getVlMarkpAprazo()).isEqualTo(DEFAULT_VL_MARKP_APRAZO);
        assertThat(testProdutoFilial.getVlAprazo()).isEqualTo(DEFAULT_VL_APRAZO);
        assertThat(testProdutoFilial.getVlMarkpAtaPrazo()).isEqualTo(DEFAULT_VL_MARKP_ATA_PRAZO);
        assertThat(testProdutoFilial.getVlAtaAPrazo()).isEqualTo(DEFAULT_VL_ATA_A_PRAZO);
        assertThat(testProdutoFilial.getQtAtacado()).isEqualTo(DEFAULT_QT_ATACADO);
        assertThat(testProdutoFilial.getQtBonificacao()).isEqualTo(DEFAULT_QT_BONIFICACAO);
        assertThat(testProdutoFilial.isFlInativo()).isEqualTo(DEFAULT_FL_INATIVO);
        assertThat(testProdutoFilial.isFlMateriaPrima()).isEqualTo(DEFAULT_FL_MATERIA_PRIMA);
        assertThat(testProdutoFilial.isFlComposto()).isEqualTo(DEFAULT_FL_COMPOSTO);
        assertThat(testProdutoFilial.getNnPontos()).isEqualTo(DEFAULT_NN_PONTOS);

        // Validate the ProdutoFilial in ElasticSearch
        ProdutoFilial produtoFilialEs = produtoFilialSearchRepository.findOne(testProdutoFilial.getId());
        assertThat(produtoFilialEs).isEqualToComparingFieldByField(testProdutoFilial);
    }

    @Test
    @Transactional
    public void getAllProdutoFilials() throws Exception {
        // Initialize the database
        produtoFilialRepository.saveAndFlush(produtoFilial);

        // Get all the produtoFilials
        restProdutoFilialMockMvc.perform(get("/api/produto-filials?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(produtoFilial.getId().intValue())))
                .andExpect(jsonPath("$.[*].qtEmbalagem").value(hasItem(DEFAULT_QT_EMBALAGEM.intValue())))
                .andExpect(jsonPath("$.[*].qtMin").value(hasItem(DEFAULT_QT_MIN.intValue())))
                .andExpect(jsonPath("$.[*].qtMax").value(hasItem(DEFAULT_QT_MAX.intValue())))
                .andExpect(jsonPath("$.[*].qtSaldo").value(hasItem(DEFAULT_QT_SALDO.intValue())))
                .andExpect(jsonPath("$.[*].dtVencimento").value(hasItem(DEFAULT_DT_VENCIMENTO.toString())))
                .andExpect(jsonPath("$.[*].vlCompra").value(hasItem(DEFAULT_VL_COMPRA.intValue())))
                .andExpect(jsonPath("$.[*].vlCusto").value(hasItem(DEFAULT_VL_CUSTO.intValue())))
                .andExpect(jsonPath("$.[*].vlCustoMedio").value(hasItem(DEFAULT_VL_CUSTO_MEDIO.intValue())))
                .andExpect(jsonPath("$.[*].vlMarkpVenda").value(hasItem(DEFAULT_VL_MARKP_VENDA.intValue())))
                .andExpect(jsonPath("$.[*].vlMarkpAtacado").value(hasItem(DEFAULT_VL_MARKP_ATACADO.intValue())))
                .andExpect(jsonPath("$.[*].vlAtaVista").value(hasItem(DEFAULT_VL_ATA_VISTA.intValue())))
                .andExpect(jsonPath("$.[*].vlMarkpAprazo").value(hasItem(DEFAULT_VL_MARKP_APRAZO.intValue())))
                .andExpect(jsonPath("$.[*].vlAprazo").value(hasItem(DEFAULT_VL_APRAZO.intValue())))
                .andExpect(jsonPath("$.[*].vlMarkpAtaPrazo").value(hasItem(DEFAULT_VL_MARKP_ATA_PRAZO.intValue())))
                .andExpect(jsonPath("$.[*].vlAtaAPrazo").value(hasItem(DEFAULT_VL_ATA_A_PRAZO.intValue())))
                .andExpect(jsonPath("$.[*].qtAtacado").value(hasItem(DEFAULT_QT_ATACADO.intValue())))
                .andExpect(jsonPath("$.[*].qtBonificacao").value(hasItem(DEFAULT_QT_BONIFICACAO.intValue())))
                .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].flMateriaPrima").value(hasItem(DEFAULT_FL_MATERIA_PRIMA.booleanValue())))
                .andExpect(jsonPath("$.[*].flComposto").value(hasItem(DEFAULT_FL_COMPOSTO.booleanValue())))
                .andExpect(jsonPath("$.[*].nnPontos").value(hasItem(DEFAULT_NN_PONTOS)));
    }

    @Test
    @Transactional
    public void getProdutoFilial() throws Exception {
        // Initialize the database
        produtoFilialRepository.saveAndFlush(produtoFilial);

        // Get the produtoFilial
        restProdutoFilialMockMvc.perform(get("/api/produto-filials/{id}", produtoFilial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(produtoFilial.getId().intValue()))
            .andExpect(jsonPath("$.qtEmbalagem").value(DEFAULT_QT_EMBALAGEM.intValue()))
            .andExpect(jsonPath("$.qtMin").value(DEFAULT_QT_MIN.intValue()))
            .andExpect(jsonPath("$.qtMax").value(DEFAULT_QT_MAX.intValue()))
            .andExpect(jsonPath("$.qtSaldo").value(DEFAULT_QT_SALDO.intValue()))
            .andExpect(jsonPath("$.dtVencimento").value(DEFAULT_DT_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.vlCompra").value(DEFAULT_VL_COMPRA.intValue()))
            .andExpect(jsonPath("$.vlCusto").value(DEFAULT_VL_CUSTO.intValue()))
            .andExpect(jsonPath("$.vlCustoMedio").value(DEFAULT_VL_CUSTO_MEDIO.intValue()))
            .andExpect(jsonPath("$.vlMarkpVenda").value(DEFAULT_VL_MARKP_VENDA.intValue()))
            .andExpect(jsonPath("$.vlMarkpAtacado").value(DEFAULT_VL_MARKP_ATACADO.intValue()))
            .andExpect(jsonPath("$.vlAtaVista").value(DEFAULT_VL_ATA_VISTA.intValue()))
            .andExpect(jsonPath("$.vlMarkpAprazo").value(DEFAULT_VL_MARKP_APRAZO.intValue()))
            .andExpect(jsonPath("$.vlAprazo").value(DEFAULT_VL_APRAZO.intValue()))
            .andExpect(jsonPath("$.vlMarkpAtaPrazo").value(DEFAULT_VL_MARKP_ATA_PRAZO.intValue()))
            .andExpect(jsonPath("$.vlAtaAPrazo").value(DEFAULT_VL_ATA_A_PRAZO.intValue()))
            .andExpect(jsonPath("$.qtAtacado").value(DEFAULT_QT_ATACADO.intValue()))
            .andExpect(jsonPath("$.qtBonificacao").value(DEFAULT_QT_BONIFICACAO.intValue()))
            .andExpect(jsonPath("$.flInativo").value(DEFAULT_FL_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.flMateriaPrima").value(DEFAULT_FL_MATERIA_PRIMA.booleanValue()))
            .andExpect(jsonPath("$.flComposto").value(DEFAULT_FL_COMPOSTO.booleanValue()))
            .andExpect(jsonPath("$.nnPontos").value(DEFAULT_NN_PONTOS));
    }

    @Test
    @Transactional
    public void getNonExistingProdutoFilial() throws Exception {
        // Get the produtoFilial
        restProdutoFilialMockMvc.perform(get("/api/produto-filials/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdutoFilial() throws Exception {
        // Initialize the database
        produtoFilialService.save(produtoFilial);

        int databaseSizeBeforeUpdate = produtoFilialRepository.findAll().size();

        // Update the produtoFilial
        ProdutoFilial updatedProdutoFilial = new ProdutoFilial();
        updatedProdutoFilial.setId(produtoFilial.getId());
        updatedProdutoFilial.setQtEmbalagem(UPDATED_QT_EMBALAGEM);
        updatedProdutoFilial.setQtMin(UPDATED_QT_MIN);
        updatedProdutoFilial.setQtMax(UPDATED_QT_MAX);
        updatedProdutoFilial.setQtSaldo(UPDATED_QT_SALDO);
        updatedProdutoFilial.setDtVencimento(UPDATED_DT_VENCIMENTO);
        updatedProdutoFilial.setVlCompra(UPDATED_VL_COMPRA);
        updatedProdutoFilial.setVlCusto(UPDATED_VL_CUSTO);
        updatedProdutoFilial.setVlCustoMedio(UPDATED_VL_CUSTO_MEDIO);
        updatedProdutoFilial.setVlMarkpVenda(UPDATED_VL_MARKP_VENDA);
        updatedProdutoFilial.setVlMarkpAtacado(UPDATED_VL_MARKP_ATACADO);
        updatedProdutoFilial.setVlAtaVista(UPDATED_VL_ATA_VISTA);
        updatedProdutoFilial.setVlMarkpAprazo(UPDATED_VL_MARKP_APRAZO);
        updatedProdutoFilial.setVlAprazo(UPDATED_VL_APRAZO);
        updatedProdutoFilial.setVlMarkpAtaPrazo(UPDATED_VL_MARKP_ATA_PRAZO);
        updatedProdutoFilial.setVlAtaAPrazo(UPDATED_VL_ATA_A_PRAZO);
        updatedProdutoFilial.setQtAtacado(UPDATED_QT_ATACADO);
        updatedProdutoFilial.setQtBonificacao(UPDATED_QT_BONIFICACAO);
        updatedProdutoFilial.setFlInativo(UPDATED_FL_INATIVO);
        updatedProdutoFilial.setFlMateriaPrima(UPDATED_FL_MATERIA_PRIMA);
        updatedProdutoFilial.setFlComposto(UPDATED_FL_COMPOSTO);
        updatedProdutoFilial.setNnPontos(UPDATED_NN_PONTOS);

        restProdutoFilialMockMvc.perform(put("/api/produto-filials")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProdutoFilial)))
                .andExpect(status().isOk());

        // Validate the ProdutoFilial in the database
        List<ProdutoFilial> produtoFilials = produtoFilialRepository.findAll();
        assertThat(produtoFilials).hasSize(databaseSizeBeforeUpdate);
        ProdutoFilial testProdutoFilial = produtoFilials.get(produtoFilials.size() - 1);
        assertThat(testProdutoFilial.getQtEmbalagem()).isEqualTo(UPDATED_QT_EMBALAGEM);
        assertThat(testProdutoFilial.getQtMin()).isEqualTo(UPDATED_QT_MIN);
        assertThat(testProdutoFilial.getQtMax()).isEqualTo(UPDATED_QT_MAX);
        assertThat(testProdutoFilial.getQtSaldo()).isEqualTo(UPDATED_QT_SALDO);
        assertThat(testProdutoFilial.getDtVencimento()).isEqualTo(UPDATED_DT_VENCIMENTO);
        assertThat(testProdutoFilial.getVlCompra()).isEqualTo(UPDATED_VL_COMPRA);
        assertThat(testProdutoFilial.getVlCusto()).isEqualTo(UPDATED_VL_CUSTO);
        assertThat(testProdutoFilial.getVlCustoMedio()).isEqualTo(UPDATED_VL_CUSTO_MEDIO);
        assertThat(testProdutoFilial.getVlMarkpVenda()).isEqualTo(UPDATED_VL_MARKP_VENDA);
        assertThat(testProdutoFilial.getVlMarkpAtacado()).isEqualTo(UPDATED_VL_MARKP_ATACADO);
        assertThat(testProdutoFilial.getVlAtaVista()).isEqualTo(UPDATED_VL_ATA_VISTA);
        assertThat(testProdutoFilial.getVlMarkpAprazo()).isEqualTo(UPDATED_VL_MARKP_APRAZO);
        assertThat(testProdutoFilial.getVlAprazo()).isEqualTo(UPDATED_VL_APRAZO);
        assertThat(testProdutoFilial.getVlMarkpAtaPrazo()).isEqualTo(UPDATED_VL_MARKP_ATA_PRAZO);
        assertThat(testProdutoFilial.getVlAtaAPrazo()).isEqualTo(UPDATED_VL_ATA_A_PRAZO);
        assertThat(testProdutoFilial.getQtAtacado()).isEqualTo(UPDATED_QT_ATACADO);
        assertThat(testProdutoFilial.getQtBonificacao()).isEqualTo(UPDATED_QT_BONIFICACAO);
        assertThat(testProdutoFilial.isFlInativo()).isEqualTo(UPDATED_FL_INATIVO);
        assertThat(testProdutoFilial.isFlMateriaPrima()).isEqualTo(UPDATED_FL_MATERIA_PRIMA);
        assertThat(testProdutoFilial.isFlComposto()).isEqualTo(UPDATED_FL_COMPOSTO);
        assertThat(testProdutoFilial.getNnPontos()).isEqualTo(UPDATED_NN_PONTOS);

        // Validate the ProdutoFilial in ElasticSearch
        ProdutoFilial produtoFilialEs = produtoFilialSearchRepository.findOne(testProdutoFilial.getId());
        assertThat(produtoFilialEs).isEqualToComparingFieldByField(testProdutoFilial);
    }

    @Test
    @Transactional
    public void deleteProdutoFilial() throws Exception {
        // Initialize the database
        produtoFilialService.save(produtoFilial);

        int databaseSizeBeforeDelete = produtoFilialRepository.findAll().size();

        // Get the produtoFilial
        restProdutoFilialMockMvc.perform(delete("/api/produto-filials/{id}", produtoFilial.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean produtoFilialExistsInEs = produtoFilialSearchRepository.exists(produtoFilial.getId());
        assertThat(produtoFilialExistsInEs).isFalse();

        // Validate the database is empty
        List<ProdutoFilial> produtoFilials = produtoFilialRepository.findAll();
        assertThat(produtoFilials).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProdutoFilial() throws Exception {
        // Initialize the database
        produtoFilialService.save(produtoFilial);

        // Search the produtoFilial
        restProdutoFilialMockMvc.perform(get("/api/_search/produto-filials?query=id:" + produtoFilial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtoFilial.getId().intValue())))
            .andExpect(jsonPath("$.[*].qtEmbalagem").value(hasItem(DEFAULT_QT_EMBALAGEM.intValue())))
            .andExpect(jsonPath("$.[*].qtMin").value(hasItem(DEFAULT_QT_MIN.intValue())))
            .andExpect(jsonPath("$.[*].qtMax").value(hasItem(DEFAULT_QT_MAX.intValue())))
            .andExpect(jsonPath("$.[*].qtSaldo").value(hasItem(DEFAULT_QT_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].dtVencimento").value(hasItem(DEFAULT_DT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].vlCompra").value(hasItem(DEFAULT_VL_COMPRA.intValue())))
            .andExpect(jsonPath("$.[*].vlCusto").value(hasItem(DEFAULT_VL_CUSTO.intValue())))
            .andExpect(jsonPath("$.[*].vlCustoMedio").value(hasItem(DEFAULT_VL_CUSTO_MEDIO.intValue())))
            .andExpect(jsonPath("$.[*].vlMarkpVenda").value(hasItem(DEFAULT_VL_MARKP_VENDA.intValue())))
            .andExpect(jsonPath("$.[*].vlMarkpAtacado").value(hasItem(DEFAULT_VL_MARKP_ATACADO.intValue())))
            .andExpect(jsonPath("$.[*].vlAtaVista").value(hasItem(DEFAULT_VL_ATA_VISTA.intValue())))
            .andExpect(jsonPath("$.[*].vlMarkpAprazo").value(hasItem(DEFAULT_VL_MARKP_APRAZO.intValue())))
            .andExpect(jsonPath("$.[*].vlAprazo").value(hasItem(DEFAULT_VL_APRAZO.intValue())))
            .andExpect(jsonPath("$.[*].vlMarkpAtaPrazo").value(hasItem(DEFAULT_VL_MARKP_ATA_PRAZO.intValue())))
            .andExpect(jsonPath("$.[*].vlAtaAPrazo").value(hasItem(DEFAULT_VL_ATA_A_PRAZO.intValue())))
            .andExpect(jsonPath("$.[*].qtAtacado").value(hasItem(DEFAULT_QT_ATACADO.intValue())))
            .andExpect(jsonPath("$.[*].qtBonificacao").value(hasItem(DEFAULT_QT_BONIFICACAO.intValue())))
            .andExpect(jsonPath("$.[*].flInativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].flMateriaPrima").value(hasItem(DEFAULT_FL_MATERIA_PRIMA.booleanValue())))
            .andExpect(jsonPath("$.[*].flComposto").value(hasItem(DEFAULT_FL_COMPOSTO.booleanValue())))
            .andExpect(jsonPath("$.[*].nnPontos").value(hasItem(DEFAULT_NN_PONTOS)));
    }
}
