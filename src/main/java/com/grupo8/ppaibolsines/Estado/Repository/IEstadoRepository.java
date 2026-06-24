package com.grupo8.ppaibolsines.Estado.Repository;

import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEstadoRepository extends JpaRepository<Estado, Long> {

    List<Estado> findByAmbito(String ambito);
}
