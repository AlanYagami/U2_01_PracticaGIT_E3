package mx.edu.utez.back_carros.carro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.back_carros.proveedor.Proveedor;

@Entity
@Table(name = "carros")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String color;

    @Column(name = "num_placas", nullable = false, unique = true)
    private String numPlacas;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;
}
