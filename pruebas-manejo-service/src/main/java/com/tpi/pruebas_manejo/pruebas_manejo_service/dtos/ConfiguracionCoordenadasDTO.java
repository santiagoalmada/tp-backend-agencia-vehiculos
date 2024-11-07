package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import com.tpi.pruebas_manejo.pruebas_manejo_service.utils.Coordenada;
import com.tpi.pruebas_manejo.pruebas_manejo_service.utils.Zona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConfiguracionCoordenadasDTO {
    private Coordenada coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<Zona> zonasRestringidas;
}
