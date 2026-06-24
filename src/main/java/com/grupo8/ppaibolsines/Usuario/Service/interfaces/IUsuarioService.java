package com.grupo8.ppaibolsines.Usuario.Service.interfaces;

import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;

public interface IUsuarioService {

    Usuario login(String nombre, String contraseña);

    Usuario buscarPorId(Long id);
}
