package com.init.inventariovacunaBACKEDN.controller;

import com.init.inventariovacunaBACKEDN.dto.LoginRequest;
import com.init.inventariovacunaBACKEDN.dto.LoginResponse;
import com.init.inventariovacunaBACKEDN.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin

public class AutenticcionController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;



    @PostMapping
    public ResponseEntity<?> obtenerToken(@RequestBody LoginRequest credenciales){
        Authentication autenticacion = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credenciales.getUsuario(),credenciales.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(autenticacion);
        String jwt = jwtProvider.generateToken(autenticacion);
        UserDetails userDetails = (UserDetails) autenticacion.getPrincipal();
        LoginResponse respuesta = new LoginResponse(userDetails.getUsername(),jwt,userDetails.getAuthorities());

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
