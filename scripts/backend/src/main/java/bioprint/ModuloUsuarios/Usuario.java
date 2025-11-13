    package bioprint.modulousuarios;

import jakarta.persistence.Entity;
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
}
