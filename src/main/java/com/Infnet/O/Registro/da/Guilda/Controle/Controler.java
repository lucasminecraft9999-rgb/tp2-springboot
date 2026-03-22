package com.Infnet.O.Registro.da.Guilda.Controle;


import com.Infnet.O.Registro.da.Guilda.DTO.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Companheiro;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtro;
import com.Infnet.O.Registro.da.Guilda.Model.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Service.Service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guildas")
@Validated
@RequiredArgsConstructor
public class Controler {

    private final Service service;

    // listando aventureiros
    //http://localhost:8080/guildas
    @GetMapping
    public ResponseEntity<List<Aventureiro>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // paginando
    // http://localhost:8080/guildas/header?page=0&size=5&ativo=true
    @GetMapping("/header")
    public ResponseEntity<List<Aventureiro>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid Filtro filtro
    ) {
        List<Aventureiro> resultado = service.listarPaginado(page, size , filtro);

        int total = service.filtrar(filtro).size();
        int totalPage = (int) Math.ceil((double) total / size);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(total))
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Pages", String.valueOf(totalPage))
                .body(resultado);
    }


    // filtrando
    //http://localhost:8080/guildas/Filtro?ativo=true&nivel=100&classe=MAGO
    @GetMapping("/Filtro")
    public ResponseEntity<?> filtro(@Valid Filtro filtro) {
    return  ResponseEntity.ok(service.filtrar(filtro));
    }


    // buscando por id
    //http://localhost:8080/guildas/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@Positive @PathVariable Long id) {
        return service.findById(id).map(aventureiroDTO -> ResponseEntity.ok(aventureiroDTO)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //cadastrando aventureiro
    //http://localhost:8080/guildas
    /*
    {
  "nome": "Kaelthas",
  "classe": "MAGO",
  "nivel": 1
}
     */

    @PostMapping
    public ResponseEntity<?> adicionarAventureiro(@RequestBody AventureiroDTO aventureiroDTO) {
       Aventureiro novo = service.adicionarAventureiro(aventureiroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }


// Atualizando Aventureiro
//http://localhost:8080/guildas/3
/*
{
 json

  "nome": "gg",
  "classe": "GUERREIRO",
  "nivel": 55,
	"ativo": false
}
 */

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id , @RequestBody AventureiroDTO atualizandoaventureiro){
         return Optional.ofNullable(service.atualizar(id ,
                         atualizandoaventureiro.nome() ,
                         atualizandoaventureiro.classe() ,
                         atualizandoaventureiro.nivel()))
                 .map(a -> ResponseEntity.ok("O aventureiro " + atualizandoaventureiro.nome() + " foi atualizado com sucesso"))
                 .orElseGet(() -> ResponseEntity.notFound().build());

    }


    // alteranedo status de ativo
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> mudarStatus(@PathVariable Long id , @RequestParam boolean ativo) {
        return service.findById(id).map(aventureiro -> {
            aventureiro.setAtivo(ativo);
            String mensagem = ativo ? "ativado" : "desativado";
            return ResponseEntity.ok("O aventureiro " + aventureiro.getNome() + " foi " + mensagem);
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    // Adicionando ou criando Companheiro
    //http://localhost:8080/guildas/1/companheiro
    /*
    Json

    {
  "nome": "Triceraptors",
  "especie": "DRAGAO_MINIATURA",
  "lealdade": 10
}
     */

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<?> adicionarCompanheiro(@PathVariable Long id , @RequestBody Companheiro companheiro){
        return service.atualizarCompanheiro(id , companheiro)
                .map(a -> ResponseEntity.ok("O companheiro de " + a.getNome() + " foi atualizado para " + companheiro.nome() + "!"))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // deletando Companheiro
    //http://localhost:8080/guildas/1
    @DeleteMapping("{id}")
    public ResponseEntity<?> RemoverCompanheiro(@PathVariable Long id){
            service.RemoverCompanheiro(id);
        return ResponseEntity.noContent().build();
    }

}
