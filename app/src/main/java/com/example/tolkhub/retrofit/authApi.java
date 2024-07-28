package com.example.tolkhub.retrofit;

import com.example.tolkhub.Model.AuthenticationModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface authApi {
    @POST("/authentication/users")
    Call<AuthenticationModel> save(@Body AuthenticationModel authmodel);

    @GET("/authentication/users/{PhoneNumberId}")
    Call<AuthenticationModel> FindByPhoneNumber(@Path("PhoneNumberId") String PhoneNumberId);

@PUT("/authentication/users/{PhoneNumberId}")
Call<AuthenticationModel>  putMethod (@Path("PhoneNumberId") String PhoneNumberId,@Body AuthenticationModel authModel);
}
