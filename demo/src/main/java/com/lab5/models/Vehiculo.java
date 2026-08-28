package com.lab5.models;

public class Vehiculo {
    private Long id;
    private String marca, modelo;
    private Double precio;
    private Integer anyo;

    public Vehiculo(Long id, String marca, String modelo, Double precio, Integer anyo){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.anyo = anyo;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public String getMarca(){
        return this.marca;
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    public String getModelo(){
        return this.modelo;
    }
    public void setPrecio(Double precio){
        this.precio = precio;
    }

    public Double getPrecio(){
        return this.precio;
    }

    public void setAnyo(Integer anyo){
        this.anyo = anyo;
    }

    public Integer getAnyo(){
        return this.anyo;
    }
}
