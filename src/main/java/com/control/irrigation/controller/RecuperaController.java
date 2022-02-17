package com.control.irrigation.controller;

import com.control.irrigation.ObjetoError;
import com.control.irrigation.model.Usuario;
import com.control.irrigation.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@RequestMapping(value = "/recuperar")
@RequiredArgsConstructor
public class RecuperaController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@ResponseBody
	@PostMapping(value="/")
	public ResponseEntity<Usuario> recuperar(@RequestBody Usuario login) throws Exception{

		ObjetoError objetoError = new ObjetoError();

		Usuario user = usuarioRepository.findUserByLogin(login.getLogin());

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}


}
