package com.grupo8.ppaibolsines.Usuario.Controllers.implementations;

import com.grupo8.ppaibolsines.Sesion.Data.Model.Sesion;
import com.grupo8.ppaibolsines.Sesion.Service.interfaces.ISesionService;
import com.grupo8.ppaibolsines.Usuario.Controllers.interfaces.IUsuarioController;
import com.grupo8.ppaibolsines.Usuario.Controllers.request.LoginRequest;
import com.grupo8.ppaibolsines.Usuario.Controllers.response.LoginResponse;
import com.grupo8.ppaibolsines.Usuario.Controllers.response.UsuarioResponse;
import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;
import com.grupo8.ppaibolsines.Usuario.Mappers.UsuarioMapper;
import com.grupo8.ppaibolsines.Usuario.Service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController implements IUsuarioController {

    private final IUsuarioService usuarioService;
    private final ISesionService sesionService;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioController(IUsuarioService usuarioService, ISesionService sesionService) {
        this.usuarioService = usuarioService;
        this.sesionService = sesionService;
        this.usuarioMapper = new UsuarioMapper();
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioService.login(loginRequest.nombreUsuario(), loginRequest.contrasenia());
        Sesion sesion = sesionService.iniciarSesion(usuario);

        LoginResponse response = new LoginResponse(
                sesion.getId().toString(),
                usuarioMapper.toResponse(usuario)
        );
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }
}
