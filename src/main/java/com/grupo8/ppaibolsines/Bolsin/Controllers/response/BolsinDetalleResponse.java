package com.grupo8.ppaibolsines.Bolsin.Controllers.response;

import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;
import com.grupo8.ppaibolsines.Remito.Controllers.response.RemitoResponse;
import com.grupo8.ppaibolsines.Shared.Controllers.response.CambioEstadoResponse;

import java.time.LocalDate;
import java.util.List;

public record BolsinDetalleResponse(
        Long id,
        String nroPrecinto,
        LocalDate fechaEnvio,
        Double peso,
        ComisionMedicaResponse cmOrigen,
        ComisionMedicaResponse cmDestino,
        String estadoActual,
        List<RemitoResponse> remitos,
        List<CambioEstadoResponse> cambiosEstado
) {
}
