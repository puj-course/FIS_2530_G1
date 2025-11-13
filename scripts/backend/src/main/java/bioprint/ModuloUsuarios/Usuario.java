package bioprint.ModuloUsuarios;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Usuario {
    @Id
    private String nombre;
    private String contrasena;
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    void setNombre(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void setContrasena(String contrasena) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
