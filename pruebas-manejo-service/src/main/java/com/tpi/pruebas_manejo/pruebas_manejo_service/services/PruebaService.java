package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.CrearPruebaRequestDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.FinalizarPruebaRequestDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Empleado;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Interesado;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Vehiculo;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.EmpleadoRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.InteresadoRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PruebaRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PruebaService {
    @Autowired
    private InteresadoRepository interesadoRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private PruebaRepository pruebaRepository;

    // crearPrueba
    // Crear una nueva prueba con los datos recibidos (DTO).
    // Asignar el Vehículo y el Interesado a la prueba.
    // Realizar las validaciones.
    // Guardar la prueba en la base de datos.

    public void crearPrueba(CrearPruebaRequestDTO request) {
        // Buscamos el interesado:
        Interesado interesado = interesadoRepository.findById(request.getInteresadoId()).
                orElseThrow(() -> new RuntimeException("Interesado no encontrado."));

        // Validamos que el interesado no tenga la licencia vencida:
        if (interesado.tenesLicenciaVencida()) {
            throw new RuntimeException("La licencia del interesado está vencida.");
        };

        // Validamos que el interesado no esté restringido para realizar pruebas:
        if (interesado.estasRestringido()) {
            throw new RuntimeException("El interesado está restringido para realizar pruebas.");
        };

        // Buscamos el vehículo:
        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId()).
                orElseThrow(() -> new RuntimeException("Vehículo no encontrado."));

        // Validamos que el vehículo no esté siendo probado en ese mismo momento:
        if (vehiculo.estasSiendoProbado()) {
            throw new RuntimeException("El vehículo está siendo probado en este momento.");
        };

        // Buscamos el empleado:
        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId()).
                orElseThrow(() -> new RuntimeException("Empleado no encontrado."));

        // Fecha de inicio (fecha y hora actuales)
        LocalDateTime fechaHoraInicio = LocalDateTime.now();

        // Creamos la prueba:
        Prueba prueba = new Prueba();
        prueba.setInteresado(interesado);
        prueba.setVehiculo(vehiculo);
        prueba.setEmpleado(empleado);
        prueba.setFechaHoraInicio(fechaHoraInicio);  // Asignamos la fecha y hora actual

        // Guardamos la prueba:
        pruebaRepository.save(prueba);
    }

    // getPruebasEnCurso
    // Devuelve una lista con las pruebas que están en curso.
    public List<Prueba> getPruebasEnCurso() {
        return pruebaRepository.findByFechaHoraFinIsNull();
    }

    // finalizarPrueba
    public void finalizarPrueba(FinalizarPruebaRequestDTO request) {
        // Buscamos la prueba:
        Prueba prueba = pruebaRepository.findById(request.getPruebaId()).
                orElseThrow(() -> new RuntimeException("Prueba no encontrada."));

        // Validamos que la prueba no haya finalizado:
        if (prueba.estasFinalizada()) {
            throw new RuntimeException("La prueba ya ha finalizado.");
        }

        // Fecha de fin (fecha y hora actuales)
        LocalDateTime fechaHoraFin = LocalDateTime.now();

        // Finalizamos la prueba:
        prueba.setFechaHoraFin(fechaHoraFin);
        prueba.setComentarios(request.getComentarios());

        // Actualizamos la prueba
        pruebaRepository.save(prueba);

    }
}
