package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.*;
import com.Infnet.O.Registro.da.Guilda.DTO.Enum.ClasseAventureiro;
import com.Infnet.O.Registro.da.Guilda.ErroException.RunException;
import com.Infnet.O.Registro.da.Guilda.Model.Aventureiro;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@org.springframework.stereotype.Service
public class Service {


    // Aventureiros
    // ==================================================================================================//


    final List<Aventureiro> aventureiros = new ArrayList<>();
    final Random random = new Random();
    // cadastrando aventureiros
    // ============================================================================================//
    public Service() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            cadastrar("Aventureiro " + i,
                    ClasseAventureiro.values()[i % ClasseAventureiro.values().length],
                    random.nextInt(100) + 1,
                    null);
        });
    }

    public Long getProximoId() {
        return aventureiros.stream().mapToLong(Aventureiro::getId).max().orElse(0l) + 1;
    }

    public Aventureiro adicionarAventureiro(AventureiroDTO aventureiroDTO) {
        Long proximoID = getProximoId();
        Aventureiro aventureiro = new Aventureiro(proximoID, aventureiroDTO.nome(), aventureiroDTO.classe(), aventureiroDTO.nivel(), true, null);
        aventureiros.add(aventureiro);
        return aventureiro;
    }

    public void cadastrar(String nome, ClasseAventureiro classe, Integer nivel, Companheiro companheiro) {
        adicionarAventureiro(new AventureiroDTO(getProximoId(), nome, classe, nivel, true, null));
    }

    // ============================================================================================//


    public @Nullable List<Aventureiro> listar() {
        return aventureiros.stream().sorted((a1 , a2) -> a1.getId().compareTo(a2.getId())).toList();
    }

    public List<Aventureiro> filtrar(@Valid Filtro filtro) {
        return aventureiros.stream().
                filter(a -> filtro.classe() == null || filtro.classe().equals(a.getClasse())).
                filter(a -> filtro.ativo() == null || filtro.ativo().equals(a.isAtivo())).
                filter(a -> filtro.nivel() == null || a.getNivel() >= filtro.nivel())
                .sorted((a1, a2) -> a1.getId().compareTo(a2.getId()))
                .collect(Collectors.toList());
    }

    public List<Aventureiro> listarPaginado(int page, int size, Filtro filtro) {

        List<Aventureiro> filtrados = filtrar(filtro);

        int total = filtrados.size();
        int inicio = Math.min(page * size, total);
        int fim = Math.min(inicio + size, total);

        return filtrados.subList(inicio, fim)
                .stream()
                .map(a -> new Aventureiro(
                        a.getId(),
                        a.getNome(),
                        a.getClasse(),
                        a.getNivel(),
                        a.isAtivo(),
                        a.getCompanheiro()
                ))
                .toList();
    }

    public Optional<Aventureiro> findById(@Positive  Long id) {
        Optional<Aventureiro> first = aventureiros.stream().filter(A -> A.getId().equals(id)).findFirst();
        return first;
    }




    public Aventureiro atualizar(Long id, String novoNome, ClasseAventureiro novaClasse, Integer novoNivel) {
        return aventureiros.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(aventureiro -> {
                    aventureiro.setNome(novoNome);
                    aventureiro.setClasse(novaClasse);
                    aventureiro.setNivel(novoNivel);
                    return aventureiro;
                })
                .orElse(null);
    }


    public void RemoverCompanheiro(Long id) {
        aventureiros.stream().filter(a -> a.getId().equals(id)).findFirst().ifPresent(aventureiro -> aventureiro.setCompanheiro(null));

    }

    public Optional<Aventureiro> atualizarCompanheiro(Long id, Companheiro companheiro) {
        return findById(id).map(a -> {
            a.setCompanheiro(companheiro);
            return a;
        });
    }
}
