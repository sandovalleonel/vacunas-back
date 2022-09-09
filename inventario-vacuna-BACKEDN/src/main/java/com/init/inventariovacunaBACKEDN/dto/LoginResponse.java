package com.init.inventariovacunaBACKEDN.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor


public class LoginResponse {

    private String usuario;
    private String token;
    private Collection<? extends GrantedAuthority> rol;




}
