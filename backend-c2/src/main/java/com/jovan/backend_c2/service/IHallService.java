package com.jovan.backend_c2.service;

import java.sql.SQLException;
import java.util.List;

import com.jovan.backend_c2.model.Hall;

import io.jsonwebtoken.io.IOException;


public interface IHallService {

	Hall createHall(String name) throws IOException, SQLException;
	List<Hall> getAllHalls();
}
