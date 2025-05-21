package mx.edu.utez.back_carros.proveedor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Proveedor> listarProveedores() {
        return service.listarTodos();
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarProveedor(@RequestBody Proveedor proveedor) {
        service.guardar(proveedor);
        return ResponseEntity.ok().build();
    }
}

