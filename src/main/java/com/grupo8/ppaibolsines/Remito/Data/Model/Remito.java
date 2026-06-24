package com.grupo8.ppaibolsines.Remito.Data.Model;

import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.DetalleRemito.Data.Model.DetalleRemito;
import com.grupo8.ppaibolsines.Documentacion.Data.Model.Documentacion;
import com.grupo8.ppaibolsines.Empleado.Data.Model.Empleado;
import com.grupo8.ppaibolsines.Estado.Data.Model.Estado;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "remito")
@Getter
@Setter
@NoArgsConstructor
public class Remito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private LocalDate fecha;

    @ManyToOne
    private ComisionMedica cmOrigen;

    @ManyToOne
    private Estado estado;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "remito_id")
    private List<DetalleRemito> detalleRemitos = new ArrayList<>();

    public Remito(String numero, LocalDate fecha, ComisionMedica cmOrigen) {
        this.numero = numero;
        this.fecha = fecha;
        this.cmOrigen = cmOrigen;
    }

    public List<Documentacion> buscarDocumentacionIncluida() {
        return this.detalleRemitos.stream()
                .map(DetalleRemito::getDocumentacion)
                .toList();
    }

    public boolean estasGenerado() {
        return this.estado != null;
    }

    public String getDatosRemito() {
        return this.numero + " - " + this.fecha;
    }

    public void modificarDocumentacionIncluida(List<DetalleRemito> detalleRemitos) {
        this.detalleRemitos = detalleRemitos;
    }

    public String mostrarInformacionRemito() {
        return getDatosRemito();
    }

    public boolean tenesEstaCMDestino(ComisionMedica comisionMedica) {
        return this.detalleRemitos.stream()
                .map(DetalleRemito::getAreaCMCDestino)
                .filter(cm -> cm != null)
                .anyMatch(cm -> cm.getId().equals(comisionMedica.getId()));
    }

    public boolean tenesEstaCMOrigen(ComisionMedica comisionMedica) {
        return this.cmOrigen != null && comisionMedica != null
                && this.cmOrigen.getId().equals(comisionMedica.getId());
    }

    public void tomarDocumentacion(DetalleRemito detalleRemito) {
        this.detalleRemitos.add(detalleRemito);
    }

    public void asignarEstado(Estado estado) {
        setEstado(estado);
    }

    public void actualizarEstadoDoc(Estado estado, Empleado empleadoResponsable) {
        for (DetalleRemito detalleRemito : this.detalleRemitos) {
            detalleRemito.actualizarEstadoDoc(estado, empleadoResponsable);
        }
    }
}
