package com.grupo8.ppaibolsines.Estado.Data.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estado")
@Getter
@Setter
@NoArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ambito;
    private String descripcion;
    private String nombre;

    public Estado(String ambito, String descripcion, String nombre) {
        this.ambito = ambito;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public boolean esAmbitoBolsin() {
        return "Bolsin".equalsIgnoreCase(this.ambito);
    }

    public boolean esAmbitoDocumentacion() {
        return "Documentacion".equalsIgnoreCase(this.ambito);
    }

    public boolean esAmbitoRemito() {
        return "Remito".equalsIgnoreCase(this.ambito);
    }

    public boolean esEnviado() {
        return "Enviado".equalsIgnoreCase(this.nombre); // Si el nombre del estado es "Enviado", entonces el bolsín está en estado "Enviado"
    }

    public boolean esRecibidoEnCMDestino() {
        return "Recibido en CM Destino".equalsIgnoreCase(this.nombre);
    }

    public boolean esRecibidoYAceptado() {
        return "Recibido y Aceptado".equalsIgnoreCase(this.nombre);
    }

    public boolean esRecibidaYAceptada() {
        return "Recibida y Aceptada".equalsIgnoreCase(this.nombre);
    }

    public boolean esNoRecibida() {
        return "No Recibida".equalsIgnoreCase(this.nombre);
    }

    public boolean esRecibidoYAceptadoParcial() {
        return "Recibido y Aceptado Parcial".equalsIgnoreCase(this.nombre);
    }

    public boolean esRecibidaYRechazada() {
        return "Recibida y Rechazada".equalsIgnoreCase(this.nombre);
    }

    public boolean esParaRedirigir() {
        return "Para Redirigir".equalsIgnoreCase(this.nombre);
    }
}
