package com.grupo8.ppaibolsines.ComisionMedica.Service.interfaces;

import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;

import java.util.List;

public interface IComisionMedicaService {

    List<ComisionMedica> listarTodas();

    ComisionMedica buscarPorId(Long id);
}
