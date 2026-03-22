package com.Infnet.O.Registro.da.Guilda.DTO;

import com.Infnet.O.Registro.da.Guilda.DTO.Enum.ClasseAventureiro;
import jakarta.validation.constraints.Min;

public record Filtro(
        ClasseAventureiro classe,
        Boolean ativo,
       @Min(0) Integer nivel
) {
}
