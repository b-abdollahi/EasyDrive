package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Behzad on 6/9/2015.
 */
@Entity
public class Location extends Model {


    @Id
    public Long id;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String city;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String state;

    public static play.db.ebean.Model.Finder<Long, Location> find = new play.db.ebean.Model.Finder<>(Long.class, Location.class);

    public static List<Location> allLocations(){
        return find.all();
    }

    public Location(){

    }


    public static Map<String, String> allLocationsAsOptions(){
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
        for (Location location : Location.allLocations()) {
            options.put(location.id.toString(), location.toString());
        }
        return options;
    }

    @Override
    public String toString() {
        return state + ": " + city;
    }
}
