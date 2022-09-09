package com.init.inventariovacunaBACKEDN.service;

import com.init.inventariovacunaBACKEDN.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorEmail(username);

        if (usuario == null)
            throw new UsernameNotFoundException("Usuario no encontrado");

        return crearUserDetails(usuario);
    }

    private UserDetails crearUserDetails(Usuario usuario){
        Set<GrantedAuthority> autoridades = new HashSet<>();

        usuario.getRoles().forEach(rolUsuario ->{
            autoridades.add(new SimpleGrantedAuthority(rolUsuario.getNombre().toString()));
        });


        return new User(usuario.getEmail(),usuario.getPassword(),true,true,true,true,autoridades);
    }



}
