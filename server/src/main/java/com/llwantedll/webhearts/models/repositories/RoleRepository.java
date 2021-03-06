package com.llwantedll.webhearts.models.repositories;

import com.llwantedll.webhearts.models.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role getByKey(String name);
}
