public class Voters {

    private String nationalId ;
    private String fullname;

    public Voters(String nationalId, String fullname, String sex, String city, String region, String passward) {
        this.nationalId = nationalId;
        this.fullname = fullname;
        this.sex = sex;
        this.city = city;
        this.region = region;
        this.passward = passward;
    }

    private String sex;
    private String city;
    private String region;
    private String passward;

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }


}
