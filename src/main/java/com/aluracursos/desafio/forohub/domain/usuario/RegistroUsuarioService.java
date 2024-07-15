package com.aluracursos.desafio.forohub.domain.usuario;


import com.aluracursos.desafio.forohub.infra.security.PasswordEncryptionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncryptionService passwordEncryptionService;

    @Transactional
    public DatosRespuestaUsuario crear(DatosRegistroUsuario datos) {
        if (repository.existsByNombre(datos.nombre())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        String contrasenaEncriptada = passwordEncryptionService.encryptPassword(datos.contrasena());

        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre());
        usuario.setEmail(datos.email());
        usuario.setContrasena(contrasenaEncriptada);
        usuario.setEstatus(true);
        repository.save(usuario);

        return new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}
