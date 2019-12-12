package com.hpe.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hpe.domain.User;

public interface UserService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void save(User user);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(User user);

	@DELETE
	public void delete(@QueryParam("id") Integer id);

	@GET
	@Path("findAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAll();

	@GET
	@Path("findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findById(@PathParam("id")Integer id);

}
