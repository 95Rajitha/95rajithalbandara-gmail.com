package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    // access by the role of any user
@RequestMapping("/")     // this can be written as same as GetMapping("/") which is a composed annotation for
    public String home(){
        return "<h1> hellow </h1>";
    }

   // following mappings goes with the authorization theories since we are going to set three type of API for each to any user who is registered or not
   // and the other role is registered users and the other roll is for admin and they are directed to different APIs as per the roles

    // access by the role of registered users
@GetMapping("/user")
    public String user(){

    return "<h1> hellow user </h1>";
    }

    // access by the role of the admin
@GetMapping("/admin")
    public String admin(){

    return "<h1> hellow admin</h1>";
    }


    // role base authorization is typical in most applications...
    // so we are going to address a number of use cases here
    //                 THE WAY TO THIS
    //          using an object of type HTTP
    // http security lets you configure what are the paths
    // and what are the access restrictions for the paths
    // how you get access to this object on which you put your authorization configuration
    //its similar as you did it in the SecurityConfiguration class to access the Authentication object to manage authentication
    //     >>> extend the "WebSecurityConfigurerAdapter" class and override the "configure(HttpSecurity)" method which is a overloaded method in the same parent class
    //

}
