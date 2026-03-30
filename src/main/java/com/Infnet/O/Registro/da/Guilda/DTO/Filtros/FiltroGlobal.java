package com.Infnet.O.Registro.da.Guilda.DTO.Filtros;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record FiltroGlobal(
        // Filtro aventureiro
        Aventureiro.ClasseAventureiro classe,
        Boolean ativo,
        @Min(0) Integer nivel,

        // filtro Missao

          Missao.Status status,
        Missao.NivelPerigo perigo ,

        // filtrar rank e missao por data

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim

) {
}
