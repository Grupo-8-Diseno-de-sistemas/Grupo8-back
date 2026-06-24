package com.grupo8.ppaibolsines.ComisionMedica.Repository;

import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComisionMedicaRepository extends JpaRepository<ComisionMedica, Long> {
}
