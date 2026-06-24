package com.grupo8.ppaibolsines.Empleado.Service.implementations;

import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Empleado.Repository.IEmpleadoRepository;
import com.grupo8.ppaibolsines.Empleado.Service.interfaces.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService implements IEmpleadoService {

    private final IEmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(IEmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el empleado con id " + id));
    }
}
