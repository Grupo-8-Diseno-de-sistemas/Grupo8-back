package com.grupo8.ppaibolsines.GestorRegBolsin.Service.interfaces;

import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
import com.grupo8.ppaibolsines.Remito.Data.Model.Remito;
import com.grupo8.ppaibolsines.Sesion.Data.Model.Sesion;

import java.time.LocalDateTime;
import java.util.List;

public interface IGestorRegBolsin {

    List<Bolsin> registrarRecepcionBolsin(Sesion sesion);

    ComisionMedica buscarCMDeUsuarioLogueado(Sesion sesion);

    List<Bolsin> buscarBolsinesConEstadoEnviado();

    List<Bolsin> filtrarPorNroPrecinto(String nroPrecinto);

    List<Bolsin> filtrarPorCmOrigen(Long cmOrigenId);

    Bolsin tomarSeleccionBolsin(Long idBolsin);

    List<Remito> buscarRemitoBolsin();

    void tomarSeleccionOpcRecepcion(int opcion);

    void tomarSeleccionConfirmacion(boolean confirmado);

    void actualizarEstados();

    Estado buscarEstadoParaAsignarBolsin();

    LocalDateTime buscarFechaYHoraActual();

    void asignarEstadoBolsin(Estado estado);

    Estado buscarEstadoParaAsignarRemito();

    void asignarEstadoRemito(Estado estado);

    Estado buscarEstadoParaAsignarDocumentacion();

    void asignarEstadoDocumentacion(Estado estado);

    void llamarCU29();

    void FinCU();

    Bolsin getSeleccionadoBolsin();
}
