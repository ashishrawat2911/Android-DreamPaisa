package com.rawat.ashish.game.networks;


import com.rawat.ashish.game.model.Login;
import com.rawat.ashish.game.model.User;
import com.rawat.ashish.game.model.UserDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("add_user.php")
    Call<User> addUser(@Query("user_name") String userName,
                       @Query("password") String password,
                       @Query("first_name") String firstName,
                       @Query("last_name") String lastName,
                       @Query("mobile") String mobile,
                       @Query("user_type") String userType,
                       @Query("user_referral_code") String referralCode);
    @GET("user_details.php")
    Call<UserDetails> getUser(@Query("user_id") String userId);
    @GET("login.php")
    Call<Login> login(@Query("user_name") String userName, @Query("password") String userPassword);
}