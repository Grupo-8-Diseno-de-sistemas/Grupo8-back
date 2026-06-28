package com.grupo8.ppaibolsines.Usuario.Mappers;

import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;
import com.grupo8.ppaibolsines.ComisionMedica.Mappers.ComisionMedicaMapper;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Usuario.Controllers.response.UsuarioResponse;
import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;

public class UsuarioMapper {

    private final ComisionMedicaMapper comisionMedicaMapper = new ComisionMedicaMapper();

    public UsuarioResponse toResponse(Usuario usuario) {
        Empleado empleado = usuario.getEmpleado();
        ComisionMedicaResponse cm = empleado != null && empleado.getCM() != null
                ? comisionMedicaMapper.toResponse(empleado.getCM())
                : null;

        return new UsuarioResponse(
                empleado != null ? empleado.getId() : usuario.getId(),
                usuario.getNombre(),
                empleado != null ? empleado.getNombre() : null,
                empleado != null ? empleado.getApellido() : null,
                empleado != null ? empleado.getEmail() : null,
                cm
        );
    }
}
