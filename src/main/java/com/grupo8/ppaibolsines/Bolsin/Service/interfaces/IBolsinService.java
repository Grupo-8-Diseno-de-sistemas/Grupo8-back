package com.grupo8.ppaibolsines.Bolsin.Service.interfaces;

import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;

import java.util.List;

public interface IBolsinService {

    List<Bolsin> buscarConEstadoEnviado(ComisionMedica cmDestino, String numeroPrecinto, ComisionMedica cmOrigen);

    List<Bolsin> buscarTodos();

    Bolsin buscarPorId(Long id);

    Bolsin guardar(Bolsin bolsin);
}
