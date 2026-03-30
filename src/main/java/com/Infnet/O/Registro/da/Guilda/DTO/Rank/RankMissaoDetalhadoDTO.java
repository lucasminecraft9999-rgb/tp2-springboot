package com.Infnet.O.Registro.da.Guilda.DTO.Rank;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;

public record RankMissaoDetalhadoDTO(
        String nome,
        Missao.Status status ,
        Missao.NivelPerigo nivelPerigo,
        long totalDeParticipantes ,
        Long somaDeRecompensasRecebidas
) {
}
