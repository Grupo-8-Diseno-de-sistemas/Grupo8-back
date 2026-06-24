package com.grupo8.ppaibolsines.Usuario.Controllers.response;

import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;

public record UsuarioResponse(
        Long id,
        String nombreUsuario,
        String nombre,
        String apellido,
        String email,
        ComisionMedicaResponse cm
) {
}
