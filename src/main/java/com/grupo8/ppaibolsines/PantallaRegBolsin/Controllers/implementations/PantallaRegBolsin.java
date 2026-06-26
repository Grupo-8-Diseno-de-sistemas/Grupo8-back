package com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.implementations;

import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinResponse;
import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.Bolsin.Mappers.BolsinMapper;
import com.grupo8.ppaibolsines.Bolsin.Service.interfaces.IBolsinService;
import com.grupo8.ppaibolsines.Estado.Service.interfaces.IEstadoService;
import com.grupo8.ppaibolsines.GestorRegBolsin.Service.implementations.GestorRegBolsin;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.interfaces.IPantallaRegBolsin;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.request.RegistrarRecepcionRequest;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.response.RecepcionResponse;
import com.grupo8.ppaibolsines.Sesion.Data.Model.Sesion;
import com.grupo8.ppaibolsines.Sesion.Service.interfaces.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bolsines")
public class PantallaRegBolsin implements IPantallaRegBolsin {

    private final IBolsinService bolsinService;
    private final IEstadoService estadoService;
    private final ISesionService sesionService;
    private final BolsinMapper bolsinMapper;

    @Autowired
    public PantallaRegBolsin(IBolsinService bolsinService,
                              IEstadoService estadoService,
                              ISesionService sesionService) {
        this.bolsinService = bolsinService;
        this.estadoService = estadoService;
        this.sesionService = sesionService;
        this.bolsinMapper = new BolsinMapper();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<BolsinResponse>> listarBolsinesEnviados(
            @RequestParam("sesionId") Long sesionId) {

        Sesion sesion = sesionService.buscarPorId(sesionId);

        GestorRegBolsin gestorRegBolsin = new GestorRegBolsin(bolsinService, estadoService);
        List<Bolsin> bolsines = gestorRegBolsin.registrarRecepcionBolsin(sesion);

        return ResponseEntity.ok(bolsines.stream().map(bolsinMapper::toResponse).toList());
    }

    @Override
    @PutMapping("/{id}/recepcion")
    public ResponseEntity<RecepcionResponse> registrarRecepcionBolsin(@PathVariable Long id,
                                                                        @RequestBody RegistrarRecepcionRequest request) {
        if (request.opcion() != 1) {
            throw new IllegalStateException(
                    "La opción " + request.opcion() + " no está implementada en esta iteración del CU (solo opción 1)");
        }

        Sesion sesion = sesionService.buscarPorId(request.sesionId());

        GestorRegBolsin gestorRegBolsin = new GestorRegBolsin(bolsinService, estadoService);
        gestorRegBolsin.registrarRecepcionBolsin(sesion);
        Bolsin bolsin = gestorRegBolsin.tomarSeleccionBolsin(id);
        gestorRegBolsin.buscarRemitoBolsin();
        gestorRegBolsin.tomarSeleccionOpcRecepcion(request.opcion());
        gestorRegBolsin.tomarSeleccionConfirmacion(true);
        gestorRegBolsin.llamarCU29();
        gestorRegBolsin.FinCU();

        RecepcionResponse response = new RecepcionResponse(
                true,
                "Recepción de bolsín registrada exitosamente",
                bolsinMapper.toDetalleResponse(bolsin)
        );
        return ResponseEntity.ok(response);
    }
}
