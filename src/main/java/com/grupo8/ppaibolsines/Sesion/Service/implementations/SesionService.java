package com.grupo8.ppaibolsines.Sesion.Service.implementations;

import com.grupo8.ppaibolsines.Sesion.Data.Model.Sesion;
import com.grupo8.ppaibolsines.Sesion.Repository.ISesionRepository;
import com.grupo8.ppaibolsines.Sesion.Service.interfaces.ISesionService;
import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SesionService implements ISesionService {

    private final ISesionRepository sesionRepository;

    @Autowired
    public SesionService(ISesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    @Override
    public Sesion iniciarSesion(Usuario usuario) {
        Sesion sesion = new Sesion(usuario, LocalDateTime.now());
        return sesionRepository.save(sesion);
    }

    @Override
    public Sesion buscarPorId(Long id) {
        return sesionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la sesión con id " + id));
    }
}
