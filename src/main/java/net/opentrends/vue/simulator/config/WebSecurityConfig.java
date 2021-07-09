package net.opentrends.vue.simulator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
	private final UserDetailsService userDetailsService;
	
	public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder,
			CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler, UserDetailsService userDetailsService) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.customizeAuthenticationSuccessHandler = customizeAuthenticationSuccessHandler;
		this.userDetailsService = userDetailsService;
	}		
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService uds = userDetailsService;
		auth.userDetailsService(uds).passwordEncoder(bCryptPasswordEncoder);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/signup").permitAll()
            .antMatchers("/dashboard/**").hasAuthority("ADMIN")
            .antMatchers("/simulator/**").hasAuthority("ADMIN")
            .antMatchers("/api/vue/simulator/**").hasAuthority("API")
            .anyRequest().authenticated()
            .and().csrf().disable()
            .headers().frameOptions().disable()
            .and().httpBasic()
            .and().formLogin()
            	.successHandler(customizeAuthenticationSuccessHandler)
            	.loginPage("/login")
            	.failureUrl("/login?error=true")
            	.usernameParameter("email")
            	.passwordParameter("password")
            	.and()
            .logout()
            	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            	.logoutSuccessUrl("/login");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**", "/styles/**");
	}
}
