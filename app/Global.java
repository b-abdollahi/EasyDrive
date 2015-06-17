import com.avaje.ebean.Ebean;
import models.Car;
import models.Location;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.data.format.Formats;
import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;
import play.filters.csrf.CSRFFilter;
import play.libs.Yaml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Global extends GlobalSettings {

    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        //Class[] filters={CSRFFilter.class,BasicAuthenticationFilter.class};
        Class[] filters={CSRFFilter.class};
        return filters;
    }

    public void onStart(Application app) {
        // Register our DateFormater
        Formatters.register(Date.class,
                new SimpleFormatter<Date>() {

                    private final static String PATTERN = "dd-MM-yyyy";

                    public Date parse(String text, Locale locale)
                            throws ParseException {
                        if (text == null || text.trim().isEmpty()) {
                            return null;
                        }
                        SimpleDateFormat sdf =
                                new SimpleDateFormat(PATTERN, locale);
                        sdf.setLenient(false);
                        return sdf.parse(text);
                    }

                    public String print(Date value, Locale locale) {
                        if (value == null) {
                            return "";
                        }
                        return new SimpleDateFormat(PATTERN, locale)
                                .format(value);
                    }

                });
        Formatters.register(Date.class, new Formats.AnnotationDateFormatter());
        InitialData.insert(app);
    }

//    public Promise<Result> onError(RequestHeader request, Throwable t) {
//        return Promise.<Result>pure(internalServerError(
//                views.html.errorPage.render(t)
//        ));
//    }

    static class InitialData {

        public static void insert(Application app) {
            if(Ebean.find(Car.class).findRowCount() == 0) {

                Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data-cars.yml");

                Ebean.save(all.get("cars"));

            }
            if(Ebean.find(Location.class).findRowCount() == 0) {

                Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data-locations.yml");

                Ebean.save(all.get("locations"));

            }
        }

    }
}