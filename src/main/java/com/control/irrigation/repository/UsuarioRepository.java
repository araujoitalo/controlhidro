package com.control.irrigation.repository;

import com.control.irrigation.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    @Query("select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);


}
