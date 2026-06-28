package com.grupo8.ppaibolsines.Sesion.Data.Model;

import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.Usuario.Data.Model.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sesion")
@Getter
@Setter
@NoArgsConstructor
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime horaInicioSesion;
    private LocalDateTime horaCierreSesion;

    @ManyToOne
    private Usuario usuario;

    public Sesion(Usuario usuario, LocalDateTime horaInicioSesion) {
        this.usuario = usuario;
        this.horaInicioSesion = horaInicioSesion;
    }

    public ComisionMedica obtenerUsuarioLogueado() {
        return this.usuario.obtenerEmpleadoLogueado(); // Obtengo la CM del empleado logueado desde el usuario
    }
}
