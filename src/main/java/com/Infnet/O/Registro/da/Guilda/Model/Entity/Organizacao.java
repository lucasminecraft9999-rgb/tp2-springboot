package com.Infnet.O.Registro.da.Guilda.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organizacoes" , schema = "audit")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Organizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome" , nullable = false, unique = true)
    private String nome;

    @Column(name = "ativo" , nullable = false )
    private Boolean ativo;

    @Column(name = "created_at" , nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organizacao")
    private List<Usuario> usuarios;
}
