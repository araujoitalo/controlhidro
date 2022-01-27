package com.control.irrigation.controller;

import com.control.irrigation.model.Usuario;
import com.control.irrigation.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;

@RestController /*Arquitetura REST*/
@RequestMapping(value = "/usuario")
public class IndexController {

    @Autowired /*se fosse CDI seria @Inject*/
    private UsuarioRepository usuarioRepository;

    /*Serviço RESTful*/
    @GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
    public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Integer id
                                                ,@PathVariable(value = "venda") Long venda){

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity(usuario.get(), HttpStatus.OK);
    }

    /*Serviço RESTful*/
    @GetMapping(value = "/{id}", produces = "application/json")
    @CacheEvict(value = "cacheusuarios", allEntries = true)
    @CachePut("cacheusuarios")
    public ResponseEntity<Usuario> init(@PathVariable(value = "id") Integer id){

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity(usuario.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheususer", allEntries = true)
    @CachePut("cacheusuar")
    public ResponseEntity<List<Usuario>> usuario (){
        List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

    /*END-POINT consulta de usuário por nome*/
    @GetMapping(value = "/usuarioPorNome/{nome}", produces = "application/json")
    @CachePut("cacheusuarios")
    public ResponseEntity<List<Usuario>> usuarioPorNome (@PathVariable("nome") String nome) throws InterruptedException{

        List<Usuario> list = (List<Usuario>) usuarioRepository.findUserByNome(nome);

        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }


    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){

        for (int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhacriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

    }

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){

        for (int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        Usuario userTemporario = usuarioRepository.findById(usuario.getIdUsuario()).get();

        if (!userTemporario.getSenha().equals(usuario.getSenha())) { /*Senhas diferentes*/
            String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
            usuario.setSenha(senhacriptografada);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public String delete(@PathVariable(value = "id") Integer id){

       usuarioRepository.deleteById(id);

        return "ok";
    }

    @PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
    public ResponseEntity<Usuario> cadastrarvenda(@PathVariable Long iduser,
                                                  @PathVariable Long idvenda){

        return new ResponseEntity("id user:" + iduser + "id venda:" + idvenda, HttpStatus.OK);

    }
}
