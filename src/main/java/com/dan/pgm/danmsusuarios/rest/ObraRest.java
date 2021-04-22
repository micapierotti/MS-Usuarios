package com.dan.pgm.danmsusuarios.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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

import com.dan.pgm.danmsusuarios.domain.Empleado;
import com.dan.pgm.danmsusuarios.domain.Obra;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/obra")
@Api(value = "ObraRest", description = "Permite gestionar las obras de la empresa")
public class ObraRest {
	
	private static final List<Obra> listaObras = new ArrayList<>();
    private static Integer ID_GEN = 1;
	 
	@PostMapping
	 @ApiOperation(value = "Carga una obra")
	    public ResponseEntity<Obra> crear(@RequestBody Obra nuevo){
	    	System.out.println(" crear obra "+nuevo);
	        nuevo.setId(ID_GEN++);
	        listaObras.add(nuevo);
	        return ResponseEntity.ok(nuevo);
	    }
	
	 @PutMapping(path = "/{id}")
	    @ApiOperation(value = "Actualiza una obra")
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Actualizado correctamente"),
	        @ApiResponse(code = 401, message = "No autorizado"),
	        @ApiResponse(code = 403, message = "Prohibido"),
	        @ApiResponse(code = 404, message = "El ID no existe")
	    })
	    public ResponseEntity<Obra> actualizar(@RequestBody Obra nuevo,  @PathVariable Integer id){
	        OptionalInt indexOpt =   IntStream.range(0, listaObras.size())
	        .filter(i -> listaObras.get(i).getId().equals(id))
	        .findFirst();

	        if(indexOpt.isPresent()){
	            listaObras.set(indexOpt.getAsInt(), nuevo);
	            return ResponseEntity.ok(nuevo);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @DeleteMapping(path = "/{id}")
	    @ApiOperation(value = "Borra una obra por id")
	    public ResponseEntity<Obra> borrar(@PathVariable Integer id){
	        OptionalInt indexOpt =   IntStream.range(0, listaObras.size())
	        .filter(i -> listaObras.get(i).getId().equals(id))
	        .findFirst();

	        if(indexOpt.isPresent()){
	            listaObras.remove(indexOpt.getAsInt());
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping(path = "/{id}")
	    @ApiOperation(value = "Busca una obra por id")
	    public ResponseEntity<Obra> obraPorId(@PathVariable Integer id){

	        Optional<Obra> c =  listaObras
	                .stream()
	                .filter(unaObra -> unaObra.getId().equals(id))
	                .findFirst();
	        return ResponseEntity.of(c);
	    }
	    
	    @GetMapping
	    @ApiOperation(value = "Busca una obra por cliente y/o tipo de obra")
	    public ResponseEntity<List<Obra>> obraPorNombre(@RequestParam(name="idCliente", required = false) Integer idCliente, @RequestParam(name="idTipoObra", required = false) Integer idTipoObra){
	    	
	    	List<Obra> listaObrasFinal = new ArrayList<>(listaObras);
	    	System.out.println("idCliente: " + idCliente);
	    	System.out.println("idTipoObra: " + idTipoObra);
	    	if(idCliente != null) {
	    		System.out.println("idCliente no nulo");
	    		for(Obra ob:listaObras) {
	    			System.out.println("comparación de ids, son distintos?: " + (ob.getCliente().getId() != idCliente));
	    			if(ob.getCliente().getId() != idCliente) {
	    				System.out.println("Id cliente de obra a borrar: " + ob.getCliente().getId());
	    				listaObrasFinal.remove(ob);
	    				System.out.println("Lista actualizada: " + listaObrasFinal);
	    			}
	    		}
	    	}
	    	if(idTipoObra != null) {
	    		System.out.println("idTipoObra no nulo");
	    		for(Obra ob:listaObras) {
	    			System.out.println("comparación de ids , son distintos?: " + (ob.getTipo().getId() != idTipoObra));
	    			if(ob.getTipo().getId() != idTipoObra) {
	    				System.out.println("Id tipo obra de obra a borrar: " + ob.getTipo().getId());
	    				listaObrasFinal.remove(ob);
	    				System.out.println("Lista actualizada: " + listaObrasFinal);
	    			}
	    		}
	    	}
	    	
	    	System.out.println("Lista a devolver: " + listaObrasFinal);
	       
	        return ResponseEntity.ok(listaObrasFinal);
	    }

}
