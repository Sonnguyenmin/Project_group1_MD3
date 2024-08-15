package ra.nhom1_watchingfilmonline.model.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.filter.EncodingFilter;


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

                authorize
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/main/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/film/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
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
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        })) // Xử lý thành công
                        .permitAll()
        ).logout(
                logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .permitAll())
                .csrf().and(); // Nếu bạn đã cấu hình CSRF trong các form, thì cần bật CSRF
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

}
