package com.dan.pgm.danmsusuarios.rest;

import java.util.ArrayList;
import java.util.List;
import com.dan.pgm.danmsusuarios.domain.Obra;
import com.dan.pgm.danmsusuarios.dtos.ClienteDTO;
import com.dan.pgm.danmsusuarios.dtos.MostrarClienteDTO;
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
    public ResponseEntity<String> crear(@RequestBody ClienteDTO nuevoCliente){

        System.out.println("Crear cliente "+ nuevoCliente.toString());

        if (nuevoCliente.getObras() != null) {
            if(nuevoCliente.getObras().size() > 0) {
                for (Obra ob : nuevoCliente.getObras()) {
                    if (ob.getTipo() == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La obra " + ob.getId() + " debe tener un tipo asignado");
                    }
                }
            }
        }

        clienteSrv.crearCliente(nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @GetMapping(path = "/by-id/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    public ResponseEntity<MostrarClienteDTO> clientePorId(@PathVariable Integer id){
        System.out.println("entró al get por id");
        Cliente c = clienteSrv.buscarPorId(id);
        if(c != null){
            MostrarClienteDTO mostrarClienteDTO = new MostrarClienteDTO(c);
            return ResponseEntity.ok(mostrarClienteDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(path = "/cuit/{cuit}")
    @ApiOperation(value = "Busca un cliente por cuit")
    public ResponseEntity<MostrarClienteDTO> clientePorCUIT(@PathVariable String cuit){
        Cliente c = clienteSrv.buscarPorCuit(cuit);
        if(c != null){
            MostrarClienteDTO mostrarClienteDTO = new MostrarClienteDTO(c);
            return ResponseEntity.ok(mostrarClienteDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Busca un cliente por razon social si se pasa la razón social por query param")
    public ResponseEntity<List<MostrarClienteDTO>> todos(@RequestParam(name="razonSocial", required = false) String razonSocial) {
        List<Cliente> clientes;
        if(razonSocial != null) {
    	    clientes = clienteSrv.buscarTodosRazonSocial(razonSocial);
    	} else {
            clientes = clienteSrv.buscarTodos();
        }

        if(clientes.size() > 0){
            List<MostrarClienteDTO> mostrarClienteDTOList = new ArrayList<MostrarClienteDTO>();
            clientes.stream().forEach(cli -> mostrarClienteDTOList.add(new MostrarClienteDTO(cli)));
            return ResponseEntity.ok(mostrarClienteDTOList);
        }else {
            return ResponseEntity.notFound().build();
        }

    }


    @PutMapping(path = "/update-by-id/{id}")
    @ApiOperation(value = "Actualiza un cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente")
    })
    public ResponseEntity<MostrarClienteDTO> actualizar(@RequestBody Cliente nuevo,  @PathVariable Integer id){
        Cliente clienteActualizado = clienteSrv.actualizarCliente(nuevo, id);
        if(clienteActualizado != null){
            MostrarClienteDTO mostrarClienteDTO = new MostrarClienteDTO(clienteActualizado);
            return ResponseEntity.ok(mostrarClienteDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    // TODO corroborar respondeEntity<pedido> con ResponseEntity.ok?

    @DeleteMapping(path = "/delete-by-id/{id}")
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

