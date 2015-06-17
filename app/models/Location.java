package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Location extends Model {


    @Id
    public Long id;

    @Constraints.MaxLength(50)
    public String city;

    @Constraints.MaxLength(50)
    public String state;

    public static play.db.ebean.Model.Finder<Long, Location> find = new play.db.ebean.Model.Finder<>(Long.class, Location.class);

    public static List<Location> allLocations(){
        return find.all();
    }

    public Location(){

    }

    public static Location findByID(Long id) {
        return find.where().eq("id", id).findUnique();
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
