package com.grupo8.ppaibolsines.Documentacion.Repository;

import com.grupo8.ppaibolsines.Documentacion.Data.Model.Documentacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDocumentacionRepository extends JpaRepository<Documentacion, Long> {
}
