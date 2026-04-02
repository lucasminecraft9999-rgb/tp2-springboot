package com.Infnet.O.Registro.da.Guilda.repository.participacao;

import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParticipacaoRepository extends JpaRepository<ParticipacaoEmMissao, Long> {


    // buscar por completo
// ============================================================================================================================

    @Query("SELECT p FROM ParticipacaoEmMissao p WHERE p.aventureiro.id = :id ORDER BY p.dataDeRegistro DESC LIMIT 1")
    Optional<ParticipacaoEmMissao> findFirstByAventureiroIdOrderByDataDeRegistroDesc(Long id);

    Long countByAventureiroId(Long aventureiroId);

// ============================================================================================================================



    //

    @Query("SELECT p FROM ParticipacaoEmMissao p join fetch p.aventureiro where p.missao.id = :missaoId")
    List<ParticipacaoEmMissao> findByMissaoId(Long missaoId);


    @Query("select new com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO(" +
            "p.aventureiro.id, " +
            "p.aventureiro.nome, " +
            "count(p.id)," +
            "sum(p.recompensaEmOuro), " +
            "sum(case when p.destaqueMVP = true then 1 else 0 end))" +
            "from ParticipacaoEmMissao p "+
            "where p.dataDeRegistro BETWEEN :inicio and :fim " +
            "and (:status is null or p.missao.status = :status) " +
            "group by p.aventureiro.id , p.aventureiro.nome " +
            "order by sum(p.recompensaEmOuro) desc , count(p.id) desc ")
    List<RankAventureiroDTO> gerarRank(
            LocalDateTime inicio,
            LocalDateTime fim ,
            Missao.Status status);


    @Query("SELECT new com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO(" +
            "m.titulo, m.status, m.nivelPerigo, " +
            "COUNT(pa.id), SUM(pa.recompensaEmOuro)) " +
            "FROM ParticipacaoEmMissao pa JOIN pa.missao m   " +
            "WHERE m.dataCriacao BETWEEN :inicio AND :fim "  +
            "GROUP BY m.titulo, m.status, m.nivelPerigo " +
            "ORDER BY SUM(pa.recompensaEmOuro) DESC")
    List<RankMissaoDetalhadoDTO> gerarRankMissao(
            @Param("inicio")LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
            );



}
