package com.grupo8.ppaibolsines.GestorRegBolsin.Service.implementations;

import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.Bolsin.Service.interfaces.IBolsinService;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
import com.grupo8.ppaibolsines.Estado.Service.interfaces.IEstadoService;
import com.grupo8.ppaibolsines.GestorRegBolsin.Service.interfaces.IGestorRegBolsin;
import com.grupo8.ppaibolsines.Remito.Data.Model.Remito;
import com.grupo8.ppaibolsines.Sesion.Data.Model.Sesion;
import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class GestorRegBolsin implements IGestorRegBolsin {

    private final IBolsinService bolsinService;
    private final IEstadoService estadoService;

    private LocalDateTime fechaHoraActual;
    private Empleado empleadoLogueado;
    private ComisionMedica cmDeEmpleadoLogueado;
    private Bolsin seleccionadoBolsin;
    private Integer seleccionadaOpcionRecepcion;
    private List<Bolsin> listBolsinesEnviados;

    public GestorRegBolsin(IBolsinService bolsinService, IEstadoService estadoService) {
        this.bolsinService = bolsinService;
        this.estadoService = estadoService;
    }

    @Override
    public List<Bolsin> registrarRecepcionBolsin(Sesion sesion, String numeroPrecinto, ComisionMedica cmOrigen) {
        buscarCMDeUsuarioLogueado(sesion);
        return buscarBolsinesConEstadoEnviado(this.cmDeEmpleadoLogueado, numeroPrecinto, cmOrigen);
    }

    @Override
    public ComisionMedica buscarCMDeUsuarioLogueado(Sesion sesion) {
        Usuario usuario = sesion.obtenerUsuarioLogueado();
        this.empleadoLogueado = usuario.obtenerEmpleadoLogueado();
        this.cmDeEmpleadoLogueado = this.empleadoLogueado.getCM();
        return this.cmDeEmpleadoLogueado;
    }

    @Override
    public List<Bolsin> buscarBolsinesConEstadoEnviado(ComisionMedica cmDestino, String numeroPrecinto, ComisionMedica cmOrigen) {
        this.listBolsinesEnviados = bolsinService.buscarConEstadoEnviado(cmDestino, numeroPrecinto, cmOrigen);
        return this.listBolsinesEnviados;
    }

    @Override
    public Bolsin tomarSeleccionBolsin(Long idBolsin) {
        this.seleccionadoBolsin = bolsinService.buscarPorId(idBolsin);
        return this.seleccionadoBolsin;
    }

    @Override
    public List<Remito> buscarRemitoBolsin() {
        return this.seleccionadoBolsin.obtenerInformacionRemito();
    }

    @Override
    public void tomarSeleccionOpcRecepcion(int opcion) {
        this.seleccionadaOpcionRecepcion = opcion;
    }

    @Override
    public void tomarSeleccionConfirmacion(boolean confirmado) {
        if (!confirmado) {
            throw new IllegalStateException("El Encargado de Bolsines no confirmó la opción de recepción seleccionada");
        }
        actualizarEstados();
    }

    @Override
    public void actualizarEstados() {
        Estado estadoBolsin = buscarEstadoParaAsignarBolsin();
        this.fechaHoraActual = buscarFechaYHoraActual();
        asignarEstadoBolsin(estadoBolsin);

        Estado estadoRemito = buscarEstadoParaAsignarRemito();
        asignarEstadoRemito(estadoRemito);

        Estado estadoDocumentacion = buscarEstadoParaAsignarDocumentacion();
        asignarEstadoDocumentacion(estadoDocumentacion);
    }

    @Override
    public Estado buscarEstadoParaAsignarBolsin() {
        return estadoService.buscarPorAmbito("Bolsin").stream()
                .filter(Estado::esRecibidoEnCMDestino)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No existe un Estado de ámbito Bolsin para Recibido en CM Destino"));
    }

    @Override
    public LocalDateTime buscarFechaYHoraActual() {
        return LocalDateTime.now();
    }

    @Override
    public void asignarEstadoBolsin(Estado estado) {
        this.seleccionadoBolsin.asignarEstado(estado, this.empleadoLogueado);
    }

    @Override
    public Estado buscarEstadoParaAsignarRemito() {
        return estadoService.buscarPorAmbito("Remito").stream()
                .filter(Estado::esRecibidoYAceptado)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No existe un Estado de ámbito Remito para Recibido y Aceptado"));
    }

    @Override
    public void asignarEstadoRemito(Estado estado) {
        this.seleccionadoBolsin.asignarEstadoARemito(estado);
    }

    @Override
    public Estado buscarEstadoParaAsignarDocumentacion() {
        return estadoService.buscarPorAmbito("Documentacion").stream()
                .filter(Estado::esRecibidaYAceptada)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No existe un Estado de ámbito Documentacion para Recibida y Aceptada"));
    }

    @Override
    public void asignarEstadoDocumentacion(Estado estado) {
        this.seleccionadoBolsin.asignarEstadoADocumentacion(estado, this.empleadoLogueado);
    }

    @Override
    public void llamarCU29() {
        // CU 29 - Notificar recepción de bolsín: fuera del alcance de esta iteración (flujo básico).
    }

    @Override
    public void FinCU() {
        bolsinService.guardar(this.seleccionadoBolsin);
    }

    @Override
    public Bolsin getSeleccionadoBolsin() {
        return this.seleccionadoBolsin;
    }
}
