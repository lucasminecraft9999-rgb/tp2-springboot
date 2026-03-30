package com.Infnet.O.Registro.da.Guilda.DTO.Companheiro;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;
import jakarta.validation.constraints.Max;



public record CompanheiroDTO(
        String nome ,
        String especie,
        @Max(100) int lealdade
) {
}
