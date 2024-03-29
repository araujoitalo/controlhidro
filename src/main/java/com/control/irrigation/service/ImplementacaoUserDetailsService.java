package com.control.irrigation.service;

import com.control.irrigation.model.Usuario;
import com.control.irrigation.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*Consulta no banco o usuario*/
        Usuario usuario = usuarioRepository.findUserByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario nao foi encontrado");
        }
        return new User(usuario.getLogin(),
                        usuario.getPassword(),
                        usuario.getAuthorities());
    }

    /**public void insereAcessoPadrao(Integer idUsuario) {
        String constraint = usuarioRepository.consultaConstraintRole();
        usuarioRepository.removerConstraintRole(constraint);
        usuarioRepository.removerIndexRole(constraint);
        usuarioRepository.insereAcessoRolePadrao(idUsuario);
    }**/
}
