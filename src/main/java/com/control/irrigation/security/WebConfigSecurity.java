package com.control.irrigation.security;

import com.control.irrigation.service.ImplementacaoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*Mapeia URL, endereços, autoriza ou bloqueia acessos a URL*/
@Configuration
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

    /*Configura as solicitaçoes de acesso por Http*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*Ativando a proteçao contra usuario que nao estao validados por TOKEN*/
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        /*Ativando a permissao para acesso a pagina inicial do sistema.*/
        .disable().authorizeRequests().antMatchers("/").permitAll()
        .antMatchers("/index").permitAll()

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

        /*URL de Logout - Redireciona apos o usuario deslogar do sistema*/
        .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

        /*Mapeia URL de Logout e invalida o Usuario*/
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

        /*Filtra requisiçoes de login para autenticaçao*/
        .and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)

        /*Filtra demais requisiçoes para verificar a presença do TOKEN JWT no HEADER HTTP*/
        .addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*Service que ira consultar o usuario no banco de dados*/
        auth.userDetailsService(implementacaoUserDetailsService)

        /*Padrao de codificaçao de senha*/
        .passwordEncoder(new BCryptPasswordEncoder());
    }
}
