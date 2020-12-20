package condor.sales.Models;

public class User {

    private String id;
    private String code;
    private String name;
    private String phone;
    private String city;
    private String adresse;
    private String email;
    private String password;
    private String lastsync;
    private String token;


    public User(String id,String code,String name , String phone, String city,String adresse, String email, String password, String token, String lastsync) {   this.id = id;
         this.code = code;
         this.name = name;
         this.phone = phone;
         this.city = city;
         this.adresse = adresse;
         this.email = email;
         this.password = password;
         this.token = token;
         this.lastsync = lastsync;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getAdresse() { return adresse; }

    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getLastsync() { return lastsync; }

    public void setLastsync(String lastsync) { this.lastsync = lastsync; }



}
