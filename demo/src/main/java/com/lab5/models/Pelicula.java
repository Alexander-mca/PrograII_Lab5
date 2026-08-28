package com.lab5.models;


public class Pelicula {
    private Long id;
    private String titulo, director, genero;
    private Integer anyo;
    
    public Pelicula(Long id, String titulo, String director, String genero, Integer anyo){
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.anyo = anyo;
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

    public void setDirector(String director){
        this.director = director;
    }

    public String getDirector(){
        return this.director;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setAnyo(Integer anyo){
        this.anyo = anyo;
    }

    public Integer getAnyo(){
        return this.anyo;
    }
}
