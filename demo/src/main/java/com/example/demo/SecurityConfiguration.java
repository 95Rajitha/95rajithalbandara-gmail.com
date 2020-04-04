package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity  // DO this when you are doing configuration in web security in Spring
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // super.configure(auth); remove this to avoid calling the parent class and do my own implementation
        // you can set the configuration to the auth object

        auth.inMemoryAuthentication() //setting the type of authentication that we need as  an in memory auth and provide the user password and roles in method chaining
                    .withUser("User")
                    .password("1234")
                    .roles("USER") // since we are not doing a role based authorization so that we can only use this as "USER" one way; up to this point we only have one user
                    .and()     // from this onwards we can have more users by chainning and and method returns the same object which inMemmoryAuhthentication() method returns
                    .withUser("Admin")
                    .password("1234")
                    .roles("ADMIN");

    }

    // setting a password encoder using @Beans

    @Bean  // creating a bean of type passwordEncoder and expose to the spring security. and this make the return of the method spring bean
    public PasswordEncoder getPasswordEncoder(){

        //spring security is looking for any beans as passwordEncoder and it is used for password encoding
        // here we can return any encoder type for this practical we only return noOpPasswordEncoder.getInstance()
            return NoOpPasswordEncoder.getInstance();
        //NoOpPasswordEncoder basically a password encoder which does nothing
        // actually we are using this ti avoid spring boot to encode the password so that we can still use the plain txt password
        // this depricated because noOpPassword is deplicated as its a warning that this is not something that a developer should us in the production
        // we have satisfied the spring security requirement to encode the password

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); remove this as to customize as in your way


//        http.authorizeRequests()  // you specify the mapping from path to role
//                .antMatchers("/**")//this method let us configure the what path should be mapped by Using WILD CARDS as "antMatchers("/**")" it sepecify all paths in the current level and any nested levels below this
//                .hasRole("ADMIN") // role is specified for the Path specified above incase if you need to specify several roles so that you can make it by adding hasAnyRole("USER","ADMIN") so that it will allow only for these people to access but not the other group of roles apart from this
//                .and()           // close the method chaining
//                .formLogin();      // specify the type of the logging formloggin type its a popular choice its the default in spring security



        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN") // to allow this URL to access by the ADMIN and the USER itself
                .antMatchers("/").permitAll()
                .and().formLogin();

        // how to do the most typical way Role=user --> /user Role=admin --> /admin
        // this is done typically most restrictive way to the least restrictive way
        // that mean from admin URL to the user URL what if we put the mapping
        // from user to the admin




//       http.authorizeRequests()
//                        .antMatchers("/","static/css","static/js") // allow static assets
//                        .permitAll(); // use this method to give access to any kind of users to allow access; free for all anybody can use this; Public URLS



    }
}
