package com.grupo8.ppaibolsines.ComisionMedica.Mappers;

import com.grupo8.ppaibolsines.ComisionMedica.Controllers.response.ComisionMedicaResponse;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;

public class ComisionMedicaMapper {

    public ComisionMedicaResponse toResponse(ComisionMedica comisionMedica) {
        return new ComisionMedicaResponse(
                comisionMedica.getId(),
                comisionMedica.getNombre(),
                comisionMedica.getCodigo()
        );
    }
}
