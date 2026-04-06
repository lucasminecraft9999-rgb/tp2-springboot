# Nome: Lucas Silva De Souza

Para rodar o projeto
precisa digitar no terminal do projeto

```
docker compose up -d
```

O docker tem uma imagem que tem containers e dentro desse containers tem os schemas e dentro desse schemas tem as tabelas.

O schema chamado audit, ja tem as tabelas de usuario e organizacao para utilizar acessar os aventureiros da guilda, cada usuario e tem o seu aventureiro para logar, e o aventureiro tem a sua organizacao.

Porem para fazer que cada usuario e organizacao ter seu aventureiro precisarei mapeiar no springboot e fazer que os usuarios tenha seu aventureiro e organizacao.

Entidades:

Usuario:

```
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
```


Organizacao:

```
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

```

No schema audit ainda tem outras funçoes como Role, Permission, Auditoria

Roles: serve para saber o que o usuario é se ele e ADMIN , AUDITOR.
Permission: serve para que cada usuario tenha acesso area restrita do aventureiro como alterar, remover, adicionar, atualizar.
Auditoria(audit_entries): para saber se o api é csv ou banco de dados.

Role:

```
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

```

Permission:

```
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

```

Auditoria:

```
package com.Infnet.O.Registro.da.Guilda.Model.Entity;  
  
import jakarta.persistence.*;  
import lombok.*;  
  
import java.time.LocalDateTime;  
  
@Entity  
@Table(name = "audit_entries" , schema = "audit")  
@Getter  
@Setter  
@NoArgsConstructor  
@AllArgsConstructor  
public class Auditoria {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @ManyToOne  
    @JoinColumn(name = "organizacao_id" , nullable = true)  
    private Organizacao organizacao;  
  
    @ManyToOne  
    @JoinColumn(name = "actor_user_id" , nullable = true)  
    private Usuario actor_user_id;  
  
    @Column(name = "actor_api_key_id" , nullable = true)  
    private Long actorApiKey;  
  
    @Column(name = "action" , nullable = false)  
    private String action;  
  
    @Column(name = "entity_schema" , nullable = false)  
    private String entitySchema;  
  
    @Column(name = "entity_name" , nullable = false)  
    private String entityName;  
  
    @Column(name = "entity_id" , nullable = false)  
    private String entityId;  
  
    @Column(name = "occurred_at" , nullable = false)  
    private LocalDateTime occurredAt;  
  
    @Column(name = "ip", columnDefinition = "inet")  
    private String ip;  
  
    @Column(name = "user_agent", nullable = false)  
    private String userAgent;  
  
    @Column(name = "diff", columnDefinition = "jsonb")  
    private String diff;  
  
    @Column(name = "metadata", columnDefinition = "jsonb")  
    private String metadata;  
  
    @Column(name = "success" , nullable = false)  
    private Boolean success;  
  
}

```

Aqui farei mostratei o hibernate da criação:

HIBERNATE Auditoria:
```

Hibernate: 
    select
        a1_0.id,
        a1_0.action,
        a1_0.actor_api_key_id,
        a1_0.actor_user_id,
        a1_0.diff,
        a1_0.entity_id,
        a1_0.entity_name,
        a1_0.entity_schema,
        a1_0.ip,
        a1_0.metadata,
        a1_0.occurred_at,
        a1_0.organizacao_id,
        a1_0.success,
        a1_0.user_agent 
    from
        audit.audit_entries a1_0
Hibernate: 
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.email,
        u1_0.nome,
        u1_0.organizacao_id,
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome,
        u1_0.senha_hash,
        u1_0.status,
        u1_0.ultimo_login_em,
        u1_0.updated_at 
    from
        audit.usuarios u1_0 
    join
        audit.organizacoes o1_0 
            on o1_0.id=u1_0.organizacao_id 
    where
        u1_0.id=?
2026-04-06T08:13:07.143-03:00 TRACE 12251 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
Hibernate: 
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.email,
        u1_0.nome,
        u1_0.organizacao_id,
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome,
        u1_0.senha_hash,
        u1_0.status,
        u1_0.ultimo_login_em,
        u1_0.updated_at 
    from
        audit.usuarios u1_0 
    join
        audit.organizacoes o1_0 
            on o1_0.id=u1_0.organizacao_id 
    where
        u1_0.id=?
        
```

Hibernate Permission:

```
Hibernate: 
    select
        p1_0.id,
        p1_0.code,
        p1_0.descricao 
    from
        audit.permissions p1_0
```


Hibernate Roles:

```
Hibernate: 
    select
        r1_0.id,
        r1_0.created_at,
        r1_0.descricao,
        r1_0.nome,
        r1_0.organizacao_id 
    from
        audit.roles r1_0
Hibernate: 
    select
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome 
    from
        audit.organizacoes o1_0 
    where
        o1_0.id=?
2026-04-06T08:18:54.300-03:00 TRACE 13085 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
Hibernate: 
    select
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome 
    from
        audit.organizacoes o1_0 
    where
        o1_0.id=?


```


Hibernate Organizacoes:
```
Hibernate: 
    select
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome 
    from
        audit.organizacoes o1_0
```


Hibernate Usuario:
```
Hibernate: 
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.email,
        u1_0.nome,
        u1_0.organizacao_id,
        r1_0.usuario_id,
        r1_1.id,
        r1_1.created_at,
        r1_1.descricao,
        r1_1.nome,
        r1_1.organizacao_id,
        u1_0.senha_hash,
        u1_0.status,
        u1_0.ultimo_login_em,
        u1_0.updated_at 
    from
        audit.usuarios u1_0 
    left join
        user_roles r1_0 
            on u1_0.id=r1_0.usuario_id 
    left join
        audit.roles r1_1 
            on r1_1.id=r1_0.role_id
Hibernate: 
    select
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome 
    from
        audit.organizacoes o1_0 
    where
        o1_0.id=?

```

---
## Agora mostratei que eu fiz com o shcema aventura;

no schema aventura preciso criar as entidades para criar e mapeiar as tabelas
assim coloquei dllo como UPDATE para que ele crie as tabelas e atualize:


Aqui estao as entidades do schema aventura criada:


Aventureiro:
```

package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;  
  
  
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;  
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;  
import jakarta.persistence.*;  
import jakarta.validation.constraints.Min;  
import jakarta.validation.constraints.NotNull;  
import lombok.*;  
  
import org.hibernate.annotations.CreationTimestamp;  
import org.hibernate.annotations.UpdateTimestamp;  
  
import java.time.LocalDateTime;  
import java.util.Arrays;  
  
@Entity  
@Table(name = "aventureiro" , schema = "aventura")  
@AllArgsConstructor@Setter@Getter@NoArgsConstructor  
@Builder  
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
```

Utilizei tanto para organizacoes e tanto usuario: muito para um(ManyToOne)

Usuario: Um usuario pode ter vários aventureiros

Organizacoes: Uma organizacao pode ter vários aventureiros.

Tambem coloquei o companheiro como um para um(OneToOne)

Companheiro: Um aventureiro pode ter apenas um companheiro e um companheiro só pode ter apenas um aventureiro



Companheiro

```

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
```

como dizia se no Aventureiro coloquei OneToOne em companheiro
aqui no Companheiro coloquei OneToOne em aventureiro

e organizacao tambem
uma organizacao pode ter varios companheiro


Missao:
```
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
@Builder  
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
```

ParticipacaoEmMissao:

```
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

```


os hibernates das entididades de dominio

Aventureiro:
```
Hibernate: 
    select
        a1_0.id,
        a1_0.ativo,
        a1_0.classe,
        a1_0.data_atualizacao,
        a1_0.data_criacao,
        a1_0.nivel,
        a1_0.nome,
        a1_0.organizacao_id,
        a1_0.usuario_id 
    from
        aventura.aventureiro a1_0

```


Companheiro:
```
Hibernate: 
    select
        c1_0.id,
        c1_0.aventureiro_id,
        a1_0.id,
        a1_0.ativo,
        a1_0.classe,
        a1_0.data_atualizacao,
        a1_0.data_criacao,
        a1_0.nivel,
        a1_0.nome,
        a1_0.organizacao_id,
        a1_0.usuario_id,
        c1_0.especie,
        c1_0.lealdade,
        c1_0.nome,
        c1_0.organizacao_id,
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome 
    from
        aventura.companheiro c1_0 
    join
        aventura.aventureiro a1_0 
            on a1_0.id=c1_0.aventureiro_id 
    join
        audit.organizacoes o1_0 
            on o1_0.id=c1_0.organizacao_id
```

ParticipacaoEmMissao:

```
Hibernate: 
    select
        pem1_0.id,
        pem1_0.aventureiro_id,
        pem1_0.data_de_registro,
        pem1_0.destaque_mvp,
        pem1_0.missao_id,
        pem1_0.organizacao_id,
        pem1_0.papel_na_missao,
        pem1_0.recompensa_em_ouro 
    from
        aventura.participacao_em_missao pem1_0


```

Missao:
```

Hibernate: 
    select
        m1_0.id,
        m1_0.data_criacao,
        m1_0.data_inicio,
        m1_0.data_termino,
        m1_0.nivel_perigo,
        m1_0.organizacao_id,
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome,
        m1_0.status,
        m1_0.titulo 
    from
        aventura.missao m1_0 
    join
        audit.organizacoes o1_0 
            on o1_0.id=m1_0.organizacao_id


```

---
## Perguntas e respostas:

### Como foi garantida a unicidade da participação

Utilizei a unicidade UniqueConstraint na colunas aventureiro_id e missao_id, para o aventureiro não participar da mesma missão mais de uma vez


### Como foi garantida a dependência entre Aventureiro e Companheiro

A dependência entre Aventureiro e companheiro foi modelada como um relacionamento um para um(@OneToOne), o aventureiro possa ter apenas um companheiro.

No lado do companheiro coloquei nullable = false para que todo companheiro deve existir ao lado do seu aventureiro, e coloquei ophanRemoval = true, para que toda associação entre o aventureiro e companheiro , o companheiro deve ser excluído automaticamente no banco de dados


### Como foram tratadas as regras de integridade descritas nos requisitos

As regras de integridade foram garantidas por meio de restrições no banco de dados e anotações do JPA. Foi utilizado nullable = false para impedir que campos obrigatórios sejam nulos, e unique = true para garantir a unicidade de determinados atributos.
Além disso, foi utilizado updatable = false em campos de data, assegurando que não sejam alterados após a criação. Também foi aplicada a anotação @UniqueConstraint para garantir a unicidade de combinações de atributos, impedindo a duplicidade de registros no sistema.

---
## Querys
aqui mostrarei que as tabelas estao sendo criadas do schema aventura e audit

primeiro mostrarei do schema audit


Usuario:
![[usuario.png]]

Organizacao:
![[organizacao.png]]


Role:
![[Roles.png]]

Permission:
![[permission.png]]

Auditoria:
![[Auditoria.png]]

---
## Tests

Os tests sobre os filtros eu criei primeiro no service para depois fazer o test, eu fiz isso porque não sabia que tinha como fazer pelo repositório.
Como eu fiz, tinha inserido manualmente em sql nas tabelas do schema aventura e assim fui fazendo os repositórios com a configuração do post em update

#### ServiceAventureiroTest:

inserindo os aventureiros e missoes para fazer os testes:

```
@BeforeEach  
void setUp(){  
    // organizacao  
    Organizacao orgExistente = organizacaoRepository.findById(1L).orElseThrow(() -> new RuntimeException("Organizacao nao encontrada"));  
  
    // Usuario  
    Usuario usuarioExistente = usuarioRepository.findById(1L).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));  
  
    // Aventureiro  
    Aventureiro aventureiroLucas = aventureiroRepository.save(Aventureiro.builder().organizacao(orgExistente).usuarioResponsavelPeloCadastro(usuarioExistente).nome("Lucas").classe(GUERREIRO).nivel(50).ativo(true).build());  
  
    // Companheiro  
    Companheiro companheiro = companheiroRepository.save(Companheiro.builder().aventureiro(aventureiroLucas).nome("Zion").especie(Companheiro.Especie.LOBO).lealdade(100).organizacao(orgExistente).build());  
  
    aventureiroLucas.setCompanheiro(companheiro);  
  
    // criar 4 participacao da missao para aventureiroLucas  
    for (int i = 1; i <= 4; i++){  
  
        Missao missao = missaoRepository.save(Missao.builder().dataInicio(LocalDateTime.now()).dataTermino(LocalDateTime.now()).titulo("Missao " + i).nivelPerigo(Missao.NivelPerigo.CRITICO).status(Missao.Status.EM_ANDAMENTO).organizacao(orgExistente).build());  
        participacaoRepository.save(ParticipacaoEmMissao.builder().dataDeRegistro(LocalDateTime.now()).destaqueMVP(true).papelNaMissao(ParticipacaoEmMissao.PapelNaMissao.LIDER).recompensaEmOuro(500L).aventureiro(aventureiroLucas).missao(missao).organizacao(orgExistente).build());  
  
    }  
  
    // criar 10 aventureiros aleatorios  
    for (int i = 0; i < 10; i++)  
    {  
        Aventureiro aventureiro1 =  aventureiroRepository.save(Aventureiro.builder().organizacao(orgExistente).usuarioResponsavelPeloCadastro(usuarioExistente).nome("aventureiro: " + i).classe(MAGO).nivel(i + 10).ativo(true).build());  
    }  
  
}

```

---
#### fazendo os tests:

```

@Test  
void filtrarAventureirosPorClasse(){  
    PageRequest pageable = PageRequest.of(0, 10);  
    FiltroGlobal filtro = new FiltroGlobal(GUERREIRO , true , 50 , null , null , null , null);  
  
    Page<AventureiroDTO> aventureiros = serviceAventureiro.filtrar(filtro , pageable);  
  
    assertThat(aventureiros.getContent()).isNotEmpty();  
    assertThat(aventureiros.getContent().get(0).nome()).isEqualTo("Lucas");  
  
    System.out.println("Id: " + aventureiros.getContent().get(0).id() + " Aventureiro: " + aventureiros.getContent().get(0).nome() + " | Classe:" + aventureiros.getContent().get(0).classe() + " | Nivel: " + aventureiros.getContent().get(0).nivel() + " | Ativo: " + aventureiros.getContent().get(0).ativo());  
}

```

resultado:
```
Hibernate: 
    select
        a1_0.id,
        a1_0.nome,
        a1_0.classe,
        a1_0.nivel,
        a1_0.ativo 
    from
        aventura.aventureiro a1_0 
    where
        (
            ? is null 
            or a1_0.ativo=?
        ) 
        and (
            ? is null 
            or a1_0.classe=?
        ) 
        and (
            ? is null 
            or a1_0.nivel>=?
        ) 
    fetch
        first ? rows only
2026-04-06T10:00:31.233-03:00 TRACE 23476 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:BOOLEAN) <- [true]
2026-04-06T10:00:31.233-03:00 TRACE 23476 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:BOOLEAN) <- [true]
2026-04-06T10:00:31.233-03:00 TRACE 23476 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [GUERREIRO]
2026-04-06T10:00:31.233-03:00 TRACE 23476 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (4:VARCHAR) <- [GUERREIRO]
2026-04-06T10:00:31.233-03:00 TRACE 23476 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (5:INTEGER) <- [50]
2026-04-06T10:00:31.233-03:00 TRACE 23476 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (6:INTEGER) <- [50]
2026-04-06T10:00:31.233-03:00 TRACE 23476 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (7:INTEGER) <- [10]
Id: 421 Aventureiro: Lucas | Classe:GUERREIRO | Nivel: 50 | Ativo: true

```




```
@Test  
void deveFiltrarPorNome(){  
    PageRequest pageable = PageRequest.of(0, 10);  
    Page<AventureiroDTO> aventureiros = serviceAventureiro.buscarNome("Lucas" , pageable);  
  
    assertThat(aventureiros.getContent()).isNotEmpty();  
    assertThat(aventureiros.getContent().get(0).nome()).isEqualTo("Lucas");  
  
  
    System.out.println("Id: " + aventureiros.getContent().get(0).id() + " Aventureiro: " + aventureiros.getContent().get(0).nome() + " | Classe:" + aventureiros.getContent().get(0).classe() + " | Nivel: " + aventureiros.getContent().get(0).nivel() + " | Ativo: " + aventureiros.getContent().get(0).ativo());  
  
}

```

resultado:

```

Hibernate: 
    select
        a1_0.id,
        a1_0.nome,
        a1_0.classe,
        a1_0.nivel,
        a1_0.ativo 
    from
        aventura.aventureiro a1_0 
    where
        a1_0.nome like ? escape '' 
    fetch
        first ? rows only
2026-04-06T10:01:33.179-03:00 TRACE 23642 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [%Lucas%]
2026-04-06T10:01:33.179-03:00 TRACE 23642 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:INTEGER) <- [10]
Id: 432 Aventureiro: Lucas | Classe:GUERREIRO | Nivel: 50 | Ativo: true

```


```

@Test  
void BuscarPorCompleto(){  
  
    entityManager.flush();  
  
    entityManager.clear();  
  
    Aventureiro lucas = aventureiroRepository.findAll().get(0);  
  
    DetalhesDoAventureiroDTO aventureiros = serviceAventureiro.buscarPorCompleto(lucas.getId());  
  
    System.out.println("Id: " + aventureiros.aventureiro().id() + " | Nome: " + aventureiros.aventureiro().nome() + " | Classe: " + aventureiros.aventureiro().classe() + " | Nivel: " + aventureiros.aventureiro().nivel() + " | Ativo: " + aventureiros.aventureiro().ativo() + " | Companheiro: " + aventureiros.nomeCompanheiro() + " | Missao: " + aventureiros.ultimaMissao());  
}

```

resultado:

```
Hibernate: 
    select
        count(pem1_0.id) 
    from
        aventura.participacao_em_missao pem1_0 
    left join
        aventura.aventureiro a1_0 
            on a1_0.id=pem1_0.aventureiro_id 
    where
        a1_0.id=?
2026-04-06T10:02:55.255-03:00 TRACE 23929 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [443]
Id: 443 | Nome: Lucas | Classe: GUERREIRO | Nivel: 50 | Ativo: true | Companheiro: Zion | Missao: Missao 4


```


---

#### ServiceMissaoTest


```
@Test  
void buscarMissao(){  
  
    LocalDateTime dataInicio = LocalDateTime.of(2026, 4, 1, 12, 0);  
    LocalDateTime dataFim = LocalDateTime.now();  
  
    FiltroGlobal filtro = new FiltroGlobal(null , null , null , Missao.Status.EM_ANDAMENTO , Missao.NivelPerigo.CRITICO , dataInicio , dataFim);  
  
    Page<MissaoDTO> missaoDTO = serviceMissao.buscarMissao(filtro, PageRequest.of(0, 10));  
  
    assertThat(missaoDTO.getContent()).isNotEmpty();  
  
  
    missaoDTO.getContent().forEach(m -> {  
                System.out.println("Missao: " + m.titulo() + " | Status: " + m.status() + " | NivelPerigo: " + m.nivelPerigo());  
                System.out.println("Data de inicio: " + m.dataInicio() + " | Data de termino: " + m.dataTermino());  
            }  
    );  
  
}

```


resultado:

```

Hibernate: 
    select
        m1_0.id,
        m1_0.titulo,
        m1_0.status,
        m1_0.nivel_perigo,
        m1_0.data_criacao,
        m1_0.data_inicio,
        m1_0.data_termino 
    from
        aventura.missao m1_0 
    where
        (
            ? is null 
            or m1_0.status=?
        ) 
        and (
            ? is null 
            or m1_0.nivel_perigo=?
        ) 
        and (
            cast(? as timestamp(6)) is null 
            or m1_0.data_criacao>=cast(? as timestamp(6))
        ) 
        and (
            cast(? as timestamp(6)) is null 
            or m1_0.data_termino<=cast(? as timestamp(6))
        ) 
    fetch
        first ? rows only
2026-04-06T10:06:11.537-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [EM_ANDAMENTO]
2026-04-06T10:06:11.537-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:VARCHAR) <- [EM_ANDAMENTO]
2026-04-06T10:06:11.537-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [CRITICO]
2026-04-06T10:06:11.538-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (4:VARCHAR) <- [CRITICO]
2026-04-06T10:06:11.538-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (5:TIMESTAMP) <- [2026-04-01T12:00]
2026-04-06T10:06:11.538-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (6:TIMESTAMP) <- [2026-04-01T12:00]
2026-04-06T10:06:11.538-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (7:TIMESTAMP) <- [2026-04-06T10:06:11.474162416]
2026-04-06T10:06:11.538-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (8:TIMESTAMP) <- [2026-04-06T10:06:11.474162416]
2026-04-06T10:06:11.538-03:00 TRACE 24332 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (9:INTEGER) <- [10]
Missao: Missao 1 | Status: EM_ANDAMENTO | NivelPerigo: CRITICO
Data de inicio: 2026-04-06T10:06:11.402370 | Data de termino: 2026-04-06T10:06:11.402378
Missao: Missao 2 | Status: EM_ANDAMENTO | NivelPerigo: CRITICO
Data de inicio: 2026-04-06T10:06:11.411692 | Data de termino: 2026-04-06T10:06:11.411719
Missao: Missao 3 | Status: EM_ANDAMENTO | NivelPerigo: CRITICO
Data de inicio: 2026-04-06T10:06:11.419702 | Data de termino: 2026-04-06T10:06:11.419707
Missao: Missao 4 | Status: EM_ANDAMENTO | NivelPerigo: CRITICO
Data de inicio: 2026-04-06T10:06:11.426592 | Data de termino: 2026-04-06T10:06:11.426601


```


```

@Test  
void detalharMissao(){  
    entityManager.flush();  
    entityManager.clear();  
  
    DetalharMissaoDTO detalharMissaoDTO = serviceMissao.detalharMissao(this.missaoId);  
    assertThat(detalharMissaoDTO).isNotNull();  
    System.out.println("Missao: " + detalharMissaoDTO.titulo() + " | Status: " + detalharMissaoDTO.status() + " | NivelPerigo: " + detalharMissaoDTO.nivelPerigo() + " | participacao_em_missao:  " + detalharMissaoDTO.participantes());  
  
}

```


resultado:
```
Hibernate: 
    select
        c1_0.id,
        c1_0.aventureiro_id,
        a1_0.id,
        a1_0.ativo,
        a1_0.classe,
        a1_0.data_atualizacao,
        a1_0.data_criacao,
        a1_0.nivel,
        a1_0.nome,
        a1_0.organizacao_id,
        o1_0.id,
        o1_0.ativo,
        o1_0.created_at,
        o1_0.nome,
        a1_0.usuario_id,
        urpc1_0.id,
        urpc1_0.created_at,
        urpc1_0.email,
        urpc1_0.nome,
        urpc1_0.organizacao_id,
        o2_0.id,
        o2_0.ativo,
        o2_0.created_at,
        o2_0.nome,
        urpc1_0.senha_hash,
        urpc1_0.status,
        urpc1_0.ultimo_login_em,
        urpc1_0.updated_at,
        c1_0.especie,
        c1_0.lealdade,
        c1_0.nome,
        c1_0.organizacao_id,
        o3_0.id,
        o3_0.ativo,
        o3_0.created_at,
        o3_0.nome 
    from
        aventura.companheiro c1_0 
    join
        aventura.aventureiro a1_0 
            on a1_0.id=c1_0.aventureiro_id 
    join
        audit.organizacoes o1_0 
            on o1_0.id=a1_0.organizacao_id 
    join
        audit.usuarios urpc1_0 
            on urpc1_0.id=a1_0.usuario_id 
    join
        audit.organizacoes o2_0 
            on o2_0.id=urpc1_0.organizacao_id 
    join
        audit.organizacoes o3_0 
            on o3_0.id=c1_0.organizacao_id 
    where
        c1_0.aventureiro_id=?
2026-04-06T10:07:38.564-03:00 TRACE 24545 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [465]
Missao: Missao 4 | Status: EM_ANDAMENTO | NivelPerigo: CRITICO | participacao_em_missao:  [PartipantesDaMissaoDTO[aventureiroId=465, nome=Lucas, classe=LIDER, recompensa=500, foiMVP=true]]

```

---


#### ServiceRankTest


```
@Test  
void buscarRank(){  
  
    LocalDateTime inicio = LocalDateTime.of(2026, 4, 1, 12, 0);  
    LocalDateTime fim = LocalDateTime.now();  
  
    FiltroGlobal filtro = new FiltroGlobal(null , null , null , Missao.Status.EM_ANDAMENTO , null , inicio , fim);  
  
    List<RankAventureiroDTO> rankAventureiroDTOS = serviceRank.buscarRank(filtro);  
  
    assertThat(rankAventureiroDTOS).isNotEmpty();  
    RankAventureiroDTO rankAventureiroDTO = rankAventureiroDTOS.get(0);  
    System.out.println("nome: " + rankAventureiroDTO.nome() + " | total de recompensas: " + rankAventureiroDTO.somaDeRecompensasRecebidas() + " | quantidade de destaques obtidos: " + rankAventureiroDTO.quantidadeDestaquesObtidos() + " | Total de Participacoes: " + rankAventureiroDTO.TotalDeParticipacoes());  
  
}
```

```
Hibernate: 
    select
        pem1_0.aventureiro_id,
        a1_0.nome,
        count(pem1_0.id),
        sum(pem1_0.recompensa_em_ouro),
        sum(case 
            when pem1_0.destaque_mvp=true 
                then 1 
            else 0 
    end) 
from
    aventura.participacao_em_missao pem1_0 
join
    aventura.aventureiro a1_0 
        on a1_0.id=pem1_0.aventureiro_id 
join
    aventura.missao m1_0 
        on m1_0.id=pem1_0.missao_id 
where
    pem1_0.data_de_registro between ? and ? 
    and (
        ? is null 
        or m1_0.status=?
    ) 
group by
    pem1_0.aventureiro_id,
    a1_0.nome 
order by
    sum(pem1_0.recompensa_em_ouro) desc,
    count(pem1_0.id) desc
2026-04-06T10:09:20.913-03:00 TRACE 24850 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:TIMESTAMP) <- [2026-04-01T12:00]
2026-04-06T10:09:20.913-03:00 TRACE 24850 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:TIMESTAMP) <- [2026-04-06T10:09:20.840973832]
2026-04-06T10:09:20.913-03:00 TRACE 24850 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [EM_ANDAMENTO]
2026-04-06T10:09:20.913-03:00 TRACE 24850 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (4:VARCHAR) <- [EM_ANDAMENTO]
nome: Lucas | total de recompensas: 2000 | quantidade de destaques obtidos: 4 | Total de Participacoes: 4


```


```
  @Test  
    void buscarRankDetalhado(){  
        LocalDateTime inicio = LocalDateTime.of(2026, 4, 1, 12, 0);  
        LocalDateTime fim = LocalDateTime.now();  
  
        FiltroGlobal filtro = new FiltroGlobal(null , null , null , null , null , inicio , fim);  
  
        List<RankMissaoDetalhadoDTO> rankMissaoDetalhadoDTOS = serviceRank.DetalhesRank(filtro);  
  
        assertThat(rankMissaoDetalhadoDTOS).isNotEmpty();  
        RankMissaoDetalhadoDTO rankMissaoDetalhadoDTO = rankMissaoDetalhadoDTOS.get(0);  
        System.out.println("Nome: " + rankMissaoDetalhadoDTO.nome() + " | Status: " + rankMissaoDetalhadoDTO.status() + " | Nivel de perigo: " + rankMissaoDetalhadoDTO.nivelPerigo() + " | Soma das recompensa: " + rankMissaoDetalhadoDTO.somaDeRecompensasRecebidas() + " | Total de Participantes: " + rankMissaoDetalhadoDTO.totalDeParticipantes());  
    }  
}
```

```

Hibernate: 
    select
        m1_0.titulo,
        m1_0.status,
        m1_0.nivel_perigo,
        count(pem1_0.id),
        sum(pem1_0.recompensa_em_ouro) 
    from
        aventura.participacao_em_missao pem1_0 
    join
        aventura.missao m1_0 
            on m1_0.id=pem1_0.missao_id 
    where
        m1_0.data_criacao between ? and ? 
    group by
        m1_0.titulo,
        m1_0.status,
        m1_0.nivel_perigo 
    order by
        sum(pem1_0.recompensa_em_ouro) desc
2026-04-06T10:11:24.109-03:00 TRACE 25331 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:TIMESTAMP) <- [2026-04-01T12:00]
2026-04-06T10:11:24.109-03:00 TRACE 25331 --- [O-Registro-da-Guilda] [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:TIMESTAMP) <- [2026-04-06T10:11:24.048175638]
Nome: Missao 2 | Status: EM_ANDAMENTO | Nivel de perigo: CRITICO | Soma das recompensa: 500 | Total de Participantes: 1

```

---
