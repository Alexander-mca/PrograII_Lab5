package com.lab5.models;


public class Estudiante {
    private Long id;
    private String nombre, apellido, carrera;
    private Integer edad;

    public Estudiante(Long id, String nombre, String apellido, String carrera, Integer edad){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.edad = edad;
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

    public void setCarrera(String carrera){
        this.carrera = carrera;
    }

    public String getCarrera(){
        return this.carrera;
    }

    public void setEdad(Integer edad){
        this.edad = edad;
    }

    public Integer getEdad(){
        return this.edad;
    }


}
