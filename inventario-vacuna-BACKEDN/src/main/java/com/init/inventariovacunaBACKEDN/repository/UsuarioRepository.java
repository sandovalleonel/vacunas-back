package com.init.inventariovacunaBACKEDN.repository;

import com.init.inventariovacunaBACKEDN.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    boolean existsByEmail(String email);
    Usuario findByEmail(String email);
}
