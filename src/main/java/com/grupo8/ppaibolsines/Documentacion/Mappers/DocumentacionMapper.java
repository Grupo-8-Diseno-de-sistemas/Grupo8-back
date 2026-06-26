package com.grupo8.ppaibolsines.Documentacion.Mappers;

import com.grupo8.ppaibolsines.Documentacion.Controllers.response.DocumentacionResponse;
import com.grupo8.ppaibolsines.Documentacion.Data.Model.Documentacion;
import com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Data.Model.CambioEstadoDocumentacion;

public class DocumentacionMapper {

    public DocumentacionResponse toResponse(Documentacion documentacion) {
        CambioEstadoDocumentacion estadoActual = documentacion.getEstadoActual();

        return new DocumentacionResponse(
                documentacion.getId(),
                documentacion.getAsunto(),
                documentacion.mostrarTipoDocumentacion(),
                documentacion.getFechaPase(),
                estadoActual != null ? estadoActual.getEstado().getNombre() : null
        );
    }
}
