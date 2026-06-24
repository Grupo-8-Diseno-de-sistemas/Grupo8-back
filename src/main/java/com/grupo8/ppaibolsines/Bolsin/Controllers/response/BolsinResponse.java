package com.grupo8.ppaibolsines.Bolsin.Controllers.response;

import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;

import java.time.LocalDate;

public record BolsinResponse(
        Long id,
        String nroPrecinto,
        LocalDate fechaEnvio,
        Double peso,
        ComisionMedicaResponse cmOrigen,
        ComisionMedicaResponse cmDestino,
        String estadoActual
) {
}
