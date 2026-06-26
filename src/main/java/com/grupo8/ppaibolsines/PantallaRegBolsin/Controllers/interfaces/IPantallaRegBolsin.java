package com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.interfaces;

import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinDetalleResponse;
import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinResponse;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.request.RegistrarRecepcionRequest;
import com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.response.RecepcionResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPantallaRegBolsin {

    ResponseEntity<List<BolsinResponse>> listarBolsinesEnviados(Long sesionId, String nroPrecinto, Long cmOrigenId);

    ResponseEntity<RecepcionResponse> registrarRecepcionBolsin(Long idBolsin, RegistrarRecepcionRequest request);
}
