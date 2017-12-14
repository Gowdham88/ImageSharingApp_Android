package com.numnu.android.network;


import com.numnu.android.network.request.CompleteSignUpData;
import com.numnu.android.network.response.CommonResponse;
import com.numnu.android.network.response.EventBusinessesResponse;
import com.numnu.android.network.response.EventDetailResponse;
import com.numnu.android.network.response.EventItemsResponse;
import com.numnu.android.network.response.EventPostsResponse;
import com.numnu.android.network.response.ItemDetailsResponse;
import com.numnu.android.network.response.ItemsByTagResponse;
import com.numnu.android.network.response.LoginResponse;
import com.numnu.android.network.response.SignupResponse;
import com.numnu.android.network.response.TagsResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
 Created by rajesh on 25/8/16.
*/
public interface ApiServices {


    @Headers("Content-Type: application/json")
    @POST("/users")
    Call<SignupResponse> completeSignUp(@Body CompleteSignUpData completeSignUpData);

    @GET("/users")
    Call<CommonResponse> checkUserName(@Query("checkusername") String s);

    @GET("/users/signin")
    Call<LoginResponse> login();

    @Multipart
    @POST("/users/{id}/images")
    Call<CommonResponse> uploadImage(@Path("id") String id, @Part MultipartBody.Part image);

    @GET("/tags")
    Call<TagsResponse> getTags(@Query("beginWith") String s);


   //get item details
    @GET("/items/{id}")
    Call<ItemDetailsResponse> getItem(@Path("id") String id);


    //get event details
    @GET("/events/{id}")
    Call<EventDetailResponse> getEvent(@Path("id") String id);

    //get event business details
    @GET("/events/{id}/businesses")
    Call<EventBusinessesResponse> getEventBusinesses(@Path("id") String id,@Query("page") String page);

    @GET("/events/{id}/businesses")
    Call<EventBusinessesResponse> getEventBusinesses(@Path("id") String id);

    @GET("/events/{id}/itemtags")
    Call<EventItemsResponse> getEventItems(@Path("id") String id);

    @GET("/events/{id}/itemtags")
    Call<EventItemsResponse> getEventItems(@Path("id") String id,@Query("page") String page);

    @GET("/events/{eventId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getItemsByTagId(@Path("eventId") String eventId, @Path("tagId") String tagId);

    @GET("/events/{eventId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getItemsByTagId(@Path("eventId") String eventId,@Path("tagId") String tagId,@Query("page") String page);

    @GET("/events/{id}/posts")
    Call<EventPostsResponse> getEventPosts(@Path("id") String id);

    @GET("/events/{id}/posts")
    Call<EventPostsResponse> getEventPosts(@Path("id") String id,@Query("page") String page);

//    @POST("/mobile/login")
//    Call<Collection> login(@Query("email") String email, @Query("password") String serial, @Query("mac_address") String mac);
//
//    @POST("/mobile/bill/collection")
//    Call<Collection> upload(@Body Collection collection);
//
//    @POST("/mobile/bill/collection")
//    Call<Collection> sync(@Body Collection collection);

   /* //update fcm id
    @GET("/mobile/update/{id}")
    Call<CommonResponse> updateFcmId(@Path("id") String id, @Query("fcm_token") String s);

    @GET("/tracker/create")
    Call<CommonResponse> updateLocation(@Query("imei") String imei, @Query("type") String type, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("accuracy") String accuracy);

    @POST("/geolocation/v1/geolocate")
    Call<GeoLocationResponse> getLatLong(@Query("key") String s, @Body GeoLocationRequest fcmSend);

    @POST("/mobile/find")
    Call<List<IdResponse>> getId(@Body IdRequest idRequest);
*/

}
