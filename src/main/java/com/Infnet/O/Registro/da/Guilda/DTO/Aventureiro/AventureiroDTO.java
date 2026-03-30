package com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AventureiroDTO(
        Long id,
       @NotBlank String nome,
       @NotNull String classe ,
        @NotNull Integer nivel,
        Boolean ativo
) {

}
