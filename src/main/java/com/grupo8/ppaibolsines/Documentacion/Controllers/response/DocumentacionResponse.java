package com.grupo8.ppaibolsines.Documentacion.Controllers.response;

import java.time.LocalDate;

public record DocumentacionResponse(
        Long id,
        String asunto,
        String tipoDocumento,
        LocalDate fechaPase,
        String estadoActual
) {
}
