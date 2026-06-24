package com.grupo8.ppaibolsines.Sesion.Repository;

import com.grupo8.ppaibolsines.Sesion.Data.Model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISesionRepository extends JpaRepository<Sesion, Long> {
}
