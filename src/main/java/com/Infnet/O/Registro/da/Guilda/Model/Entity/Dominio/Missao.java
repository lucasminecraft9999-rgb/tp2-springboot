package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "missao" , schema = "aventura")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Missao {

@Id
@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
private Long id;

@ManyToOne
@JoinColumn(name = "organizacao_id" , nullable = false)
private Organizacao organizacao;

@Column(name = "titulo" , length = 150 , nullable = false)
private String titulo;


public enum NivelPerigo {
BAIXO, MEDIO, ALTO , CRITICO
}

@Enumerated(EnumType.STRING)
@Column(name = "nivel_perigo" , nullable = false)
private NivelPerigo nivelPerigo;


public enum Status {
    PLANEJADA ,
    EM_ANDAMENTO ,
    CONCLUIDA ,
    CANCELADA
}

@Enumerated(EnumType.STRING)
@Column(name = "status" , nullable = false)
private Status status;

@Column(name = "data_criacao" , nullable = false ,updatable = false)
@CreationTimestamp
private LocalDateTime dataCriacao;

@Column(name = "data_inicio")
private LocalDateTime dataInicio;

@Column(name = "data_termino")
private LocalDateTime dataTermino;

}
