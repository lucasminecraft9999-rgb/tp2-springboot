package com.Infnet.O.Registro.da.Guilda.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
