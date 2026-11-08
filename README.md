<div align="center">

# 🎓 Sistema Integral de Gestión Escolar & Plataforma LMS Multi-Tenant

<p align="center">
  <img src="assets/banner.svg" alt="Banner EduGestión y LMS" width="100%">
</p>

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21%20%7C%2025-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.x-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org/)
[![H2 Database](https://img.shields.io/badge/H2_Database-In--Memory-003B57?style=for-the-badge&logo=databricks&logoColor=white)](https://www.h2database.com/)

<p align="center">
  <strong>Solución integral que combina la gestión académica y financiera escolar tradicional con una moderna academia online LMS de cursos de marca blanca, creada con estándares de arquitectura limpia en Spring Boot.</strong>
</p>

[Demostración](#-credenciales-de-prueba-y-demostración) •
[Módulos](#-módulos-y-capacidades-del-sistema) •
[Arquitectura](#-arquitectura-del-software) •
[Instalación](#-instalación-y-ejecución-rápida) •
[Endpoints](#-rutas-y-endpoints-del-sistema)

</div>

---

## 📌 Tabla de Contenidos

- [Descripción General](#-descripción-general)
- [Arquitectura del Software](#-arquitectura-del-software)
- [Módulos y Capacidades del Sistema](#-módulos-y-capacidades-del-sistema)
  - [1. Plataforma LMS y Creador de Cursos](#1-plataforma-lms-y-creador-de-cursos)
  - [2. Gestión Escolar y Académica](#2-gestión-escolar-y-académica)
  - [3. Gestión Financiera y Tesorería](#3-gestión-financiera-y-tesorería)
  - [4. Seguridad y Middleware RBAC](#4-seguridad-y-middleware-rbac)
- [Credenciales de Prueba y Demostración](#-credenciales-de-prueba-y-demostración)
- [Instalación y Ejecución Rápida](#-instalación-y-ejecución-rápida)
- [Rutas y Endpoints del Sistema](#-rutas-y-endpoints-del-sistema)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Licencia y Autor](#-licencia-y-autor)

---

## 📖 Descripción General

Este proyecto resuelve dos necesidades fundamentales en una única plataforma unificada:

1. **Gestión Escolar Completa**: Matrículas de estudiantes por grado escolar, asignación docente, registro y cálculo de calificaciones académicas, y balance financiero (ingresos de matrículas vs gastos operativos y nómina).
2. **Plataforma LMS de Aprendizaje y Monetización**: Creación y venta de cursos online bajo tu propia marca con reproductor de lecciones en video/conferencias, cuestionarios evaluados al instante, retos prácticos con entrega de soluciones, contenido programado por goteo (*Drip Content*), foros de debate y emisión automática de certificados oficiales al alcanzar el 100% de progreso.

---

## 🏗️ Arquitectura del Software

El sistema sigue el patrón de **Arquitectura en Capas Limpia (Clean Layered Architecture)** asegurando alta cohesión, bajo acoplamiento y facilidad de testing:

<p align="center">
  <img src="assets/architecture.svg" alt="Arquitectura en Capas" width="100%">
</p>

- **Capa de Presentación**: Plantillas HTML5 con Thymeleaf, maquetación moderna mediante Tailwind CSS y menú lateral responsivo con soporte móvil.
- **Capa de Seguridad**: Spring Security con autenticación basada en sesiones seguras, protección CSRF y control de acceso basado en roles (`ROLE_ADMIN`, `ROLE_PROFESOR`, `ROLE_ESTUDIANTE`).
- **Capa de Controladores & Servicios**: Controladores REST y MVC desacoplados que orquestan los casos de uso de matrícula, finanzas, cursos y evaluaciones.
- **Capa de Persistencia**: 14 repositorios Spring Data JPA con consultas optimizadas (`JOIN FETCH`) y motor Hibernate 7 sobre base de datos H2 en memoria.

---

## 🚀 Módulos y Capacidades del Sistema

<p align="center">
  <img src="assets/features.svg" alt="Módulos de la Plataforma" width="100%">
</p>

### 1. Plataforma LMS y Creador de Cursos

- **🛠️ Creador de Cursos Intuitivo**: Gestión completa de cursos estructurados en módulos y lecciones (Video, Masterclass en vivo, Lecturas, Quizzes y Tareas prácticas).
- **🏷️ Categorías Ilimitadas**: Organización taxonómica flexible de cursos por áreas temáticas con badges distintivos.
- **🧙‍♂️ Asistente de Instalación Inteligente (`/instalador`)**: Wizard paso a paso para inicializar la base de datos, configurar la academia y el administrador del sistema.
- **☁️ Soporte para Amazon S3**: Integración configurable de almacenamiento en la nube (AWS S3 Bucket, Endpoint, Región y Access Keys) para assets y videos.
- **⏳ Contenido de Goteo (Drip Content)**: Desbloqueo progresivo programado según los días transcurridos desde la fecha de inscripción del alumno.
- **👁️ Vista Previa del Curso (Free Preview)**: Muestras gratuitas de lecciones para visitantes antes de adquirir el curso, acelerando la conversión.
- **🌐 Inicio de Sesión Social**: Botones de acceso directo con Google, GitHub y Microsoft en la pantalla de autenticación.
- **❓ Sistema de Cuestionarios Avanzado (Quizzes)**: Evaluaciones interactivas con validación en tiempo real, puntaje aprobatorio y retroalimentación explicativa inmediata.
- **🔍 Búsqueda y Filtrado de Cursos**: Búsqueda por palabras clave en tiempo real y filtrado dinámico por categorías.
- **📊 Ingresos e Informes de Instructores**: Panel financiero con métricas de ventas, comisión de la plataforma y saldo disponible para retiro.
- **❤️ Lista de Deseos (Wishlist)**: Guardado de cursos de interés en un solo clic con vista dedicada `/lms/wishlist` para enrolamiento posterior.
- **📎 Archivos Adjuntos del Curso**: Soporte para descargas complementarias en cada lección (`.zip`, `.pdf`, `.ipynb`).
- **📋 Tareas y Proyectos Prácticos**: Asignación de retos prácticos con entrega por enlace o notas y calificación retroactiva del docente.
- **🎓 Página de Clase Moderna**: Aula virtual inmersiva tipo plataforma de streaming con selector de lecciones, barra de progreso y modo oscuro.
- **⭐ Reseñas de Cursos y Sistema de Calificación**: Desglose gráfico de 1 a 5 estrellas con promedio general acumulado y testimonios de estudiantes.
- **📝 Blog Integrado (`/blog`)**: Sistema editorial con artículos categorizados, tiempo estimado de lectura y buscador integrado.
- **💸 Sistema de Retiro del Instructor**: Solicitud de retiro de fondos acumulados vía transferencia bancaria, PayPal o Stripe con seguimiento de estados (*PENDIENTE*, *APROBADO*, *PAGADO*).
- **💬 Debates del Curso (Q&A)**: Foros de discusión por curso con preguntas de estudiantes y respuestas destacadas del docente.
- **📁 Gestor de Medios (`/lms/medios`)**: Biblioteca centralizada para administración y previsualización de imágenes, videos y documentos.
- **📄 Páginas Dinámicas (`/paginas/{slug}`)**: CMS para páginas institucionales como Nosotros, Términos de Servicio, Privacidad y Preguntas Frecuentes.
- **🍪 Aviso de Cookies (Cookie Consent)**: Banner flotante responsivo con persistencia local (`localStorage`) que cumple normativas GDPR.
- **📈 Seguimiento del Progreso y Certificado Oficial**: Cálculo porcentual en vivo y emisión automática del certificado oficial con código de verificación QR al alcanzar el 100%.
- **⚡ Alto Rendimiento**: Arquitectura no bloqueante en Spring Boot, JPA optimizado y tiempos de respuesta menores a 50 ms.

### 2. Gestión Escolar y Académica

- **Matrículas Escolares**: Inscripción de estudiantes organizados por grados escolares y periodos académicos.
- **Portal de Profesores**: Registro ágil de notas por estudiante, visualización de asignaturas a cargo y listado de alumnos.
- **Portal de Estudiantes**: Consulta de calificaciones, promedio general acumulado e historial de materias cursadas.

### 3. Gestión Financiera y Tesorería

- **Balance en Tiempo Real**: Cálculo automático de ingresos totales, gastos acumulados y balance neto disponible.
- **Ingresos**: Cobro automatizado de matrículas y pensiones estudiantiles.
- **Egresos**: Registro de pagos a proveedores, mantenimiento, nómina docente y servicios.

### 4. Seguridad y Middleware RBAC

- Encriptación robusta de contraseñas con `BCryptPasswordEncoder`.
- Redirección inteligente post-login según el rol del usuario autenticado:
  - `ROLE_ADMIN` ➔ Redirección al panel administrativo `/admin`.
  - `ROLE_PROFESOR` ➔ Redirección al panel docente y creador de cursos `/profesor`.
  - `ROLE_ESTUDIANTE` ➔ Redirección a su aula y calificaciones `/estudiante`.

---

## 🔑 Credenciales de Prueba y Demostración

El sistema se inicia con datos precargados listos para probar todos los perfiles de usuario desde [http://localhost:8080/login](http://localhost:8080/login):

| Rol | Correo Electrónico | Contraseña | Permisos y Capacidades |
| :--- | :--- | :--- | :--- |
| **👑 Administrador** | `admin@colegio.edu` | `admin123` | Control total escolar, finanzas, tesorería, configuración de marca blanca y academia. |
| **👨‍🏫 Profesor / Instructor** | `roberto.gomez@colegio.edu` | `prof123` | Creador de cursos, gestión de lecciones/quizzes, respuesta a debates y notas académicas. |
| **🎓 Estudiante** | `santiago.gomez@estudiante.edu` | `est123` | Aula virtual, resolución de cuestionarios, entregas prácticas y certificado al 100%. |

---

## 💻 Instalación y Ejecución Rápida

### Requisitos Previos

- **Java JDK 21 o superior** (probado y verificado en OpenJDK 25).
- Conexión a internet para la descarga inicial de dependencias Maven.

### Pasos para Ejecutar

```bash
# 1. Clonar el repositorio
git clone https://github.com/hamstersoftwarecol/sistema-gestion-escolar-lms.git
cd sistema-gestion-escolar-lms

# 2. Compilar y ejecutar con el Maven Wrapper incluido
./mvnw spring-boot:run
```

Una vez que la consola muestre `Started DemoApplication`, abre tu navegador en:
👉 **[http://localhost:8080](http://localhost:8080)** o **[http://localhost:8080/lms](http://localhost:8080/lms)**

Para acceder a la consola de base de datos H2:
👉 **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)** (JDBC URL: `jdbc:h2:mem:escolardb` / Usuario: `sa` / Clave: vacía)

---

## 🌐 Rutas y Endpoints del Sistema

### Rutas Públicas (LMS, Blog, Páginas y Asistente)
- `GET /` ➔ Redirección o página principal.
- `GET /login` ➔ Formulario de autenticación con botones sociales (Google, GitHub, Microsoft).
- `GET /instalador` ➔ Asistente inteligente de instalación y puesta en marcha.
- `GET /blog` ➔ Portal de artículos y novedades educativas.
- `GET /blog/{id}` ➔ Lectura de artículo completo con autor y tiempo de lectura.
- `GET /paginas/{slug}` ➔ Páginas dinámicas (`/nosotros`, `/terminos`, `/privacidad`, `/preguntas-frecuentes`).
- `GET /lms` ➔ Catálogo y marketplace de cursos con filtros por categoría y barra de búsqueda.
- `GET /lms/curso/{id}` ➔ Temario detallado, botón a lista de deseos, reseñas y foro Q&A.
- `GET /lms/curso/{id}/preview/{leccionId}` ➔ Vista previa gratuita de lecciones de muestra.

### Aula Virtual, Wishlist & Experiencia del Alumno (Requiere Autenticación)
- `GET /lms/wishlist` ➔ Lista de deseos personal del estudiante ❤️.
- `POST /lms/curso/{id}/wishlist` ➔ Alternar (agregar o quitar) curso en lista de deseos.
- `GET /lms/aprender/{cursoId}` ➔ Aula interactiva moderna con reproductor y temario lateral.
- `POST /lms/aprender/{cursoId}/completar` ➔ Marcar lección completada y recalcular progreso.
- `POST /lms/aprender/{cursoId}/quiz` ➔ Enviar evaluación interactiva con calificación inmediata.
- `POST /lms/aprender/{cursoId}/tarea` ➔ Entrega de reto práctico del estudiante.
- `GET /lms/curso/{cursoId}/certificado` ➔ Certificado oficial descargable e imprimible (al 100%).
- `POST /lms/curso/{id}/comprar` ➔ Simulación de compra e inscripción en curso.

### Portal de Docentes, Medios y Retiros
- `GET /lms/instructor` ➔ Panel de cursos, ingresos acumulados y balance disponible.
- `POST /lms/instructor/solicitar-retiro` ➔ Solicitar retiro de fondos generados.
- `GET /lms/medios` ➔ Gestor de biblioteca multimedia para videos, imágenes y guías.
- `POST /lms/medios/subir` ➔ Añadir nuevo recurso al gestor de medios.
- `POST /lms/instructor/crear-curso` ➔ Crear nuevo producto educativo.
- `POST /lms/instructor/modulo/crear` ➔ Añadir módulo o sección temaria.
- `POST /lms/instructor/leccion/crear` ➔ Crear lección (Video, Conferencia, Quiz, Tarea, Drip).
- `POST /lms/curso/{id}/debate/{debateId}/responder` ➔ Responder inquietud de un alumno.

### Gestión Administrativa & Marca Blanca
- `GET /admin` ➔ Dashboard administrativo con estadísticas de la institución.
- `GET /matriculas` ➔ Gestión de matrículas por estudiante y grado escolar.
- `GET /finanzas` ➔ Módulo de tesorería (ingresos, gastos, balance).
- `GET /lms/configuracion` ➔ Editor de marca blanca y configuración de almacenamiento Amazon S3.

---

## 📁 Estructura del Proyecto

```text
javaspring/
├── pom.xml                                   # Configuración de dependencias Maven
├── mvnw / mvnw.cmd                           # Maven Wrapper portable
├── assets/                                   # Recursos gráficos y diagramas SVG
│   ├── banner.svg                            # Banner principal del proyecto
│   ├── architecture.svg                      # Diagrama de arquitectura en capas
│   └── features.svg                          # Showcase de módulos y características
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── config/                       # Inicializador de datos (DataLoader)
│   │   │   ├── controller/                   # Controladores escolares y financieros
│   │   │   ├── lms/                          # Sub-sistema LMS
│   │   │   │   ├── controller/               # LmsController (Marketplace, Aula, Instructor)
│   │   │   │   ├── model/                    # Modelos (Curso, Modulo, Leccion, Quiz, Tarea, etc.)
│   │   │   │   ├── repository/               # Repositorios JPA del LMS
│   │   │   │   └── service/                  # LmsService (Lógica de negocio, Drip, Progreso)
│   │   │   ├── model/                        # Entidades escolares (Usuario, Matricula, Transaccion)
│   │   │   ├── repository/                   # Repositorios JPA escolares
│   │   │   ├── security/                     # SecurityConfig, CustomUserDetailsService, SuccessHandler
│   │   │   └── service/                      # Servicios escolares y contables
│   │   └── resources/
│   │       ├── application.properties        # Configuración de JPA, H2 y Thymeleaf
│   │       └── templates/                    # Vistas HTML Thymeleaf con Tailwind
│   │           ├── admin/                    # Vistas administrativas
│   │           ├── fragments/                # Layout común y Sidebar responsivo
│   │           ├── lms/                      # Vistas del LMS (catálogo, aula, certificado, etc.)
│   │           └── login.html                # Formulario de inicio de sesión
│   └── test/                                 # Pruebas unitarias y de integración
└── README.md                                 # Documentación técnica en español
```

---

## 📄 Licencia

Este proyecto se encuentra bajo la Licencia [MIT](LICENSE). Puedes utilizarlo libremente para fines educativos, comerciales o institucionales.

---

<div align="center">
  <sub>Desarrollado con ❤️ para centros educativos, academias online y creadores de contenido.</sub>
</div>
