package com.grupo8.ppaibolsines.CambioEstadoBolsin.Service.implementations;

import com.grupo8.ppaibolsines.CambioEstadoBolsin.Data.Model.CambioEstadoBolsin;
import com.grupo8.ppaibolsines.CambioEstadoBolsin.Repository.ICambioEstadoBolsinRepository;
import com.grupo8.ppaibolsines.CambioEstadoBolsin.Service.interfaces.ICambioEstadoBolsinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CambioEstadoBolsinService implements ICambioEstadoBolsinService {

    private final ICambioEstadoBolsinRepository cambioEstadoBolsinRepository;

    @Autowired
    public CambioEstadoBolsinService(ICambioEstadoBolsinRepository cambioEstadoBolsinRepository) {
        this.cambioEstadoBolsinRepository = cambioEstadoBolsinRepository;
    }

    @Override
    public CambioEstadoBolsin guardar(CambioEstadoBolsin cambioEstadoBolsin) {
        return cambioEstadoBolsinRepository.save(cambioEstadoBolsin);
    }
}
