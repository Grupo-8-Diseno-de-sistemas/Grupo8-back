package com.grupo8.ppaibolsines.CambioEstadoBolsin.Data.Model;

import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
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
@Table(name = "cambio_estado_bolsin")
@Getter
@Setter
@NoArgsConstructor
public class CambioEstadoBolsin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHorainicio;

    @ManyToOne
    private Empleado empleadoResponsable;

    @ManyToOne
    private Estado estado;

    public CambioEstadoBolsin(LocalDateTime fechaHorainicio, Estado estado, Empleado empleadoResponsable) {
        this.fechaHorainicio = fechaHorainicio;
        this.estado = estado;
        this.empleadoResponsable = empleadoResponsable;
    }

    public boolean sosActual() {
        return this.fechaHoraFin == null; // Si la fechaHoraFin es null, significa que el cambio de estado es actual
    }

    public boolean sosEnviado() {
        return this.sosActual() && this.estado.esEnviado(); // Si el cambio de estado es actual y el estado es "Enviado", entonces el bolsín está en estado "Enviado"
    }
}
