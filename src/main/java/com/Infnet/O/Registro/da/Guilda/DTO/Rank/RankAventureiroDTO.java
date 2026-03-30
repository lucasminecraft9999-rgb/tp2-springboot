package com.Infnet.O.Registro.da.Guilda.DTO.Rank;

public record RankAventureiroDTO(
    Long id,
    String nome,
    long totalDeParticipantes ,
    Long somaDeRecompensasRecebidas,
    Long quantidadeDestaquesObtidos
) {
}
