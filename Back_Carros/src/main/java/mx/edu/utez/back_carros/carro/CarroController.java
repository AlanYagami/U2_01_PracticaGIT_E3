package mx.edu.utez.back_carros.carro;

import mx.edu.utez.back_carros.carro.Carro;
import mx.edu.utez.back_carros.carro.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carros")
@CrossOrigin(origins = "*")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> getAllCarros() {
        return carroService.getAllCarros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCarroById(@PathVariable Long id) {
        return carroService.getCarroById(id);
    }

    @PostMapping
    public ResponseEntity<Object> saveCarro(@RequestBody Carro carro) {
        return carroService.saveCarro(carro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCarro(@PathVariable Long id, @RequestBody Carro carro) {
        return carroService.updateCarro(id, carro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCarro(@PathVariable Long id) {
        return carroService.deleteCarro(id);
    }
}