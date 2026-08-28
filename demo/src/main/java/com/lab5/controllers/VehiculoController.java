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

import com.lab5.models.Vehiculo;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private List<Vehiculo> vehiculos = new ArrayList<>(
        List.of(
            new Vehiculo(1L,"Toyota","Corolla", 22000.0,2024),
            new Vehiculo(2L, "Honda","Civic",25000.0,2023), 
            new Vehiculo(3L,"Ford","Mustang", 31000.0, 2024)
        )
    );

    // GET Obtener todos los vehiculos
        @GetMapping
        public ResponseEntity<?> obtenerVehiculos(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Vehiculos obtenidos correctamente",
                    "total", vehiculos.size(),
                    "datos", vehiculos
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerVehiculo(@PathVariable Long id){
            for (Vehiculo vehiculo : vehiculos){
                if(vehiculo.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Vehiculo encontrado",
                            "datos", vehiculo
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Vehiculo no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un vehiculo
        @PostMapping
        public ResponseEntity<?> crearVehiculo(@RequestBody Vehiculo[] vehiculosbody){
            if(vehiculosbody.length != 0){
                for (Vehiculo vehiculo : vehiculosbody) {
                    //Validacion simple
                    if(vehiculo.getMarca()==null || vehiculo.getPrecio()==null || vehiculo.getPrecio()<=0 
                    || vehiculo.getMarca().isBlank()
                    || vehiculo.getAnyo() == null
                        ||vehiculo.getAnyo()<=0 ||vehiculo.getModelo()==null){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //validar vehiculo duplicado
                    for (Vehiculo item : vehiculos) {
                        if (item.getMarca().equalsIgnoreCase(vehiculo.getMarca())
                            && item.getModelo().equals(vehiculo.getModelo())) {
                            ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "Ya existe un vehiculo con esa marca y modelo",
                                    "codigo", 400
                                )
                            );
                            break;
                        }
                    }

                    //Se asigna el vehiculo
                    vehiculo.setId((long)vehiculos.size() + 1);
                    vehiculos.add(vehiculo);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "Vehiculo creado correctamente",
                    "codigo", 201,
                    "datos", vehiculosbody
                )
            );
            


        }
        //Put - actualizar vehiculos
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoActualizado){
            for(Vehiculo vehiculo: vehiculos){
                if(vehiculo.getId().equals(id)){
                        if(vehiculoActualizado.getMarca() ==null
                        || vehiculoActualizado.getMarca().isBlank()  
                        ||vehiculoActualizado.getPrecio()==null
                        ||vehiculoActualizado.getPrecio() <= 0
                        ||vehiculoActualizado.getModelo()==null
                        ||vehiculoActualizado.getAnyo()==null
                        ||vehiculoActualizado.getAnyo()<=0){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    vehiculo.setMarca(vehiculoActualizado.getMarca());
                    vehiculo.setModelo(vehiculoActualizado.getModelo());
                    vehiculo.setAnyo(vehiculoActualizado.getAnyo());
                    vehiculo.setPrecio(vehiculoActualizado.getPrecio());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Vehiculo actualizado correctamente",
                            "codigo", 200,
                            "datos", vehiculo
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "vehiculo no encontrado",
                    "codigo", 404
                )
            );               
        }

        //Patch - actualiza parcialmente un vehiculo
        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Vehiculo vehiculoActualizado){
            for (Vehiculo vehiculo : vehiculos) {
                if(vehiculo.getId().equals(id)){
                    if(vehiculoActualizado.getMarca() != null){
                        if(vehiculoActualizado.getMarca().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "La marca no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        vehiculo.setMarca(vehiculoActualizado.getMarca());
                    }

                    if(vehiculoActualizado.getModelo() != null){
                        if(vehiculoActualizado.getModelo().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "La modelo no puede estar vacia",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        vehiculo.setModelo(vehiculoActualizado.getModelo());
                    }

                    if(vehiculoActualizado.getPrecio()!=null){
                        if(vehiculoActualizado.getPrecio()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El precio deben ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        vehiculo.setPrecio(vehiculoActualizado.getPrecio());
                    }

                    if(vehiculoActualizado.getAnyo()!=null){
                        if(vehiculoActualizado.getAnyo()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El año deben ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        vehiculo.setAnyo(vehiculoActualizado.getAnyo());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Vehiculo actualizado parcialmente",
                            "codigo", 200,
                            "datos" , vehiculo
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Vehiculo no encontrado",
                    "codigo", 400
                )
            );
        }

        //elimina un vehiculo en especifico
        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarVehiculo(@PathVariable Long id){
            for (Vehiculo vehiculo : vehiculos) {
                if(vehiculo.getId().equals(id)){
                    vehiculos.remove(vehiculo);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "vehiculo eliminado",
                            "datos",vehiculo
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "vehiculo no encontrado",
                    "codigo", 404
                )
            );
        }

}
