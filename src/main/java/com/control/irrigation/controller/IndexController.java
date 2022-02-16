package com.control.irrigation.controller;

import com.control.irrigation.model.Usuario;
import com.control.irrigation.repository.TelefoneRepository;
import com.control.irrigation.repository.UsuarioRepository;
import com.control.irrigation.service.ImplementacaoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

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
    public ResponseEntity<Page<Usuario>> usuario () throws InterruptedException {

        PageRequest page = PageRequest.of(0, 5, Sort.by("nome"));

        Page<Usuario> list = usuarioRepository.findAll(page);

        return new ResponseEntity<Page<Usuario>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/page/{pagina}", produces = "application/json")
    @CacheEvict(value = "cacheususer", allEntries = true)
    @CachePut("cacheusuar")
    public ResponseEntity<Page<Usuario>> usuarioPagina (@PathVariable("pagina") int pagina) throws InterruptedException {

        PageRequest page = PageRequest.of(pagina, 5, Sort.by("nome"));

        Page<Usuario> list = usuarioRepository.findAll(page);

        return new ResponseEntity<Page<Usuario>>(list, HttpStatus.OK);
    }

    /*END-POINT consulta de usuário por nome*/
    @GetMapping(value = "/usuarioPorNome/{nome}", produces = "application/json")
    @CachePut("cacheusuarios")
    public ResponseEntity<Page<Usuario>> usuarioPorNome (@PathVariable("nome") String nome) throws InterruptedException{

        PageRequest pageRequest = null;
        Page<Usuario> list = null;

        if (nome == null || (nome != null && nome.trim().isEmpty())
                || nome.equalsIgnoreCase("undefined")) {/*Nao informou o nome*/
            pageRequest = PageRequest.of(0, 5, Sort.by("nome"));
            list = usuarioRepository.findAll(pageRequest);
        }else {
            pageRequest = PageRequest.of(0, 5, Sort.by("nome"));
            list = usuarioRepository.findUserByNamePage(nome, pageRequest);
        }
        return new ResponseEntity<Page<Usuario>>(list, HttpStatus.OK);
    }

    /*END-POINT consulta de usuário por nome*/
    @GetMapping(value = "/usuarioPorNome/{nome}/page/{page}", produces = "application/json")
    @CachePut("cacheusuarios")
    public ResponseEntity<Page<Usuario>> usuarioPorNomePage (@PathVariable("nome") String nome,
                                                             @PathVariable("page") int page) throws InterruptedException{

        PageRequest pageRequest = null;
        Page<Usuario> list = null;

        if (nome == null || (nome != null && nome.trim().isEmpty())
                || nome.equalsIgnoreCase("undefined")) {/*Nao informou o nome*/
            pageRequest = PageRequest.of(page, 5, Sort.by("nome"));
            list = usuarioRepository.findAll(pageRequest);
        }else {
            pageRequest = PageRequest.of(page, 5, Sort.by("nome"));
            list = usuarioRepository.findUserByNamePage(nome, pageRequest);
        }
        return new ResponseEntity<Page<Usuario>>(list, HttpStatus.OK);
    }


    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){

        for (int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhacriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        //implementacaoUserDetailsService.insereAcessoPadrao(usuarioSalvo.getIdUsuario());

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

    @DeleteMapping(value = "/removerTelefone/{id}", produces = "application/json" )
    public String deleteTelefone(@PathVariable(value = "id") Long id){
        telefoneRepository.deleteById(id);
    return "ok";
    }
}
