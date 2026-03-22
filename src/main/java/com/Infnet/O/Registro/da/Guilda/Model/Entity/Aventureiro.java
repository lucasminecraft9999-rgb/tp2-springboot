//package com.Infnet.O.Registro.da.Guilda.Model.Entity;
//
//import com.Infnet.O.Registro.da.Guilda.DTO.Companheiro;
//import com.Infnet.O.Registro.da.Guilda.DTO.Enum.ClasseAventureiro;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//@Entity
//@Table(name = "aventureiros" , schema = "aventura")
//@AllArgsConstructor@Setter@Getter@NoArgsConstructor
//@ToString
//public class Aventureiro {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "nome", unique = true , nullable = true)
//    private String nome;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "classe", nullable = false)
//      private ClasseAventureiro classe;
//
//    @Column(name = "nivel", nullable = false)
//    @NotNull @Min(1)    private Integer nivel;
//
//    @Column(name = "ativo", nullable = false)
//      private boolean ativo;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "companheiro", nullable = false)
//      private Companheiro companheiro;
//
//
//// caso precise
//    @ManyToOne
//    @JoinColumn(name = "organizacao_id")
//   private Organizacao organizacao;
//}
