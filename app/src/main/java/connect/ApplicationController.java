package connect;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import networkservice.NetworkServiceInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SEHWA on 2017-09-20.
 */

public class ApplicationController extends Application {

    private static ApplicationController instance;
    public static ApplicationController getInstance(){
        return instance;
    }

    private NetworkServiceInterface networkServiceInterface;
    public NetworkServiceInterface getNetworkServiceInterface(){
        return networkServiceInterface;
    }

    private String BASE_URL = "http://elasticbeanstalkEasycarry-env.us-east-2.elasticbeanstalk.com";

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
        buildNetworkService();
    }

    public void buildNetworkService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkServiceInterface = retrofit.create(NetworkServiceInterface.class);
    }

}
