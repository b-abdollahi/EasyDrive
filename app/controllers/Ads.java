package controllers;

import com.avaje.ebean.Page;
import models.Ad;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ads.adList;

/**
 * Created by Behzad on 6/9/2015.
 */
public class Ads extends Controller {

    private static final Form<Ad> adForm = Form.form(Ad.class);

    public static Result index() {
        return redirect(routes.Ads.list(0));
    }

    public static Result list(int page) {
        Page<Ad> ads = Ad.find(page);
        return ok(adList.render(ads));
    }

    public static Result newAd() {
        return ok(views.html.ads.details.render(adForm));
    }

    public static Result details(Long id) {
        Ad ad = Ad.findByID(id);
        if (ad == null) {
            flash("error", "Advertisement does not exist. ");
            return redirect(request().getHeader("referer"));
        }
        Form<Ad> filledForm = adForm.fill(ad);
        return ok(views.html.ads.details.render(filledForm));
    }

    public static Result save() {
        Form<Ad> boundForm = adForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(views.html.ads.details.render(boundForm));
        }

        Ad ad = boundForm.get();
        // TODO: saving logic should be implemented here

        return ok();
    }

    public static Result delete(Long id){
        Ad ad = Ad.findByID(id);
        if (ad == null){
            flash("error", "Advertisement not found.");
            return redirect(request().getHeader("referer"));
        }

        // TODO: delete connected objects before deleting
        ad.delete();
        return redirect(routes.Ads.list(0));
    }


}
