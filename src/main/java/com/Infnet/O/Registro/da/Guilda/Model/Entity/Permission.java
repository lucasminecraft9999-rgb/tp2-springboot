package com.Infnet.O.Registro.da.Guilda.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions" , schema = "audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code" , nullable = false , unique = true)
    private String code;

    @Column(name = "descricao" , nullable = false)
    private String descricao;

}
