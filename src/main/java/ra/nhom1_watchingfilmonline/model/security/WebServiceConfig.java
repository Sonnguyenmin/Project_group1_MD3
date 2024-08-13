package ra.nhom1_watchingfilmonline.model.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import ra.model3_project_springmvc.filter.EncodingFilter;
import ra.nhom1_watchingfilmonline.model.security.CustomerUserDetailService;


@Service
@Configuration
@EnableWebSecurity
public class WebServiceConfig {
    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new EncodingFilter(), ChannelProcessingFilter.class);
        http.authorizeHttpRequests((authorize)->
                authorize.requestMatchers("/index").permitAll()
                        .requestMatchers(HttpMethod.GET,"/register/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/register/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/main/**").hasAnyRole("USER","ADMIN")
//
        ).formLogin(
                form -> form.loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(((request, response, authentication) -> {
                            CustomerUserDetail customerUserDetail = (CustomerUserDetail) authentication.getPrincipal();
                            if(customerUserDetail.getAuthorities().stream().anyMatch(item -> item.getAuthority().equals("ADMIN"))){
                                response.sendRedirect("/loadAdmin");
                            }else {
                                response.sendRedirect("/loadUser");
                            }
                        })) // Xử lý thành công
                        .permitAll()
        ).logout(
                logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

}
