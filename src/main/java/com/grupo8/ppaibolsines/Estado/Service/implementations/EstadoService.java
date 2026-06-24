package com.grupo8.ppaibolsines.Estado.Service.implementations;

import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
import com.grupo8.ppaibolsines.Estado.Repository.IEstadoRepository;
import com.grupo8.ppaibolsines.Estado.Service.interfaces.IEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService implements IEstadoService {

    private final IEstadoRepository estadoRepository;

    @Autowired
    public EstadoService(IEstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    public List<Estado> buscarPorAmbito(String ambito) {
        return estadoRepository.findByAmbito(ambito);
    }
}
