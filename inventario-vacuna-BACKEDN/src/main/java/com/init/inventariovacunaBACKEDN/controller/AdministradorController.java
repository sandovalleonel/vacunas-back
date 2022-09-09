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



public class AdministradorController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Listar usuarios", description = "Lista con todos los usuarios del sistema")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Buscar usuario", description = "Busqueda de usuario por id")

    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuario> BuscarUsuarioPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null)
            return new ResponseEntity("El usuario con id "+ usuario.getId() +" no existe", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(usuario,HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Guardar usuario", description = "Crear nuevo usuario")
    @PostMapping("/guardar")
    public ResponseEntity<Usuario> guardarUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new MensajeBindinResult().verErrores(bindingResult),HttpStatus.BAD_REQUEST);

        Rol rolEmpleado = rolRepository.findByNombre(NombreRol.ROLE_EMPLEADO);
        usuario.addRol(rolEmpleado);

        if (usuarioService.existeUsuario(usuario.getEmail()))
            return new ResponseEntity("El email ya esta registrado", HttpStatus.BAD_REQUEST);

        usuario.setPassword(passwordEncoder.encode(usuario.getCedula()));

        Usuario usuarioNuevo = usuarioService.guardarUsuario(usuario);

        Detalle detalle = new Detalle();
        detalle.setUsuario(usuarioNuevo);
        detalleRepository.save(detalle);


        return new ResponseEntity<>(usuarioNuevo,HttpStatus.CREATED);
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Actualizar usuario", description = "Actualizar usuario")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@Valid @RequestBody Usuario usuario,BindingResult bindingResult,@PathVariable Long id){

        if (bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeBindinResult().verErrores(bindingResult),HttpStatus.BAD_REQUEST);
        }

        Usuario usuarioAntiguo = usuarioService.buscarPorId(id);
        if (usuarioAntiguo == null)
            return new ResponseEntity("El usuario con id "+ usuario.getId() +" no existe", HttpStatus.BAD_REQUEST);

        ///validar que el email ya no ete registrado en otro usuario
        Usuario usuarioEmail = usuarioService.buscarPorEmail(usuario.getEmail());
        if(usuarioEmail != null){
            if (usuarioAntiguo.getId() != usuarioEmail.getId())
                return new ResponseEntity("Error el email ya esta registrado",HttpStatus.BAD_REQUEST);
        }



        usuarioAntiguo.setNombres(usuario.getNombres());
        usuarioAntiguo.setApellidos(usuario.getApellidos());
        usuarioAntiguo.setCedula(usuario.getCedula());
        usuarioAntiguo.setEmail(usuario.getEmail());

        Usuario usuarioNuevo = usuarioService.guardarUsuario(usuarioAntiguo);

        return new ResponseEntity<>(usuarioNuevo,HttpStatus.CREATED);
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Eliminar usuario", description = "Eliminar usuario por id")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null)
            return new ResponseEntity("El usuario con id "+ usuario.getId() +" no existe", HttpStatus.BAD_REQUEST);

        usuarioService.borrarUsuario(id);

        return new ResponseEntity<>(usuario,HttpStatus.OK);
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Buscar usuario", description = "Buscar usuario por estado de vacuna true/false")
    @GetMapping("/buscar-por-estado")
    public ResponseEntity<List<Usuario>> buscarPorEstadoDeVacuna(@RequestParam Boolean estado){
        List<Usuario> usuarios = usuarioService.listarUsuarios()
                .stream()
                .filter(usuario -> usuario.getDetalle().getEstadoVacuna() == estado)
                .collect(Collectors.toList());

         return new ResponseEntity(usuarios,HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Buscar usuario", description = "Buscar usuario por tipo de vacuna recibida")
    @GetMapping("/buscar-por-vacuna")
    public ResponseEntity<List<Usuario>> buscarPorNombreVacuna(@RequestParam NombreVacuna vacuna){

        List<Usuario> usuarios = usuarioService.listarUsuarios()
                .stream()
                .filter(usuario -> usuario.getDetalle().getVacuna() != null)
                .filter(usuario -> usuario.getDetalle().getVacuna().equals(vacuna) == true )
                .collect(Collectors.toList());

        return new ResponseEntity(usuarios,HttpStatus.OK);
    }

   // @GetMapping("/buscar-por-fecha-vacuna")
    public ResponseEntity<List<Usuario>> buscarPorFecha(@RequestParam Date fecha){
        List<Usuario> usuarios = usuarioService.listarUsuarios()
                .stream()
                //.filter()
                .collect(Collectors.toList());

        return new ResponseEntity(usuarios,HttpStatus.OK);
    }



}
