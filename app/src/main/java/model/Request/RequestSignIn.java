package model.Request;

/**
 * Created by SEHWA on 2017-09-20.
 */

public class RequestSignIn {

    private String email;
    private String password;

    public RequestSignIn(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

}
