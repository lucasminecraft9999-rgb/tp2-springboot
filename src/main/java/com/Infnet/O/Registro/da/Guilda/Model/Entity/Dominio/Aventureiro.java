package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "aventureiro" , schema = "aventura")
@AllArgsConstructor@Setter@Getter@NoArgsConstructor
@ToString
public class Aventureiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id" , nullable = false)
    private Organizacao organizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id" , nullable = false)
    private Usuario usuarioResponsavelPeloCadastro;

    @Column(name = "nome", length = 120 , nullable = false)
    private String nome;


    public enum ClasseAventureiro {
        GUERREIRO,
        MAGO,
        ARQUEIRO,
        CLERIGO,
        LADINO;

        // aqui coloquei um static para para se o usuario nao conseguir digitar uma classe invalida
        public static ClasseAventureiro buscar(String texto) {
            return Arrays.stream(values()).filter(c -> c.name().equalsIgnoreCase(texto)).
                    findFirst().
                    orElseThrow(() -> new RuntimeException("Classe " + texto + " invalido!"));
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "classe" , nullable = false)
      private ClasseAventureiro classe;


    @Column(name = "nivel" , nullable = false)
    @NotNull @Min(1)
    private Integer nivel;


    @Column(name = "ativo" , nullable = false)
      private boolean ativo;

    @Column(name = "data_criacao" , updatable = false , nullable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao" , nullable = false)
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;
}
