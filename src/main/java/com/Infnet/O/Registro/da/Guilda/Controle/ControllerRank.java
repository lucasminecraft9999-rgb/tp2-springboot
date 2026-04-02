package com.Infnet.O.Registro.da.Guilda.Controle;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceRank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
@Validated
@RequiredArgsConstructor
public class ControllerRank {
    private final ServiceRank serviceRank;


    @GetMapping
    public ResponseEntity<List<RankAventureiroDTO>> buscarRank(FiltroGlobal filtroRank){
        return ResponseEntity.ok(serviceRank.buscarRank(filtroRank));
    }


    @GetMapping("/periodo")
    public ResponseEntity<List<RankMissaoDetalhadoDTO>> DetalhesRank(FiltroGlobal filtroRank){
        return ResponseEntity.ok(serviceRank.DetalhesRank(filtroRank));
    }


}
