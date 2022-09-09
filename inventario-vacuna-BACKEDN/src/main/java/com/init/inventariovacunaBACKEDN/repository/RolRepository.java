package com.init.inventariovacunaBACKEDN.repository;

import com.init.inventariovacunaBACKEDN.entity.Rol;
import com.init.inventariovacunaBACKEDN.enums.NombreRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
    Rol findByNombre(NombreRol nombreRol);
}
