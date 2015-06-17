package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Entity
public class Car extends Model {

    @Id
    public Long id;

    @Constraints.MaxLength(50)
    public String manufacturer;

    @Constraints.MaxLength(50)
    public String model;

    @OneToMany(mappedBy = "car")
    public Ad ad;

    public Car(){

    }


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

}
