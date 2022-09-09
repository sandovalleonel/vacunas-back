package com.init.inventariovacunaBACKEDN.utils;

import com.init.inventariovacunaBACKEDN.entity.Detalle;
import com.init.inventariovacunaBACKEDN.entity.Rol;
import com.init.inventariovacunaBACKEDN.entity.Usuario;
import com.init.inventariovacunaBACKEDN.enums.NombreRol;
import com.init.inventariovacunaBACKEDN.repository.DetalleRepository;
import com.init.inventariovacunaBACKEDN.repository.RolRepository;
import com.init.inventariovacunaBACKEDN.repository.UsuarioRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargarDatos  implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        rolRepository.save(new Rol(1L,NombreRol.ROLE_ADMIN));
        rolRepository.save(new Rol(2l,NombreRol.ROLE_EMPLEADO));


        Usuario  usuarioAdmin = new Usuario();
        usuarioAdmin.setId(1L);
        usuarioAdmin.setCedula("1004255988");
        usuarioAdmin.setNombres("ADMIN");
        usuarioAdmin.setApellidos("ADMIN");
        usuarioAdmin.setEmail("admin@admin.com");
        usuarioAdmin.setPassword(passwordEncoder.encode("123"));

        Rol rolAdmin = rolRepository.findByNombre(NombreRol.ROLE_ADMIN);

        usuarioAdmin.addRol(rolAdmin);


        Usuario usuarioNew = usuarioRepository.save(usuarioAdmin);
        System.out.println(usuarioNew);



    }
}
