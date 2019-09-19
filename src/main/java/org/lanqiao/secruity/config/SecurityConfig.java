package org.lanqiao.secruity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定义权限规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //使用用户认证
        http.formLogin().usernameParameter("username").passwordParameter("password").loginPage("/userlogin");
        //记住我
        http.rememberMe().rememberMeParameter("rememberMe");
        //注销
        http.logout().logoutSuccessUrl("/");
    }
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .withUser("admin1").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP2")
                    .and()
                    .withUser("admin2").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP2","VIP3")
                    .and()
                    .withUser("admin3").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP3","VIP1");

    }
}
