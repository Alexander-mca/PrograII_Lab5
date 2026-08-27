package com.lab5.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab5.models.Empleado;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private List<Empleado> empleados = new ArrayList<>(
        List.of(
            new Empleado(1L, "Jose Calderon", "Supervisor", "Ventas",8000.0),
            new Empleado(2L,"Dinora Ochoa", "Agente back office", "Reportes",6000.0),
            new Empleado(3L, "Briana Solis", "Agente de Ventas","Ventas", 6000.0)
        )
    );
    //GET - todos los empleados
     @GetMapping
        public ResponseEntity<?> obtenerEmpleados(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Empleados obtenidos correctamente",
                    "total", empleados.size(),
                    "datos", empleados
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerEmpleado(@PathVariable Long id){
            for (Empleado empleado : empleados){
                if(empleado.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Empleado encontrado",
                            "datos", empleado
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Empleado no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un empleado
        @PostMapping
        public ResponseEntity<?> crearEmpleado(@RequestBody Empleado[] empleadosbody){
            if(empleadosbody.length != 0){
                for (Empleado empleado : empleadosbody) {
                    //Validacion simple
                    if(empleado.getNombre()==null || empleado.getSalario()<=0 
                    || empleado.getPuesto()==null 
                    || empleado.getSalario() == null
                    ||empleado.getDepartamento()==null|| empleado.getNombre().isBlank()){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //si pueden haber empleados con el mismo nombre, apellido y carrera

                    //Se asigna el empleado
                    empleado.setId((long)empleados.size() + 1);
                    empleados.add(empleado);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "Empleado creado correctamente",
                    "codigo", 201,
                    "datos", empleadosbody
                )
            );
            


        }

        //PUT para actualizar un empleado
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado){
            for(Empleado empleado: empleados){
                if(empleado.getId().equals(id)){
                        if(empleadoActualizado.getNombre() ==null
                        || empleadoActualizado.getNombre().isBlank() || 
                        empleadoActualizado.getSalario() <= 0    
                        || empleadoActualizado.getSalario() == null
                        || empleadoActualizado.getPuesto()==null
                        || empleadoActualizado.getDepartamento()==null){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    empleado.setNombre(empleadoActualizado.getNombre());
                    empleado.setPuesto(empleadoActualizado.getPuesto());
                    empleado.setDepartamento(empleadoActualizado.getDepartamento());
                    empleado.setSalario(empleadoActualizado.getSalario());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Empleado actualizado correctamente",
                            "codigo", 200,
                            "datos", empleado
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Empleado no encontrado",
                    "codigo", 404
                )
            );               
        }

        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Empleado empleadoActualizado){
            for (Empleado empleado : empleados) {
                if(empleado.getId().equals(id)){
                    if(empleadoActualizado.getNombre() != null){
                        if(empleadoActualizado.getNombre().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El nombre no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        empleado.setNombre(empleadoActualizado.getNombre());
                    }
                    
                    if(empleadoActualizado.getPuesto() != null){
                        if(empleadoActualizado.getPuesto().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El puesto no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        empleado.setPuesto(empleadoActualizado.getPuesto());
                    }

                    if(empleadoActualizado.getSalario() != null){
                        if(empleadoActualizado.getSalario()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El salario debe ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        empleado.setSalario(empleadoActualizado.getSalario());
                    }

                    if(empleadoActualizado.getDepartamento() != null){
                        empleado.setDepartamento(empleadoActualizado.getDepartamento());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Empleado actualizado parcialmente",
                            "codigo", 200,
                            "datos" , empleado
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Empleado no encontrado",
                    "codigo", 400
                )
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id){
            for (Empleado empleado : empleados) {
                if(empleado.getId().equals(id)){
                    empleados.remove(empleado);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "Empleado eliminado",
                            "datos" , empleado
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Empleado no encontrado",
                    "codigo", 404
                )
            );
        }
}
