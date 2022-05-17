package com.joaomenezes.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaomenezes.dscatalog.entities.Category;
import com.joaomenezes.dscatalog.repositories.CategoryRepository;

//essa annotation ira registrar a classe como um componente
//que ira participar do sistema de injecao de dependencia automatizado so spring
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll(){
		return repository.findAll();
	}

}
