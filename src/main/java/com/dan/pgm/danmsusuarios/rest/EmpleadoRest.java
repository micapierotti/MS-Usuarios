package com.dan.pgm.danmsusuarios.rest;

import com.dan.pgm.danmsusuarios.dtos.EmpleadoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.dan.pgm.danmsusuarios.services.EmpleadoService;
import com.dan.pgm.danmsusuarios.domain.Empleado;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/api/empleado")
@Api(value = "EmpleadoRest", description = "Permite gestionar los empleados de la empresa")
public class EmpleadoRest {

	@Autowired
	EmpleadoService empleadoSrv;

	@PostMapping(path = "/new")
	@ApiOperation(value = "Carga un empleado")
	public ResponseEntity<String> crear(@RequestBody EmpleadoDTO nuevoEmpleado){
		System.out.println("Crear empleado " +nuevoEmpleado);

		if(nuevoEmpleado==null)
			return ResponseEntity.badRequest().body("Por favor cree un empleado válido.");

		empleadoSrv.crearEmpleado(nuevoEmpleado);
		return ResponseEntity.status(HttpStatus.CREATED).body("OK");
	}
	
	 @PutMapping(path = "/{idEmpleado}")
	    @ApiOperation(value = "Actualiza un empleado")
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Actualizado correctamente"),
	        @ApiResponse(code = 401, message = "No autorizado"),
	        @ApiResponse(code = 403, message = "Prohibido"),
	        @ApiResponse(code = 404, message = "El ID no existe")
	    })
	 public ResponseEntity<Empleado> actualizar(@RequestBody Empleado nuevoEmpleado,  @PathVariable Integer idEmpleado){
		 return ResponseEntity.ok(empleadoSrv.actualizarEmpleado(nuevoEmpleado, idEmpleado));
	    }

	    @DeleteMapping(path = "/{idEmpleado}")
	    @ApiOperation(value = "Borra un empleado por id")
	    public ResponseEntity<Empleado> borrar(@PathVariable Integer idEmpleado){
			boolean result = empleadoSrv.borrarEmpleado(idEmpleado);
			if(result) return ResponseEntity.ok().build();

			return ResponseEntity.notFound().build();
	    }

	    @GetMapping(path = "/{idEmpleado}")
	    @ApiOperation(value = "Busca un empleado por id")
	    public ResponseEntity<Empleado> empleadoPorId(@PathVariable Integer idEmpleado){
			Empleado e = empleadoSrv.buscarPorId(idEmpleado);
			if(e != null)
				return ResponseEntity.ok(e);
			else
				return ResponseEntity.notFound().build();
	    }

	    @GetMapping(path = "/by-name/{nombre}")
	    @ApiOperation(value = "Busca un empleado por nombre")
	    public ResponseEntity<Empleado> empleadoPorNombre(@PathVariable String nombre){
			return ResponseEntity.ok(empleadoSrv.buscarPorNombre(nombre));
	    }

		@GetMapping()
		@ApiOperation(value = "Devuelve todos los empleados")
		public ResponseEntity<List<Empleado>> buscarTodos(){
			List<Empleado> empleados = empleadoSrv.buscarTodos();
			if(empleados.size()>0) return ResponseEntity.ok(empleados);
			return ResponseEntity.notFound().build();
	}
}
