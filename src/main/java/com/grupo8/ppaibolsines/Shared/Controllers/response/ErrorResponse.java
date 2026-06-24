package com.grupo8.ppaibolsines.Shared.Controllers.response;

public record ErrorResponse(boolean exito, String mensaje, String codigo) {

    public ErrorResponse(String mensaje) {
        this(false, mensaje, null);
    }
}
