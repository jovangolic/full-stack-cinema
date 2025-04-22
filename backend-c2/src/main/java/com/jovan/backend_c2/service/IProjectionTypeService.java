package com.jovan.backend_c2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.jovan.backend_c2.model.ProjectionType;

public interface IProjectionTypeService {

	ProjectionType createProjectionType(String name) throws SQLException, IOException;
	Optional<ProjectionType> getProjectionTypeById(Long id);
	ProjectionType findProjectionTypeByName(String name);
	List<ProjectionType> getAllTypes();
}

