package com.Infnet.O.Registro.da.Guilda.repository.aventureiro;


import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    @Query("""
 SELECT NEW com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO(
  a.id,
   a.nome,
    a.classe,
     a.nivel,
      a.ativo
  ) 
   FROM Aventureiro a 
   WHERE (:ativo is null or a.ativo = :ativo)
     AND (:classe is null or a.classe = :classe)
      AND (:nivel is null or a.nivel >= :nivel) 
       """)
    Page<AventureiroDTO> findByFiltro(Boolean ativo, Aventureiro.ClasseAventureiro classe, Integer nivel, Pageable pageable);




    @Query("""
SELECT NEW com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO(
  a.id,
  a.nome,
  a.classe,
  a.nivel,
  a.ativo
) 
FROM Aventureiro a 
WHERE a.nome LIKE %:nome%
""")
    Page<AventureiroDTO> buscarPorNome(String nome, Pageable pageable);








}

