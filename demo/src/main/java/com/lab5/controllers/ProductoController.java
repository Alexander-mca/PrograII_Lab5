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

import com.lab5.models.Producto;





@RestController
@RequestMapping("/api/productos")
public class ProductoController {
        private List<Producto> productos = new ArrayList<>(
            List.of(
            new Producto(1L, "Producto 1", "Categoria 1", 10.0),
            new Producto(2L, "Producto 2", "Categoria 2", 20.0),
            new Producto(3L, "Producto 3", "Categoria 3", 30.0)
        ));

        // GET Obtener todos los productos
        @GetMapping
        public ResponseEntity<?> obtenerProductos(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Productos obtenidos correctamente",
                    "total", productos.size(),
                    "datos", productos
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerProducto(@PathVariable Long id){
            for (Producto producto : productos){
                if(producto.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Producto encontrado",
                            "datos", producto
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Producto no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un producto
        @PostMapping
        public ResponseEntity<?> crearProducto(@RequestBody Producto[] productosbody){
            if(productosbody.length != 0){
                for (Producto producto : productosbody) {
                    //Validacion simple
                    if(producto.getNombre()==null || producto.getPrecio()<=0 || producto.getNombre().isBlank()){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //validar producto duplicado
                    for (Producto item : productos) {
                        if (item.getNombre().equalsIgnoreCase(producto.getNombre())) {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "Ya existe un producto con ese nombre",
                                    "codigo", 409
                                )
                            );
                        }
                    }

                    //Se asigna el producto
                    producto.setId((long)productos.size() + 1);
                    productos.add(producto);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "Producto creado correctamente",
                    "codigo", 201,
                    "datos", productosbody
                )
            );
            


        }

        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado){
            for(Producto producto: productos){
                if(producto.getId().equals(id)){
                        if(productoActualizado.getNombre() ==null
                        || productoActualizado.getNombre().isBlank() || 
                        productoActualizado.getPrecio() <= 0){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    producto.setNombre(productoActualizado.getNombre());
                    producto.setCategoria(productoActualizado.getCategoria());
                    producto.setPrecio(productoActualizado.getPrecio());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Producto actualizado correctamente",
                            "codigo", 200,
                            "datos", producto
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Producto no encontrado",
                    "codigo", 404
                )
            );               
        }

        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Producto productoActualizado){
            for (Producto producto : productos) {
                if(producto.getId().equals(id)){
                    if(productoActualizado.getNombre() != null){
                        if(productoActualizado.getNombre().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El nombre no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        producto.setNombre(productoActualizado.getNombre());
                    }
                    
                    if(productoActualizado.getPrecio()!=null){
                        if(productoActualizado.getPrecio()<=0){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El precio debe ser mayor a cero.",
                                    "codigo", 400
                                )
                            );
                        }

                        producto.setPrecio(productoActualizado.getPrecio());
                    }

                    if(productoActualizado.getCategoria() != null){
                        producto.setCategoria(productoActualizado.getCategoria());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Producto actualizado parcialmente",
                            "codigo", 200,
                            "datos" , producto
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Producto no encontrado",
                    "codigo", 400
                )
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
            for (Producto producto : productos) {
                if(producto.getId().equals(id)){
                    productos.remove(producto);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "Producto eliminado",
                            "datos",producto
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Producto no encontrado",
                    "codigo", 404
                )
            );
        }
        

        

        
}
