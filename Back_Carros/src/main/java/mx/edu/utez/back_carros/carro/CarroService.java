package mx.edu.utez.back_carros.carro;

import mx.edu.utez.back_carros.carro.Carro;
import mx.edu.utez.back_carros.proveedor.Proveedor;
import mx.edu.utez.back_carros.carro.CarroRepository;
import mx.edu.utez.back_carros.proveedor.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<Carro>> getAllCarros() {
        return new ResponseEntity<>(carroRepository.findAll(), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getCarroById(Long id) {
        Optional<Carro> optionalCarro = carroRepository.findById(id);
        if(optionalCarro.isPresent()) {
            return new ResponseEntity<>(optionalCarro.get(), HttpStatus.OK);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "El carro con ID: " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<Object> saveCarro(Carro carro) {
        Map<String, Object> response = new HashMap<>();

        // Validar si ya existe un carro con las mismas placas
        if(carroRepository.existsByNumPlacas(carro.getNumPlacas())) {
            response.put("mensaje", "Ya existe un carro con esas placas");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Validar que exista el proveedor
        if(carro.getProveedor() == null || carro.getProveedor().getId() == null) {
            response.put("mensaje", "El carro debe tener un proveedor asignado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Proveedor> optionalProveedor = proveedorRepository.findById(carro.getProveedor().getId());
        if(!optionalProveedor.isPresent()) {
            response.put("mensaje", "El proveedor especificado no existe");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Asignar el proveedor completo
        carro.setProveedor(optionalProveedor.get());

        try {
            Carro savedCarro = carroRepository.save(carro);
            response.put("mensaje", "Carro guardado exitosamente");
            response.put("carro", savedCarro);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al guardar el carro");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Object> updateCarro(Long id, Carro carro) {
        Map<String, Object> response = new HashMap<>();
        Optional<Carro> optionalCarro = carroRepository.findById(id);

        if(optionalCarro.isPresent()) {
            Carro existingCarro = optionalCarro.get();

            // Validar que exista el proveedor si se está actualizando
            if(carro.getProveedor() != null && carro.getProveedor().getId() != null) {
                Optional<Proveedor> optionalProveedor = proveedorRepository.findById(carro.getProveedor().getId());
                if(!optionalProveedor.isPresent()) {
                    response.put("mensaje", "El proveedor especificado no existe");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                existingCarro.setProveedor(optionalProveedor.get());
            }

            existingCarro.setMarca(carro.getMarca());
            existingCarro.setModelo(carro.getModelo());
            existingCarro.setColor(carro.getColor());
            existingCarro.setNumPlacas(carro.getNumPlacas());

            try {
                Carro updatedCarro = carroRepository.save(existingCarro);
                response.put("mensaje", "Carro actualizado exitosamente");
                response.put("carro", updatedCarro);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("mensaje", "Error al actualizar el carro");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("mensaje", "El carro con ID: " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteCarro(Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Carro> optionalCarro = carroRepository.findById(id);

        if(optionalCarro.isPresent()) {
            try {
                carroRepository.deleteById(id);
                response.put("mensaje", "Carro eliminado exitosamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar el carro");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("mensaje", "El carro con ID: " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}