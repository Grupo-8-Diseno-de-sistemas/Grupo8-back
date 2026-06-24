package com.grupo8.ppaibolsines.Empleado.Data.Model;

import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "empleado")
@Getter
@Setter
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apellido;
    private String email;
    private String nombre;

    @ElementCollection
    private List<String> perfiles = new ArrayList<>();

    @ManyToOne
    private ComisionMedica comisionMedica;

    public Empleado(String apellido, String email, String nombre, ComisionMedica comisionMedica) {
        this.apellido = apellido;
        this.email = email;
        this.nombre = nombre;
        this.comisionMedica = comisionMedica;
    }

    public boolean esTuCM(ComisionMedica comisionMedica) {
        return this.comisionMedica != null
                && comisionMedica != null
                && Objects.equals(this.comisionMedica.getId(), comisionMedica.getId());
    }

    public ComisionMedica getCM() {
        return this.comisionMedica;
    }

    public ComisionMedica mostrarCM() {
        return getCM();
    }

    public boolean sosGCM() {
        return perfiles != null && perfiles.contains("GCM");
    }
}
