package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.ConfiguracionCoordenadasDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.PosicionDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Posicion;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Vehiculo;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PosicionRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class PosicionService {
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private PosicionRepository posicionRepository;
    @Autowired
    private ConfiguracionCoordenadasService ConfiguracionCoordenadasService;


    // actualizarPosicion
    // Actualizar la posición del vehículo con los datos recibidos (DTO).

    public void actualizarPosicion( PosicionDTO request ){

        // Obtenemos la configuración de coordenadas:
        ConfiguracionCoordenadasDTO configuracionDTO = ConfiguracionCoordenadasService.obtenerConfiguracion();

        System.out.println("Agencia coordenada: " + configuracionDTO.getCoordenadasAgencia());

        // Buscamos el vehículo:
        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId()).
                orElseThrow(() -> new RuntimeException("Vehículo no encontrado."));

        // Buscamos la posicion que le corresponde al vehiculo (si no existe, la creamos):
        Posicion posicion = posicionRepository.findByVehiculoId(vehiculo.getId());
        if (posicion == null) {
            posicion = new Posicion();
            posicion.setVehiculo(vehiculo);
        }

        // Fecha y hora actuales
        LocalDateTime fechaHora = LocalDateTime.now();

        // Actualizamos la posición del vehículo:
        posicion.setLatitud(request.getLatitud());
        posicion.setLongitud(request.getLongitud());
        posicion.setFechaHora(fechaHora);

        // Seteamos la posición al vehículo:
        vehiculo.setPosicion(posicion);

        // Guardamos la posición en la base de datos:
        posicionRepository.save(posicion);

        // Verificar si el vehiculo esta en alguna prueba en curso (si es asi no hacemos nada)
        if (!vehiculo.estasSiendoProbado()) {
            return;
        };

        // Verificar si la posición está dentro de limites establecidos:
        if (!posicion.estaDentroDelRadio(configuracionDTO.getCoordenadasAgencia(),  configuracionDTO.getRadioAdmitidoKm() )) {
            // Enviar notificación al otro servicio
            System.out.printf("El vehículo %d está fuera del radio permitido.\n", vehiculo.getId());
        };

        // Verificar si la posición está fuera de limites establecidos:
        if (posicion.estaDentroDeZonas(configuracionDTO.getZonasRestringidas() )) {
            // Enviar notificación al otro servicio
            System.out.printf("El vehículo %d está dentro de una zona peligrosa\n", vehiculo.getId());
        };
    }
}
