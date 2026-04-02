package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Arrays;

@Entity
@Table(name = "companheiro" , schema = "aventura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Companheiro {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@OneToOne
@JoinColumn(name = "aventureiro_id", nullable = false, unique = true)
@OnDelete(action = OnDeleteAction.CASCADE)
private Aventureiro aventureiro;

@Column(name = "nome", length = 120 , nullable = false)
private String nome;

public enum Especie {
    LOBO ,
    CORUJA ,
    GOLEM ,
    DRAGAO_MINIATURA;

    public static Especie buscar(String texto) {
        return Arrays.stream(values()).filter(e -> e.name().equalsIgnoreCase(texto)).
                findFirst().
                orElseThrow(() -> new RuntimeException("Especie " + texto + " invalido!"));
    }
}

@Enumerated(EnumType.STRING)
@Column(name = "especie" , nullable = false)
private  Especie especie;

@Column(name = "lealdade" , nullable = false)
@Min(0)
@Max(100)
private int lealdade;

@ManyToOne
@JoinColumn(name = "organizacao_id" , nullable = false)
private Organizacao organizacao;
}
