package com.ejemplo.miproyectospring;


public class Recomendador implements Observador {

    @Override
    public void actualizar(String categoria, int respuesta) {
        System.out.println("\n💡 Recomendación: " + generarRecomendacion(categoria, respuesta));
    }

    private String generarRecomendacion(String categoria, int respuesta) {
        return switch (categoria) {
            case "fuenteEnergia" -> switch (respuesta) {
                case 4 -> "Considera cambiar a energías renovables como solar o eólica.";
                default -> "Excelente elección de fuente energética.";
            };
            case "usoLed" -> switch (respuesta) {
                case 3 -> "Cambia tus bombillos por LED, ahorrarás energía y dinero.";
                case 2 -> "Intenta reemplazar todos los bombillos por LED.";
                default -> "Perfecto, los LED reducen mucho tu consumo.";
            };
            case "enchufados" -> switch (respuesta) {
                case 3 -> "Desenchufa los aparatos que no uses para evitar consumo fantasma.";
                default -> "Buen hábito al manejar tus aparatos eléctricos.";
            };
            case "dispositivosAhorradores" -> switch (respuesta) {
                case 2 -> "Instalar reductores de flujo puede ayudarte a ahorrar mucha agua.";
                default -> "Muy bien, los dispositivos ahorradores hacen gran diferencia.";
            };
            case "tipoTransporte" -> switch (respuesta) {
                case 1, 2, 3 -> "Usar transporte público o bicicleta reduce mucho tu huella.";
                default -> "Excelente, medios sostenibles ayudan al planeta.";
            };
            case "tipoDieta" -> switch (respuesta) {
                case 4 -> "Reducir la carne roja disminuye drásticamente las emisiones.";
                case 3 -> "Podrías probar días sin carne para equilibrar tu impacto.";
                default -> "Muy bien, tus hábitos alimenticios son sostenibles.";
            };
            default -> "Sigue completando el formulario para más consejos sostenibles.";
        };
    }
}

