package controllers;

import models.Ad;
import models.User;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import views.html.ads.searchResult;

import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    public static class Login {
        public String email;
        public String password;
    }

    public static class Register {
        public String email;
        public String password;
        public String tel;
        public String name;
    }

    public static Result login() {
        return ok( login.render(form(Login.class)) );
    }

    public static Result newUser() {
        return ok(register.render(form(Register.class)));
    }

    public static Result register(){
        Form<Register> registerForm = form(Register.class)
                .bindFromRequest();
        String email = registerForm.get().email;
        String name = registerForm.get().name;
        String tel = registerForm.get().tel;
        String password = registerForm.get().password;
        if (email.equals("") || name.equals("") || tel.equals("") ||password.equals("")){
            flash("error", "Please correct the form below.");
            return badRequest(register.render(registerForm));
        }

        if (User.isRegistered(email)){
            flash("error", "This Email address is already a user! please sign in.");
            return badRequest(register.render(registerForm));
        }
        User newUser = new User(email,password, name,tel);
        newUser.save();
        session().clear();
        session("email", email);
        return redirect(routes.Ads.index());
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class)
                .bindFromRequest();
        String email = loginForm.get().email;
        String password = loginForm.get().password;
        if (User.authenticate(email, password) == null){
            flash("error", "Invalid Email/password!");
            return badRequest(login.render(loginForm));
        }
        session().clear();
        session("email", email);
        return redirect(routes.Ads.index());
    }

    public static Result logout() {
        session().clear();
        return redirect(routes.Ads.index());
    }

    public static Result search(String keyword) {
        if (keyword.equals("")){
            flash("error", "What do you like to search for?!");
            return redirect(request().getHeader("referer"));
        }
        List<Ad> results = Ad.search(keyword);
        return ok(searchResult.render(results, keyword));
    }



}
