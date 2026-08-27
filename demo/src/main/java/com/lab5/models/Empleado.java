package com.lab5.models;

public class Empleado {
    private Long id;
    private String nombre, puesto, departamento;
    private Double salario;

    public Empleado(Long id, String nombre, String puesto, String departamento, Double salario){
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.departamento = departamento;
        this.salario = salario;
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

    public void setPuesto(String puesto){
        this.puesto = puesto;
    }

    public String getPuesto(){
        return this.puesto;
    }

    public void setDepartamento(String departamento){
        this.departamento = departamento;
    }

    public String getDepartamento(){
        return this.departamento;
    }

    public void setSalario(Double salario){
        this.salario = salario;
    }

    public Double getSalario(){
        return this.salario;
    }
}
