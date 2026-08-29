# Laboratorio sobre APIs Rest con Spring Framework

Este proyecto se desarrollaron 10 APIs REST independientes con Spring y Maven

1. API Productos

Modelo:

Producto
- id
- nombre
- precio
- categoria

Endpoints
GET     /api/productos
GET     /api/productos/{id}
POST    /api/productos
´[
    {
        "nombre": "La Foca",
        "categoria" : "Jabon",
        "precio" : 5
    }
]´
PUT     /api/productos/{id}
´
{
        "nombre": "La Foca",
        "categoria" : "Jabon",
        "precio" : 5
}
´
PATCH   /api/productos/{id}
´{
        "precio" : 5
}´ 
DELETE  /api/productos/{id}

 

2. API Estudiantes

Modelo
Estudiante
- id
- nombre
- apellido
- carrera
- edad

Endpoints
GET     /api/estudiantes
GET     /api/estudiantes/{id}
POST    /api/estudiantes
´
[
    {
        "nombre":"Alejandro",
        "apellido":"Polanco",
        "carrera": "Agronomia",
        "edad": 25
    }
]
´
PUT     /api/estudiantes/{id}
´
{
    "nombre":"Alejandro",
    "apellido":"Polanco",
    "carrera": "Agronomia",
    "edad": 25
}
´
PATCH   /api/estudiantes/{id}
´
{
    "edad": 25
}
´
DELETE  /api/estudiantes/{id}


3. API Libros

Modelo
Libro
- id
- titulo
- autor
- genero
- precio

Endpoints
GET     /api/libros
GET     /api/libros/{id}
POST    /api/libros
´
[
{
    "titulo" : "Cien años de soledad",
    "autor" : "Gabriel Garcia Marquez",
    "genero" : "Realismo Magico",
    "precio" : 120
}

]
´
PUT     /api/libros/{id}
´
{
    "titulo" : "Cien años de soledad",
    "autor" : "Gabriel Garcia Marquez",
    "genero" : "Realismo Magico",
    "precio" : 120
}
´
PATCH   /api/libros/{id}
´{
    "precio" : 120
}´
DELETE  /api/libros/{id}


4. API Empleados

Modelo
Empleado
- id
- nombre
- puesto
- salario
- departamento

Endpoints
GET     /api/empleados
GET     /api/empleados/{id}
POST    /api/empleados
´
[
    {
    "nombre": "Ana Gómez",
    "puesto": "Desarrolladora de Software",
    "departamento": "Tecnología",
    "salario": 2500
  }
]
´
PUT     /api/empleados/{id}
´
{
    "nombre": "Ana Gómez",
    "puesto": "Desarrolladora de Software",
    "departamento": "Tecnología",
    "salario": 2500
  }
´
PATCH   /api/empleados/{id}
´
{
    "salario": 2500
}
´
DELETE  /api/empleados/{id}


5. Películas

Modelo
Pelicula
- id
- titulo
- director
- genero
- anio

Endpoints
GET     /api/peliculas
GET     /api/peliculas/{id}
POST    /api/peliculas
´
[
    {
    "titulo": "Inception",
    "director": "Christopher Nolan",
    "genero": "Ciencia ficción",
    "año": 2010
  }
]
´
PUT     /api/peliculas/{id}
´
{
    "titulo": "Inception",
    "director": "Christopher Nolan",
    "genero": "Ciencia ficción",
    "año": 2010
  }
´
PATCH   /api/peliculas/{id}
´
{
    "año": 2010
  }
´
DELETE  /api/peliculas/{id}


6. API Cursos

Modelo
Curso
- id
- nombre
- descripcion
- creditos
- modalidad

Endpoints
GET     /api/cursos
GET     /api/cursos/{id}
POST    /api/cursos
´
[
{
    "nombre": "Introducción a la Inteligencia Artificial",
    "descripcion": "Conceptos básicos de aprendizaje automático, redes neuronales y ética en la IA.",
    "modalidad": "Virtual",
    "creditos": 4
  }
]
´
PUT     /api/cursos/{id}
´
{
    "nombre": "Introducción a la Inteligencia Artificial",
    "descripcion": "Conceptos básicos de aprendizaje automático, redes neuronales y ética en la IA.",
    "modalidad": "Virtual",
    "creditos": 4
  }
´
PATCH   /api/cursos/{id}
´
{
    "modalidad": "Virtual",
    "creditos": 4
  }
´
DELETE  /api/cursos/{id}

 

7. API Vehiculos

Modelo
Vehiculo
- id
- marca
- modelo
- anio
- precio

Endpoints
GET     /api/vehiculos
GET     /api/vehiculos/{id}
POST    /api/vehiculos
´
[
{
    "marca": "Chevrolet",
    "modelo": "Onix",
    "año": 2023,
    "precio_usd": 18500
  }
]
´
PUT     /api/vehiculos/{id}
´
{
    "marca": "Chevrolet",
    "modelo": "Onix",
    "año": 2023,
    "precio_usd": 18500
  }
´
PATCH   /api/vehiculos/{id}
´
{
    "año": 2023,
    "precio_usd": 18500
  }
´
DELETE  /api/vehiculos/{id}

 

8. API Tareas

Modelo
Tarea
- id
- titulo
- descripcion
- prioridad
- completada

Endpoints
GET     /api/tareas
GET     /api/tareas/{id}
POST    /api/tareas
´
[
{
    "titulo": "Preparar presentación de ventas",
    "descripcion": "Diseñar las diapositivas para la reunión trimestral con los inversores.",
    "prioridad": "Alta",
    "completada": false
  }
]
´
PUT     /api/tareas/{id}
´
{
    "titulo": "Preparar presentación de ventas",
    "descripcion": "Diseñar las diapositivas para la reunión trimestral con los inversores.",
    "prioridad": "Alta",
    "completada": false
  }
´
PATCH   /api/tareas/{id}
´
{
    "prioridad": "Alta",
    "completada": false
  }
´
DELETE  /api/tareas/{id}

 

9. API Clientes

Modelo
Cliente
- id
- nombre
- apellido
- correo
- telefono

Endpoints
GET     /api/clientes
GET     /api/clientes/{id}
POST    /api/clientes
´
[
{
    "nombre": "Carlos",
    "apellido": "Mendoza",
    "correo": "carlos.mendoza@example.com",
    "telefono": "2234-5678"
  }
]
´
PUT     /api/clientes/{id}
´
{
    "nombre": "Carlos",
    "apellido": "Mendoza",
    "correo": "carlos.mendoza@example.com",
    "telefono": "2234-5678"
  }
´
PATCH   /api/clientes/{id}
´
{
    "correo": "carlos.mendoza@example.com",
    "telefono": "2234-5678"
  }
´
DELETE  /api/clientes/{id}


10. API Pedidos

Modelo
Pedido
- id
- cliente
- producto
- cantidad
- total
- estado

Endpoints
GET     /api/pedidos
GET     /api/pedidos/{id}
POST    /api/pedidos
´
[
    {
    "cliente": "Sofía Martínez",
    "producto": "Auriculares Sony WH-1000XM4",
    "estado": "Entregado",
    "cantidad": 1,
    "total": 299.00
  }
]
´
PUT     /api/pedidos/{id}
´
{
    "cliente": "Sofía Martínez",
    "producto": "Auriculares Sony WH-1000XM4",
    "estado": "Entregado",
    "cantidad": 1,
    "total": 299.00
  }
´
PATCH   /api/pedidos/{id}
´
{
    "cantidad": 1,
    "total": 299.00
  }
´
DELETE  /api/pedidos/{id}
