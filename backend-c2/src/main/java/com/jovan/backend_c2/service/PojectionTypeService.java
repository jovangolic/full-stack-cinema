package com.jovan.backend_c2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jovan.backend_c2.model.ProjectionType;
import com.jovan.backend_c2.repository.ProjectionTypeRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PojectionTypeService implements IProjectionTypeService {
	
	
	private final ProjectionTypeRepository typeRepository;

	@Override
	public ProjectionType createProjectionType(String name) throws SQLException, IOException {
		if(!name.equals("2D") && !name.equals("3D") && !name.equals("4D")) {
			throw new IllegalArgumentException("Invalid projection type name: " + name);
		}
		ProjectionType type = new ProjectionType();
		type.setName(name);
		return typeRepository.save(type);
	}

	@Override
	public Optional<ProjectionType> getProjectionTypeById(Long id) {
		return Optional.of(typeRepository.findById(id).get());
	}

	@Override
	public List<ProjectionType> getAllTypes() {
		return typeRepository.findAll();
	}

	@Override
	public ProjectionType findProjectionTypeByName(String name) {
		return typeRepository.findProjectionTypeByName(name);
	}
}
