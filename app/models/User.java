package models;


import play.data.validation.Constraints;
        import play.db.ebean.Model;

        import javax.persistence.Entity;
        import javax.persistence.Id;
        import javax.persistence.ManyToMany;
        import javax.persistence.OneToMany;
        import java.util.LinkedList;
        import java.util.List;

@Entity
public class User extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String name;

    @Constraints.Required
    @Constraints.MaxLength(50)
    public String tel;

    public static Finder<Long, User> finder = new Finder<Long, User>(Long.class, User.class);

    public User() {
    }

    public User(String email, String password, String name, String tel) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User authenticate(String email,
                                    String password) {
        return finder.where().eq("email", email).eq("password", password).findUnique();
    }

    public static boolean isRegistered(String email){
        return (finder.where().eq("email", email).findUnique() != null);
    }

}