package com.grupo8.ppaibolsines.TipoDocumento.Mappers;

import com.grupo8.ppaibolsines.TipoDocumento.Data.Model.TipoDocumento;

public class TipoDocumentoMapper {

    public String toNombre(TipoDocumento tipoDocumento) {
        return tipoDocumento.getNombre();
    }
}
