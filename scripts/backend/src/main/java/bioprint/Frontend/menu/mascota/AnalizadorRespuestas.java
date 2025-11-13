package bioprint.Frontend.menu.mascota;

public class AnalizadorRespuestas {

    public static String evaluarPuntaje(double puntaje) {
        if (puntaje < 40) {
            return "bajo";
        } else if (puntaje < 70) {
            return "medio";
        } else {
            return "alto";
        }
    }
}
