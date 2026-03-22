package com.Infnet.O.Registro.da.Guilda.DTO;

import com.Infnet.O.Registro.da.Guilda.DTO.Enum.ClasseCompanheiro;
import jakarta.validation.constraints.Max;

public record Companheiro(
        String nome ,
        ClasseCompanheiro especie,
        @Max(100) int lealdade
) {
}
