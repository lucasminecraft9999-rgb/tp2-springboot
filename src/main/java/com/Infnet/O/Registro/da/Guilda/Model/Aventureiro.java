//package com.Infnet.O.Registro.da.Guilda.Model;
//
//import com.Infnet.O.Registro.da.Guilda.DTO.Enum.ClasseAventureiro;
//import com.Infnet.O.Registro.da.Guilda.DTO.Companheiro;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@AllArgsConstructor@Setter@Getter@ToString
//public class Aventureiro {
//    @NotNull  private Long id;
//   @NotNull private String nome;
//      private ClasseAventureiro classe;
//    @NotNull @Min(1)    private Integer nivel;
//      private boolean ativo;
//    private Companheiro companheiro;
//
//
//// // caso precise
////    @ManyToOne
////    @JoinColumn(name = "organizacao_id")
////    private Organizacao organizacao;
//}
