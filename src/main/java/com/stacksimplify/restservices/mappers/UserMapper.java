package com.stacksimplify.restservices.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.stacksimplify.restservices.dtos.UserMsDto;
import com.stacksimplify.restservices.entities.Users;

@Mapper(componentModel="Spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	//User to UserMsDto	
	@Mapping(source="email", target="emailAddress")
	@Mapping(source="role", target="rolename")
	UserMsDto userToUserMsDto(Users user);
	
	//List<User> to List<UserMsDto>
	List<UserMsDto> usersToUserDtos(List<Users> users); 
}
