package com.grupo8.ppaibolsines.Sesion.Service.interfaces;

import com.grupo8.ppaibolsines.Sesion.Data.Model.Sesion;
import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;

public interface ISesionService {

    Sesion iniciarSesion(Usuario usuario);

    Sesion buscarPorId(Long id);
}
