package com.rawat.ashish.game.networks;


import com.rawat.ashish.game.model.Login;
import com.rawat.ashish.game.model.PaymentRequest;
import com.rawat.ashish.game.model.UpdatedBalance;
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
    Call<Login> login(@Query("mobile") String userName,
                      @Query("password") String userPassword);

    @GET("update_cash_balance.php")
    Call<UpdatedBalance> updateReferCash(@Query("user_id") String userId,
                                         @Query("refer_cash") String referCash);

    @GET("update_cash_balance.php")
    Call<UpdatedBalance> updateEarnedCash(@Query("user_id") String userId,
                                          @Query("earned_cash") String earnedCash);

    @GET("update_cash_balance.php")
    Call<UpdatedBalance> updateReferEarnedCash(@Query("user_id") String userId,
                                               @Query("refer_cash") String referCash,
                                               @Query("self_income") String earnedCash);

    @GET("add_payment_request.php")
    Call<PaymentRequest> inAccountPaymentRequest(@Query("user_id") String userId,
                                                 @Query("holder_name") String holderName,
                                                 @Query("account_number") String accountNumber,
                                                 @Query("ifsc") String iFSCCode,
                                                 @Query("bank_name") String bankName);
    @GET("add_payment_request.php")
    Call<PaymentRequest> payTmPaymentRequest(@Query("user_id") String userId,
                                             @Query("paytm") String payTm);
    @GET("add_payment_request.php")
    Call<PaymentRequest> payPalPaymentRequest(@Query("user_id") String userId,
                                             @Query("paypal_email") String payTm);

}