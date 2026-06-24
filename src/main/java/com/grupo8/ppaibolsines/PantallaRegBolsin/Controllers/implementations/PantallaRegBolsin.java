package com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.implementations;

import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinDetalleResponse;
import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinResponse;
import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.Bolsin.Mappers.BolsinMapper;
import com.grupo8.ppaibolsines.Bolsin.Service.interfaces.IBolsinService;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.ComisionMedica.Service.interfaces.IComisionMedicaService;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Empleado.Service.interfaces.IEmpleadoService;
import com.grupo8.ppaibolsines.Estado.Service.interfaces.IEstadoService;
import com.grupo8.ppaibolsines.GestorRegBolsin.Service.implementations.GestorRegBolsin;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.interfaces.IPantallaRegBolsin;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.request.RegistrarRecepcionRequest;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.response.RecepcionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final IEmpleadoService empleadoService;
    private final IComisionMedicaService comisionMedicaService;
    private final BolsinMapper bolsinMapper;

    @Autowired
    public PantallaRegBolsin(IBolsinService bolsinService,
                              IEstadoService estadoService,
                              IEmpleadoService empleadoService,
                              IComisionMedicaService comisionMedicaService) {
        this.bolsinService = bolsinService;
        this.estadoService = estadoService;
        this.empleadoService = empleadoService;
        this.comisionMedicaService = comisionMedicaService;
        this.bolsinMapper = new BolsinMapper();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<BolsinResponse>> listarBolsinesEnviados(
            @RequestParam("cmDestino") Long cmDestinoId,
            @RequestParam(value = "precinto", required = false) String precinto,
            @RequestParam(value = "cmOrigen", required = false) Long cmOrigenId) {

        ComisionMedica cmDestino = comisionMedicaService.buscarPorId(cmDestinoId);
        ComisionMedica cmOrigen = cmOrigenId != null ? comisionMedicaService.buscarPorId(cmOrigenId) : null;

        GestorRegBolsin gestorRegBolsin = new GestorRegBolsin(bolsinService, estadoService);
        List<Bolsin> bolsines = gestorRegBolsin.buscarBolsinesConEstadoEnviado(cmDestino, precinto, cmOrigen);

        return ResponseEntity.ok(bolsines.stream().map(bolsinMapper::toResponse).toList());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BolsinDetalleResponse> obtenerDetalleBolsin(@PathVariable Long id) {
        GestorRegBolsin gestorRegBolsin = new GestorRegBolsin(bolsinService, estadoService);
        Bolsin bolsin = gestorRegBolsin.tomarSeleccionBolsin(id);
        gestorRegBolsin.buscarRemitoBolsin();

        return ResponseEntity.ok(bolsinMapper.toDetalleResponse(bolsin));
    }

    @Override
    @PutMapping("/{id}/recepcion")
    public ResponseEntity<RecepcionResponse> registrarRecepcionBolsin(@PathVariable Long id,
                                                                        @RequestBody RegistrarRecepcionRequest request) {
        if (request.opcion() != 1) {
            throw new IllegalStateException(
                    "La opción " + request.opcion() + " no está implementada en esta iteración del CU (solo opción 1)");
        }

        Empleado empleadoLogueado = empleadoService.buscarPorId(request.empleadoId());

        GestorRegBolsin gestorRegBolsin = new GestorRegBolsin(bolsinService, estadoService);
        gestorRegBolsin.buscarCMDeUsuarioLogueado(empleadoLogueado);
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
