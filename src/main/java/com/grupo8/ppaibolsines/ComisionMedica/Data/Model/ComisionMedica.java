package com.grupo8.ppaibolsines.ComisionMedica.Data.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comision_medica")
@Getter
@Setter
@NoArgsConstructor
public class ComisionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String direccion;
    private String email;
    private String nombre;
    private String telefono;

    public ComisionMedica(String codigo, String direccion, String email, String nombre, String telefono) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
    }
}
