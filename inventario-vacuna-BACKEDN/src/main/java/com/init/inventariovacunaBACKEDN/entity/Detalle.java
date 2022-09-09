package com.init.inventariovacunaBACKEDN.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.init.inventariovacunaBACKEDN.enums.NombreVacuna;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "detalles_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Past(message = "la fecha de nacimiento debe ser una fecha pasada")
    private Date fechaNacimiento;

    @Size(max = 100, message = "la dirección no puede superar los 100 caracteres")
    private String domicilio;

    @Size(max = 15, message = "el telefono no puede superar los 15 caracteres")
    private String telefono;

    private Boolean estadoVacuna = false;

    @Enumerated(EnumType.STRING)
    private NombreVacuna vacuna;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Past(message = "la fecha de vacuna debe ser una fecha pasada")
    private Date fechaVacuna;

    @Max(value = 10, message = "el número de dosis debes ser menor a 10")
    @Min(value = 0, message = "el número de dosis no puede ser negativo")
    private int numeroDosis;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    public Detalle(String domicilio, String telefono) {
        this.domicilio = domicilio;
        this.telefono = telefono;
    }
}
