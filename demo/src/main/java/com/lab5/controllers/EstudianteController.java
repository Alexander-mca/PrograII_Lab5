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

import com.lab5.models.Estudiante;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    private List<Estudiante> estudiantes = new ArrayList<>(
        List.of(
            new Estudiante(1L,"Cecilia","Lorenzana","Ingenieria en Sistemas",25),
            new Estudiante(2L, "Rogelio","Diaz","Derecho",40),
            new Estudiante(3L, "Tanya", "Alonzo", "Arquitectura", 20)
        )
    );
    // GET Obtener todos los estudiantes
        @GetMapping
        public ResponseEntity<?> obtenerEstudiantes(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "estudiantes obtenidos correctamente",
                    "total", estudiantes.size(),
                    "datos", estudiantes
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerEstudiante(@PathVariable Long id){
            for (Estudiante estudiante : estudiantes){
                if(estudiante.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Estudiante encontrado",
                            "datos", estudiante
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Estudiante no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un Estudiante
        @PostMapping
        public ResponseEntity<?> crearEstudiante(@RequestBody Estudiante[] estudiantesbody){
            if(estudiantesbody.length != 0){
                for (Estudiante estudiante : estudiantesbody) {
                    //Validacion simple
                    if(estudiante.getNombre()==null || estudiante.getEdad()<=0 || estudiante.getApellido()==null ||estudiante.getCarrera()==null|| estudiante.getNombre().isBlank()){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //si pueden haber estudiantes con el mismo nombre, apellido y carrera

                    //Se asigna el Estudiante
                    estudiante.setId((long)estudiantes.size() + 1);
                    estudiantes.add(estudiante);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "Estudiante creado correctamente",
                    "codigo", 201,
                    "datos", estudiantesbody
                )
            );
            


        }

        //PUT para actualizar un estudiante
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteActualizado){
            for(Estudiante estudiante: estudiantes){
                if(estudiante.getId().equals(id)){
                        if(estudianteActualizado.getNombre() ==null
                        || estudianteActualizado.getNombre().isBlank() || 
                        estudianteActualizado.getEdad() <= 0    
                        || estudianteActualizado.getCarrera()==null
                        || estudianteActualizado.getApellido()==null){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    estudiante.setNombre(estudianteActualizado.getNombre());
                    estudiante.setApellido(estudianteActualizado.getApellido());
                    estudiante.setCarrera(estudianteActualizado.getCarrera());
                    estudiante.setEdad(estudianteActualizado.getEdad());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Estudiante actualizado correctamente",
                            "codigo", 200,
                            "datos", estudiante
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Estudiante no encontrado",
                    "codigo", 404
                )
            );               
        }

        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Estudiante estudianteActualizado){
            for (Estudiante estudiante : estudiantes) {
                if(estudiante.getId().equals(id)){
                    if(estudianteActualizado.getNombre() != null){
                        if(estudianteActualizado.getNombre().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El nombre no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        estudiante.setNombre(estudianteActualizado.getNombre());
                    }
                    
                    if(estudianteActualizado.getApellido() != null){
                        if(estudianteActualizado.getApellido().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El apellido no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        estudiante.setApellido(estudianteActualizado.getApellido());
                    }

                    if(estudianteActualizado.getEdad() != null){
                        if(estudianteActualizado.getEdad()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "La edad debe ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        estudiante.setEdad(estudianteActualizado.getEdad());
                    }

                    if(estudianteActualizado.getCarrera() != null){
                        estudiante.setCarrera(estudianteActualizado.getCarrera());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Estudiante actualizado parcialmente",
                            "codigo", 200,
                            "datos" , estudiante
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Estudiante no encontrado",
                    "codigo", 400
                )
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarEstudiante(@PathVariable Long id){
            for (Estudiante estudiante : estudiantes) {
                if(estudiante.getId().equals(id)){
                    estudiantes.remove(estudiante);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "Estudiante eliminado",
                            "datos" , estudiante
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Estudiante no encontrado",
                    "codigo", 404
                )
            );
        }

}
