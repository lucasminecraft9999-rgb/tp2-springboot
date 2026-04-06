package com.Infnet.O.Registro.da.Guilda;


import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.DetalhesDoAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceMissao;
import com.Infnet.O.Registro.da.Guilda.repository.CompanheiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventureiro.AventureiroRepository;

import com.Infnet.O.Registro.da.Guilda.repository.missao.MissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.organizacao.OrganizacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.participacao.ParticipacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.usuario.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro.ClasseAventureiro.GUERREIRO;
import static com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro.ClasseAventureiro.MAGO;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Import(com.Infnet.O.Registro.da.Guilda.Service.ServiceAventureiro.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ServiceAventureiroTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    @Autowired
    private CompanheiroRepository companheiroRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoRepository participacaoRepository;


    @Autowired
    private com.Infnet.O.Registro.da.Guilda.Service.ServiceAventureiro serviceAventureiro;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        // organizacao
        Organizacao orgExistente = organizacaoRepository.findById(1L).orElseThrow(() -> new RuntimeException("Organizacao nao encontrada"));

        // Usuario
        Usuario usuarioExistente = usuarioRepository.findById(1L).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        // Aventureiro
        Aventureiro aventureiroLucas = aventureiroRepository.save(Aventureiro.builder().organizacao(orgExistente).usuarioResponsavelPeloCadastro(usuarioExistente).nome("Lucas").classe(GUERREIRO).nivel(50).ativo(true).build());

        // Companheiro
        Companheiro companheiro = companheiroRepository.save(Companheiro.builder().aventureiro(aventureiroLucas).nome("Zion").especie(Companheiro.Especie.LOBO).lealdade(100).organizacao(orgExistente).build());

        aventureiroLucas.setCompanheiro(companheiro);

        // criar 4 participacao da missao para aventureiroLucas
        for (int i = 1; i <= 4; i++){

            Missao missao = missaoRepository.save(Missao.builder().dataInicio(LocalDateTime.now()).dataTermino(LocalDateTime.now()).titulo("Missao " + i).nivelPerigo(Missao.NivelPerigo.CRITICO).status(Missao.Status.EM_ANDAMENTO).organizacao(orgExistente).build());
            participacaoRepository.save(ParticipacaoEmMissao.builder().dataDeRegistro(LocalDateTime.now()).destaqueMVP(true).papelNaMissao(ParticipacaoEmMissao.PapelNaMissao.LIDER).recompensaEmOuro(500L).aventureiro(aventureiroLucas).missao(missao).organizacao(orgExistente).build());

        }

        // criar 10 aventureiros aleatorios
        for (int i = 0; i < 10; i++)
        {
            Aventureiro aventureiro1 =  aventureiroRepository.save(Aventureiro.builder().organizacao(orgExistente).usuarioResponsavelPeloCadastro(usuarioExistente).nome("aventureiro: " + i).classe(MAGO).nivel(i + 10).ativo(true).build());
        }

    }





    @Test
    void ListarAventureiros(){
        Page<Aventureiro> aventureiros = aventureiroRepository.findAll(PageRequest.of(0, 10));
        assertThat(aventureiros).isNotNull();
        assertThat(aventureiros.getTotalElements()).isGreaterThan(0);

        aventureiros.forEach(aventureiro -> {
            String nomeCompanheiro = (aventureiro.getCompanheiro() != null)
                    ? aventureiro.getCompanheiro().getNome()
                    : "Nenhum";


            System.out.println("Aventureiro: " + aventureiro.getNome() + " | Companheiro: " + nomeCompanheiro + " | Classe:" + aventureiro.getClasse() + " | Nivel: " + aventureiro.getNivel() + " | Ativo: " + aventureiro.isAtivo());
        });

    }


    @Test
    void filtrarAventureirosPorClasse(){
        PageRequest pageable = PageRequest.of(0, 10);
        FiltroGlobal filtro = new FiltroGlobal(GUERREIRO , true , 50 , null , null , null , null);

        Page<AventureiroDTO> aventureiros = serviceAventureiro.filtrar(filtro , pageable);

        assertThat(aventureiros.getContent()).isNotEmpty();
        assertThat(aventureiros.getContent().get(0).nome()).isEqualTo("Lucas");

        System.out.println("Id: " + aventureiros.getContent().get(0).id() + " Aventureiro: " + aventureiros.getContent().get(0).nome() + " | Classe:" + aventureiros.getContent().get(0).classe() + " | Nivel: " + aventureiros.getContent().get(0).nivel() + " | Ativo: " + aventureiros.getContent().get(0).ativo());
    }


    @Test
    void deveFiltrarPorNome(){
        PageRequest pageable = PageRequest.of(0, 10);
        Page<AventureiroDTO> aventureiros = serviceAventureiro.buscarNome("Lucas" , pageable);

        assertThat(aventureiros.getContent()).isNotEmpty();
        assertThat(aventureiros.getContent().get(0).nome()).isEqualTo("Lucas");


        System.out.println("Id: " + aventureiros.getContent().get(0).id() + " Aventureiro: " + aventureiros.getContent().get(0).nome() + " | Classe:" + aventureiros.getContent().get(0).classe() + " | Nivel: " + aventureiros.getContent().get(0).nivel() + " | Ativo: " + aventureiros.getContent().get(0).ativo());

    }


    @Test
    void BuscarPorCompleto(){

        entityManager.flush();

        entityManager.clear();

        Aventureiro lucas = aventureiroRepository.findAll().get(0);

        DetalhesDoAventureiroDTO aventureiros = serviceAventureiro.buscarPorCompleto(lucas.getId());

        System.out.println("Id: " + aventureiros.aventureiro().id() + " | Nome: " + aventureiros.aventureiro().nome() + " | Classe: " + aventureiros.aventureiro().classe() + " | Nivel: " + aventureiros.aventureiro().nivel() + " | Ativo: " + aventureiros.aventureiro().ativo() + " | Companheiro: " + aventureiros.nomeCompanheiro() + " | Missao: " + aventureiros.ultimaMissao());
    }

}


