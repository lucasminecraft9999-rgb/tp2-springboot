package com.Infnet.O.Registro.da.Guilda.Model.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roles" , schema = "audit",uniqueConstraints = {
        @UniqueConstraint(name = "uk_organizacao_nome" , columnNames = {"organizacao_id" , "nome"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne
@JoinColumn(name = "organizacao_id" , nullable = false)
private Organizacao organizacao;

@Column(name = "nome" , nullable = false)
private String nome;


@Column(name = "descricao" , nullable = false)
private String descricao;

@Column(name = "created_at" , nullable = false)
private LocalDateTime createdAt;

@ManyToMany
    @JoinTable(name = "role_permissions" ,
    joinColumns = @JoinColumn(name = "role_id") ,
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

}
