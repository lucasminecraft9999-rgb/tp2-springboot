package com.Infnet.O.Registro.da.Guilda;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceAventureiro;
import com.Infnet.O.Registro.da.Guilda.repository.CompanheiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventureiro.AventureiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.organizacao.OrganizacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(ServiceAventureiro.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;


    // estou testando se o join esta conseguindo capturar os roles
    @Test
    void deveListarUsuariosComRoles() {
        List<Usuario> usuarios = usuarioRepository.findAllWithRoles();

        assertThat(usuarios).isNotEmpty();

        usuarios.forEach(usuario -> {
            System.out.println("Usuario: " + usuario.getNome() + " tem os roles:");

            System.out.println("Email do usuario: " + usuario.getEmail() + "");

            usuario.getRoles().forEach(role ->{

                System.out.println(" - " + role.getNome());

                role.getPermissions().forEach(permission -> {
                    System.out.println(" - " + "Permissiao: " + permission.getCode());
                });
            });
        });
    }
}
