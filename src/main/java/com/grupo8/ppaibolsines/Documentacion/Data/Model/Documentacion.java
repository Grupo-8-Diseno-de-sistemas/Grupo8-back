package com.grupo8.ppaibolsines.Documentacion.Data.Model;

import com.grupo8.ppaibolsines.CambioEstadoDocumentacion.Data.Model.CambioEstadoDocumentacion;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
import com.grupo8.ppaibolsines.TipoDocumento.Data.Model.TipoDocumento;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documentacion")
@Getter
@Setter
@NoArgsConstructor
public class Documentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String asunto;
    private LocalDate fechaPase;
    private String numero;

    @ManyToOne
    private TipoDocumento tipoDocumento;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentacion_id")
    private List<CambioEstadoDocumentacion> cambiosEstadoDocumentacion = new ArrayList<>();

    public Documentacion(String asunto, LocalDate fechaPase, String numero, TipoDocumento tipoDocumento) {
        this.asunto = asunto;
        this.fechaPase = fechaPase;
        this.numero = numero;
        this.tipoDocumento = tipoDocumento;
    }

    public void remitar(Estado estado) {
        asignarEstado(estado, null);
    }

    public void cancelar(Estado estado) {
        asignarEstado(estado, null);
    }

    public void darDeBaja(Estado estado) {
        asignarEstado(estado, null);
    }

    public void enBolsinSaliente(Estado estado) {
        asignarEstado(estado, null);
    }

    public void quitarDeBolsin(Estado estado, boolean seQuitaDocumentacion) {
        if (seQuitaDocumentacion) {
            asignarEstado(estado, null);
        }
    }

    public void enviarBolsin(Estado estado) {
        asignarEstado(estado, null);
    }

    public void rechazar(Estado estado, Empleado empleadoResponsable) {
        asignarEstado(estado, empleadoResponsable);
    }

    public void redirigirDocumentacion(Estado estado, Empleado empleadoResponsable) {
        asignarEstado(estado, empleadoResponsable);
    }

    public void aceptar(Estado estado, Empleado empleadoResponsable) {
        asignarEstado(estado, empleadoResponsable);
    }

    public void noRecibir(Estado estado, Empleado empleadoResponsable) {
        asignarEstado(estado, empleadoResponsable);
    }

    public void reenvioDocumentacion(Estado estado, Empleado empleadoResponsable) {
        asignarEstado(estado, empleadoResponsable);
    }

    public void crearCE(Estado estado, Empleado empleadoResponsable) {
        CambioEstadoDocumentacion cambioEstado = new CambioEstadoDocumentacion(LocalDateTime.now(), estado, empleadoResponsable); // Crea un nuevo cambio de estado de la documentación con la fecha y hora actual, el estado y el empleado responsable
        this.cambiosEstadoDocumentacion.add(cambioEstado); // Agrega el cambio de estado a la lista de cambios de estado de la documentación
    }

    public String mostrarTipoDocumentacion() {
        return this.tipoDocumento != null ? this.tipoDocumento.getNombre() : null; // Devuelve el nombre del tipo de documentación si existe, de lo contrario devuelve null
    }

    public void setEstado(Estado estado) {
        this.cambiosEstadoDocumentacion.stream()
                .filter(CambioEstadoDocumentacion::sosActual)
                .findFirst()
                .ifPresent(actual -> actual.setEstado(estado));
    }

    public void asignarEstado(Estado estado, Empleado empleadoResponsable) {
        getEstadoActual(); // Metodo disparador para actualizar la fecha de fin del estado actual
        crearCE(estado, empleadoResponsable); // Crea un nuevo cambio de estado de la documentación con el estado y empleado responsable
    }

    public CambioEstadoDocumentacion getEstadoActual() {
        CambioEstadoDocumentacion actual = this.cambiosEstadoDocumentacion.stream()
                .filter(CambioEstadoDocumentacion::sosActual)
                .findFirst()
                .orElse(null);
        if (actual != null) { // Si se encuentra un cambio de estado actual, se actualiza su fecha de fin con la fecha y hora actual
            actual.setFechaHoraFin(LocalDateTime.now()); // Actualiza la fecha de fin del estado actual
        }
        return actual;
    }
}
