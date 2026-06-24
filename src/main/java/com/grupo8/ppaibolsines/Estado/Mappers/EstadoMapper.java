package com.grupo8.ppaibolsines.Estado.Mappers;

import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;

public class EstadoMapper {

    public String toNombre(Estado estado) {
        return estado.getNombre();
    }
}
