package com.Infnet.O.Registro.da.Guilda;


import com.Infnet.O.Registro.da.Guilda.repository.CompanheiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.auditoria.AuditoriaRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventureiro.AventureiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.missao.MissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.organizacao.OrganizacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.participacao.ParticipacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.permission.PermissionRepository;
import com.Infnet.O.Registro.da.Guilda.repository.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class HibernatesTest {

@Autowired
    private AuditoriaRepository auditoriaRepository;

@Autowired
    private PermissionRepository permissionRepository;

@Autowired
    private RoleRepository roleRepository;

@Autowired
    private OrganizacaoRepository organizacaoRepository;


// aventureiro


    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private CompanheiroRepository companheiroRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoRepository participacaoRepository;



@Test
void listarAuditoria(){
    auditoriaRepository.findAll();
}

@Test
void listarPermissoes(){
    permissionRepository.findAll();
}


@Test
    void listarRoles(){
        roleRepository.findAll();
    }

    @Test
    void listarOrganizacoes(){
        organizacaoRepository.findAll();
    }


// aventura


@Test void listarAventureiros(){
    aventureiroRepository.findAll();
}

@Test void listarCompanheiros(){
    companheiroRepository.buscarCompanheiros();
}

@Test void listarMissoes(){
missaoRepository.buscarMissoes();
}


@Test void listarParticipacoes(){
    participacaoRepository.findAll();
}


}
