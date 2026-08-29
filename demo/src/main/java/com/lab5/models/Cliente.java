package com.lab5.models;

public class Cliente {
    private Long id;
    private String nombre, apellido, correo, telefono;
    
    public Cliente(Long id, String nombre, String apellido, String correo, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return this.id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public String getApellido(){
        return this.apellido;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }
    public String getCorreo(){
        return this.correo;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    public String getTelefono(){
        return this.telefono;
    }
}
