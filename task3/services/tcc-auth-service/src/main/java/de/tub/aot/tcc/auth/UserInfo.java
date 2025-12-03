package de.tub.aot.tcc.auth;

public class UserInfo {
    public String sub;
    public String preferred_username;
    public String email ;
    public boolean email_verified ;
    public String given_name ;
    public String family_name;
    public String name ;
    public  String vehicleType;

    public UserInfo (){
        this.sub = "123456";
        this.preferred_username = "testuser";
        this.email = "testuser@example.com";
        this.email_verified = true;
        this.given_name = "Test";
        this.family_name = "User";
        this.name = "Test User";
        this.vehicleType = "emergeny vehicle";
    }
}
