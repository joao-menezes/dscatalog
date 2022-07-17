package com.joaomenezes.dscatalog.resources;

import com.joaomenezes.dscatalog.dto.ProductDTO;
import com.joaomenezes.dscatalog.services.CategoryService;
import com.joaomenezes.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//o resource implementa o controller REST

//o annotation serve para configurar algo no codigo de 
//uma forma enxuta
//utiliza algo q já está implementado

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	//injetar automaticamente a dependencia
	@Autowired
	private ProductService service;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
		Page<ProductDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	//insert new category
	@PostMapping
	public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO dto) {
		dto = service.insertProduct(dto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		//repsonse with code 201
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
		dto = service.updateProduct(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		service.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

}
