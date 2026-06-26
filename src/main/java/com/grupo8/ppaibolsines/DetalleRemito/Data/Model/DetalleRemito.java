package com.grupo8.ppaibolsines.DetalleRemito.Data.Model;

import com.grupo8.ppaibolsines.ComisionMedica.Data.Model.ComisionMedica;
import com.grupo8.ppaibolsines.Documentacion.Data.Model.Documentacion;
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

@Entity
@Table(name = "detalle_remito")
@Getter
@Setter
@NoArgsConstructor
public class DetalleRemito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ComisionMedica areaCMCDestino;

    @ManyToOne
    private Documentacion documentacion;

    public DetalleRemito(ComisionMedica areaCMCDestino, Documentacion documentacion) {
        this.areaCMCDestino = areaCMCDestino;
        this.documentacion = documentacion;
    }

    public void aceptarDocumentacion(Estado estado, Empleado empleadoResponsable) {
        this.documentacion.aceptar(estado, empleadoResponsable);
    }

    public void actualizarEstadoDoc(Estado estado, Empleado empleadoResponsable) {
        this.documentacion.asignarEstado(estado, empleadoResponsable);
    }

    public Documentacion getDocumentacion() {
        this.documentacion.getAsunto();
        this.documentacion.mostrarTipoDocumentacion();
        return this.documentacion;
    }

    public String mostrarDocumentacion() {
        return this.documentacion.getDatosDocumentacion();
    }
}
