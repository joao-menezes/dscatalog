package com.joaomenezes.dscatalog.resources;

import java.net.URI;
import java.util.List;

import com.joaomenezes.dscatalog.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.joaomenezes.dscatalog.dto.CategoryDTO;
import com.joaomenezes.dscatalog.services.CategoryService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//o resource implementa o controller REST

//o annotation serve para configurar algo no codigo de 
//uma forma enxuta
//utiliza algo q já está implementado

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	//injetar automaticamente a dependencia
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> insertCategory(@RequestBody CategoryDTO dto) {
		dto = service.insertCategory(dto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		//repsonse with code 201
		return ResponseEntity.created(uri).body(dto);
	}


}
