package com.init.inventariovacunaBACKEDN.controller;

import com.init.inventariovacunaBACKEDN.entity.Detalle;
import com.init.inventariovacunaBACKEDN.entity.Rol;
import com.init.inventariovacunaBACKEDN.entity.Usuario;
import com.init.inventariovacunaBACKEDN.enums.NombreRol;
import com.init.inventariovacunaBACKEDN.enums.NombreVacuna;
import com.init.inventariovacunaBACKEDN.repository.DetalleRepository;
import com.init.inventariovacunaBACKEDN.repository.RolRepository;
import com.init.inventariovacunaBACKEDN.service.UsuarioService;
import com.init.inventariovacunaBACKEDN.validation.MensajeBindinResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;




        import com.init.inventariovacunaBACKEDN.entity.Detalle;
        import com.init.inventariovacunaBACKEDN.entity.Rol;
        import com.init.inventariovacunaBACKEDN.entity.Usuario;
        import com.init.inventariovacunaBACKEDN.enums.NombreRol;
        import com.init.inventariovacunaBACKEDN.enums.NombreVacuna;
        import com.init.inventariovacunaBACKEDN.repository.DetalleRepository;
        import com.init.inventariovacunaBACKEDN.repository.RolRepository;
        import com.init.inventariovacunaBACKEDN.service.UsuarioService;
        import com.init.inventariovacunaBACKEDN.validation.MensajeBindinResult;
        import io.swagger.v3.oas.annotations.Operation;
        import io.swagger.v3.oas.annotations.security.SecurityRequirement;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.access.annotation.Secured;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;
        import java.util.Date;
        import java.util.List;
        import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMIN')")



public class AdministradoUpdateController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;






    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody Usuario usuario,BindingResult bindingResult,@PathVariable Long id){

        if (bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeBindinResult().verErrores(bindingResult),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("usuarioNuevo",HttpStatus.CREATED);
    }




}
