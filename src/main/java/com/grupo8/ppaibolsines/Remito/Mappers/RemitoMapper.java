package com.grupo8.ppaibolsines.Remito.Mappers;

import com.grupo8.ppaibolsines.Documentacion.Mappers.DocumentacionMapper;
import com.grupo8.ppaibolsines.Remito.Controllers.response.RemitoResponse;
import com.grupo8.ppaibolsines.Remito.Data.Model.Remito;

public class RemitoMapper {

    private final DocumentacionMapper documentacionMapper = new DocumentacionMapper();

    public RemitoResponse toResponse(Remito remito) {
        return new RemitoResponse(
                remito.getId(),
                remito.getNumero(),
                remito.getFecha(),
                remito.getEstado() != null ? remito.getEstado().getNombre() : null,
                remito.buscarDocumentacionIncluida().stream()
                        .map(documentacionMapper::toResponse)
                        .toList()
        );
    }
}
