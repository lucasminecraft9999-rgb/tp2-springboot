package com.Infnet.O.Registro.da.Guilda.DTO.Missao;

import java.time.LocalDateTime;
import java.util.List;

public record DetalharMissaoDTO(
        Long id,
        String titulo ,
        String status,
        String nivelPerigo,
        LocalDateTime dataCriacao,
        List<PartipantesDaMissaoDTO> participantes
) {
}
