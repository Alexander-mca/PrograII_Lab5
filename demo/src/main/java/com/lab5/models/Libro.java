package com.lab5.models;

public class Libro {
    private Long id;
    private String titulo, autor, genero;
    private Double precio;
    
    public Libro(Long id, String titulo, String autor, String genero, Double precio){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.precio = precio;
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

    public void setAutor(String autor){
        this.autor = autor;
    }
    public String getAutor(){
        return this.autor;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setPrecio(Double precio){
        this.precio = precio;
    }
    public Double getPrecio(){
        return this.precio;
    }
}
