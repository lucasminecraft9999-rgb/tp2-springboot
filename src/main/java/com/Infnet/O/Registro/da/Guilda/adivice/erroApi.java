package com.Infnet.O.Registro.da.Guilda.adivice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class erroApi {
private String mensagem;
private List<String> detalhes;
}
