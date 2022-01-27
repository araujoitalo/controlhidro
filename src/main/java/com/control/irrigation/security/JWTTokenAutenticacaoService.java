package com.control.irrigation.security;

import com.control.irrigation.ApplicationContextLoad;
import com.control.irrigation.model.Usuario;
import com.control.irrigation.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JWTTokenAutenticacaoService {

    /*Tempo de validade do Token 2 dias*/
    private static final long EXPIRATION_TIME = 172800000;

    /*Uma senha unica para compor a autenticacao*/
    private static final String SECRET = "*SenhaExtremamenteSecreta";

    private  static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    /*Gerando token de autenticacao e adicionando ao cabecalho e resposta Http*/
    public void addAuthentication(HttpServletResponse response, String username) throws IOException {

        /*Mostragem do Token*/
        String JWT = Jwts.builder() /*Chama o gerador de Token*/
                        .setSubject(username) /*Adiciona o usuario*/
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*Tempo de Expiraçao*/
                        .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /*Compacataçao e algoritimo de geraçao de senha*/

        /*Junta o token com o prefixo*/
        String token = TOKEN_PREFIX + " " + JWT; /*Bearer vb45bty3vtn65vy34ytvb5t43ivb5656*/

        /*Adiona no cabeçalho http*/
        response.addHeader(HEADER_STRING, token); /*Authorization: Bearer vb45bty3vtn65vy34ytvb5t43ivb5656*/

        /*Liberando resposta para portas difernetes que usam a API ou caso clientes WEB*/
        response.addHeader("Access-Control-Allow-Origin", "*");
        liberacaoCors(response);

        /*Escreve token como resposta no corpo http*/
        response.getWriter().write("{\"Authorization\": \""+token+"\"}");
    }

    /*Retorna o usuario validado com token ou caso nao seja valido retorna null*/
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        /*Pega o token enviado no cabeçalho http*/
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            /*Faz a validaçao do token do usuario na requisicao*/
            String user = Jwts.parser().setSigningKey(SECRET) /*Bearer vb45bty3vtn65vy34ytvb5t43ivb5656*/
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")) /*vb45bty3vtn65vy34ytvb5t43ivb5656*/
                    .getBody().getSubject(); /*Italo Araujo*/

            if (user != null) {

                Usuario usuario = ApplicationContextLoad.getApplicationContext()
                                .getBean(UsuarioRepository.class).findUserByLogin(user);

                /*Retorna o usuario logado*/
                if (usuario != null) {

                    return new UsernamePasswordAuthenticationToken(
                            usuario.getLogin(),
                            usuario.getSenha(),
                            usuario.getAuthorities());
                }
            }
        }
        liberacaoCors(response);
        return null; /*Nao autorizado*/
    }

    private void liberacaoCors(HttpServletResponse response) {

        if (response.getHeader("Access-Control-Allow-Origin") == null){
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null){
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null){
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null){
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}


