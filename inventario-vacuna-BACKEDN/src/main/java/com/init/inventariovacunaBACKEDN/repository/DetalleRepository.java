package com.init.inventariovacunaBACKEDN.repository;

import com.init.inventariovacunaBACKEDN.entity.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle ,Long> {
}
