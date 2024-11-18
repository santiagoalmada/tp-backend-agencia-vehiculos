package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.ConfiguracionCoordenadasDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.NotificacionDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.PosicionDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.*;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.InteresadoRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PosicionRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PruebaRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class PosicionService {
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private PosicionRepository posicionRepository;
    @Autowired
    private InteresadoRepository interesadoRepository;

    @Autowired
    private ConfiguracionCoordenadasService ConfiguracionCoordenadasService;
    @Autowired
    private NotificacionService NotificacionService;
    @Autowired
    private PruebaRepository pruebaRepository;


    // actualizarPosicion
    // Actualizar la posición del vehículo con los datos recibidos (DTO).

    public void actualizarPosicion( PosicionDTO request ){

        // Obtenemos la configuración de coordenadas:
        ConfiguracionCoordenadasDTO configuracionDTO = ConfiguracionCoordenadasService.obtenerConfiguracion();

        System.out.println("Agencia coordenada: " + configuracionDTO.getCoordenadasAgencia());

        // Buscamos el vehículo a la que le corresponde la posición recibida en la base de datos:
        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId()).
                orElseThrow(() -> new RuntimeException("Vehículo no encontrado."));

        // Verificar si el vehiculo esta en alguna prueba en curso (si es asi no se puede actualizar la posicion)
        Prueba pruebaEnCurso = vehiculo.obtenerPruebaEnCurso();
        if (pruebaEnCurso == null) {
            throw new RuntimeException("El vehículo no está en ninguna prueba en curso.");
        }

        // Fecha y hora actuales
        LocalDateTime fechaHora = LocalDateTime.now();

        // Creamos una nueva posición para el vehículo
        Posicion nuevaPosicion = new Posicion();
        nuevaPosicion.setVehiculo(vehiculo);
        nuevaPosicion.setLatitud(request.getLatitud());
        nuevaPosicion.setLongitud(request.getLongitud());
        nuevaPosicion.setFechaHora(fechaHora);

        // Guardamos la posición en la base de datos:
        posicionRepository.save(nuevaPosicion);

        // Verificar si la posición está fuera del radio admitido:
        if ( ! nuevaPosicion.estaDentroDelRadio(configuracionDTO.getCoordenadasAgencia(), configuracionDTO.getRadioAdmitidoKm())) {

            // Crear el mensaje base para la notificación
            String mensajeBase = "El vehículo con patente %s, año %d y marca %s está a más de " + configuracionDTO.getRadioAdmitidoKm() + "km de la Empresa. " +
                    "Posición actual: Latitud %.4f, Longitud %.4f.";

            manejarRestriccionPorPosicion(
                    vehiculo, nuevaPosicion,
                    mensajeBase, fechaHora, pruebaEnCurso);

        }

        // Verificar si la posición está dentro de zonas restringidas:
        if (nuevaPosicion.estaDentroDeZonas(configuracionDTO.getZonasRestringidas())) {

            // Crear el mensaje base para la notificación
            String mensajeBase = "El vehículo con patente %s, año %d y marca %s está dentro de una zona peligrosa. " +
                    "Posición actual: Latitud %.4f, Longitud %.4f.";

            manejarRestriccionPorPosicion(
                    vehiculo, nuevaPosicion,
                    mensajeBase, fechaHora, pruebaEnCurso);
        }
    }

    // Manejar restricciones de pruebas
    private void manejarRestriccionPorPosicion(Vehiculo vehiculo, Posicion posicion,
                                    String mensajeBase, LocalDateTime fechaHora, Prueba pruebaEnCurso) {

        // Obtengo el empleado y al interado que esta realizando la prueba
        Empleado empleado = pruebaEnCurso.getEmpleado();
        Interesado interesado = pruebaEnCurso.getInteresado();

        // Restringir al interesado y marcar prueba como excedida
        interesado.setRestringido(true);
        pruebaEnCurso.setExcedioLimite(true);

        // Actualizar en la base de datos
        interesadoRepository.save(interesado);
        pruebaRepository.save(pruebaEnCurso);

        // Crear el mensaje con los detalles del vehículo y posición para la notificación
        String mensaje = String.format(
                mensajeBase,
                vehiculo.getPatente(),
                vehiculo.getAnio(),
                vehiculo.getModelo().getMarca().getNombre(),
                posicion.getLatitud(),
                posicion.getLongitud()
        );

        // Crear la notificación que se enviará al micro-servicio de notificaciones
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setMensaje(mensaje);
        notificacion.setTipo("ALERTA");
        notificacion.setTelefono(empleado.getTelefonoContacto());
        notificacion.setNombreDestinatario(empleado.getNombre() + " " + empleado.getApellido());
        notificacion.setFechaEnvio(fechaHora);

        // Enviar notificación al micro-servicio de notificaciones
        NotificacionService.enviarNotificacion(notificacion);
    }
}
