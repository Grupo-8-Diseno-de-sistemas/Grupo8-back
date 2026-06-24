package com.grupo8.ppaibolsines.DetalleRemito.Service.implementations;

import com.grupo8.ppaibolsines.DetalleRemito.Data.Model.DetalleRemito;
import com.grupo8.ppaibolsines.DetalleRemito.Repository.IDetalleRemitoRepository;
import com.grupo8.ppaibolsines.DetalleRemito.Service.interfaces.IDetalleRemitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleRemitoService implements IDetalleRemitoService {

    private final IDetalleRemitoRepository detalleRemitoRepository;

    @Autowired
    public DetalleRemitoService(IDetalleRemitoRepository detalleRemitoRepository) {
        this.detalleRemitoRepository = detalleRemitoRepository;
    }

    @Override
    public DetalleRemito guardar(DetalleRemito detalleRemito) {
        return detalleRemitoRepository.save(detalleRemito);
    }
}
