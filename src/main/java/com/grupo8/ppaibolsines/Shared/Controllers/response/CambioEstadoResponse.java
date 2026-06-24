package com.grupo8.ppaibolsines.Shared.Controllers.response;

import java.time.LocalDateTime;

public record CambioEstadoResponse(LocalDateTime fecha, String estado, String empleadoResponsable) {
}
