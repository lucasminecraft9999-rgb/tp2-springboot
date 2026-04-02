package com.Infnet.O.Registro.da.Guilda.repository.role;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r from Role r left Join FETCH r.permissions")
    List<Role> findAllWithPermissions();
}
