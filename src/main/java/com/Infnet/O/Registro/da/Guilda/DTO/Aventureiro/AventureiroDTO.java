package com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AventureiroDTO(
        Long id,
       @NotBlank String nome,
       Aventureiro.ClasseAventureiro classe ,
        @NotNull Integer nivel,
        Boolean ativo
) {

}
