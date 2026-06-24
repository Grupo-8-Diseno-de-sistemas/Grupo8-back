package com.grupo8.ppaibolsines.Usuario.Data.Model;

import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String contraseña;

    @OneToOne
    private Empleado empleado;

    public Usuario(String nombre, String contraseña, Empleado empleado) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.empleado = empleado;
    }

    public Empleado obtenerEmpleadoLogueado() {
        return this.empleado;
    }
}
