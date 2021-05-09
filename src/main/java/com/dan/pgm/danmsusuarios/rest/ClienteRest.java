package com.dan.pgm.danmsusuarios.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dan.pgm.danmsusuarios.domain.Cliente;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {
    
    private static final List<Cliente> listaClientes = new ArrayList<>();
    private static Integer ID_GEN = 1;
    @Autowired
    ClienteService clienteSrv;

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    public ResponseEntity<Cliente> clientePorId(@PathVariable Integer id){

       /* Optional<Cliente> c =  listaClientes
                .stream()
                .filter(unCli -> unCli.getId().equals(id))
                .findFirst();*/
        return ResponseEntity.of(clienteSrv.buscarPorId(id));
    }
    
    @GetMapping(path = "/cuit/{cuit}")
    @ApiOperation(value = "Busca un cliente por cuit")
    public ResponseEntity<Cliente> clientePorCUIT(@PathVariable String cuit){

        /*Optional<Cliente> c =  listaClientes
                .stream()
                .filter(unCli -> unCli.getCuit().equals(cuit))
                .findFirst();*/

        return ResponseEntity.of(clienteSrv.buscarPorCuit(cuit));
    }

    @GetMapping
    @ApiOperation(value = "Busca un cliente por razon social si se pasa la razón social por query param")
    public ResponseEntity<Iterable<Cliente>> todos(@RequestParam(name="razonSocial", required = false) String razonSocial){
    	if(razonSocial != null) {
			/*List<Cliente> cliente= new ArrayList<>();
			for(Cliente cli:listaClientes) {
				if(cli.getRazonSocial().equals(razonSocial)) {
					cliente.add(cli);
				}
			}
			
			return ResponseEntity.ok(cliente);*/
            //TODO Ver el buscarTodos pero con razon Social
            //VER CLASE ITERABLE <=> LIST
            return ResponseEntity.ok(clienteSrv.buscarTodos());
    	}
        return ResponseEntity.ok(clienteSrv.buscarTodos());
    }

    @PostMapping
    @ApiOperation(value = "Carga un cliente")
    public ResponseEntity<String> crear(@RequestBody Cliente nuevoCliente){

        System.out.println(" crear cliente "+nuevoCliente);
        nuevoCliente.setId(ID_GEN++);
        //listaClientes.add(nuevo);
        //return ResponseEntity.ok(nuevo);

        if(nuevoCliente.getObras().size() == 0) {
            return ResponseEntity.badRequest().body("Debe tener una o más obras");
        }
        for(Obra ob:nuevoCliente.getObras()){
            if(ob.getTipo() == null){
                return ResponseEntity.badRequest().body("La obra "+ ob.getId() +" debe tener un tipo asignado");
            }
        }
        if(nuevoCliente.getUser().getUser() == null) {
            return ResponseEntity.badRequest().body("Debe tener un usuario");
        }
        if(nuevoCliente.getUser().getPassword() == null) {
            return ResponseEntity.badRequest().body("Debe tener una contraseña");
        }

        clienteSrv.crearCliente(nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
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
        /* OptionalInt indexOpt =   IntStream.range(0, listaClientes.size())
        .filter(i -> listaClientes.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaClientes.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }*/
        return ResponseEntity.of(clienteSrv.actualizarCliente(nuevo, id));
    }

    // TODO Verificar existencia de cliente a borrar en BDD

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Borra un cliente por id")
    public ResponseEntity<Cliente> borrar(@PathVariable Integer id){
       /* OptionalInt indexOpt =   IntStream.range(0, listaClientes.size())
        .filter(i -> listaClientes.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
           //listaClientes.remove(indexOpt.getAsInt());
            clienteSrv.borrarCliente(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }*/
        clienteSrv.borrarCliente(id);
        return ResponseEntity.ok().build();
    }


}

