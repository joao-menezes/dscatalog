package com.joaomenezes.dscatalog.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.joaomenezes.dscatalog.dto.ProductDTO;
import com.joaomenezes.dscatalog.services.exceptions.DatabaseException;
import com.joaomenezes.dscatalog.services.exceptions.ResourceNotFoundException;
import org.hibernate.dialect.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaomenezes.dscatalog.dto.CategoryDTO;
import com.joaomenezes.dscatalog.entities.Category;
import com.joaomenezes.dscatalog.repositories.CategoryRepository;

import javax.persistence.EntityNotFoundException;

//essa annotation ira registrar a classe como um componente
//que ira participar do sistema de injecao de dependencia automatizado do spring
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	
	//trata como uma transacao no BD
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable){
		Page<Category> list = repository.findAll(pageable);
		return list.map(CategoryDTO::new); // == x -> new CategoryDTO(x)
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insertCategory(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);

		return new CategoryDTO(entity);
	}

	@Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);

		}catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Entity not found, id: " + id);
		}
    }

	public void deleteCategory(Long id) {
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Entity not found, id: " + id);
		}catch (DataIntegrityViolationException e){
			throw new DatabaseException("Integrity Violation" + e.getMessage());
		}
	}
}
