# Prueba Técnica Ionix

## Requisitos

- _Docker v20.10.19, build d85ef84_
- _Java JDK v11.0.16_
- _Maven_

## Instalación

Iniciar base de datos MySQL

```sh
docker compose up -d
```

## Uso

### Listar Usuarios

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/list_users.png?raw=true)

### Obtener usuario por email

```sh
  {
    "email": "fhayden@ionix.cl"
  }
```

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/get_user_by_email.png?raw=true)

### Crear Usuario

```sh
  {
    "userName": "Prueba"
  }
```

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/create_user_unauthorized.png?raw=true)

Esta desautorizado ya que no se ha enviado el token de acceso.

### Realizar  _Login_  para obtener un token.

```sh
  {
    "username": "test",
    "password" : "password"
  }
```

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/login.png?raw=true)

Se realiza nuevamente el post de creacion.

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/create_user.png?raw=true)

### Eliminar Usuario

```sh
  {
    "userId": "21"
  }
```

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/delete_user_unauthorized.png?raw=true)

Esta desautorizado ya que no se ha enviado el token de acceso.

### Realizar  _Login_  para obtener un token.

```sh
  {
    "username": "test",
    "password" : "password"
  }
```

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/login.png?raw=true)

Se realiza nuevamente el post de eliminacion.

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/delete_user.png?raw=true)

### Peticion a API externa

API externa

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/api_externa.png?raw=true)

Consumo de API externa en aplicativo

![alt text](https://github.com/CristobalValenzuela/prueba-tecnica-ionix/blob/master/doc/images/call_api_externa.png?raw=true)
