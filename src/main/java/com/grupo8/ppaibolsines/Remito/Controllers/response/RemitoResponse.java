package com.grupo8.ppaibolsines.Remito.Controllers.response;

import com.grupo8.ppaibolsines.Documentacion.Controllers.response.DocumentacionResponse;

import java.time.LocalDate;
import java.util.List;

public record RemitoResponse(
        Long id,
        String nroRemito,
        LocalDate fechaEmision,
        String estadoActual,
        List<DocumentacionResponse> documentacion
) {
}
