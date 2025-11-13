package bioprint.Frontend.menu.mascota;

import java.util.HashMap;
import java.util.Map;

public class Recomendacion {

    private static final Map<String, String[]> recomendaciones = new HashMap<>();

    static {
        recomendaciones.put("bajo", new String[]{
                "Recuerda, apagar las luces cuando no las uses",
                "Evita el uso excesivo de plásticos desechables",
                "Puedes usar transporte público o caminar"
        });

        recomendaciones.put("medio", new String[]{
                "Vas por buen camino sigue mejorando tus hábitos",
                "Intenta reducir el consumo de agua al ducharte",
                "Usa bombillos LED para ahorrar energía"
        });

        recomendaciones.put("alto", new String[]{
                "¡Excelente! Tu huella es muy baja",
                "Sigue siendo ejemplo para los demás",
                "Puedes ayudar a otros a reducir su impacto ambiental"
        });
    }

    public static String[] obtenerRecomendaciones(String nivel) {
        return recomendaciones.getOrDefault(nivel.toLowerCase(), new String[]{"No hay recomendaciones disponibles"});
    }
}

