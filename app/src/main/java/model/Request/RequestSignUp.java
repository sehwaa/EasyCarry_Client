package model.Request;

/**
 * Created by SEHWA on 2017-09-23.
 */

public class RequestSignUp {

    private String email;
    private String username;
    private String phoneNo;
    private String password;

    public RequestSignUp(String email, String username, String phoneNo, String password){
        this.email = email;
        this.username = username;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }

    public String getPhoneNo(){
        return phoneNo;
    }

    public String getPassword(){
        return password;
    }
}
