package com.init.inventariovacunaBACKEDN.controller;

import com.init.inventariovacunaBACKEDN.entity.Detalle;
import com.init.inventariovacunaBACKEDN.entity.Usuario;
import com.init.inventariovacunaBACKEDN.repository.DetalleRepository;
import com.init.inventariovacunaBACKEDN.service.UsuarioService;
import com.init.inventariovacunaBACKEDN.validation.MensajeBindinResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/empleado")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_EMPLEADO')")
public class EmpleadoController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DetalleRepository detalleRepository;



    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Mi perfil", description = "Todos los datos del usuario logeado")
    @GetMapping("/ver")
    public ResponseEntity<Usuario> verPerfil(Principal principal){
        //obtener email
        String email = principal.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Detalle", description = "Los datos del empleado que se pueden actualizar")
    @GetMapping("/verdetalle")
    public ResponseEntity<Detalle> verDetalle(Principal principal){
        //obtener email
        String email = principal.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);

        Detalle detalle = usuario.getDetalle();

        return new ResponseEntity<>(detalle, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Actualizar perfil", description = "Actualizar los detalles de usuario")
    @PutMapping("/actualizar")
    public ResponseEntity<Detalle> actualizarDetalle(@Valid  @RequestBody Detalle detalle, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new MensajeBindinResult().verErrores(bindingResult),HttpStatus.BAD_REQUEST);

       Detalle det =  detalleRepository.findById(detalle.getId()).orElse(null);

       if (det == null)
           return new ResponseEntity("Error el detalle no tiene id",HttpStatus.BAD_REQUEST);
       det.setDomicilio(detalle.getDomicilio());
       det.setFechaNacimiento(detalle.getFechaNacimiento());
       det.setTelefono(detalle.getTelefono());
       det.setEstadoVacuna(detalle.getEstadoVacuna());
       det.setFechaVacuna(detalle.getFechaVacuna());
       det.setVacuna(detalle.getVacuna());
       det.setNumeroDosis(detalle.getNumeroDosis());

       Detalle detalleNuevo = detalleRepository.save(det);
       return new ResponseEntity<>(detalleNuevo,HttpStatus.CREATED);
    }



}
