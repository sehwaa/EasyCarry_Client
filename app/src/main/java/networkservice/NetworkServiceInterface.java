package networkservice;

import java.util.Date;
import java.util.List;

import model.Request.LockerInfo;
import model.Request.Lockers;
import model.Request.RequestSignIn;
import model.Request.RequestSignUp;
import model.Response.ResponseReservation;
import model.Response.ResponseReservationCheck;
import model.Response.ResponseReservationHistory;
import model.Response.ResponseSignIn;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SEHWA on 2017-09-20.
 */

public interface NetworkServiceInterface {

    @POST("/user/sign/up")
    Call<ResponseBody> signupCall(@Body RequestSignUp requestSignUp);

    @POST("/user/sign/in")
    Call<ResponseSignIn> signInCall(@Body RequestSignIn requestSignIn);

    @POST("/user/sign/out")
    Call<ResponseBody> signoutCall(@Header("Cookie") String cookie);

    @Multipart
    @POST("/user/reservation/do")
    Call<ResponseReservation> getReservationId (@Header("Cookie") String cookie,
                                                @Part("station_start") String station_start,
                                                @Part("station_dest") String station_dest,
                                                @Part("requestedTime") String requestedTime,
                                                @Part("totalPrice") int totalPrice,
                                                @Part("numOfLockers") int numOfLockers,
                                                @Part("lockers") Lockers lockers,
                                                @Part("email") String email,
                                                @Part("username") String username,
                                                @Part("precaution") String precaution,
                                                @Part MultipartBody.Part file);


    @GET("/user/reservation/check")
    Call<List<ResponseReservationCheck>> reservationCheckCall(@Header("Cookie") String cookie);

    @GET("/user/reservation/history")
    Call<List<ResponseReservationHistory>> reservationHistoryCall(@Header("Cookie") String cookie);

    @POST("/user/me/modify")
    Call<ResponseBody> modifyCall(@Header("Cookie") String cookie,
                                  @Query("username") String username,
                                  @Query("phoneNo") String phoneNo);

    @POST("/user/reservation/{oid}/cancel")
    Call<ResponseBody> cancelCall(@Header("Cookie") String cookie,
                                  @Path("oid") String oid);
}
