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

import com.lab5.models.Pelicula;


@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private List<Pelicula> peliculas = new ArrayList<>(
        List.of(
            new Pelicula(1L,"El Padrino", "Francis Ford Cappola","Drama/Crimen",1972),
            new Pelicula(2L, "Psicosis", "Alfres Hitchcock", "Terror/Suspenso", 1960),
            new Pelicula(3L, "2001: Odisea del espacio","Stanley Kubrick", "Ciencia Ficcion",1968)
        )
    );

    @GetMapping
    public ResponseEntity<?> obtenerPeliculas(){
        return ResponseEntity.ok(
            Map.of(
                "mensaje", "Peliculas obtenidas correctamente",
                "total",peliculas.size(),
                "datos",peliculas
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPelicula(@PathVariable Long id){
        for (Pelicula pelicula : peliculas) {
            if(pelicula.getId().equals(id)){
                return ResponseEntity.ok(
                    Map.of(
                        "mensaje","pelicula encontrada",
                        "datos",pelicula
                    )
                );
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Map.of(
                "mensaje","Pelicula no encontrada",
                "codigo",404
            )
        );
    }

    @PostMapping
    public ResponseEntity<?> crearPelicula(@RequestBody Pelicula[] peliculasbody){
        if(peliculasbody.length>0){
            for (Pelicula pelicula : peliculasbody) {
                //valida que todos los campos no vengan vacios
                if(pelicula.getTitulo()==null || pelicula.getDirector()==null
                    ||pelicula.getGenero()==null  || pelicula.getAnyo()==null
                    ||pelicula.getAnyo()<=0){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje","Datos invalidos, ingrese todos los datos",
                                "codigo",400
                            )
                        );
                }
                //valida que una pelicula no exista ya
                for (Pelicula existente : peliculas) {
                    if(existente.getTitulo().equals(pelicula.getTitulo())){
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje","La pelicula "+pelicula.getTitulo()+" ya existe",
                                "codigo" , 400
                            )
                        );
                        break;
                    }
                }

                //se agrega la pelicula a la lista
                pelicula.setId((long)peliculas.size()+1);
                peliculas.add(pelicula);

            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
            Map.of(
                "mensaje","Pelicula creada con exito",
                "codigo",201,
                "datos",peliculasbody
            )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPelicula(@PathVariable Long id, @RequestBody Pelicula peliculaActualizada){
        for(Pelicula pelicula : peliculas){
            if(pelicula.getId().equals(id)){
                if(peliculaActualizada.getTitulo()==null
                ||peliculaActualizada.getGenero()==null
                ||peliculaActualizada.getDirector()==null
                ||peliculaActualizada.getAnyo()==null || peliculaActualizada.getAnyo()<=0){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of(
                            "mensaje", "Datos invalidos",
                            "codigo",400
                        )
                    );
                }

                pelicula.setTitulo(peliculaActualizada.getTitulo());
                pelicula.setDirector(peliculaActualizada.getDirector());
                pelicula.setGenero(peliculaActualizada.getGenero());
                pelicula.setAnyo(peliculaActualizada.getAnyo());

                return ResponseEntity.ok(
                    Map.of(
                        "mensaje", "pelicula actualizada correctamente",
                        "codigo" , 200,
                        "datos", pelicula
                    )
                );
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Map.of( 
                "mensaje","pelicula no encontrada",
                "codigo",404
            )
        );
    }

     @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Pelicula peliculaActualizada){
            for (Pelicula pelicula : peliculas) {
                if(pelicula.getId().equals(id)){
                    if(peliculaActualizada.getTitulo() != null){
                        if(peliculaActualizada.getTitulo().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El titulo no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        pelicula.setTitulo(peliculaActualizada.getTitulo());
                    }
                    
                    if(peliculaActualizada.getDirector() != null){
                        if(peliculaActualizada.getDirector().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El director no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        pelicula.setDirector(peliculaActualizada.getDirector());
                    }

                    if(peliculaActualizada.getAnyo() != null){
                        if(peliculaActualizada.getAnyo()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El año debe ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        pelicula.setAnyo(peliculaActualizada.getAnyo());
                    }

                    if(peliculaActualizada.getGenero() != null){
                        pelicula.setGenero(peliculaActualizada.getGenero());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Pelicula actualizada parcialmente",
                            "codigo", 200,
                            "datos" , pelicula
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Pelicula no encontrada",
                    "codigo", 400
                )
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarPelicula(@PathVariable Long id){
            for (Pelicula pelicula : peliculas) {
                if(pelicula.getId().equals(id)){
                    peliculas.remove(pelicula);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "Pelicula eliminada",
                            "datos" , pelicula
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Pelicula no encontrada",
                    "codigo", 404
                )
            );
        }
    

}
