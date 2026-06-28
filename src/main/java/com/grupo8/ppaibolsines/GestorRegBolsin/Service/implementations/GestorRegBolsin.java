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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<Bolsin> registrarRecepcionBolsin(Sesion sesion) {
        buscarCMDeUsuarioLogueado(sesion);
        return buscarBolsinesConEstadoEnviado();
    }

    @Override
    public ComisionMedica buscarCMDeUsuarioLogueado(Sesion sesion) {
        this.cmDeEmpleadoLogueado = sesion.obtenerUsuarioLogueado(); // Obtengo la CM del empleado logueado desde la sesión
        return this.cmDeEmpleadoLogueado;
    }

    @Override
    public List<Bolsin> buscarBolsinesConEstadoEnviado() { // Obtengo todos los bolsines y filtro por los que tienen estado "Enviado" y cuyo CM destino coincide con el del empleado logueado
        List<Bolsin> todosBolsines = buscar();
        this.listBolsinesEnviados = new ArrayList<>();
        for (Bolsin bolsin : todosBolsines) { // Recorro todos los bolsines y filtro por los que tienen estado "Enviado" y cuyo CM destino coincide con el del empleado logueado
            if (bolsin.esTuCMDestino(this.cmDeEmpleadoLogueado) && bolsin.sosEnviado()) { // Si el bolsín tiene como CM destino la CM del empleado logueado y su estado es "Enviado", lo agrego a la lista de bolsines enviados
                bolsin.obtenerCMOrigen(); // Metodo para obtener la CM de origen del bolsín
                bolsin.getNroPrecinto(); // Metodo para obtener el número de precinto del bolsín
                this.listBolsinesEnviados.add(bolsin); // Agrego el bolsín a la lista de bolsines enviados
            }
        }
        return this.listBolsinesEnviados;
    }

    private List<Bolsin> buscar() {
        return bolsinService.buscarTodos();
    }

    @Override
    public List<Bolsin> filtrarPorNroPrecinto(String nroPrecinto) {
        List<Bolsin> resultado = this.listBolsinesEnviados.stream()
                .filter(b -> b.getNroPrecinto().equalsIgnoreCase(nroPrecinto))
                .toList();
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("No se encontró bolsín con número de precinto: " + nroPrecinto);
        }
        return resultado;
    }

    @Override
    public List<Bolsin> filtrarPorCmOrigen(Long cmOrigenId) {
        List<Bolsin> resultado = this.listBolsinesEnviados.stream()
                .filter(b -> b.esTuCMOrigenId(cmOrigenId))
                .toList();
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("No se encontró bolsín con la CM de origen ingresada.");
        }
        return resultado;
    }

    @Override
    public Bolsin tomarSeleccionBolsin(Long idBolsin) { // Tomo el bolsín seleccionado por el usuario a través de su ID
        this.seleccionadoBolsin = this.listBolsinesEnviados.stream() // Busco el bolsín en la lista de bolsines enviados cuyo ID coincida con el ID del bolsín seleccionado
                .filter(b -> b.getId().equals(idBolsin))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bolsín no encontrado en la lista"));
        return this.seleccionadoBolsin; // Retorno el bolsín seleccionado
    }

    @Override
    public List<Remito> buscarRemitoBolsin() { // Obtengo la información del remito del bolsín seleccionado
        return this.seleccionadoBolsin.obtenerInformacionRemito(); // Retorno la información del remito del bolsín seleccionado
    }

    @Override
    public void tomarSeleccionOpcRecepcion(int opcion) { // Tomo la opción de recepción seleccionada por el usuario
        this.seleccionadaOpcionRecepcion = opcion;
    }

    @Override
    public void tomarSeleccionConfirmacion(boolean confirmado) { // Tomo la confirmación del Encargado de Bolsines sobre la opción de recepción seleccionada
        if (!confirmado) {
            throw new IllegalStateException("El Encargado de Bolsines no confirmó la opción de recepción seleccionada");
        } // Flujo alternativo A6: si el Encargado de Bolsines no confirma la seleccion de la opcion de recepecion a registrar.
        actualizarEstados(); // Actualizo los estados del bolsín, remito y documentación según la opción de recepción seleccionada
    }

    @Override
    public void actualizarEstados() {
        Estado estadoBolsin = buscarEstadoParaAsignarBolsin(); // Busco el estado de ámbito Bolsin para Recibido en CM Destino
        this.fechaHoraActual = buscarFechaYHoraActual(); // Obtengo la fecha y hora actual
        asignarEstadoBolsin(estadoBolsin); // Asigno el estado de ámbito Bolsin para Recibido en CM Destino al bolsín seleccionado

        if (this.seleccionadaOpcionRecepcion == 1) {
            Estado estadoRemito = buscarEstadoParaAsignarRemito(); // Busco el estado de ámbito Remito para Recibido y Aceptado
            asignarEstadoRemito(estadoRemito); // Metodo de emboltorio para asignar el estado de ámbito Remito para Recibido y Aceptado al remito del bolsín seleccionado
            Estado estadoDoc = buscarEstadoParaAsignarDocumentacion(); // Busco el estado de ámbito Documentacion para Recibida y Aceptada
            asignarEstadoDocumentacion(estadoDoc); // Metodo de emboltorio para asignar el estado de ámbito Documentacion para Recibida y Aceptada a la documentación del bolsín seleccionado
        } else if (this.seleccionadaOpcionRecepcion == 2) {
            Estado estadoRemito = buscarEstadoParaAsignarRemitoParcial();
            asignarEstadoRemito(estadoRemito);
            Estado estadoDoc = buscarEstadoParaAsignarDocNoRecibida();
            asignarEstadoDocumentacion(estadoDoc);
        } else if (this.seleccionadaOpcionRecepcion == 3) {
            Estado estadoRemito = buscarEstadoParaAsignarRemitoParcial();
            asignarEstadoRemito(estadoRemito);
            Estado estadoDoc = buscarEstadoParaAsignarDocRechazada();
            asignarEstadoDocumentacion(estadoDoc);
        } else if (this.seleccionadaOpcionRecepcion == 4) {
            Estado estadoRemito = buscarEstadoParaAsignarRemitoParcial();
            asignarEstadoRemito(estadoRemito);
            Estado estadoDoc = buscarEstadoParaAsignarDocParaRedirigir();
            asignarEstadoDocumentacion(estadoDoc);
        }
    }

    @Override
    public Estado buscarEstadoParaAsignarBolsin() {
        for (Estado estado : estadoService.buscarTodos()) { // Recorro todos los estados y busco el estado de ámbito Bolsin para Recibido en CM Destino
            if (estado.esAmbitoBolsin() && estado.esRecibidoEnCMDestino()) { // Si el estado es de ámbito Bolsin y es Recibido en CM Destino, lo retorno
                return estado;
            }
        }
        throw new IllegalStateException("No existe un Estado de ámbito Bolsin para Recibido en CM Destino");
    }

    @Override
    public LocalDateTime buscarFechaYHoraActual() {
        return LocalDateTime.now(); // Retorno la fecha y hora actual
    }

    @Override
    public void asignarEstadoBolsin(Estado estado) {
        this.seleccionadoBolsin.asignarEstado(estado, this.empleadoLogueado); // Metodo de la clase Bolsin para asignar el estado de ámbito Bolsin para Recibido en CM Destino al bolsín seleccionado
    }

    @Override
    public Estado buscarEstadoParaAsignarRemito() {
        for (Estado estado : estadoService.buscarTodos()) { // Recorro todos los estados y busco el estado de ámbito Remito para Recibido y Aceptado
            if (estado.esAmbitoRemito() && estado.esRecibidoYAceptado()) { // Si el estado es de ámbito Remito y es Recibido y Aceptado, lo retorno
                return estado;
            }
        }
        throw new IllegalStateException("No existe un Estado de ámbito Remito para Recibido y Aceptado");
    }

    @Override
    public void asignarEstadoRemito(Estado estado) {
        this.seleccionadoBolsin.asignarEstadoARemito(estado); // Metodo de la clase Bolsin para asignar el estado de ámbito Remito para Recibido y Aceptado al remito del bolsín seleccionado
    }

    @Override
    public Estado buscarEstadoParaAsignarRemitoParcial() {
        for (Estado estado : estadoService.buscarTodos()) {
            if (estado.esAmbitoRemito() && estado.esRecibidoYAceptadoParcial()) {
                return estado;
            }
        }
        throw new IllegalStateException("No existe un Estado de ámbito Remito para Recibido y Aceptado Parcial");
    }

    @Override
    public Estado buscarEstadoParaAsignarDocumentacion() {
        for (Estado estado : estadoService.buscarTodos()) { // Recorro todos los estados y busco el estado de ámbito Documentacion para Recibida y Aceptada
            if (estado.esAmbitoDocumentacion() && estado.esRecibidaYAceptada()) { // Si el estado es de ámbito Documentacion y es Recibida y Aceptada, lo retorno
                return estado;
            }
        }
        throw new IllegalStateException("No existe un Estado de ámbito Documentacion para Recibida y Aceptada");
    }

    @Override
    public Estado buscarEstadoParaAsignarDocNoRecibida() {
        for (Estado estado : estadoService.buscarTodos()) {
            if (estado.esAmbitoDocumentacion() && estado.esNoRecibida()) {
                return estado;
            }
        }
        throw new IllegalStateException("No existe un Estado de ámbito Documentacion para No Recibida");
    }

    @Override
    public Estado buscarEstadoParaAsignarDocRechazada() {
        for (Estado estado : estadoService.buscarTodos()) {
            if (estado.esAmbitoDocumentacion() && estado.esRecibidaYRechazada()) {
                return estado;
            }
        }
        throw new IllegalStateException("No existe un Estado de ámbito Documentacion para Recibida y Rechazada");
    }

    @Override
    public Estado buscarEstadoParaAsignarDocParaRedirigir() {
        for (Estado estado : estadoService.buscarTodos()) {
            if (estado.esAmbitoDocumentacion() && estado.esParaRedirigir()) {
                return estado;
            }
        }
        throw new IllegalStateException("No existe un Estado de ámbito Documentacion para Para Redirigir");
    }

    @Override
    public void asignarEstadoDocumentacion(Estado estado) {
        this.seleccionadoBolsin.asignarEstadoADocumentacion(estado, this.empleadoLogueado); // Metodo de la clase Bolsin para asignar el estado de ámbito Documentacion para Recibida y Aceptada a la documentación del bolsín seleccionado
    }

    @Override
    public void llamarCU29() {
        // CU 29 - Notificar recepción de bolsín: fuera del alcance de esta iteración.
    }

    @Override
    public void FinCU() { // Finalizo el CU y guardo los cambios en la base de datos
        bolsinService.guardar(this.seleccionadoBolsin);
    }

    @Override
    public Bolsin getSeleccionadoBolsin() {
        return this.seleccionadoBolsin;
    }
}
