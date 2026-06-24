package com.grupo8.ppaibolsines.Remito.Service.implementations;

import com.grupo8.ppaibolsines.Remito.Data.Model.Remito;
import com.grupo8.ppaibolsines.Remito.Repository.IRemitoRepository;
import com.grupo8.ppaibolsines.Remito.Service.interfaces.IRemitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemitoService implements IRemitoService {

    private final IRemitoRepository remitoRepository;

    @Autowired
    public RemitoService(IRemitoRepository remitoRepository) {
        this.remitoRepository = remitoRepository;
    }

    @Override
    public Remito guardar(Remito remito) {
        return remitoRepository.save(remito);
    }
}
