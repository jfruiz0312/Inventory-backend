package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponse;
import com.company.inventory.response.CategoryResponseRest;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryDao categoryDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
	    CategoryResponseRest response = new CategoryResponseRest();
	    try {
	        List<Category> categories = (List<Category>) categoryDao.findAll();
	        
	        // Validar si hay categorías
	        if (categories.isEmpty()) {
	            response.setMetadata("No hay datos", "01", "No se encontraron categorías");
	            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
	        }
	        
	        // Inicializar el objeto de respuesta si es necesario
	        if (response.getCategoryResponse() == null) {
	            response.setCategoryResponse(new CategoryResponse ());
	        }
	        
	        response.getCategoryResponse().setCategory(categories);
	        response.setMetadata("Éxito", "00", "Categorías consultadas correctamente");
	        
	        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	        
	    } catch (Exception e) {
	        response.setMetadata("Error", "-1", "Error interno al consultar categorías: " + e.getMessage());
	        e.printStackTrace();
	        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> lista = new ArrayList<>();
		try {
			Optional<Category> category = categoryDao.findById(id);

			// Validar si hay categorías
			if (category.isPresent()) {
				lista.add(category.get());
				response.getCategoryResponse().setCategory(lista);
				response.setMetadata("Éxito", "00", "Categoría consultada correctamente");

			} else {

				response.setMetadata("No hay datos", "01", "No se encontro categoría por ID :" + id );
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMetadata("Error", "-1", "Error interno al consultar categorías: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
