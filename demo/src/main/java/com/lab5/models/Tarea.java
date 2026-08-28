package com.lab5.models;

public class Tarea {
    private Long id;
    private String titulo, descripcion, prioridad;
    public Boolean completada;

    public Tarea(Long id, String titulo, String descripcion, String prioridad, Boolean completada){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.completada = completada;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setPrioridad(String prioridad){
        this.prioridad = prioridad;
    }

    public String getPrioridad(){
        return this.prioridad;
    }


    
}
