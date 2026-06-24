package com.grupo8.ppaibolsines.Empleado.Repository;

import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
}
