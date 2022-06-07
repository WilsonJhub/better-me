package com.develop.betterme;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BetterMeController {

    // Autowire repo
    @Autowired
    SiteUserRepo siteUserRepo;

    // <--- get route to /login --->
    @GetMapping("/login")
    public String getLoginPage() {return "/login";}

    // <--- post route /login --->
    @PostMapping ("/login")
    public RedirectView logInUser(String username, String password){

        // compare given credentials to the credentials in the database
        // plaintext password
        SiteUser userFromDB = siteUserRepo.findByUsername(username);
        if((userFromDB == null) || (!BCrypt.checkpw(password, userFromDB.password))){
            return new RedirectView("/login");
        }
        return new RedirectView("/");
    }

    // <--- get route to signup --->
    @GetMapping("/signup")
    public String getSignUpPage() {return "/signup";}

    // post route to signup
    @PostMapping("/signup")
    public RedirectView signUpUser(Model m, String username, String password){
        //bcrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(9));

        SiteUser newSiteUser = new SiteUser(username, hashedPassword);
        siteUserRepo.save(newSiteUser);

        return new RedirectView("/login");
    }
}
