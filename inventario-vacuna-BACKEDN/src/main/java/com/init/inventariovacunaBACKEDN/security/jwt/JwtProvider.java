package com.init.inventariovacunaBACKEDN.security.jwt;



import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;


    @Component
    public class JwtProvider {
        private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

        private String secret = "good" ;

        private int expiration = 99999;

        public String generateToken(Authentication authentication){
            //Usuario usuarioPrincipal = (Usuario) authentication.getPrincipal();
            //user es de
            User usuarioPrincipal = (User) authentication.getPrincipal();
            return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }

        public String getNombreUsuarioFromToken(String token){
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        }

        public boolean validateToken(String token){
            try {
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
                return true;
            }catch (MalformedJwtException e){
                logger.error("token mal formado");
            }catch (UnsupportedJwtException e){
                logger.error("token no soportado");
            }catch (ExpiredJwtException e){
                logger.error("token expirado");
            }catch (IllegalArgumentException e){
                logger.error("token vacío");
            }catch (SignatureException e){
                logger.error("fail en la firma");
                System.out.println(e.getMessage());
            }
            return false;
        }
    }