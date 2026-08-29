package com.lab5.models;

public class Pedido{
    private Long id;
    private String cliente, producto, estado;
    private Integer cantidad;
    private Double total;

    public Pedido(Long id, String cliente, String producto, String estado, Integer cantidad, Double total){
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public void setCliente(String cliente){
        this.cliente = cliente;
    }

    public String getCliente(){
        return this.cliente;
    }

    public void setProducto(String producto){
        this.producto = producto;
    }

    public String getProducto(){
        return this.producto;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public String getEstado(){
        return this.estado;
    }

    public void setCantidad(Integer cantidad){
        this.cantidad = cantidad;
    }
    public Integer getCantidad(){
        return this.cantidad;
    }
    
    public void setTotal(Double total){
        this.total = total;
    }

    public Double getTotal(){
        return this.total;
    }

}