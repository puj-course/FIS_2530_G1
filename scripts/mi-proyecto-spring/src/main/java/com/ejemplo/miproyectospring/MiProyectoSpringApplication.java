package com.ejemplo.miproyectospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiProyectoSpringApplication {

    public static void main(String[] args) {
        // Ejecutar Spring Boot
        SpringApplication.run(MiProyectoSpringApplication.class, args);
        
        // Tu lógica de suma
        System.out.println("=== MI PROGRAMA DE SUMA ===");
        int numero1 = 15;
        int numero2 = 25;
        int resultado = numero1 + numero2;
        
        System.out.println("La suma de " + numero1 + " + " + numero2 + " = " + resultado);
        System.out.println("¡Spring Boot está ejecutándose en http://localhost:8080 !");
    }
}