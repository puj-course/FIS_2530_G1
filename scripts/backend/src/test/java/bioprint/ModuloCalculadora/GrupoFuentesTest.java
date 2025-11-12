package bioprint.ModuloCalculadora;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GrupoFuentesTest {

    @Test
    void testSumaFuentes() {
        FuenteHuella f1 = () -> 100;
        FuenteHuella f2 = () -> 200;

        GrupoFuentes grupo = new GrupoFuentes();
        grupo.addFuente(f1);
        grupo.addFuente(f2);

        assertEquals(300, grupo.calcularCO2());
    }

    @Test
    void testGrupoVacio() {
        GrupoFuentes grupo = new GrupoFuentes();
        assertEquals(0, grupo.calcularCO2());
    }
}