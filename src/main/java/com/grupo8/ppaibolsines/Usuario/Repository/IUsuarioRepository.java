package com.grupo8.ppaibolsines.Usuario.Repository;

import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);
}
