package com.Infnet.O.Registro.da.Guilda.repository.missao;

import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface MissaoRepository extends JpaRepository<Missao , Long> {

    @Query("""
        SELECT NEW com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO(
            m.id,
            m.titulo,
            m.status,
            m.nivelPerigo,
            m.dataCriacao,
            m.dataInicio,
            m.dataTermino
        )
        FROM Missao m 
        WHERE (:status IS NULL OR m.status = :status) 
          AND (:perigo IS NULL OR m.nivelPerigo = :perigo) 
          AND (:inicio IS NULL OR m.dataCriacao >= :inicio) 
          AND (:fim IS NULL OR m.dataCriacao <= :fim)
    """)
    Page<MissaoDTO> filtrarMissoes(
            Missao.Status status,
            Missao.NivelPerigo perigo,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable
    );



}


