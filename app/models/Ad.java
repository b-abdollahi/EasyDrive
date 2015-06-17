package models;

import Utils.DateFormat;
import com.avaje.ebean.Page;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;


@Entity
public class Ad extends Model {

    @Id
    public Long id;


    @ManyToOne
    public Location location;

    @ManyToOne
    public Car car;

    @ManyToOne
    public User user;

    @javax.persistence.Column(length=2000)
    @Constraints.MaxLength(2000)
    public String description;

    @DateFormat("dd-MM-yyyy")
    public Date date = new Date();

    @Lob
    private byte[] picture;


    public static Finder<Long, Ad> find = new Finder<>(Long.class, Ad.class);

    public Ad(){

    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }


    public static Ad findByID(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    public static Page<Ad> find(int page){
        return find.where()
                .orderBy("id asc")
                .findPagingList(6)
                .setFetchAhead(false)
                .getPage(page);
    }

    public static List<Ad> search(String keywork){
        if (keywork.equals("")){
            return null;
        }
        List<Ad> deviceModelsByCarMan = find.where().ilike("car.manufacturer", "%" + keywork + "%").findList();
        List<Ad> deviceModelsByCarModel = find.where().ilike("car.model", "%" + keywork + "%").findList();
        List<Ad> deviceModelsByLocationCity = find.where().ilike("location.city", "%" + keywork + "%").findList();
        List<Ad> deviceModelsByLocationState = find.where().ilike("location.state", "%" + keywork + "%").findList();
        List<Ad> deviceModelsByUser = find.where().ilike("user.name", "%" + keywork + "%").findList();
        List<Ad> deviceModelsByDescription = find.where().ilike("description", "%" + keywork + "%").findList();
        Set<Ad> results = new HashSet<>();
        for (Ad ad : deviceModelsByDescription) {
            results.add(ad);
        }
        for (Ad ad : deviceModelsByUser) {
            results.add(ad);
        }
        for (Ad ad : deviceModelsByLocationState) {
            results.add(ad);
        }
        for (Ad ad : deviceModelsByLocationCity) {
            results.add(ad);
        }
        for (Ad ad : deviceModelsByCarModel) {
            results.add(ad);
        }
        for (Ad ad : deviceModelsByCarMan) {
            results.add(ad);
        }
        return new ArrayList<>(results);
    }

}
