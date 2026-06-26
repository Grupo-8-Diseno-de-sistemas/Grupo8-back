package com.grupo8.ppaibolsines.Estado.Service.interfaces;

import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;

import java.util.List;

public interface IEstadoService {

    List<Estado> buscarPorAmbito(String ambito);

    List<Estado> buscarTodos();
}
