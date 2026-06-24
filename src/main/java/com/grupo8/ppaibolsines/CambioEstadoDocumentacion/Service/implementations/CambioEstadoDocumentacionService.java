package com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Service.implementations;

import com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Data.Model.CambioEstadoDocumentacion;
import com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Repository.ICambioEstadoDocumentacionRepository;
import com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Service.interfaces.ICambioEstadoDocumentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CambioEstadoDocumentacionService implements ICambioEstadoDocumentacionService {

    private final ICambioEstadoDocumentacionRepository cambioEstadoDocumentacionRepository;

    @Autowired
    public CambioEstadoDocumentacionService(ICambioEstadoDocumentacionRepository cambioEstadoDocumentacionRepository) {
        this.cambioEstadoDocumentacionRepository = cambioEstadoDocumentacionRepository;
    }

    @Override
    public CambioEstadoDocumentacion guardar(CambioEstadoDocumentacion cambioEstadoDocumentacion) {
        return cambioEstadoDocumentacionRepository.save(cambioEstadoDocumentacion);
    }
}
