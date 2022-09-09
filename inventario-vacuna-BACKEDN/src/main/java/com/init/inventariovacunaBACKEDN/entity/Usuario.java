package com.init.inventariovacunaBACKEDN.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.init.inventariovacunaBACKEDN.validation.CedulaValidacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "el campo cédula no puede quedar en blanco")
    @Size(max = 10, message = "el campo cedula debe tener 10 dígitos")
    //@Column(unique = true)
    @CedulaValidacion(message = "la cedula ingresada es invalida")
    private String cedula;

    @NotBlank(message = "el campo nombres no puede quedar en blanco")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "el nombre de usuario solo debe tener letras")
    private String nombres;

    @NotBlank(message = "el campo apellidos no puede quedar en blanco")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "el apellido de usuario solo debe tener letras")
    private String apellidos;

    @NotBlank(message = "el campo no puede quedar en blanco")
    @Email(message = "el campo no tiene formato de email")
    @Column(unique = true)
    private String email;
    private String password;

    //manytomany roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
                name = "usuarios_roles",
                joinColumns = @JoinColumn(name = "usuario_id"),
                inverseJoinColumns =  @JoinColumn(name = "rol_id")
                )
    private Set<Rol> roles = new HashSet<>();

    //one to one detalle

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    @JsonManagedReference
    private Detalle detalle;






    public  void  addRol(Rol rol){
        this.roles.add(rol);
    }






}
