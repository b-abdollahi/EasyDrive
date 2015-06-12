package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Behzad on 6/9/2015.
 */
@Entity
public class Car extends Model implements PathBindable<Car> {

    @Id
    public Long id;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String manufacturer;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String model;

    @OneToMany(mappedBy = "car")
    public Ad ad;


    @Constraints.Required
    @Constraints.MaxLength(2000)
    public String description;

    public static play.db.ebean.Model.Finder<Long, Car> find = new play.db.ebean.Model.Finder<>(Long.class, Car.class);

    public static Car findByID(Long id) {
        return find.where().eq("id", id).findUnique();
    }


    public static List<Car> allCars(){
        return find.all();
    }


    public static Map<String, String> allCarsAsOptions(){
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
        for (Car car : Car.allCars()) {
            options.put(car.id.toString(), car.toString());
        }
        return options;
    }


    @Override
    public String toString() {
        return manufacturer + ": " + model;
    }

    @Override
    public Car bind(String key, String id) {
        return findByID(Long.parseLong(id));
    }

    @Override
    public String unbind(String key) {
        return this.id.toString();
    }

    @Override
    public String javascriptUnbind() {
        return this.id.toString();
    }
}
