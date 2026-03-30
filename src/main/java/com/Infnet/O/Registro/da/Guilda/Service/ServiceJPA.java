package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.DetalhesDoAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Companheiro.CompanheiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.PartipantesDaMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.repository.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ServiceJPA {


    // Repositorios
    // ==================================================================================================//

    private final AventureiroRepository repository;
    private final participacaoRepository participacaoRepository;
    private final missaoRepository missaoRepository;
    private final CompanheiroRepository companheiroRepository;

    public ServiceJPA(AventureiroRepository repository, participacaoRepository participacaoRepository , missaoRepository missaoRepository , CompanheiroRepository companheiroRepository) {
        this.repository = repository;
        this.participacaoRepository = participacaoRepository;
        this.missaoRepository = missaoRepository;
        this.companheiroRepository = companheiroRepository;
    }

    public Page<AventureiroDTO> filtrar(FiltroGlobal filtroAventureiro, Pageable pageable) {
        Page<Aventureiro> aventureiros = repository.findByAtivoAndClasseAndNivel(
                filtroAventureiro.ativo(),
                filtroAventureiro.classe(),
                filtroAventureiro.nivel(),
                pageable
        );

        return aventureiros.map(aventureiro -> new AventureiroDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse().name(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
        ));

    }


    public Page<AventureiroDTO> buscarNome(String nome , Pageable pageable) {
    Page<Aventureiro> aventureiros = repository.findByNomeContainingIgnoreCase(nome , pageable);

    return aventureiros.map(aventureiro1 -> new AventureiroDTO(
            aventureiro1.getId(),
            aventureiro1.getNome(),
            aventureiro1.getClasse().name(),
            aventureiro1.getNivel(),
            aventureiro1.isAtivo()
    ));
    }


    public DetalhesDoAventureiroDTO buscarPorCompleto(Long id) {
        Aventureiro aventureiro = repository.findById(id).orElseThrow();

        Optional<ParticipacaoEmMissao> participacaoEmMIssao = participacaoRepository.findFirstByAventureiroIdOrderByDataDeRegistroDesc(id);

        AventureiroDTO aventureiroDTO = new AventureiroDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse().name(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
                );

            return new DetalhesDoAventureiroDTO(
                    aventureiroDTO,
                    Optional.ofNullable(aventureiro.getCompanheiro())
                            .map(Companheiro::getNome)
                            .orElse("Nenhum"),
                    participacaoRepository.countByAventureiroId(id),

                    participacaoEmMIssao.map(p -> p.getMissao().getTitulo())
                            .orElse("Nenhuma missão registrada")

            );

    }


    // buscar missao

    public Page<MissaoDTO> buscarMissao(FiltroGlobal filtroMissao , Pageable pageable) {

        Page<Missao> missaos = missaoRepository.filtrarMissoes(
                filtroMissao.status(),
                filtroMissao.perigo(),
                filtroMissao.inicio(),
                filtroMissao.fim(),
                pageable
        );


    return missaos.map(missao -> new MissaoDTO(
            missao.getId(),
            missao.getTitulo(),
            missao.getStatus().name(),
            missao.getNivelPerigo().name(),
            missao.getDataCriacao(),
            missao.getDataInicio(),
            missao.getDataTermino()
    ));
    }

    // detalhes da missao

    public DetalharMissaoDTO detalharMissao(Long id) {
        Missao missao = missaoRepository.findById(id).orElseThrow();

        List<ParticipacaoEmMissao> participacao = participacaoRepository.findByMissaoId(id);

        List<PartipantesDaMissaoDTO> partipantesDaMissaoDTOS = participacao.stream().map(p -> new PartipantesDaMissaoDTO(
        p.getAventureiro().getId(),
        p.getAventureiro().getNome(),
        p.getPapelNaMissao().name(),
        p.getRecompensaEmOuro(),
        p.getDestaqueMVP()
        )).toList();



        return new DetalharMissaoDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus().name(),
                missao.getNivelPerigo().name(),
                missao.getDataCriacao(),
                partipantesDaMissaoDTOS
        );

    }


    // buscar rank
    public List<RankAventureiroDTO> buscarRank(FiltroGlobal filtroRank){
    List<RankAventureiroDTO> ranking = participacaoRepository.gerarRank(filtroRank.inicio() , filtroRank.fim() , filtroRank.status());
    return ranking;
    }

    // detalhes do rank

    public List<RankMissaoDetalhadoDTO> DetalhesRank(FiltroGlobal filtroRank) {
    List<RankMissaoDetalhadoDTO> detalhes = participacaoRepository.gerarRankMissao(filtroRank.inicio() ,filtroRank.fim());
    return detalhes;
    }


    // adicionar aventureiro

    public AventureiroDTO adicionarAventureiro(@Valid AventureiroDTO aventureiroDTO ){

        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setNome(aventureiroDTO.nome());
        aventureiro.setNivel(aventureiroDTO.nivel());
        aventureiro.setAtivo(aventureiroDTO.ativo());

        aventureiro.setClasse(Aventureiro.ClasseAventureiro.valueOf(aventureiroDTO.classe().toUpperCase()));

        Aventureiro aventureiroSalvo = repository.save(aventureiro);

        return new AventureiroDTO(
                aventureiroSalvo.getId(),
                aventureiroSalvo.getNome(),
                aventureiroSalvo.getClasse().name(),
                aventureiroSalvo.getNivel(),
                aventureiroSalvo.isAtivo()
        );

    }
// atualizar aventureiro

    public AventureiroDTO atualizar(Long id , AventureiroDTO aventureiroDTO) {

        Aventureiro aventureiro = repository.findById(id).orElseThrow(() -> new RuntimeException("Aventureiro nao encontrado"));

        aventureiro.setNome(aventureiroDTO.nome());
        aventureiro.setNivel(aventureiroDTO.nivel());
        aventureiro.setAtivo(aventureiroDTO.ativo());
        aventureiro.setClasse(Aventureiro.ClasseAventureiro.buscar(aventureiroDTO.classe()));

        Aventureiro aventureiroSalvo = repository.save(aventureiro);

        return new AventureiroDTO(
                aventureiroSalvo.getId(),
                aventureiroSalvo.getNome(),
                aventureiroSalvo.getClasse().name(),
                aventureiroSalvo.getNivel(),
                aventureiroSalvo.isAtivo()
        );
    }



    // atualizar status do aventureiro

    public AventureiroDTO statusAtivo(Long id, boolean ativo) {

        Aventureiro aventureiro = repository.findById(id).orElseThrow(() -> new RuntimeException("Aventureiro nao encontrado"));

        aventureiro.setAtivo(ativo);

        Aventureiro aventureiroSalvo = repository.save(aventureiro);

        return new AventureiroDTO(
                aventureiroSalvo.getId(),
                aventureiroSalvo.getNome(),
                aventureiroSalvo.getClasse().name(),
                aventureiroSalvo.getNivel(),
                aventureiroSalvo.isAtivo()
        );
    }

    // deletar companheiro

    public AventureiroDTO deletarCompanheiro(Long id) {
        Companheiro companheiro = companheiroRepository.findById(id).orElseThrow(() -> new RuntimeException("Companheiro nao encontrado"));

        Aventureiro aventureiro = companheiro.getAventureiro();
        aventureiro.setCompanheiro(null);
        repository.save(aventureiro);
        companheiroRepository.delete(companheiro);

        return new AventureiroDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse().name(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
        );
    }


    // adicionar companheiro
    // perguntar pro professor!
    public CompanheiroDTO atualizarCompanheiro(Long id , CompanheiroDTO companheiroDTO) {
        Aventureiro aventureiro = repository.findById(id).orElseThrow(() -> new RuntimeException("Aventureiro nao encontrado"));


      Companheiro companheiro = aventureiro.getCompanheiro();

      if (companheiro != null) {
          companheiro = new Companheiro();
          companheiro.setAventureiro(aventureiro);
          companheiro.setOrganizacao(aventureiro.getOrganizacao());
          aventureiro.setCompanheiro(companheiro);
      }

        companheiro.setNome(companheiroDTO.nome());
        companheiro.setEspecie(Companheiro.Especie.buscar(companheiroDTO.especie()));
        companheiro.setLealdade(companheiroDTO.lealdade());


        Companheiro companheiroSalvo = companheiroRepository.save(companheiro);
        return new CompanheiroDTO(
                companheiroSalvo.getNome(),
                companheiroSalvo.getEspecie().name(),
                companheiroSalvo.getLealdade()
        );
    }
}