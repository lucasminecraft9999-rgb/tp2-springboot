package com.Infnet.O.Registro.da.Guilda.Model.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios", schema = "audit",uniqueConstraints = {
        @UniqueConstraint(name = "uk_organizacao_email" , columnNames = {"organizacao_id" , "email"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id" , nullable = false)
    private Organizacao organizacao;

    @Column(name = "nome" , nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha_hash" , nullable = false)
    private String senhaHash;


    @Column(name = "status" , nullable = false)
    private String status;

    @Column(name = "ultimo_login_em")
    private LocalDateTime ultimoLoginEm;

    @Column(name = "created_at" , nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "user_roles" ,
            joinColumns = @JoinColumn(name = "usuario_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id") )
    private List<Role> roles;

}
