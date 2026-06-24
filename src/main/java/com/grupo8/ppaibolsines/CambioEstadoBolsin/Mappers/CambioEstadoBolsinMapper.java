package com.grupo8.ppaibolsines.CambioEstadoBolsin.Mappers;

import com.grupo8.ppaibolsines.CambioEstadoBolsin.Data.Model.CambioEstadoBolsin;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Shared.Controllers.response.CambioEstadoResponse;

public class CambioEstadoBolsinMapper {

    public CambioEstadoResponse toResponse(CambioEstadoBolsin cambioEstadoBolsin) {
        Empleado empleado = cambioEstadoBolsin.getEmpleadoResponsable();
        String nombreCompleto = empleado != null ? empleado.getNombre() + " " + empleado.getApellido() : null;

        return new CambioEstadoResponse(
                cambioEstadoBolsin.getFechaHorainicio(),
                cambioEstadoBolsin.getEstado().getNombre(),
                nombreCompleto
        );
    }
}
