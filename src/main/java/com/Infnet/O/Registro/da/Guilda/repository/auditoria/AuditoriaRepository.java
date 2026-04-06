package com.Infnet.O.Registro.da.Guilda.repository.auditoria;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    @Query("""
 SELECT a FROM Auditoria a
  LEFT JOIN a.organizacao o
   LEFT JOIN a.actor_user_id u
  """)
    List<Auditoria> buscarAuditorias();

}
