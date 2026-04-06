package com.Infnet.O.Registro.da.Guilda.repository;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CompanheiroRepository extends JpaRepository <Companheiro, Long> {


    @Query("""
    SELECT c
    FROM Companheiro c
    JOIN FETCH c.aventureiro
    JOIN FETCH c.organizacao
""")
    List<Companheiro> buscarCompanheiros();

}
