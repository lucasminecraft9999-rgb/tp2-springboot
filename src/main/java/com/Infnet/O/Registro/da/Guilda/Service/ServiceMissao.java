package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.PartipantesDaMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.repository.missao.MissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.participacao.ParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceMissao {

private final MissaoRepository missaoRepository;
private final ParticipacaoRepository participacaoRepository;



    public Page<MissaoDTO> buscarMissao(FiltroGlobal filtroMissao , Pageable pageable) {

        return missaoRepository.filtrarMissoes(
                filtroMissao.status(),
                filtroMissao.perigo(),
                filtroMissao.inicio(),
                filtroMissao.fim(),
                pageable
        );
    }



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



}
