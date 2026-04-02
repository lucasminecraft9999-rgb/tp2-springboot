package com.Infnet.O.Registro.da.Guilda.Controle;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceMissao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missao")
@Validated
@RequiredArgsConstructor
public class ControlerMissao {

    private final ServiceMissao serviceMissao;



    // filtrar e listar missoes
    @GetMapping
    public ResponseEntity<Page<MissaoDTO>> ListarMissoes(FiltroGlobal filtroMissao , Pageable pageable){
        return ResponseEntity.ok(serviceMissao.buscarMissao(filtroMissao , pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalharMissaoDTO> detalharMissao(@PathVariable Long id){
        return ResponseEntity.ok(serviceMissao.detalharMissao(id));
    }

}
