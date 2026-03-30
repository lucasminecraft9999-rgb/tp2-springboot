package com.Infnet.O.Registro.da.Guilda.repository;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    @Query
            ("select a from Aventureiro a where " +
            "(:ativo is null or a.ativo = :ativo) and "  +
            "(:classe is null or a.classe = :classe) and "  +
            "(:nivel is null or a.nivel = :nivel)")
            Page<Aventureiro> findByAtivoAndClasseAndNivel(
            Boolean ativo,
            Aventureiro.ClasseAventureiro classe,
            Integer nivel,
            Pageable pageable
    );

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome , Pageable pageable);


}

