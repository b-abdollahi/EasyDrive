package models;

import Utils.DateFormat;
import com.avaje.ebean.Page;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Ad extends Model {

    @Id
    public Long id;


    @ManyToOne
    public Location location;

    @ManyToOne
    public Car car;

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
                .findPagingList(15)
                .setFetchAhead(false)
                .getPage(page);
    }

}
