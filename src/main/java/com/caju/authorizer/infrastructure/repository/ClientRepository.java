package com.caju.authorizer.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.caju.authorizer.infrastructure.entity.Client;

public interface ClientRepository  extends JpaRepository<Client, Integer>{
}

