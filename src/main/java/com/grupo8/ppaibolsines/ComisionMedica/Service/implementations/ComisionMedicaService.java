package com.grupo8.ppaibolsines.ComisionMedica.Service.implementations;

import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.ComisionMedica.Repository.IComisionMedicaRepository;
import com.grupo8.ppaibolsines.ComisionMedica.Service.interfaces.IComisionMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComisionMedicaService implements IComisionMedicaService {

    private final IComisionMedicaRepository comisionMedicaRepository;

    @Autowired
    public ComisionMedicaService(IComisionMedicaRepository comisionMedicaRepository) {
        this.comisionMedicaRepository = comisionMedicaRepository;
    }

    @Override
    public List<ComisionMedica> listarTodas() {
        return comisionMedicaRepository.findAll();
    }

    @Override
    public ComisionMedica buscarPorId(Long id) {
        return comisionMedicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la comisión médica con id " + id));
    }
}
