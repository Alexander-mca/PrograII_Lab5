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

import com.lab5.models.Curso;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    private List<Curso> cursos = new ArrayList<>(
        List.of(new Curso(1L,"Introduccion a la Programacion con Python", 
        "Conceptos básicos de lógica de programación, variables, estructuras de control y funciones utilizando Python.","Virtual",4),
        new Curso(2L, "Cálculo Diferencial e Integral", 
        "Estudio de límites, derivadas, integrales y sus aplicaciones en la resolución de problemas de ingeniería.","Presencial",4),
        new Curso(3L, "Bases de Datos 1", 
            "Diseño de modelos entidad-relación, normalización y lenguaje SQL para la gestión de datos.",
            "Hibrida", 3))
    );

    // GET Obtener todos los cursos
        @GetMapping
        public ResponseEntity<?> obtenerCursos(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "cursos obtenidos correctamente",
                    "total", cursos.size(),
                    "datos", cursos
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerCurso(@PathVariable Long id){
            for (Curso curso : cursos){
                if(curso.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Curso encontrado",
                            "datos", curso
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Curso no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un Curso
        @PostMapping
        public ResponseEntity<?> crearCurso(@RequestBody Curso[] cursosbody){
            if(cursosbody.length != 0){
                for (Curso curso : cursosbody) {
                    //Validacion simple
                    if(curso.getNombre()==null || curso.getCreditos()==null || curso.getCreditos()<=0 || curso.getNombre().isBlank()
                        ||curso.getDescripcion()==null || curso.getModalidad()==null){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //validar curso duplicado
                    for (Curso item : cursos) {
                        if (item.getNombre().equalsIgnoreCase(curso.getNombre())) {
                            ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "Ya existe un curso con ese nombre",
                                    "codigo", 400
                                )
                            );
                            break;
                        }
                    }

                    //Se asigna el curso
                    curso.setId((long)cursos.size() + 1);
                    cursos.add(curso);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "Curso creado correctamente",
                    "codigo", 201,
                    "datos", cursosbody
                )
            );
            


        }
        //Put - actualizar cursos
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarCurso(@PathVariable Long id, @RequestBody Curso cursoActualizado){
            for(Curso curso: cursos){
                if(curso.getId().equals(id)){
                        if(cursoActualizado.getNombre() ==null
                        || cursoActualizado.getNombre().isBlank()  
                        ||cursoActualizado.getCreditos()==null
                        ||cursoActualizado.getCreditos() <= 0
                        ||cursoActualizado.getDescripcion()==null
                        ||cursoActualizado.getModalidad()==null){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    curso.setNombre(cursoActualizado.getNombre());
                    curso.setDescripcion(cursoActualizado.getDescripcion());
                    curso.setModalidad(cursoActualizado.getModalidad());
                    curso.setCreditos(cursoActualizado.getCreditos());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Curso actualizado correctamente",
                            "codigo", 200,
                            "datos", curso
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Curso no encontrado",
                    "codigo", 404
                )
            );               
        }

        //Patch - actualiza parcialmente un curso
        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Curso cursoActualizado){
            for (Curso curso : cursos) {
                if(curso.getId().equals(id)){
                    if(cursoActualizado.getNombre() != null){
                        if(cursoActualizado.getNombre().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El nombre no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        curso.setNombre(cursoActualizado.getNombre());
                    }

                    if(cursoActualizado.getDescripcion() != null){
                        if(cursoActualizado.getDescripcion().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "La descripcion no puede estar vacia",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        curso.setDescripcion(cursoActualizado.getDescripcion());
                    }

                    if(cursoActualizado.getCreditos()!=null){
                        if(cursoActualizado.getCreditos()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "Los creditos deben ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        curso.setCreditos(cursoActualizado.getCreditos());
                    }
                    
                    if(cursoActualizado.getModalidad() != null){
                        curso.setModalidad(cursoActualizado.getModalidad());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Curso actualizado parcialmente",
                            "codigo", 200,
                            "datos" , curso
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Curso no encontrado",
                    "codigo", 400
                )
            );
        }

        //elimina un curso en especifico
        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
            for (Curso curso : cursos) {
                if(curso.getId().equals(id)){
                    cursos.remove(curso);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "Curso eliminado",
                            "datos",curso
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Curso no encontrado",
                    "codigo", 404
                )
            );
        }

}
