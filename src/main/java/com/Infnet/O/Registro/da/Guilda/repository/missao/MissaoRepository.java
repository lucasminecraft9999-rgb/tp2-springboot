package com.Infnet.O.Registro.da.Guilda.repository.missao;

import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MissaoRepository extends JpaRepository<Missao , Long> {



    @Query("""
        SELECT m
        FROM Missao m
        JOIN FETCH m.organizacao
    """)
    List<Missao> buscarMissoes();


    @Query("""
    SELECT NEW com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO(
        m.id, m.titulo, m.status, m.nivelPerigo, m.dataCriacao, m.dataInicio, m.dataTermino
    )
    FROM Missao m 
    WHERE (:status IS NULL OR m.status = :status) 
      AND (:perigo IS NULL OR m.nivelPerigo = :perigo) 
      AND (CAST(:inicio AS timestamp) IS NULL OR m.dataCriacao >= CAST(:inicio AS timestamp)) 
      AND (CAST(:fim AS timestamp) IS NULL OR m.dataTermino <= CAST(:fim AS timestamp))
""")
    Page<MissaoDTO> filtrarMissoes(
            Missao.Status status,
            Missao.NivelPerigo perigo,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable
    );
}


