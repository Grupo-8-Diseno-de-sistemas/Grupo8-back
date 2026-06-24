package com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Mappers;

import com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Data.Model.CambioEstadoDocumentacion;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Shared.Controllers.response.CambioEstadoResponse;

public class CambioEstadoDocumentacionMapper {

    public CambioEstadoResponse toResponse(CambioEstadoDocumentacion cambioEstadoDocumentacion) {
        Empleado empleado = cambioEstadoDocumentacion.getEmpleadoResponsable();
        String nombreCompleto = empleado != null ? empleado.getNombre() + " " + empleado.getApellido() : null;

        return new CambioEstadoResponse(
                cambioEstadoDocumentacion.getFechaHorainicio(),
                cambioEstadoDocumentacion.getEstado().getNombre(),
                nombreCompleto
        );
    }
}
