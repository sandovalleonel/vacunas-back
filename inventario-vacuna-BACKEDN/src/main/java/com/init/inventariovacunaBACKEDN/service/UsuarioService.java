package com.init.inventariovacunaBACKEDN.service;

import com.init.inventariovacunaBACKEDN.entity.Detalle;
import com.init.inventariovacunaBACKEDN.entity.Usuario;
import com.init.inventariovacunaBACKEDN.repository.DetalleRepository;
import com.init.inventariovacunaBACKEDN.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DetalleRepository detalleRepository;

    public List<Usuario> listarUsuarios(){
        //evitar retornar al admin que esta quemado en la posicion 1
        List<Usuario> usuarios = usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getId() != 1).collect(Collectors.toList());

        return usuarios;
    }

    public Usuario buscarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuario;
    }
    public Usuario buscarPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario;
    }

    public void  borrarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public  boolean existeUsuario(String email){
        return usuarioRepository.existsByEmail(email);
    }


}
