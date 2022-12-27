package com.ailtonluiz.sgs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ailtonluiz.sgs.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/layout/**")
				.antMatchers("/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/usuarios/novo").hasRole("CADASTRAR_USUARIO")
				.antMatchers("/produtos/novo").hasRole("CADASTRAR_PRODUTO")
				.antMatchers("/compras/**").hasRole("CADASTRAR_COMPRA")
				.antMatchers("/vendas/novo").hasRole("CADASTRAR_VENDA")
				.antMatchers("/gruposProdutos/**").hasRole("CADASTRAR_GRUPO")
				.antMatchers("/marcas/**").hasRole("CADASTRAR_MARCA")
				.antMatchers("/estoques/**").hasRole("CADASTRAR_ESTOQUE")
				.antMatchers("/relatorios/**").hasRole("ACESSAR_RELATORIO")
				.antMatchers("/parametros/**").hasRole("ACESSAR_PARAMETRO")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.sessionManagement()
				 .invalidSessionUrl("/login")
			     .maximumSessions(1)
			     .expiredUrl("/login");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}