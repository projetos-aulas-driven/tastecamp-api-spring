package com.tastecamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tastecamp.api.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

}
