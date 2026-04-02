package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.DetalhesDoAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.repository.aventureiro.AventureiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.companheiro.CompanheiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.missao.MissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.participacao.ParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.Optional;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceAventureiro {


    // Repositorios
// ==================================================================================================//

    private final AventureiroRepository repository;
    private final ParticipacaoRepository participacaoRepository;


// ==================================================================================================//


    public Page<AventureiroDTO> filtrar(FiltroGlobal filtroAventureiro, Pageable pageable) {
        return  repository.findByFiltro(
            filtroAventureiro.ativo(),
            filtroAventureiro.classe(),
            filtroAventureiro.nivel(),
            pageable
    );
    }



    public Page<AventureiroDTO> buscarNome(String nome , Pageable pageable) {
     return repository.buscarPorNome(nome , pageable);

    }


    public DetalhesDoAventureiroDTO buscarPorCompleto(Long id) {
        Aventureiro aventureiro = repository.findById(id).orElseThrow();

        Optional<ParticipacaoEmMissao> participacaoEmMIssao = participacaoRepository.findFirstByAventureiroIdOrderByDataDeRegistroDesc(id);

        AventureiroDTO aventureiroDTO = new AventureiroDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
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


    }
