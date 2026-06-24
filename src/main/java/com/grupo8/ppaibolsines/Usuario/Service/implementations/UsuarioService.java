package com.grupo8.ppaibolsines.Usuario.Service.implementations;

import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;
import com.grupo8.ppaibolsines.Usuario.Repository.IUsuarioRepository;
import com.grupo8.ppaibolsines.Usuario.Service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario login(String nombre, String contraseña) {
        Usuario usuario = usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña inválidos"));

        if (!usuario.getContraseña().equals(contraseña)) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        return usuario;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con id " + id));
    }
}
