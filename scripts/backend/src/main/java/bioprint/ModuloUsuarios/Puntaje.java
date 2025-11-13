package bioprint.modulousuarios;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "puntajes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Puntaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valor;

    private LocalDate fecha;

    // Relación con Usuario (muchos puntajes por usuario)
    @ManyToOne
    @JoinColumn(name = "usuario_nombre", referencedColumnName = "nombre")
    private Usuario usuario;
}
