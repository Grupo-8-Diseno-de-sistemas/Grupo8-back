package com.grupo8.ppaibolsines.PantallaRegBolsin.Controllers.response;

import com.grupo8.ppaibolsines.Bolsin.Controllers.response.BolsinDetalleResponse;

public record RecepcionResponse(boolean exito, String mensaje, BolsinDetalleResponse bolsin) {
}
