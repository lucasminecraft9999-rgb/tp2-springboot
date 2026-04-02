package com.Infnet.O.Registro.da.Guilda.DTO.Missao;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;

import java.time.LocalDateTime;

public record MissaoDTO(
        Long id,
        String titulo ,
        Missao.Status status,
        Missao.NivelPerigo nivelPerigo,
        LocalDateTime dataCriacao,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino
        ) {}
