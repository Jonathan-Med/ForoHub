package com.aluracursos.desafio.forohub.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.aluracursos.desafio.forohub.domain.usuario.DatosAutenticacionUsuario;
import com.aluracursos.desafio.forohub.domain.usuario.DatosRegistroUsuario;
import com.aluracursos.desafio.forohub.domain.usuario.Usuario;
import com.aluracursos.desafio.forohub.infra.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Prueba el acceso a los endpoints /topicos y /cursos sin un token válido\n" +
            "Espera una respuesta HTTP 403")
    public void testProtectedEndpoints() throws Exception {
        mockMvc.perform(get("/topicos")
                        .header("Authorization", "Bearer invalid_token"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/cursos")
                        .header("Authorization", "Bearer invalid_token"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Prueba el endpoint /login con credenciales válidas\n" +
            "Espera una respuesta HTTP 200 OK y un token JWT en la respuesta")
    public void testAutenticarUsuario() throws Exception {
        DatosAutenticacionUsuario datosAutenticacionUsuario = new DatosAutenticacionUsuario("usuario", "contrasena1!");

        Usuario usuario = new Usuario("usuario", "contrasenaEncriptada");
        User user = new User(usuario.getNombre(), usuario.getContrasena(), new ArrayList<>());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authToken);
        when(tokenService.generarToken(any(Usuario.class))).thenReturn("tokenJWT");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datosAutenticacionUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwToken").value("tokenJWT")); // Corregido: Cambiado de "$.token" a "$.jwToken"
    }


    @Test
    @DisplayName("Prueba el endpoint /usuario/registrar para registrar un nuevo usuario\n" +
            "Espera una respuesta HTTP 200 OK")
    public void testRegistrarUsuario() throws Exception {
        DatosRegistroUsuario datosRegistroUsuario = new DatosRegistroUsuario("nuevoUsuario4", "email2@ejemplo.com", "COntrasena#3!");

        Usuario nuevoUsuario = new Usuario();

        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datosRegistroUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("nuevoUsuario4"))
                .andExpect(jsonPath("$.email").value("email2@ejemplo.com"));
    }

}
