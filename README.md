# TechFlow — Sistema de Gestión de Tickets de Soporte TI

> Sistema web desarrollado con Java EE para la gestión integral de incidencias técnicas en organizaciones.

## Tecnologías utilizadas

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache_Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)

## Funcionalidades

- Autenticación con 3 roles: Administrador, Técnico y Usuario
- Ciclo de vida completo del ticket (registro → atención → resolución → cierre)
- Reportes estadísticos con gráficas interactivas y exportación a PDF
- Consultas dinámicas por título y rango de fechas
- 6 módulos de mantenimiento (Usuarios, Técnicos, Roles, Estados, Prioridades, Tipos de problema)
- Sistema de comentarios con historial de atención por ticket

## Arquitectura
Patrón MVC + DAO
├── Servlets       → Controladores (lógica de negocio)
├── JSP + JSTL     → Vistas (interfaz de usuario)
├── Entidades + DAOs → Modelo (acceso a datos)
└── MySQL          → Base de datos relacional

## Configuración para ejecutar

1. Clona el repositorio
2. Importa en Eclipse como **Dynamic Web Project**
3. Configura `MySQLConexion.java` con tus credenciales de MySQL
4. Ejecuta el script `bd_gestiontickets.sql` en MySQL Workbench
5. Despliega en **Apache Tomcat 9**
6. Accede a `http://localhost:8080/gestion_ticket`

## Desarrollado por

**Ashly Galvez** — Computación e Informática, CIBERTEC  
Curso: Lenguaje de Programación 1
