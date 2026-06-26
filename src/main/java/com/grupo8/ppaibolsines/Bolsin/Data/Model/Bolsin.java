package com.grupo8.ppaibolsines.Bolsin.Data.Model;

import com.grupo8.ppaibolsines.CambioEstadoBolsin.Data.Model.CambioEstadoBolsin;
import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
import com.grupo8.ppaibolsines.Remito.Data.Model.Remito;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bolsin")
@Getter
@Setter
@NoArgsConstructor
public class Bolsin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroPrecinto;
    private String numeroBolsin;
    private Double peso;

    @ManyToOne
    private ComisionMedica cmOrigen;

    @ManyToOne
    private ComisionMedica cmDestino;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bolsin_id")
    private List<Remito> remitos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bolsin_id")
    private List<CambioEstadoBolsin> cambiosEstadoBolsin = new ArrayList<>();

    public Bolsin(String numeroPrecinto, String numeroBolsin, Double peso, ComisionMedica cmOrigen, ComisionMedica cmDestino) {
        this.numeroPrecinto = numeroPrecinto;
        this.numeroBolsin = numeroBolsin;
        this.peso = peso;
        this.cmOrigen = cmOrigen;
        this.cmDestino = cmDestino;
    }

    public void asociarRemito(Remito remito) {
        this.remitos.add(remito);
    }

    public void crearCEBolsin(Estado estado, Empleado empleadoResponsable) {
        CambioEstadoBolsin cambioEstado = new CambioEstadoBolsin(LocalDateTime.now(), estado, empleadoResponsable);
        this.cambiosEstadoBolsin.add(cambioEstado);
    }

    public String obtenerCMOrigen() {
        return this.cmOrigen.getNombre();
    }

    public boolean esTuCMOrigenId(Long cmOrigenId) {
        return this.cmOrigen != null && cmOrigenId != null
                && this.cmOrigen.getId().equals(cmOrigenId);
    }

    public boolean esTuCMOrigen(ComisionMedica comisionMedica) {
        return this.cmOrigen != null && comisionMedica != null
                && this.cmOrigen.getId().equals(comisionMedica.getId());
    }

    public String getNroPrecinto() {
        return this.numeroPrecinto;
    }

    public String mostrarDatos() {
        return this.numeroPrecinto + " - " + this.numeroBolsin;
    }

    public ComisionMedica obtenerCMDestino() {
        return this.cmDestino;
    }

    public List<Remito> obtenerInformacionRemito() {
        for (Remito remito : this.remitos) {
            remito.getNumero();
            remito.buscarDocumentacion();
        }
        return this.remitos;
    }

    public void setDetalleBolsin(List<Remito> remitos) {
        this.remitos = remitos;
    }

    public boolean sosEnviado() {
        for (CambioEstadoBolsin ce : this.cambiosEstadoBolsin) {
            if (ce.sosEnviado()) {
                return true;
            }
        }
        return false;
    }

    public void asignarEstado(Estado estado, Empleado empleadoResponsable) {
        getEstadoActual();
        crearCEBolsin(estado, empleadoResponsable);
    }

    public void asignarEstadoARemito(Estado estado) {
        for (Remito remito : this.remitos) {
            remito.asignarEstado(estado);
        }
    }

    public void asignarEstadoADocumentacion(Estado estado, Empleado empleadoResponsable) {
        for (Remito remito : this.remitos) {
            remito.actualizarEstadoDoc(estado, empleadoResponsable);
        }
    }

    public boolean esTuCMDestino(ComisionMedica comisionMedica) {
        return this.cmDestino != null && comisionMedica != null
                && this.cmDestino.getId().equals(comisionMedica.getId());
    }

    public CambioEstadoBolsin getEstadoActual() {
        CambioEstadoBolsin actual = this.cambiosEstadoBolsin.stream()
                .filter(CambioEstadoBolsin::sosActual)
                .findFirst()
                .orElse(null);
        if (actual != null) {
            actual.setFechaHoraFin(LocalDateTime.now());
        }
        return actual;
    }
}
