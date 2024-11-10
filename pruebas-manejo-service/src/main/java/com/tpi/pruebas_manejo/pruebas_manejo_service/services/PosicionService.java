package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.ConfiguracionCoordenadasDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.NotificacionDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.PosicionDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Empleado;
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
    @Autowired
    private NotificacionService NotificacionService;


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

        // Obtengo el empleado que esta realizando la prueba
        Empleado empleado = vehiculo.getEmpleadoEnPrueba();
        fechaHora = LocalDateTime.now();

        // Verificar si la posición está dentro de limites establecidos:
        if (!posicion.estaDentroDelRadio(configuracionDTO.getCoordenadasAgencia(),  configuracionDTO.getRadioAdmitidoKm() )) {

            // Mensaje
            String mensaje = String.format(
                    "El vehículo con patente %s, año %d y marca %s está a más de " + configuracionDTO.getRadioAdmitidoKm() + " de la Empesa. " +
                            "Posición actual: Latitud %.4f, Longitud %.4f.",
                    vehiculo.getPatente(),
                    vehiculo.getAnio(),
                    vehiculo.getModelo().getMarca().getNombre(),
                    vehiculo.getPosicion().getLatitud(),
                    vehiculo.getPosicion().getLongitud()
            );


            NotificacionDTO notificacion = new NotificacionDTO();
            // generar un id random
            notificacion.setId(123);
            notificacion.setMensaje(mensaje);
            notificacion.setTipo("ALERTA");
            notificacion.setTelefono(empleado.getTelefonoContacto());

            // Enviar notificación al otro servicio
            NotificacionService.enviarNotificacion(notificacion);

        };

        // Verificar si la posición está fuera de limites establecidos:
        if (posicion.estaDentroDeZonas(configuracionDTO.getZonasRestringidas() )) {

            // Mensaje
            String mensaje = String.format(
                    "El vehículo con patente %s, año %d y marca %s está dentro de una zona peligrosa. " +
                            "Posición actual: Latitud %.4f, Longitud %.4f.",
                    vehiculo.getPatente(),
                    vehiculo.getAnio(),
                    vehiculo.getModelo().getMarca().getNombre(),
                    vehiculo.getPosicion().getLatitud(),
                    vehiculo.getPosicion().getLongitud()
            );

            NotificacionDTO notificacion = new NotificacionDTO();
            notificacion.setId(124);
            notificacion.setMensaje(mensaje);
            notificacion.setTipo("ALERTA");
            notificacion.setTelefono(empleado.getTelefonoContacto());

            // Enviar notificación al otro servicio
            NotificacionService.enviarNotificacion(notificacion);
        };
    }
}
