package com.Infnet.O.Registro.da.Guilda.DTO;


import com.Infnet.O.Registro.da.Guilda.DTO.Enum.ClasseAventureiro;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AventureiroDTO(
        Long id,
       @NotBlank String nome,
       @NotNull ClasseAventureiro classe ,
        @NotNull Integer nivel,
        Boolean ativo,
       Companheiro companheiro
) {

}
