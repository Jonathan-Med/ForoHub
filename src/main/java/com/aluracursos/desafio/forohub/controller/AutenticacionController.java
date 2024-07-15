package com.aluracursos.desafio.forohub.controller;

import com.aluracursos.desafio.forohub.domain.usuario.DatosAutenticacionUsuario;
import com.aluracursos.desafio.forohub.domain.usuario.Usuario;
import com.aluracursos.desafio.forohub.infra.security.DatosJWToken;
import com.aluracursos.desafio.forohub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody DatosAutenticacionUsuario datosAutenticacionUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.nombre(), datosAutenticacionUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var usuario = (User) usuarioAutenticado.getPrincipal();
        var JWToken = tokenService.generarToken(new Usuario(usuario.getUsername(), usuario.getPassword()));
        return ResponseEntity.ok(new DatosJWToken(JWToken));
    }
}