package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Behzad on 6/9/2015.
 */
@Entity
public class User extends Model {


    @Id
    public Long id;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String name;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String tel;

    public User(){

    }


}
