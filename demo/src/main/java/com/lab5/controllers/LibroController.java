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

import com.lab5.models.Libro;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private List<Libro> libros = new ArrayList<>(
        List.of(
            new Libro(1L,"Los demas seguimos aqui","Patrick Ness", "Ficcion",150.0),
            new Libro(2L,"Persona Normal","Benito Taibo", "Narrativa", 120.0)
        )
    );

    // GET Obtener todos los libros
        @GetMapping
        public ResponseEntity<?> obtenerLibros(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Libros obtenidos correctamente",
                    "total", libros.size(),
                    "datos", libros
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerLibro(@PathVariable Long id){
            for (Libro libro : libros){
                if(libro.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Libro encontrado",
                            "datos", libro
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "libro no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un libro
        @PostMapping
        public ResponseEntity<?> crearLibro(@RequestBody Libro[] librosbody){
            if(librosbody.length != 0){
                for (Libro libro : librosbody) {
                    //Validacion simple
                    if(libro.getTitulo()==null || libro.getPrecio()==null || libro.getPrecio()<=0 || libro.getTitulo().isBlank()
                        ||libro.getAutor()==null || libro.getGenero()==null){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //validar libro duplicado
                    for (Libro item : libros) {
                        if (item.getTitulo().equalsIgnoreCase(libro.getTitulo())) {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "Ya existe un libro con ese nombre",
                                    "codigo", 409
                                )
                            );
                        }
                    }

                    //Se asigna el libro
                    libro.setId((long)libros.size() + 1);
                    libros.add(libro);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "libro creado correctamente",
                    "codigo", 201,
                    "datos", librosbody
                )
            );
            


        }

        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarlibro(@PathVariable Long id, @RequestBody Libro libroActualizado){
            for(Libro libro: libros){
                if(libro.getId().equals(id)){
                        if(libroActualizado.getTitulo() ==null
                        || libroActualizado.getTitulo().isBlank() || 
                        libroActualizado.getPrecio() <= 0
                        ||libroActualizado.getPrecio()==null
                        ||libroActualizado.getGenero()==null
                        ||libroActualizado.getAutor()==null){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    libro.setTitulo(libroActualizado.getTitulo());
                    libro.setAutor(libroActualizado.getAutor());
                    libro.setGenero(libroActualizado.getGenero());
                    libro.setPrecio(libroActualizado.getPrecio());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "libro actualizado correctamente",
                            "codigo", 200,
                            "datos", libro
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "libro no encontrado",
                    "codigo", 404
                )
            );               
        }

        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Libro libroActualizado){
            for (Libro libro : libros) {
                if(libro.getId().equals(id)){
                    if(libroActualizado.getTitulo() != null){
                        if(libroActualizado.getTitulo().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El titulo no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        libro.setTitulo(libroActualizado.getTitulo());
                    }

                    if(libroActualizado.getAutor() != null){
                        if(libroActualizado.getAutor().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El autor no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        libro.setAutor(libroActualizado.getAutor());
                    }

                    if(libroActualizado.getPrecio()!=null){
                        if(libroActualizado.getPrecio()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El precio debe ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        libro.setPrecio(libroActualizado.getPrecio());
                    }
                    
                    if(libroActualizado.getGenero() != null){
                        libro.setGenero(libroActualizado.getGenero());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "libro actualizado parcialmente",
                            "codigo", 200,
                            "datos" , libro
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "libro no encontrado",
                    "codigo", 400
                )
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarlibro(@PathVariable Long id){
            for (Libro libro : libros) {
                if(libro.getId().equals(id)){
                    libros.remove(libro);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "libro eliminado",
                            "datos",libro
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "libro no encontrado",
                    "codigo", 404
                )
            );
        }
}
