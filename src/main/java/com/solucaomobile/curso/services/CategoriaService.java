package com.solucaomobile.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.solucaomobile.curso.domain.Categoria;
import com.solucaomobile.curso.dto.CategoriaDTO;
import com.solucaomobile.curso.repositories.CategoriaRepository;
import com.solucaomobile.curso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	private PageRequest PageRequest;
	private Object objDto;
	
	public Categoria find(Integer id) {  
		Optional<Categoria> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id +
				", Tipo: " + Categoria.class.getName())); 
	}

	public Categoria buscar(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria  newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void deleteById(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não e possivel excluir uma categoria que possui produto");
			
		}
		
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}

	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest = org.springframework.data.domain.PageRequest.of (page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(PageRequest);
		
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
		
	}

	public Object getObjDto() {
		return objDto;
	}

	public void setObjDto(Object objDto) {
		this.objDto = objDto;
	}
	
	private void updateData(Categoria newObj,Categoria obj) {
		newObj.setNome(obj.getNome());
		
	}
}
