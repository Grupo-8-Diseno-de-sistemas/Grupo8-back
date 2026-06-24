package com.grupo8.ppaibolsines.Bolsin.Service.implementations;

import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.Bolsin.Repository.IBolsinRepository;
import com.grupo8.ppaibolsines.Bolsin.Service.interfaces.IBolsinService;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BolsinService implements IBolsinService {

    private final IBolsinRepository bolsinRepository;

    @Autowired
    public BolsinService(IBolsinRepository bolsinRepository) {
        this.bolsinRepository = bolsinRepository;
    }

    @Override
    public List<Bolsin> buscarConEstadoEnviado(ComisionMedica cmDestino, String numeroPrecinto, ComisionMedica cmOrigen) {
        return bolsinRepository.findByCmDestino(cmDestino).stream()
                .filter(Bolsin::sosEnviado)
                .filter(bolsin -> numeroPrecinto == null || numeroPrecinto.isBlank()
                        || numeroPrecinto.equalsIgnoreCase(bolsin.getNumeroPrecinto()))
                .filter(bolsin -> cmOrigen == null || bolsin.esTuCMOrigen(cmOrigen))
                .toList();
    }

    @Override
    public Bolsin buscarPorId(Long id) {
        return bolsinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el bolsín con id " + id));
    }

    @Override
    public Bolsin guardar(Bolsin bolsin) {
        return bolsinRepository.save(bolsin);
    }
}
