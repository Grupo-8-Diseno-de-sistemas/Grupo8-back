package com.grupo8.ppaibolsines.TipoDocumento.Service.implementations;

import com.grupo8.ppaibolsines.TipoDocumento.Data.Model.TipoDocumento;
import com.grupo8.ppaibolsines.TipoDocumento.Repository.ITipoDocumentoRepository;
import com.grupo8.ppaibolsines.TipoDocumento.Service.interfaces.ITipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoService implements ITipoDocumentoService {

    private final ITipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    public TipoDocumentoService(ITipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public List<TipoDocumento> listarTodos() {
        return tipoDocumentoRepository.findAll();
    }
}
