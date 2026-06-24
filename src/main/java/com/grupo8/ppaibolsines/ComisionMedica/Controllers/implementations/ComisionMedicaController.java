package com.grupo8.ppaibolsines.ComisionMedica.Controllers.implementations;

import com.grupo8.ppaibolsines.ComisionMedica.Controllers.interfaces.IComisionMedicaController;
import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.ComisionMedica.Mappers.ComisionMedicaMapper;
import com.grupo8.ppaibolsines.ComisionMedica.Service.interfaces.IComisionMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comisiones")
public class ComisionMedicaController implements IComisionMedicaController {

    private final IComisionMedicaService comisionMedicaService;
    private final ComisionMedicaMapper comisionMedicaMapper;

    @Autowired
    public ComisionMedicaController(IComisionMedicaService comisionMedicaService) {
        this.comisionMedicaService = comisionMedicaService;
        this.comisionMedicaMapper = new ComisionMedicaMapper();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ComisionMedicaResponse>> listarComisiones() {
        List<ComisionMedicaResponse> comisiones = comisionMedicaService.listarTodas().stream()
                .map(comisionMedicaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(comisiones);
    }
}
