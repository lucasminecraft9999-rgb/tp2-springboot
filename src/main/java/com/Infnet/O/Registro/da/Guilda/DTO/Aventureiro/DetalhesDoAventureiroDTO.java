package com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro;

public record DetalhesDoAventureiroDTO(
        AventureiroDTO aventureiro,
        String nomeCompanheiro,
        Long totalParticipacoes,
        String ultimaMissao
) {}