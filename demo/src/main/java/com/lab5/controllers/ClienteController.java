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

import com.lab5.models.Cliente;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private List<Cliente> clientes = new ArrayList<>(
        List.of(
            new Cliente(1L, "Gabriel","Franco","gabriel.franco@gmail.com","4545-7878"),
            new Cliente(2L,"Romina", "Lopez","romina.lopez@gmail.com", "7841-5285"),
            new Cliente(3L,"Brenda","Urizar","brenda.urizar@gmail.com","5240-8523")
        )
    );

    // GET Obtener todos los clientes
        @GetMapping
        public ResponseEntity<?> obtenerClientes(){
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Clientes obtenidos correctamente",
                    "total", clientes.size(),
                    "datos", clientes
                )
            );
        }

        //Get - Obtener por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerCliente(@PathVariable Long id){
            for (Cliente cliente : clientes){
                if(cliente.getId().equals(id)){
                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Cliente encontrado",
                            "datos", cliente
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Cliente no encontrado",
                    "codigo", 404
                )
            );
        }

        //Post - Crear un cliente
        @PostMapping
        public ResponseEntity<?> crearCliente(@RequestBody Cliente[] clientesbody){
            if(clientesbody.length != 0){
                for (Cliente cliente : clientesbody) {
                    //Validacion simple
                    if(cliente.getNombre()==null ||cliente.getTelefono()==null
                    || cliente.getApellido()==null ||cliente.getCorreo()==null|| cliente.getNombre().isBlank()){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Map.of(
                                "mensaje", "Datos invalidos",
                                "codigo", 400
                            )
                        );
                    }

                    //validar cliente duplicado por medio de telefono y correo
                    for (Cliente item : clientes) {
                        if (item.getCorreo().equals(cliente.getCorreo())) {
                            ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "Ya existe un cliente con ese correo",
                                    "codigo", 409
                                )
                            );
                            break;
                        }else if(item.getTelefono().equals(cliente.getTelefono())){
                            ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of(
                                    "mensaje", "Ya existe un cliente con ese telefono",
                                    "codigo", 409
                                )
                            );
                            break;
                        }
                    }

                    //Se asigna el cliente
                    cliente.setId((long)clientes.size() + 1);
                    clientes.add(cliente);
                }
            }
            

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "mensaje", "Cliente creado correctamente",
                    "codigo", 201,
                    "datos", clientesbody
                )
            );
            


        }

        //PUT para actualizar un cliente
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado){
            for(Cliente cliente: clientes){
                if(cliente.getId().equals(id)){
                        if(clienteActualizado.getNombre() ==null
                        || clienteActualizado.getNombre().isBlank() || 
                        clienteActualizado.getCorreo()==null  
                        || clienteActualizado.getTelefono()==null
                        || clienteActualizado.getApellido()==null){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                Map.of(
                                    "mensaje", "Datos invalidos",
                                    "codigo", 400
                                )
                            );
                        }   
                    

                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setApellido(clienteActualizado.getApellido());
                    cliente.setCorreo(clienteActualizado.getCorreo());
                    cliente.setTelefono(clienteActualizado.getTelefono());

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Cliente actualizado correctamente",
                            "codigo", 200,
                            "datos", cliente
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Cliente no encontrado",
                    "codigo", 404
                )
            );               
        }

        @PatchMapping("/{id}")
        public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Cliente clienteActualizado){
            for (Cliente cliente : clientes) {
                if(cliente.getId().equals(id)){
                    if(clienteActualizado.getNombre() != null){
                        if(clienteActualizado.getNombre().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El nombre no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        cliente.setNombre(clienteActualizado.getNombre());
                    }
                    
                    if(clienteActualizado.getApellido() != null){
                        if(clienteActualizado.getApellido().isBlank()){
                            return ResponseEntity.badRequest().body(
                                Map.of(
                                    "mensaje", "El apellido no puede estar vacio",
                                    "codigo" , 400
                                )
                            );
                            
                        }
                        cliente.setApellido(clienteActualizado.getApellido());
                    }

                    if(clienteActualizado.getTelefono() != null){
                        cliente.setTelefono(clienteActualizado.getTelefono());
                    }

                    if(clienteActualizado.getCorreo() != null){
                        cliente.setCorreo(clienteActualizado.getCorreo());
                    }

                    return ResponseEntity.ok(
                        Map.of(
                            "mensaje", "Cliente actualizado parcialmente",
                            "codigo", 200,
                            "datos" , cliente
                        )
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Cliente no encontrado",
                    "codigo", 400
                )
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarCliente(@PathVariable Long id){
            for (Cliente cliente : clientes) {
                if(cliente.getId().equals(id)){
                    clientes.remove(cliente);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        Map.of(
                            "mensaje", "Cliente eliminado",
                            "datos" , cliente
                        )
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "Cliente no encontrado",
                    "codigo", 404
                )
            );
        }

}
