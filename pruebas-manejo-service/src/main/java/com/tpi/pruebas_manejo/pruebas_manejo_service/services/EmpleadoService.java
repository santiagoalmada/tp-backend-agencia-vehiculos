package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Empleado;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> getEmpleadosQueEmpiecenConApellidoD() {
        return empleadoRepository.findByApellidoStartingWith("D");
    }
}