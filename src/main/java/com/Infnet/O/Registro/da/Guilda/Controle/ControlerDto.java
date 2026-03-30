package com.Infnet.O.Registro.da.Guilda.Controle;


import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.DetalhesDoAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Companheiro.CompanheiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceJPA;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;



@RestController
@RequestMapping("/guildas")
@Validated
@RequiredArgsConstructor
public class ControlerDto {

    private final ServiceJPA serviceJPA;

  //filtrar

    @GetMapping("/aventureiros")
    public ResponseEntity<Page<AventureiroDTO>> filtro(
            @Valid FiltroGlobal filtroAventureiro, Pageable pageable) {

        return  ResponseEntity.ok(serviceJPA.filtrar(filtroAventureiro, pageable));
    }


    @GetMapping("aventureiros/peril/nome/{nome}")
    public ResponseEntity<Page<AventureiroDTO>> getNome(
            @PathVariable String nome , Pageable pageable
    ){
        return ResponseEntity.ok(serviceJPA.buscarNome(nome , pageable));
    }

    @GetMapping("/aventureiros/perfil/{id}")
    public ResponseEntity<DetalhesDoAventureiroDTO> verHistorico(@PathVariable Long id){
        DetalhesDoAventureiroDTO detalhesDoAventureiroDTO = serviceJPA.buscarPorCompleto(id);
        return ResponseEntity.ok(detalhesDoAventureiroDTO);
    }




// filtrar e listar missoes
    @GetMapping("/missoes")
    public ResponseEntity<Page<MissaoDTO>> ListarMissoes(FiltroGlobal filtroMissao , Pageable pageable){
        return ResponseEntity.ok(serviceJPA.buscarMissao(filtroMissao , pageable));
    }

    @GetMapping("/missoes/{id}")
    public ResponseEntity<DetalharMissaoDTO> detalharMissao(@PathVariable Long id){
        return ResponseEntity.ok(serviceJPA.detalharMissao(id));
    }

// filtrar listar rank

    @GetMapping("rank")
    public ResponseEntity<List<RankAventureiroDTO>> buscarRank(FiltroGlobal filtroRank){
        return ResponseEntity.ok(serviceJPA.buscarRank(filtroRank));
    }


    @GetMapping("rank/periodo")
    public ResponseEntity<List<RankMissaoDetalhadoDTO>> DetalhesRank(FiltroGlobal filtroRank){
        return ResponseEntity.ok(serviceJPA.DetalhesRank(filtroRank));
    }


    // atualizar companheiro
    @PutMapping("aventureiros/companheiro/{id}")
    public ResponseEntity<CompanheiroDTO> atualizarCompanheiro(@PathVariable Long id , @RequestBody @Valid CompanheiroDTO companheiroDTO){
        CompanheiroDTO companheiroDTO1 = serviceJPA.atualizarCompanheiro(id , companheiroDTO);
        return ResponseEntity.ok(companheiroDTO1);
    }


    // deletar companheiro
    @DeleteMapping("aventureiros/companheiro/deletar/{id}")
    public ResponseEntity<?> RemoverCompanheiro(@PathVariable Long id){
        serviceJPA.deletarCompanheiro(id);
        return ResponseEntity.noContent().build();
    }


    // adicionar aventureiro e atualizar

    @PostMapping("aventureiros/adicionar")
    public ResponseEntity<AventureiroDTO> adicionarAventureiro(@RequestBody @Valid AventureiroDTO aventureiroDTO) {
        AventureiroDTO aventureiroDTO1 = serviceJPA.adicionarAventureiro(aventureiroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(aventureiroDTO1);
    }


    @PutMapping("aventureiros/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id , @RequestBody AventureiroDTO atualizandoaventureiro){
        AventureiroDTO aventureiroDTO = serviceJPA.atualizar(id , atualizandoaventureiro);

        return ResponseEntity.ok(aventureiroDTO);
    }


    @PatchMapping("aventureiros/{id}/ativo")
    public ResponseEntity<AventureiroDTO> mudarStatus(@PathVariable Long id , @RequestParam boolean ativo) {
        AventureiroDTO aventureiroDTO = serviceJPA.statusAtivo(id , ativo);
        return ResponseEntity.ok(aventureiroDTO);
    }


}
