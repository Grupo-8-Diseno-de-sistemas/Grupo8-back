package com.grupo8.ppaibolsines.Usuario.Controllers.interfaces;

import com.grupo8.ppaibolsines.Usuario.Controllers.request.LoginRequest;
import com.grupo8.ppaibolsines.Usuario.Controllers.response.LoginResponse;
import com.grupo8.ppaibolsines.Usuario.Controllers.response.UsuarioResponse;
import org.springframework.http.ResponseEntity;

public interface IUsuarioController {

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    ResponseEntity<UsuarioResponse> obtenerUsuario(Long id);
}
