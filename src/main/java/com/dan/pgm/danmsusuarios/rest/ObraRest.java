package com.dan.pgm.danmsusuarios.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.dan.pgm.danmsusuarios.services.ClienteService;
import com.dan.pgm.danmsusuarios.services.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	ObraService obraSrv;

	@PostMapping
	 @ApiOperation(value = "Carga una obra")
	    public ResponseEntity<Obra> crear(@RequestBody Obra nuevo){
	    	System.out.println(" crear obra "+nuevo);
			Obra o = obraSrv.crearObra(nuevo);
	       	if(o != null) {
				return ResponseEntity.ok(o);
			} else {
	       		return ResponseEntity.notFound().build();
			}

	    }

	    //TODO VER?? ¿
	 @PutMapping(path = "/{id}")
	    @ApiOperation(value = "Actualiza una obra")
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Actualizado correctamente"),
	        @ApiResponse(code = 401, message = "No autorizado"),
	        @ApiResponse(code = 403, message = "Prohibido"),
	        @ApiResponse(code = 404, message = "El ID no existe")
	    })
	    public ResponseEntity<Obra> actualizar(@RequestBody Obra nuevo,  @PathVariable Integer id){
	       return ResponseEntity.ok(obraSrv.actualizarObra(nuevo));
	    }

		// TODO corroborar respondeEntity<pedido> con ResponseEntity.ok?
	    @DeleteMapping(path = "/{id}")
	    @ApiOperation(value = "Borra una obra por id")
	    public ResponseEntity<Obra> borrar(@PathVariable Integer id){
	        boolean resultado = obraSrv.borrarObra(id);
	        if(resultado){
	        	return ResponseEntity.ok().build();
			}else{
	        	return ResponseEntity.notFound().build();
			}
	    }
	    
	    @GetMapping(path = "/{id}")
	    @ApiOperation(value = "Busca una obra por id")
	    public ResponseEntity<Obra> obraPorId(@PathVariable Integer id){
			Obra o = obraSrv.buscarObraPorId(id);
			if(o != null){
				return ResponseEntity.ok(o);
			} else {
				return  ResponseEntity.notFound().build();
			}
	    }
	    
	    @GetMapping
	    @ApiOperation(value = "Busca una obra por cliente y/o tipo de obra")
	    public ResponseEntity<List<Obra>> obraPorClienteOTipo(@RequestParam(name="idCliente", required = false) Integer idCliente,
															  @RequestParam(name="tipoObra", required = false) String tipoObra){

			List<Obra> resultado = obraSrv.buscarPorClienteOTipo(idCliente, tipoObra);
			if(resultado.size() > 0){
				return ResponseEntity.ok(resultado);
			}else{
				return ResponseEntity.notFound().build();
			}
	    }

}
