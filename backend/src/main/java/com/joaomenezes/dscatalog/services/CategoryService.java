package com.joaomenezes.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaomenezes.dscatalog.dto.CategoryDTO;
import com.joaomenezes.dscatalog.entities.Category;
import com.joaomenezes.dscatalog.repositories.CategoryRepository;

//essa annotation ira registrar a classe como um componente
//que ira participar do sistema de injecao de dependencia automatizado so spring
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	
	//trata como uma transacao no BD
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

}
