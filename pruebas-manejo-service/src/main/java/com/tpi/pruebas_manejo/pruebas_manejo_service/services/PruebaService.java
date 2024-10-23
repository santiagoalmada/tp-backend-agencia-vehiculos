package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.InteresadoDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.PruebaDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.InteresadoRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PruebaRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.VehiculoRepository;

public class PruebaService {
    private PruebaRepository pruebaRepository;
    private final InteresadoRepository interesadoRepository;
    private final VehiculoRepository vehiculoRepository;

    public PruebaService(PruebaRepository pruebaRepository , InteresadoRepository interesadoRepository, VehiculoRepository vehiculoRepository) {
        this.pruebaRepository = pruebaRepository;
        this.interesadoRepository = interesadoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    // Crear una prueba
    public PruebaDTO create(PruebaDTO pruebaDTO) throws ServiceException {
        InteresadoDTO interesado = new InteresadoDTO(interesadoRepository.findById(pruebaDTO.getInteresado().getId())
                .orElseThrow(() -> new ServiceException("Interesado no encontrado")));

        if (interesado.isRestringido()) {
            throw new ServiceException("El cliente está restringido para realizar pruebas de manejo.");
        }

        // Continuar ...

        // Convertir DTO a entidad y guardar la nueva prueba
        Prueba prueba = pruebaDTO.toEntity();
        prueba.setInteresado(interesado.toEntity()); // Verificar si es correcto ASI

        return new PruebaDTO(pruebaRepository.save(prueba));
    }
}
