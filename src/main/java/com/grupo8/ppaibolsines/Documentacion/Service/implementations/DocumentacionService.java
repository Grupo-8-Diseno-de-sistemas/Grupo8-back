package com.grupo8.ppaibolsines.Documentacion.Service.implementations;

import com.grupo8.ppaibolsines.Documentacion.Data.Model.Documentacion;
import com.grupo8.ppaibolsines.Documentacion.Repository.IDocumentacionRepository;
import com.grupo8.ppaibolsines.Documentacion.Service.interfaces.IDocumentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentacionService implements IDocumentacionService {

    private final IDocumentacionRepository documentacionRepository;

    @Autowired
    public DocumentacionService(IDocumentacionRepository documentacionRepository) {
        this.documentacionRepository = documentacionRepository;
    }

    @Override
    public Documentacion guardar(Documentacion documentacion) {
        return documentacionRepository.save(documentacion);
    }
}
