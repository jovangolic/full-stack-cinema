package com.jovan.backend_c2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jovan.backend_c2.model.ProjectionType;



@Repository
public interface ProjectionTypeRepository extends JpaRepository<ProjectionType, Long> {

	
	@Query("SELECT DISTINCT pt.name FROM ProjectionType pt ")
	List<String> findDistinctProjectionTypes();
	
	ProjectionType findProjectionTypeByName(String name);

}
