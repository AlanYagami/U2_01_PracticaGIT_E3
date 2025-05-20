package mx.edu.utez.back_carros.carro;

import mx.edu.utez.back_carros.carro.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    boolean existsByNumPlacas(String numPlacas);
}