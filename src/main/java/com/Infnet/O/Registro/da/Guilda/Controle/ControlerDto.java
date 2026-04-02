package com.Infnet.O.Registro.da.Guilda.Controle;


import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.DetalhesDoAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceAventureiro;

import com.Infnet.O.Registro.da.Guilda.Service.ServiceMissao;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceRank;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;



@RestController
@RequestMapping("/aventureiros")
@Validated
@RequiredArgsConstructor
public class ControlerDto {

    private final ServiceAventureiro serviceAventureiro;

    @GetMapping
    public ResponseEntity<Page<AventureiroDTO>> filtro(
            @Valid FiltroGlobal filtroAventureiro, Pageable pageable) {

        return  ResponseEntity.ok(serviceAventureiro.filtrar(filtroAventureiro, pageable));
    }


    @GetMapping("perfil/nome/{nome}")
    public ResponseEntity<Page<AventureiroDTO>> getNome(
            @PathVariable String nome , Pageable pageable
    ){
        return ResponseEntity.ok(serviceAventureiro.buscarNome(nome , pageable));
    }


    @GetMapping("perfil/{id}")
    public ResponseEntity<DetalhesDoAventureiroDTO> verHistorico(@PathVariable Long id){
        DetalhesDoAventureiroDTO detalhesDoAventureiroDTO = serviceAventureiro.buscarPorCompleto(id);
        return ResponseEntity.ok(detalhesDoAventureiroDTO);
    }


}
