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

    public void crearCEBolsin(Estado estado, Empleado empleadoResponsable) {
        CambioEstadoBolsin cambioEstado = new CambioEstadoBolsin(LocalDateTime.now(), estado, empleadoResponsable); // Crea un nuevo cambio de estado del bolsín con la fecha actual, el estado y el empleado responsable
        this.cambiosEstadoBolsin.add(cambioEstado); // Agrega el cambio de estado a la lista de cambios de estado del bolsín
    }

    public String obtenerCMOrigen() {
        return this.cmOrigen.getNombre(); // Devuelve el nombre de la comisión médica de origen del bolsín
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
        for (Remito remito : this.remitos) { // Recorre la lista de remitos asociados al bolsín
            remito.getNumero(); // Obtiene el número del remito
            remito.buscarDocumentacion(); // Llama al metodo buscarDocumentacion() del remito para obtener la documentación asociada
        }
        return this.remitos;
    }

    public void setDetalleBolsin(List<Remito> remitos) {
        this.remitos = remitos;
    }

    public boolean sosEnviado() {
        for (CambioEstadoBolsin ce : this.cambiosEstadoBolsin) { // Recorre la lista de cambios de estado del bolsín
            if (ce.sosEnviado()) {  // Si alguno de los cambios de estado indica que el bolsín fue enviado, retorna true
                return true;
            }
        }
        return false;
    }

    public void asignarEstado(Estado estado, Empleado empleadoResponsable) {
        getEstadoActual(); // Metodo disparador para actualizar la fecha de fin del estado actual
        crearCEBolsin(estado, empleadoResponsable); // Crea un nuevo cambio de estado del bolsín con el estado y empleado responsable
    }

    public void asignarEstadoARemito(Estado estado) {
        for (Remito remito : this.remitos) { // Recorre la lista de remitos asociados al bolsín
            remito.asignarEstado(estado); // Llama al metodo asignarEstado() del remito para asignarle el estado correspondiente
        }
    }

    public void asignarEstadoADocumentacion(Estado estado, Empleado empleadoResponsable) {
        for (Remito remito : this.remitos) { // Recorre la lista de remitos asociados al bolsín
            remito.actualizarEstadoDoc(estado, empleadoResponsable); // Llama al metodo de envoltorio actualizarEstadoDoc() del remito para asignarle el estado correspondiente a la documentación asociada
        }
    }

    public boolean esTuCMDestino(ComisionMedica comisionMedica) {
        return this.cmDestino != null && comisionMedica != null
                && this.cmDestino.getId().equals(comisionMedica.getId());
    }

    public CambioEstadoBolsin getEstadoActual() {
        CambioEstadoBolsin actual = this.cambiosEstadoBolsin.stream() // Recorre la lista de cambios de estado del bolsín
                .findFirst()
                .orElse(null);
        if (actual != null) { // Si hay un cambio de estado actual, se actualiza su fecha de fin
            actual.setFechaHoraFin(LocalDateTime.now()); // Actualiza la fecha de fin del estado actual
        }
        return actual; // Retorna el cambio de estado actual del bolsín
    }
}
