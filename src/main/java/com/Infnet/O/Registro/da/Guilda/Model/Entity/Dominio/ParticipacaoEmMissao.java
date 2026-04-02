package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "participacao_em_missao" , schema = "aventura" , uniqueConstraints = @UniqueConstraint(columnNames = {"aventureiro_id" , "missao_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoEmMissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    public enum PapelNaMissao {
        LIDER,
        SUPORTE,
        ATAQUE,
        CURANDEIRO
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_na_missao", nullable = false)
    private PapelNaMissao papelNaMissao;

    @Column(name = "recompensa_em_ouro")
    @Min(0)
    private Long recompensaEmOuro;

    @Column(name = "destaque_mvp", nullable = false)
    private Boolean destaqueMVP;

    @Column(name = "data_de_registro", nullable = false , updatable = false)
    @CreationTimestamp
    private LocalDateTime dataDeRegistro;

    @ManyToOne
    @JoinColumn(name = "organizacao_id" , nullable = false)
    private Organizacao organizacao;


}

