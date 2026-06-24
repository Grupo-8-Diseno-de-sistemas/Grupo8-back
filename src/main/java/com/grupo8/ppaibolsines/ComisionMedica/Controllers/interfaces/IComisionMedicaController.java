package com.grupo8.ppaibolsines.ComisionMedica.Controllers.interfaces;

import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IComisionMedicaController {

    ResponseEntity<List<ComisionMedicaResponse>> listarComisiones();
}
