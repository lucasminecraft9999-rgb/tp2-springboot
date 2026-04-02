package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.repository.participacao.ParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceRank {

private final ParticipacaoRepository participacaoRepository;


    public List<RankAventureiroDTO> buscarRank(FiltroGlobal filtroRank){
        List<RankAventureiroDTO> ranking = participacaoRepository.gerarRank(filtroRank.inicio() , filtroRank.fim() , filtroRank.status());
        return ranking;
    }


    public List<RankMissaoDetalhadoDTO> DetalhesRank(FiltroGlobal filtroRank) {

        List<RankMissaoDetalhadoDTO> detalhes = participacaoRepository.gerarRankMissao(filtroRank.inicio() ,filtroRank.fim());

        return detalhes;
    }


}
