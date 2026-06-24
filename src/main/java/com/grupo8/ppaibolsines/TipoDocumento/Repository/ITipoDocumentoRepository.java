package com.grupo8.ppaibolsines.TipoDocumento.Repository;

import com.grupo8.ppaibolsines.TipoDocumento.Data.Model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
}
