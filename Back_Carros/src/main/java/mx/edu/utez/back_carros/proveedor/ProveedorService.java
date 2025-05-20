package mx.edu.utez.back_carros.proveedor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public void guardar(Proveedor proveedor) {
        repository.save(proveedor);
    }

    public List<Proveedor> listarTodos() {
        return repository.findAll();
    }
}
