package controllers;

import models.Ad;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.userPage;

import java.util.List;


public class Users extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result myPage(){
        String email =  session("email");
        User user = User.findByEmail(email);
        if (user == null){
            flash("error", "Please sign in.");
            return redirect(request().getHeader("referer"));
        }
        List<Ad> ads = user.ads;
        return ok(userPage.render(ads, user));
    }


}
