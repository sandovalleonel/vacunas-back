package com.init.inventariovacunaBACKEDN.entity;

import com.init.inventariovacunaBACKEDN.enums.NombreRol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NombreRol nombre;


    public Rol(NombreRol nombre) {
        this.nombre = nombre;
    }
}
