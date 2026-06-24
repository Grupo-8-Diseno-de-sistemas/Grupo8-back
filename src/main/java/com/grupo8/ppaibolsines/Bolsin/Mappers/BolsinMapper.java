package com.grupo8.ppaibolsines.Bolsin.Mappers;

import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinDetalleResponse;
import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinResponse;
import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.CambioEstadoBolsin.Data.Model.CambioEstadoBolsin;
import com.grupo8.ppaibolsines.CambioEstadoBolsin.Mappers.CambioEstadoBolsinMapper;
import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;
import com.grupo8.ppaibolsines.ComisionMedica.Mappers.ComisionMedicaMapper;
import com.grupo8.ppaibolsines.Remito.Mappers.RemitoMapper;

import java.time.LocalDate;

public class BolsinMapper {

    private final ComisionMedicaMapper comisionMedicaMapper = new ComisionMedicaMapper();
    private final RemitoMapper remitoMapper = new RemitoMapper();
    private final CambioEstadoBolsinMapper cambioEstadoBolsinMapper = new CambioEstadoBolsinMapper();

    public BolsinResponse toResponse(Bolsin bolsin) {
        ComisionMedicaResponse cmOrigen = comisionMedicaMapper.toResponse(bolsin.obtenerCMOrigen());
        ComisionMedicaResponse cmDestino = comisionMedicaMapper.toResponse(bolsin.obtenerCMDestino());

        return new BolsinResponse(
                bolsin.getId(),
                bolsin.getNroPrecinto(),
                buscarFechaEnvio(bolsin),
                bolsin.getPeso(),
                cmOrigen,
                cmDestino,
                bolsin.getEstadoActual() != null ? bolsin.getEstadoActual().getNombre() : null
        );
    }

    public BolsinDetalleResponse toDetalleResponse(Bolsin bolsin) {
        ComisionMedicaResponse cmOrigen = comisionMedicaMapper.toResponse(bolsin.obtenerCMOrigen());
        ComisionMedicaResponse cmDestino = comisionMedicaMapper.toResponse(bolsin.obtenerCMDestino());

        return new BolsinDetalleResponse(
                bolsin.getId(),
                bolsin.getNroPrecinto(),
                buscarFechaEnvio(bolsin),
                bolsin.getPeso(),
                cmOrigen,
                cmDestino,
                bolsin.getEstadoActual() != null ? bolsin.getEstadoActual().getNombre() : null,
                bolsin.obtenerInformacionRemito().stream().map(remitoMapper::toResponse).toList(),
                bolsin.getCambiosEstadoBolsin().stream().map(cambioEstadoBolsinMapper::toResponse).toList()
        );
    }

    private LocalDate buscarFechaEnvio(Bolsin bolsin) {
        return bolsin.getCambiosEstadoBolsin().stream()
                .filter(cambio -> cambio.getEstado() != null && cambio.getEstado().esEnviado())
                .map(CambioEstadoBolsin::getFechaHorainicio)
                .map(fecha -> fecha.toLocalDate())
                .findFirst()
                .orElse(null);
    }
}
