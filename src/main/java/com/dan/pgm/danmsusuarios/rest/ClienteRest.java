package com.dan.pgm.danmsusuarios.rest;

import java.util.List;
import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dan.pgm.danmsusuarios.domain.Cliente;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


//TODO ERROR 404
@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {

    @Autowired
    ClienteService clienteSrv;

    @PostMapping
    @ApiOperation(value = "Carga un cliente")
    public ResponseEntity<String> crear(@RequestBody Cliente nuevoCliente){

        System.out.println("Crear cliente "+ nuevoCliente);

        if(nuevoCliente.getObras().size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Debe tener una o más obras");
        }
        for(Obra ob:nuevoCliente.getObras()){
            if(ob.getTipo() == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La obra "+ ob.getId() +" debe tener un tipo asignado");
            }
        }

        clienteSrv.crearCliente(nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    public ResponseEntity<Cliente> clientePorId(@PathVariable Integer id){
        Cliente c = clienteSrv.buscarPorId(id);
        if(c != null){
            return ResponseEntity.ok(c);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(path = "/cuit/{cuit}")
    @ApiOperation(value = "Busca un cliente por cuit")
    public ResponseEntity<Cliente> clientePorCUIT(@PathVariable String cuit){
        return ResponseEntity.ok(clienteSrv.buscarPorCuit(cuit));
    }

    @GetMapping
    @ApiOperation(value = "Busca un cliente por razon social si se pasa la razón social por query param")
    public ResponseEntity<List<Cliente>> todos(@RequestParam(name="razonSocial", required = false) String razonSocial) {
        List<Cliente> clientes;
        if(razonSocial != null) {
    	    clientes = clienteSrv.buscarTodosRazonSocial(razonSocial);
    	} else {
            clientes = clienteSrv.buscarTodos();
        }

        if(clientes.size() > 0){
            return ResponseEntity.ok(clientes);
        }else {
            return ResponseEntity.notFound().build();
        }

    }


    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Cliente> actualizar(@RequestBody Cliente nuevo,  @PathVariable Integer id){
        return ResponseEntity.ok(clienteSrv.actualizarCliente(nuevo, id));
    }

    // TODO corroborar respondeEntity<pedido> con ResponseEntity.ok?

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Borra un cliente por id")
    public ResponseEntity<Cliente> borrar(@PathVariable Integer id){
        boolean resultado = clienteSrv.borrarCliente(id);
        if(resultado){
            return ResponseEntity.ok().build();
        } else {
            return  ResponseEntity.notFound().build();
        }

    }


}

