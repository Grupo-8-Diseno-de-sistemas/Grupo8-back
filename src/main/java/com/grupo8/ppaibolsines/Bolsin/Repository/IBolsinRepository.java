package com.grupo8.ppaibolsines.Bolsin.Repository;

import com.grupo8.ppaibolsines.Bolsin.Data.Model.Bolsin;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBolsinRepository extends JpaRepository<Bolsin, Long> {

    List<Bolsin> findByCmDestino(ComisionMedica cmDestino);
}
