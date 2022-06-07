package com.joaomenezes.dscatalog.services;


import com.joaomenezes.dscatalog.dto.ProductDTO;
import com.joaomenezes.dscatalog.dto.ProductDTO;
import com.joaomenezes.dscatalog.entities.Product;
import com.joaomenezes.dscatalog.repositories.CategoryRepository;
import com.joaomenezes.dscatalog.repositories.ProductRepository;
import com.joaomenezes.dscatalog.services.exceptions.DatabaseException;
import com.joaomenezes.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

//essa annotation ira registrar a classe como um componente
//que ira participar do sistema de injecao de dependencia automatizado do spring
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	
	//trata como uma transacao no BD
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = repository.findAll(pageRequest);
		//esta retornando um lista de Product a convertendo em um ProductDTO
		//return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		return list.map(ProductDTO::new);
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insertProduct(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = repository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getById(id);
			//entity.setName(dto.getName());
			entity = repository.save(entity);
			return new ProductDTO(entity);

		}catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Entity not found, id: " + id);
		}
    }

	public void deleteProduct(Long id) {
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Entity not found, id: " + id);
		}catch (DataIntegrityViolationException e){
			throw new DatabaseException("Integrity Violation" + e.getMessage());
		}
	}

}
