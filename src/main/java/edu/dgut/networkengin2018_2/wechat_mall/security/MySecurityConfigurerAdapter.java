package edu.dgut.networkengin2018_2.wechat_mall.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class MySecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserSerivice userSerivice;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("root").password("{noop}123456").roles("ADMIN");
        auth.userDetailsService(userSerivice);
    }

    /**
     * 忽略静态资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/admin/dist/**", "/admin/plugins/**").antMatchers("/wechatapi/**");

    }


    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAnyRole("ADMIN","USER")
                    .antMatchers("/admin/myuser/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated() /*用户登录后能访问*/
                    .and()
                .headers().frameOptions().sameOrigin().and()
                .formLogin()
                .loginPage("/admin/login").permitAll()
                .defaultSuccessUrl("/admin").and()
                .logout()
                .logoutUrl("/admin/logout").permitAll()
                .logoutSuccessUrl("/admin/login?logout");
/*
        http
                .antMatcher("/admin/**")
                .authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER").and()
                .formLogin()
                .loginPage("/admin/login").permitAll()
                .defaultSuccessUrl("/admin").and()
                .logout()
                .logoutUrl("/admin/logout").permitAll()
                .logoutSuccessUrl("/admin/login?logout");
*/
    }

}