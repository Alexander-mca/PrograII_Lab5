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

import com.lab5.models.Tarea;

@RestController
@RequestMapping("/api/tareas")
public class TareaController{
    private List<Tarea> tareas = new ArrayList<>(
        List.of(
            new Tarea(1L, "Comprar Viveres", "Adquirir frutas, verduras, leche y cafe en el supermercado",
                "Alta",true
            ),
            new Tarea(2L, "Actualizar Sistema Operativo", "Intalar las ultimas actualizaciones de seguridad en la laptop","Media",false),
            new Tarea(3L, "Rutina de ejercicio", "Completar 45 minutos de entrenamiento de fuerza y cardio","Baja", true)
        )
    );

    // GET Obtener todas las tareas
        @GetMapping
        public ResponseEntity<?> obtenerTareas(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Tareas obtenidas correctamente",
                    "total", tareas.size(),
                    "datos", tareas
                )
            );
        }

        //Get - Obtener tarea por Id
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerTarea(@PathVariable Long id){
            for (Tarea tarea : tareas){
                if(tarea.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Tarea encontrada",
                            "datos", tarea
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "tarea no encontrada",
                    "codigo", 404
                )
            );
        }

        //Post - Crear una tarea
        @PostMapping
        public ResponseEntity<?> crearTarea(@RequestBody Tarea[] tareasbody){
            if(tareasbody.length != 0){
                for (Tarea tarea : tareasbody) {
                    //Validacion simple
                    if(tarea.getTitulo()==null || tarea.getDescripcion()==null || tarea.getPrioridad()==null || tarea.getTitulo().isBlank()
                        ||tarea.completada==null ){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //validar tarea duplicada
                    for (Tarea item : tareas) {
                        if (item.getTitulo().equalsIgnoreCase(tarea.getTitulo())) {
                            ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "Ya existe una tarea con ese nombre",
                                    "codigo", 409
                                )
                            );
                            break;
                        }
                    }

                    //Se asigna la tarea
                    tarea.setId((long)tareas.size() + 1);
                    tareas.add(tarea);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "tarea creada correctamente",
                    "codigo", 201,
                    "datos", tareasbody
                )
            );
            


        }
        //Put - actualizar tareas
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tareaActualizada){
            for(Tarea tarea: tareas){
                if(tarea.getId().equals(id)){
                        if(tareaActualizada.getTitulo() ==null
                        || tareaActualizada.getTitulo().isBlank()
                        ||tareaActualizada.getDescripcion()==null
                        ||tareaActualizada.getPrioridad()==null
                        ||tareaActualizada.completada==null){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    tarea.setTitulo(tareaActualizada.getTitulo());
                    tarea.setDescripcion(tareaActualizada.getDescripcion());
                    tarea.setPrioridad(tareaActualizada.getPrioridad());
                    tarea.completada = tareaActualizada.completada;

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "tarea actualizada correctamente",
                            "codigo", 200,
                            "datos", tarea
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "tarea no encontrada",
                    "codigo", 404
                )
            );               
        }

        //Patch - actualiza parcialmente un tarea
        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Tarea tareaActualizada){
            for (Tarea tarea : tareas) {
                if(tarea.getId().equals(id)){
                    if(tareaActualizada.getTitulo() != null){
                        if(tareaActualizada.getTitulo().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El titulo no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        tarea.setTitulo(tareaActualizada.getTitulo());
                    }

                    if(tareaActualizada.getDescripcion() != null){
                        if(tareaActualizada.getDescripcion().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "La descripcion no puede estar vacia",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        tarea.setDescripcion(tareaActualizada.getDescripcion());
                    }

                    
                    
                    if(tareaActualizada.getPrioridad() != null){
                        tarea.setPrioridad(tareaActualizada.getPrioridad());
                    }

                    if(tareaActualizada.completada!=null){
                        tarea.completada = tareaActualizada.completada;
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "tarea actualizada parcialmente",
                            "codigo", 200,
                            "datos" , tarea
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "tarea no encontrada",
                    "codigo", 400
                )
            );
        }

        //elimina un tarea en especifico
        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarTarea(@PathVariable Long id){
            for (Tarea tarea : tareas) {
                if(tarea.getId().equals(id)){
                    tareas.remove(tarea);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "tarea eliminada",
                            "datos",tarea
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "tarea no encontrada",
                    "codigo", 404
                )
            );
        }
}