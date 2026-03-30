package com.Infnet.O.Registro.da.Guilda.DTO.Missao;

import java.time.LocalDateTime;

public record MissaoDTO(
        Long id,
        String titulo ,
        String status,
        String nivelPerigo,
        LocalDateTime dataCriacao,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino
        ) {}
