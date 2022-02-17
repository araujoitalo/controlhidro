package com.control.irrigation.repository;

import com.control.irrigation.model.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.transaction.Transactional;
import java.util.List;

@EnableJpaRepositories(basePackages="com.control.irrigation.repository", entityManagerFactoryRef="emf")
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

    @Query("select u from Usuario u where u.nome like %?1%")
    List<Usuario> findUserByNome(String nome);

    @Query(value = "select CONSTRAINT_NAME from INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
            "where table_name = 'USUARIOS_ROLE' and column_name = 'role_id' " +
            "and constraint_name <> 'unique_role_user';", nativeQuery = true)
    String consultaConstraintRole();

    @Modifying
    @Query(value = "alter table USUARIOS_ROLE drop FOREIGN KEY ?1;", nativeQuery = true)
    void removerConstraintRole (String constraint);

    @Modifying
    @Query(value = "alter table USUARIOS_ROLE drop INDEX ?1;", nativeQuery = true)
    void removerIndexRole (String constraint);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_role (usuario_id, role_id) values(?1, " +
            "(select id from role where nome_role = 'ROLE_USER')); ")
    void insereAcessoRolePadrao(Integer idUser);


    default Page<Usuario> findUserByNamePage(String nome, PageRequest pageRequest) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);

        /*Configurando para pesquisar por nome e paginaçao*/
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers
                        .contains().ignoreCase());

        Example<Usuario> example = Example.of(usuario, exampleMatcher);

        Page<Usuario> retorno = findAll(example, pageRequest);

        return retorno;
    }
}
