package controllers;

import Utils.Constants;
import Utils.Utilities;
import com.avaje.ebean.Page;
import com.google.common.io.Files;
import models.Ad;
import models.Car;
import models.Location;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.ads.adList;
import play.mvc.Security;
import java.io.IOException;
import java.util.Map;

public class Ads extends Controller {

    private static final Form<Ad> adForm = Form.form(Ad.class);

    public static Result index() {
        return redirect(routes.Ads.list(0));
    }

    public static Result list(int page) {
        Page<Ad> ads = Ad.find(page);
        return ok(adList.render(ads));
    }

    @Security.Authenticated(Secured.class)
    public static Result newAd() {
        return ok(views.html.ads.details.render(adForm));
    }

    @Security.Authenticated(Secured.class)
    public static Result details(Long id) {
        Ad ad = Ad.findByID(id);
        if (ad == null) {
            flash("error", "Advertisement does not exist. ");
            return redirect(request().getHeader("referer"));
        }
        Form<Ad> filledForm = adForm.fill(ad);
        return ok(views.html.ads.details.render(filledForm));
    }

    @Security.Authenticated(Secured.class)
    public static Result save() {
        Form<Ad> boundForm = adForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(views.html.ads.details.render(boundForm));
        }

        Ad ad = boundForm.get();

        Car car = Car.findByID(ad.car.id);
        if (car == null){
            flash("error", "Please correct the form below.");
            return badRequest(views.html.ads.details.render(boundForm));
        }
        ad.car = car;

        Location location = Location.findByID(ad.location.id);
        if (location == null){
            flash("error", "Please correct the form below.");
            return badRequest(views.html.ads.details.render(boundForm));
        }
        ad.location = location;
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("picture");
        if (filePart != null){
            try {
                ad.setPicture(Files.toByteArray(filePart.getFile()));
            } catch (IOException e) {
                flash("error", "Error reading file.");
                return redirect(request().getHeader("referer"));
            }
        }
        if (ad.id == null) {
            ad.save();
            flash("success",
                    String.format("Successfully added."));
        } else {
            ad.update();
            flash("success",
                    String.format("Successfully updated."));
        }
        return redirect(routes.Ads.list(0));
    }

    @Security.Authenticated(Secured.class)
    public static Result delete(Long id){
        Ad ad = Ad.findByID(id);
        if (ad == null){
            flash("error", "Advertisement not found.");
            return redirect(request().getHeader("referer"));
        }
        ad.car = null;
        ad.location = null;
        ad.delete();
        return ok();
    }

    @Security.Authenticated(Secured.class)
    public static Result uploadPicture() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("picture");
        Map<String, String[]> values = body.asFormUrlEncoded();
        String id = values.get("id")[0];
        if (id != null && id != ""){
            if (filePart != null) {
                String fileName = filePart.getFilename();
                String contentType = filePart.getContentType();
                if (contentType.toLowerCase().contains("image/png") ||
                        contentType.toLowerCase().contains("image/jpeg")) {
                    try {
                        Ad ad = Ad.findByID(Long.parseLong(id, 10));
                        byte[] picture = Files.toByteArray(filePart.getFile());
                        if (picture.length/1000 > Constants.MAX_PICTURE_SIZE){
                            flash("error", "Image must be smaller than " + Constants.MAX_PICTURE_SIZE/10 + "KB");
                            return redirect(request().getHeader("referer"));
                        }
                        ad.setPicture(Utilities.scale(picture, 600));
                        ad.update();
                        flash("success",
                                String.format("Successfully updated the picture %s", fileName));
                    } catch (IOException e) {
                        return internalServerError("Error reading file upload");
                    }
                    return redirect(request().getHeader("referer"));
                }else{
                    flash("error", "Please choose a JPEG or PNG image.");
                    return redirect(request().getHeader("referer"));
                }

            } else {
                flash("error", "Missing file");
                return redirect(request().getHeader("referer"));
            }
        }else {
            flash("error", "Advertisement type not found");
            return redirect(request().getHeader("referer"));
        }
    }

    public static Result picture(Long id) {
        final Ad ad = Ad.findByID(id);
        if (ad == null){
            return notFound();
        }
        return ok(ad.getPicture());
    }

    public static Result pictureLarge(Long id) {
        final Ad ad = Ad.findByID(id);
        if (ad == null){
            return notFound();
        }
        return ok(views.html.ads.largePicture.render(ad));
    }


}
