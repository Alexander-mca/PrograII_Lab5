package com.lab5.models;


public class Curso {
    private Long id;
    private String nombre, descripcion, modalidad;
    private Integer creditos;

    public Curso(Long id, String nombre, String descripcion, String modalidad, Integer creditos){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modalidad = modalidad;
        this.creditos = creditos;
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

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setModalidad(String modalidad){
        this.modalidad = modalidad;
    }

    public String getModalidad(){
        return this.modalidad;
    }

    public void setCreditos(Integer creditos){
        this.creditos = creditos;
    }

    public Integer getCreditos(){
        return this.creditos;
    }
}
