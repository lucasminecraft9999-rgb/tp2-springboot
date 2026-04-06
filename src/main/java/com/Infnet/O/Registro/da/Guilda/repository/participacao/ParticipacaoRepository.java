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



    @Query("""
    SELECT p
    FROM ParticipacaoEmMissao p
    JOIN p.missao m
    JOIN p.aventureiro a
    JOIN p.organizacao o
""")
    List<ParticipacaoEmMissao> buscarParticipacoes();



    // buscar por completo
// ============================================================================================================================

    @Query("SELECT p FROM ParticipacaoEmMissao p WHERE p.aventureiro.id = :id ORDER BY p.dataDeRegistro DESC LIMIT 1")
    Optional<ParticipacaoEmMissao> findFirstByAventureiroIdOrderByDataDeRegistroDesc(Long id);

    Long countByAventureiroId(Long aventureiroId);

// ============================================================================================================================

    @Query("SELECT p FROM ParticipacaoEmMissao p join fetch p.aventureiro where p.missao.id = :missaoId")
    List<ParticipacaoEmMissao> findByMissaoId(Long missaoId);



@Query("""
SELECT NEW com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO(
p.aventureiro.id , p.aventureiro.nome , count(p.id) , sum(p.recompensaEmOuro) , sum(case when p.destaqueMVP = true then 1 else 0 end)
)FROM ParticipacaoEmMissao p 
WHERE p.dataDeRegistro BETWEEN :inicio AND :fim 
AND (:status IS NULL OR p.missao.status = :status)
GROUP BY p.aventureiro.id , p.aventureiro.nome 
ORDER BY SUM(p.recompensaEmOuro) DESC , COUNT(p.id) DESC
""")
    List<RankAventureiroDTO> gerarRank(
            LocalDateTime inicio,
            LocalDateTime fim ,
            Missao.Status status);


    @Query("""
    SELECT new com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO(
        m.titulo, 
        m.status, 
        m.nivelPerigo, 
        COUNT(pa.id), 
        SUM(pa.recompensaEmOuro)
    )
    FROM ParticipacaoEmMissao pa 
    JOIN pa.missao m
    WHERE m.dataCriacao BETWEEN :inicio AND :fim
    GROUP BY m.titulo, m.status, m.nivelPerigo
    ORDER BY SUM(pa.recompensaEmOuro) DESC
""")
    List<RankMissaoDetalhadoDTO> gerarRankMissao(
            @Param("inicio")LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
            );

}
