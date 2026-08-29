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

import com.lab5.models.Pedido;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    List<Pedido> pedidos = new ArrayList<>(
        List.of(
            new Pedido(1L, "Carlos Mendoza", "Laptop Asus ZenBook", "Entregado", 1, 1250.00),
            new Pedido(2L,"Ana Maria Gomez", "Monitor LG 27 Pulgadas", "En Camino", 2, 520.50),
            new Pedido(3L, "Luis Fernando Rodriguez", "Teclado Mecanico Corsair","Procesando", 1, 85.99)
        )
    );

    // GET Obtener todos los pedidos
        @GetMapping
        public ResponseEntity<?> obtenerPedidos(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Pedidos obtenidos correctamente",
                    "total", pedidos.size(),
                    "datos", pedidos
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerPedido(@PathVariable Long id){
            for (Pedido pedido : pedidos){
                if(pedido.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Pedido encontrado",
                            "datos", pedido
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Pedido no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un pedido
        @PostMapping
        public ResponseEntity<?> crearPedido(@RequestBody Pedido[] pedidosbody){
            if(pedidosbody.length != 0){
                for (Pedido pedido : pedidosbody) {
                    //Validacion simple
                    if(pedido.getCliente()==null ||pedido.getProducto()==null
                    || pedido.getEstado()==null ||pedido.getTotal()==null|| pedido.getCliente().isBlank()
                    ||pedido.getCantidad()==null || pedido.getTotal()<=0 || pedido.getCantidad()<=0){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //Se asigna el pedido
                    pedido.setId((long)pedidos.size() + 1);
                    pedidos.add(pedido);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "Pedido creado correctamente",
                    "codigo", 201,
                    "datos", pedidosbody
                )
            );
            


        }

        //PUT para actualizar un pedido
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoActualizado){
            for(Pedido pedido: pedidos){
                if(pedido.getId().equals(id)){
                        if(pedidoActualizado.getCliente() ==null
                        || pedidoActualizado.getCliente().isBlank() || 
                        pedidoActualizado.getCantidad()==null  
                        || pedidoActualizado.getProducto()==null
                        || pedidoActualizado.getEstado()==null || pedidoActualizado.getTotal()==null 
                        || pedidoActualizado.getCantidad()<=0 || pedidoActualizado.getTotal()<=0){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    pedido.setCliente(pedidoActualizado.getCliente());
                    pedido.setProducto(pedidoActualizado.getProducto());
                    pedido.setEstado(pedidoActualizado.getEstado());
                    pedido.setCantidad(pedidoActualizado.getCantidad());
                    pedido.setTotal(pedidoActualizado.getTotal());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Pedido actualizado correctamente",
                            "codigo", 200,
                            "datos", pedido
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Pedido no encontrado",
                    "codigo", 404
                )
            );               
        }

        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Pedido pedidoActualizado){
            for (Pedido pedido : pedidos) {
                if(pedido.getId().equals(id)){
                    if(pedidoActualizado.getCliente() != null){
                        if(pedidoActualizado.getCliente().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El cliente no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        pedido.setCliente(pedidoActualizado.getCliente());
                    }
                    
                    if(pedidoActualizado.getProducto() != null){
                        if(pedidoActualizado.getProducto().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El producto no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        pedido.setProducto(pedidoActualizado.getProducto());
                    }

                    if(pedidoActualizado.getEstado() != null){
                        pedido.setEstado(pedidoActualizado.getEstado());
                    }

                    if(pedidoActualizado.getCantidad()!=null){
                        if(pedidoActualizado.getCantidad()<=0){
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "La cantidad no puede ser menor o igual a 0.",
                                    "codigo",409
                                )
                            );
                        }
                        pedido.setCantidad(pedidoActualizado.getCantidad());
                    }

                    if(pedidoActualizado.getTotal()!=null){
                        if(pedidoActualizado.getTotal()<=0){
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "El total no puede ser menor o igual a 0.",
                                    "codigo", 409
                                )
                            );
                        }
                        pedido.setTotal(pedidoActualizado.getTotal());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Pedido actualizado parcialmente",
                            "codigo", 200,
                            "datos" , pedido
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Pedido no encontrado",
                    "codigo", 400
                )
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarPedido(@PathVariable Long id){
            for (Pedido pedido : pedidos) {
                if(pedido.getId().equals(id)){
                    pedidos.remove(pedido);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "Pedido eliminado",
                            "datos" , pedido
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Pedido no encontrado",
                    "codigo", 404
                )
            );
        }
}
