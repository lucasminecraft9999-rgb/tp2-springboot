package com.Infnet.O.Registro.da.Guilda.repository;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface missaoRepository extends JpaRepository<Missao , Long> {

    @Query("SELECT m FROM Missao m WHERE " +
            "(:status IS NULL OR m.status = :status) AND " +
            "(:perigo IS NULL OR m.nivelPerigo = :perigo) AND " +
            "(COALESCE(:inicio, m.dataCriacao) <= m.dataCriacao) AND " +
            "(COALESCE(:fim, m.dataCriacao) >= m.dataCriacao)")
    Page<Missao> filtrarMissoes(
            Missao.Status status ,
            Missao.NivelPerigo perigo,
            LocalDateTime inicio ,
            LocalDateTime fim,
            Pageable pageable
    );



}


