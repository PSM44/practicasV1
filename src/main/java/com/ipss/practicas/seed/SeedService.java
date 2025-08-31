package com.ipss.practicas.seed;

import com.ipss.practicas.model.entity.*;
import com.ipss.practicas.model.enums.EstadoPractica;
import com.ipss.practicas.model.enums.RolUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class SeedService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void run() {
        // USUARIOS
        Usuario uEst = new Usuario("alumno@demo.cl", "1234", RolUsuario.ESTUDIANTE);
        Usuario uProf = new Usuario("profesor@demo.cl", "1234", RolUsuario.PROFESOR);
        em.persist(uEst);
        em.persist(uProf);

        // PERSONAS
        Estudiante est = new Estudiante(uEst, "Ana", "Pérez", "alumno@demo.cl", "987654321", "MAT-001");
        Profesor prof = new Profesor(uProf, "Carlos Soto", "profesor@demo.cl", "912345678");
        em.persist(est);
        em.persist(prof);

        // EMPRESA + JEFE
        Empresa emp = new Empresa("Inmobiliaria Demo", "76.123.456-7",
                "Av. Prueba 123, Santiago", "22223333", "contacto@inmo.cl");
        em.persist(emp);
        JefeDirecto jefe = new JefeDirecto(emp, "María López", "Jefa TI", "mlopez@inmo.cl", "226667777");
        em.persist(jefe);

        // PRÁCTICA
        Practica pr = new Practica(
                est, emp, jefe, prof,
                LocalDate.now().minusDays(15),
                LocalDate.now().plusDays(45),
                180, "Soporte a sistemas inmobiliarios (tasaciones, cartera de propiedades).",
                EstadoPractica.EN_PROCESO
        );
        em.persist(pr);

        System.out.println("[SEED] Datos iniciales cargados (SeedService)");
    }
}
