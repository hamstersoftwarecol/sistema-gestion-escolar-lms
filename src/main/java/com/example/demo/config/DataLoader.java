package com.example.demo.config;

import com.example.demo.lms.model.*;
import com.example.demo.lms.repository.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner inicializarDatosEscolares(
            UsuarioRepository usuarioRepo,
            CursoRepository cursoRepo,
            MatriculaRepository matriculaRepo,
            CalificacionRepository calificacionRepo,
            TransaccionFinancieraRepository transaccionRepo,
            PasswordEncoder passwordEncoder,
            LmsCursoRepository lmsCursoRepo,
            LmsModuloRepository lmsModuloRepo,
            LmsLeccionRepository lmsLeccionRepo,
            LmsInscripcionRepository lmsInscripcionRepo,
            LmsProgresoLeccionRepository lmsProgresoRepo,
            LmsResenaRepository lmsResenaRepo,
            LmsDebateRepository lmsDebateRepo,
            LmsAcademiaConfigRepository lmsAcademiaConfigRepo,
            LmsTareaEntregaRepository lmsTareaEntregaRepo,
            LmsWishlistRepository lmsWishlistRepo,
            LmsRetiroInstructorRepository lmsRetiroRepo,
            LmsArticuloBlogRepository lmsBlogRepo,
            LmsPaginaDinamicaRepository lmsPaginaRepo,
            LmsArchivoMediaRepository lmsMediaRepo
    ) {
        return args -> {
            if (usuarioRepo.count() > 0) return;

            String passAdmin = passwordEncoder.encode("admin123");
            String passProf = passwordEncoder.encode("prof123");
            String passEst = passwordEncoder.encode("est123");

            // 1. Usuarios Administradores
            Usuario admin = new Usuario("Dra. Sofía Mendoza", "admin@colegio.edu", passAdmin, "10203040", "+57 300 1234567", RolUsuario.ADMIN);
            usuarioRepo.save(admin);

            // 2. Profesores / Instructores
            Usuario prof1 = new Usuario("Prof. Roberto Gómez", "roberto.gomez@colegio.edu", passProf, "71234567", "+57 311 2345678", RolUsuario.PROFESOR);
            Usuario prof2 = new Usuario("Prof. Carmen Vega", "carmen.vega@colegio.edu", passProf, "43219876", "+57 312 3456789", RolUsuario.PROFESOR);
            Usuario prof3 = new Usuario("Prof. Andrés Paredes", "andres.paredes@colegio.edu", passProf, "98765432", "+57 313 4567890", RolUsuario.PROFESOR);
            usuarioRepo.saveAll(List.of(prof1, prof2, prof3));

            // 3. Estudiantes
            Usuario est1 = new Usuario("Santiago Gómez", "santiago.gomez@estudiante.edu", passEst, "10987654", "+57 315 5678901", RolUsuario.ESTUDIANTE);
            Usuario est2 = new Usuario("Valentina Ríos", "valentina.rios@estudiante.edu", passEst, "10987655", "+57 316 6789012", RolUsuario.ESTUDIANTE);
            Usuario est3 = new Usuario("Mateo Morales", "mateo.morales@estudiante.edu", passEst, "10987656", "+57 317 7890123", RolUsuario.ESTUDIANTE);
            Usuario est4 = new Usuario("Lucía Fernández", "lucia.fernandez@estudiante.edu", passEst, "10987657", "+57 318 8901234", RolUsuario.ESTUDIANTE);
            Usuario est5 = new Usuario("Diego Herrera", "diego.herrera@estudiante.edu", passEst, "10987658", "+57 319 9012345", RolUsuario.ESTUDIANTE);
            usuarioRepo.saveAll(List.of(est1, est2, est3, est4, est5));

            // 4. Cursos Escolares
            Curso c1 = new Curso("MAT-101", "Matemáticas y Cálculo", "11° Grado", "2026-I", prof1, 30, 320.0, "Aula 201", "Lun-Mié 08:00 - 10:00");
            Curso c2 = new Curso("FIS-201", "Física Mecánica y Óptica", "11° Grado", "2026-I", prof1, 28, 300.0, "Lab Física", "Mar-Jue 10:00 - 12:00");
            Curso c3 = new Curso("LIT-102", "Literatura Universal y Comunicación", "10° Grado", "2026-I", prof2, 35, 280.0, "Aula 104", "Lun-Mié 10:30 - 12:30");
            Curso c4 = new Curso("ING-301", "Inglés Académico Avanzado", "10° Grado", "2026-I", prof2, 25, 290.0, "Lab Idiomas", "Vie 08:00 - 12:00");
            Curso c5 = new Curso("QUI-105", "Química Orgánica y Experimental", "11° Grado", "2026-I", prof3, 26, 310.0, "Lab Química", "Mar-Jue 08:00 - 10:00");
            cursoRepo.saveAll(List.of(c1, c2, c3, c4, c5));

            // 5. Matrículas y Calificaciones Escolares
            Matricula m1 = matriculaRepo.save(new Matricula("MAT-2026-001", est1, c1, LocalDate.now().minusMonths(2), EstadoMatricula.ACTIVA));
            calificacionRepo.save(new Calificacion(m1, 4.5, 4.0, 4.8, "Excelente rendimiento y participación"));

            Matricula m2 = matriculaRepo.save(new Matricula("MAT-2026-002", est1, c2, LocalDate.now().minusMonths(2), EstadoMatricula.ACTIVA));
            calificacionRepo.save(new Calificacion(m2, 3.8, 3.5, 4.0, "Buen trabajo en informes de laboratorio"));

            Matricula m3 = matriculaRepo.save(new Matricula("MAT-2026-003", est2, c1, LocalDate.now().minusMonths(2), EstadoMatricula.ACTIVA));
            calificacionRepo.save(new Calificacion(m3, 5.0, 4.8, 4.9, "Destacada en resolución de problemas"));

            // 6. Transacciones Financieras Escolares
            transaccionRepo.saveAll(List.of(
                    new TransaccionFinanciera("FAC-2026-001", est1, "Matrícula Semestral 2026-I", 620.0, LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(1).minusDays(5), "Transferencia Bancaria", EstadoFinanciero.PAGADO),
                    new TransaccionFinanciera("FAC-2026-002", est1, "Pensión Mensualidad - Noviembre", 180.0, LocalDate.now().minusDays(10), LocalDate.now().plusDays(5), null, null, EstadoFinanciero.PENDIENTE),
                    new TransaccionFinanciera("FAC-2026-003", est2, "Matrícula Semestral 2026-I", 600.0, LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(1).minusDays(10), "Tarjeta de Crédito", EstadoFinanciero.PAGADO)
            ));

            // ==========================================
            // 7. INICIALIZACIÓN DE LMS & CURSOS ONLINE
            // ==========================================

            // Configuración de Marca y Academia
            LmsAcademiaConfig academia = new LmsAcademiaConfig(
                    "EduAcademy Global",
                    "Aprende habilidades de alta demanda con instructores y profesionales de la industria",
                    15.0
            );
            lmsAcademiaConfigRepo.save(academia);

            // Curso LMS 1: Spring Boot & React
            LmsCurso lms1 = new LmsCurso(
                    "Máster Completo en Java, Spring Boot 4 y Microservicios",
                    "Domina el backend empresarial moderno: APIs RESTful, Spring Security 6, JWT, Docker y Cloud Native",
                    "Aprende paso a paso a construir arquitecturas de software robustas, escalables y seguras utilizadas por las empresas tecnológicas líderes a nivel global.",
                    prof1,
                    "Desarrollo de Software",
                    "Intermedio - Avanzado",
                    89.99,
                    "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800&auto=format&fit=crop&q=80",
                    28
            );
            lms1 = lmsCursoRepo.save(lms1);

            LmsModulo mod1 = lmsModuloRepo.save(new LmsModulo(lms1, "Módulo 1: Fundamentos y Creación de Microservicios", 1));
            
            LmsLeccion lec1 = new LmsLeccion(mod1, "1.1 Bienvenida y Arquitectura del Curso", TipoLeccion.VIDEO,
                    "En esta lección cubrimos los objetivos del curso, la arquitectura hexagonal y la estructura general del proyecto.",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ", 12, null, null, 1);
            lec1.setVistaPrevia(true); // Vista Previa Gratuita para no inscritos
            lec1 = lmsLeccionRepo.save(lec1);

            LmsLeccion lec2 = new LmsLeccion(mod1, "1.2 Configuración del Proyecto con Spring Initializr", TipoLeccion.VIDEO,
                    "Descubre cómo inicializar dependencias críticas: Spring Web, JPA, H2 y DevTools.",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ", 18, "plantilla-inicial-spring.zip", "https://start.spring.io", 2);
            lec2 = lmsLeccionRepo.save(lec2);

            LmsLeccion lec3 = new LmsLeccion(mod1, "1.3 Cuestionario Rápido: Conceptos Clave de Spring", TipoLeccion.QUIZ,
                    "Evalúa tus conocimientos sobre Inyección de Dependencias, Inversión de Control (IoC) y Bean Lifecycle.",
                    null, 10, null, null, 3);
            lec3.setQuizPregunta("¿Cuál de las siguientes anotaciones de Spring Boot permite inyectar dependencias automáticamente en componentes?");
            lec3.setQuizOpcionA("@Autowired");
            lec3.setQuizOpcionB("@Component");
            lec3.setQuizOpcionC("@Repository");
            lec3.setQuizRespuestaCorrecta("A");
            lec3.setQuizExplicacion("@Autowired indica al contenedor Spring IoC que resuelva e inyecte la dependencia adecuada.");
            lec3 = lmsLeccionRepo.save(lec3);

            LmsModulo mod2 = lmsModuloRepo.save(new LmsModulo(lms1, "Módulo 2: Persistencia de Datos con Spring Data JPA", 2));
            
            LmsLeccion lec4 = new LmsLeccion(mod2, "2.1 Modelado de Entidades y Relaciones ORM", TipoLeccion.VIDEO,
                    "Mapeo objeto-relacional con anotaciones @Entity, @OneToMany, @ManyToOne y cascadas.",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ", 22, "diagrama-entidad-relacion.pdf", "https://spring.io", 1);
            lec4 = lmsLeccionRepo.save(lec4);

            LmsLeccion lec5 = new LmsLeccion(mod2, "2.2 Tarea Práctica: Crear un CRUD Completo", TipoLeccion.TAREA,
                    "Implementa un repositorio y servicio con validaciones de campos para gestionar productos o transacciones.",
                    null, 45, "enunciado-reto-crud.pdf", "https://github.com", 2);
            lec5.setDiasGoteo(3); // Contenido de Goteo: Desbloqueo a los 3 días de la inscripción
            lec5.setTareaInstrucciones("Crea un servicio REST con validaciones de campos (@Valid, @NotNull) y persistencia JPA. Envía el enlace a tu repositorio GitHub o adjunta tus comentarios y solución.");
            lec5 = lmsLeccionRepo.save(lec5);

            // Curso LMS 2: Inteligencia Artificial y Python
            LmsCurso lms2 = new LmsCurso(
                    "Inteligencia Artificial y Machine Learning Práctico con Python",
                    "De cero a experto: Scikit-Learn, Redes Neuronales, Deep Learning, PyTorch y Visión Artificial",
                    "Construye modelos de aprendizaje automático capaces de predecir tendencias, clasificar imágenes y analizar lenguaje natural con código real.",
                    prof3,
                    "Ciencia de Datos & IA",
                    "Principiante a Intermedio",
                    79.99,
                    "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800&auto=format&fit=crop&q=80",
                    22
            );
            lms2 = lmsCursoRepo.save(lms2);

            LmsModulo modAi = lmsModuloRepo.save(new LmsModulo(lms2, "Módulo 1: Fundamentos de IA y Preprocesamiento de Datos", 1));
            
            LmsLeccion lecAi1 = new LmsLeccion(modAi, "1.1 Introducción al Aprendizaje Supervisado y No Supervisado", TipoLeccion.VIDEO,
                    "Conceptos básicos de regresión, clasificación y agrupamiento con Pandas y NumPy.",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ", 20, "notebook-fundamentos.ipynb", "https://jupyter.org", 1);
            lecAi1.setVistaPrevia(true); // Vista Previa Gratuita
            lmsLeccionRepo.save(lecAi1);

            LmsLeccion lecAi2 = new LmsLeccion(modAi, "1.2 Cuestionario de Estadísticas para Machine Learning", TipoLeccion.QUIZ,
                    "Preguntas sobre desviación estándar, distribuciones normales y matrices de confusión.",
                    null, 15, null, null, 2);
            lecAi2.setQuizPregunta("¿Qué métrica evalúa el balance entre falsos positivos y falsos negativos?");
            lecAi2.setQuizOpcionA("F1-Score");
            lecAi2.setQuizOpcionB("Error Cuadrático Medio");
            lecAi2.setQuizOpcionC("Varianza");
            lecAi2.setQuizRespuestaCorrecta("A");
            lecAi2.setQuizExplicacion("El F1-Score es la media armónica entre precisión y exhaustividad (recall).");
            lmsLeccionRepo.save(lecAi2);

            // Curso LMS 3: Diseño UX/UI en Figma
            LmsCurso lms3 = new LmsCurso(
                    "Diseño UX/UI Profesional con Figma: Sistemas de Diseño y Prototipado",
                    "Crea interfaces cautivadoras, investiga usuarios, diseña Wireframes interactivos y entrega a desarrollo",
                    "Domina el software de diseño líder de la industria y crea tu portafolio profesional para trabajar como diseñador de producto digital.",
                    prof2,
                    "Diseño & Creatividad",
                    "Todos los niveles",
                    59.99,
                    "https://images.unsplash.com/photo-1581291518857-4e27b48ff24e?w=800&auto=format&fit=crop&q=80",
                    16
            );
            lms3 = lmsCursoRepo.save(lms3);

            LmsModulo modUx = lmsModuloRepo.save(new LmsModulo(lms3, "Módulo 1: Principios de Usabilidad y Design Thinking", 1));
            LmsLeccion lecUx1 = lmsLeccionRepo.save(new LmsLeccion(modUx, "1.1 Qué es UX vs UI: El Doble Diamante", TipoLeccion.ARTICULO,
                    "Guía exhaustiva con casos de estudio sobre cómo optimizar la conversión y la experiencia de usuario.",
                    null, 25, "guia-ux-metodologias.pdf", "https://figma.com", 1));

            // 8. Inscripciones y Progresos
            // Santiago en Curso 1 (Progreso 40%)
            LmsInscripcion inscripcionSantiago = lmsInscripcionRepo.save(new LmsInscripcion(est1, lms1, 89.99, "Tarjeta de Crédito"));
            lmsProgresoRepo.save(new LmsProgresoLeccion(inscripcionSantiago, lec1));
            lmsProgresoRepo.save(new LmsProgresoLeccion(inscripcionSantiago, lec2));
            inscripcionSantiago.setPorcentajeProgreso(40);
            lmsInscripcionRepo.save(inscripcionSantiago);

            // Santiago en Curso 3 (100% COMPLETADO con Certificado disponible)
            LmsInscripcion inscripcionSantiagoC3 = new LmsInscripcion(est1, lms3, 59.99, "PayPal Express");
            inscripcionSantiagoC3.setPorcentajeProgreso(100);
            inscripcionSantiagoC3.setCompletado(true);
            inscripcionSantiagoC3.setFechaCompletado(LocalDateTime.now().minusDays(2));
            inscripcionSantiagoC3 = lmsInscripcionRepo.save(inscripcionSantiagoC3);
            lmsProgresoRepo.save(new LmsProgresoLeccion(inscripcionSantiagoC3, lecUx1));

            // Inscripción de Valentina en Curso 2
            LmsInscripcion inscripcionValentina = lmsInscripcionRepo.save(new LmsInscripcion(est2, lms2, 79.99, "PSE"));
            inscripcionValentina.setPorcentajeProgreso(50);
            lmsInscripcionRepo.save(inscripcionValentina);

            // 9. Reseñas y Ratings
            lmsResenaRepo.saveAll(List.of(
                    new LmsResena(lms1, est1, 5, "¡El mejor curso de Spring Boot que he tomado! Explicación clara, ejercicios prácticos y código de calidad profesional."),
                    new LmsResena(lms1, est2, 5, "Excelente contenido, la estructura en módulos facilita mucho el aprendizaje paso a paso."),
                    new LmsResena(lms2, est2, 4, "Los ejemplos prácticos en Python están muy bien explicados. Muy recomendado.")
            ));

            // 10. Foros de Debate / Preguntas
            lmsDebateRepo.saveAll(List.of(
                    new LmsDebate(lms1, est1, "¿Cuál es la diferencia entre @Controller y @RestController?",
                            "En la lección 1.2 vi ambas anotaciones, ¿cuándo debo preferir @RestController frente a @Controller clásico?"),
                    new LmsDebate(lms2, est2, "¿Es recomendable normalizar los datos antes del entrenamiento?",
                            "Tengo dudas de si MinMaxScaler es más conveniente que StandardScaler para este conjunto de datos.")
            ));

            // Instructor responde la primera pregunta
            lmsDebateRepo.findAll().stream().findFirst().ifPresent(d -> {
                d.setRespuesta("@RestController combina @Controller y @ResponseBody. Es ideal para APIs REST ya que serializa el retorno automáticamente a JSON.");
                d.setFechaRespuesta(LocalDateTime.now());
                lmsDebateRepo.save(d);
            });

            // 11. Lista de Deseos (Wishlist)
            lmsWishlistRepo.save(new LmsWishlist(est1, lms2));

            // 12. Retiros de Instructores
            lmsRetiroRepo.save(new LmsRetiroInstructor(prof1, 50.0, "PayPal", "roberto.gomez@paypal.me"));

            // 13. Blog Integrado
            lmsBlogRepo.saveAll(List.of(
                    new LmsArticuloBlog(
                            "Las 7 Mejores Prácticas para Construir Microservicios Resilientes con Spring Boot 4",
                            "mejores-practicas-microservicios-spring-boot",
                            "Aprende patrones esenciales de circuit breaker, inyección de dependencias y balanceo de carga para entornos de producción.",
                            "El desarrollo de sistemas distribuidos requiere considerar la tolerancia a fallos desde el primer día. En este artículo analizamos cómo Spring Boot 4 y Spring Cloud simplifican la arquitectura de microservicios...",
                            "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800&auto=format&fit=crop&q=80",
                            prof1,
                            "Desarrollo de Software",
                            6
                    ),
                    new LmsArticuloBlog(
                            "Cómo la Inteligencia Artificial Generativa está Transformando la Educación Online",
                            "ia-generativa-transformando-educacion",
                            "Explora cómo los tutores virtuales y la generación automática de cuestionarios aumentan la retención estudiantil en un 40%.",
                            "La educación personalizada ya no es una promesa del futuro. Con modelos de lenguaje avanzados, cada estudiante puede recibir explicaciones adaptadas a su ritmo de aprendizaje...",
                            "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800&auto=format&fit=crop&q=80",
                            prof3,
                            "Inteligencia Artificial",
                            4
                    ),
                    new LmsArticuloBlog(
                            "Sistemas de Diseño en Figma: Del Prototipo al Código Limpio",
                            "sistemas-de-diseno-figma-a-codigo",
                            "Estrategias para diseñadores y desarrolladores front-end para sincronizar tokens de diseño y componentes en Tailwind CSS.",
                            "Un sistema de diseño exitoso es el puente de comunicación entre diseñadores UI y desarrolladores de software. Descubre cómo definir variables, colores y tipografías consistentes...",
                            "https://images.unsplash.com/photo-1581291518857-4e27b48ff24e?w=800&auto=format&fit=crop&q=80",
                            prof2,
                            "Diseño UX/UI",
                            5
                    )
            ));

            // 14. Páginas Dinámicas (CMS)
            lmsPaginaRepo.saveAll(List.of(
                    new LmsPaginaDinamica(
                            "nosotros",
                            "Acerca de Nuestra Academia & Misión",
                            "Somos una institución dedicada a democratizar la educación tecnológica y escolar de la más alta calidad en Latinoamérica. Combinamos la calidez pedagógica con las herramientas digitales más modernas de la industria.",
                            "fa-graduation-cap"
                    ),
                    new LmsPaginaDinamica(
                            "terminos",
                            "Términos y Condiciones del Servicio",
                            "Al utilizar nuestra plataforma educativa, aceptas nuestras condiciones de uso ético, derechos de autor sobre las clases y cursos adquiridos, y políticas de certificación.",
                            "fa-scale-balanced"
                    ),
                    new LmsPaginaDinamica(
                            "privacidad",
                            "Política de Privacidad y Cookies",
                            "Respetamos tu privacidad. Tus datos personales y de progreso académico están resguardados con cifrado estándar de la industria y nunca serán comercializados.",
                            "fa-shield-halved"
                    ),
                    new LmsPaginaDinamica(
                            "preguntas-frecuentes",
                            "Preguntas Frecuentes (FAQ)",
                            "¿Cómo obtengo mi certificado? Al completar el 100% de las lecciones, cuestionarios y tareas, tu certificado oficial estará disponible de forma inmediata en tu aula virtual.",
                            "fa-circle-question"
                    )
            ));

            // 15. Gestor de Medios Inicial
            lmsMediaRepo.saveAll(List.of(
                    new LmsArchivoMedia("banner-promocional-spring.png", "IMAGEN", "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800", 320L, prof1),
                    new LmsArchivoMedia("guia-entidad-relacion-jpa.pdf", "DOCUMENTO", "https://spring.io", 540L, prof1),
                    new LmsArchivoMedia("masterclass-figma-tokens.mp4", "VIDEO", "https://www.youtube.com/embed/dQw4w9WgXcQ", 12500L, prof2)
            ));
        };
    }
}
