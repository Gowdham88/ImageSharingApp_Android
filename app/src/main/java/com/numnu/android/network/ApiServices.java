package com.numnu.android.network;


import android.location.Location;

import com.numnu.android.network.request.BookmarkRequestData;
import com.numnu.android.network.request.CompleteSignUpData;
import com.numnu.android.network.response.BookmarkResponse;
import com.numnu.android.network.response.BusinessEventsResponse;
import com.numnu.android.network.response.BusinessLocationResponse;
import com.numnu.android.network.response.BusinessResponse;
import com.numnu.android.network.response.CommonResponse;
import com.numnu.android.network.response.EventBusinessDetailResponse;
import com.numnu.android.network.response.EventBusinessesResponse;
import com.numnu.android.network.response.EventDetailResponse;
import com.numnu.android.network.response.EventItemDetailResponse;
import com.numnu.android.network.response.EventItemsResponse;
import com.numnu.android.network.response.EventPostsResponse;
import com.numnu.android.network.response.GetBookmarksResponse;
import com.numnu.android.network.response.ItemDetailsResponse;
import com.numnu.android.network.response.ItemLocationResponse;
import com.numnu.android.network.response.ItemsByTagResponse;
import com.numnu.android.network.response.LocationDetailResponse;
import com.numnu.android.network.response.LoginResponse;
import com.numnu.android.network.response.PostdataItem;
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

    @GET("/signinuser")
    Call<LoginResponse> login();

    @Multipart
    @POST("/users/{id}/images")
    Call<CommonResponse> uploadImage(@Path("id") String id, @Part MultipartBody.Part image);

    @GET("/users/{userId}/posts")
    Call<EventPostsResponse> getPostsByUserId(@Path("userId") String userId);

    @GET("/users/{userId}/posts")
    Call<EventPostsResponse> getPostsByUserId(@Path("userId") String userId, @Query("page") String page);

    @GET("/tags")
    Call<TagsResponse> getTags(@Query("beginWith") String s);


    //get item details
    @GET("/items/{itemId}")
    Call<ItemDetailsResponse> getItem(@Path("itemId") String id);

    @GET("/items/{itemId}/posts")
    Call<EventPostsResponse> getItemPosts(@Path("itemId") String id);

    @GET("/items/{itemId}/posts")
    Call<EventPostsResponse> getItemPosts(@Path("itemId") String id, @Query("page") String page);


    //get event details
    @GET("/events/{eventId}")
    Call<EventDetailResponse> getEvent(@Path("eventId") String id);

    //get event business details
    @GET("/events/{eventId}/businesses")
    Call<EventBusinessesResponse> getEventBusinesses(@Path("eventId") String id, @Query("page") String page);

    @GET("/events/{eventId}/businesses")
    Call<EventBusinessesResponse> getEventBusinesses(@Path("eventId") String id);

    @GET("/events/{eventId}/businesses/{businessId}")
    Call<EventBusinessDetailResponse> getEventBusinessDetail(@Path("eventId") String eventId, @Path("businessId") String businessId);

    @GET("/events/{eventId}/businesses/{businessId}/itemtags")
    Call<EventItemsResponse> getEventBusinessItemTags(@Path("eventId") String eventId, @Path("businessId") String businessId);

    @GET("/events/{eventId}/businesses/{businessId}/itemtags")
    Call<EventItemsResponse> getEventBusinessItemTags(@Path("eventId") String eventId, @Path("businessId") String businessId, @Query("page") String page);

    @GET("/events/{eventId}/businesses/{businessId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getEventBusinessItemsByTagId(@Path("eventId") String eventId, @Path("businessId") String businessId, @Path("tagId") String tagId);

    @GET("/events/{eventId}/businesses/{businessId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getEventBusinessItemsByTagId(@Path("eventId") String eventId, @Path("businessId") String businessId, @Path("tagId") String tagId, @Query("page") String page);

    @GET("/events/{eventId}/businesses/{businessId}/posts")
    Call<EventPostsResponse> getEventBusinessPosts(@Path("eventId") String eventId, @Path("businessId") String businessId);

    @GET("/events/{eventId}/businesses/{businessId}/posts")
    Call<EventPostsResponse> getEventBusinessPosts(@Path("eventId") String eventId, @Path("businessId") String businessId, @Query("page") String page);

    @GET("/events/{eventId}/itemtags")
    Call<EventItemsResponse> getEventItems(@Path("eventId") String id);

    @GET("/events/{eventId}/itemtags")
    Call<EventItemsResponse> getEventItems(@Path("eventId") String id, @Query("page") String page);

    @GET("/events/{eventId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getItemsByTagId(@Path("eventId") String eventId, @Path("tagId") String tagId);

    @GET("/events/{eventId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getItemsByTagId(@Path("eventId") String eventId, @Path("tagId") String tagId, @Query("page") String page);

    @GET("/events/{eventId}/items/{itemId}/posts")
    Call<EventPostsResponse> getEventItemPosts(@Path("eventId") String eventId, @Path("itemId") String itemId);

    @GET("/events/{eventId}/items/{itemId}/posts")
    Call<EventPostsResponse> getEventItemPosts(@Path("eventId") String eventId, @Path("itemId") String itemId, @Query("page") String page);

    @GET("/events/{eventId}/items/{itemId}")
    Call<EventItemDetailResponse> getEventItemDetail(@Path("eventId") String eventId, @Path("itemId") String itemId);

    @GET("/events/{eventId}/posts")
    Call<EventPostsResponse> getEventPosts(@Path("eventId") String id);

    @GET("/events/{eventId}/posts")
    Call<EventPostsResponse> getEventPosts(@Path("eventId") String id, @Query("page") String page);

    @GET("/posts/{id}")
    Call<PostdataItem> getPostById(@Path("id") String id);


    @GET("/businesses/{id}")
    Call<BusinessResponse> getBusinessById(@Path("id") String id);

    @GET("/businesses/{id}/events")
    Call<BusinessEventsResponse> getEventsByBusinessId(@Path("id") String id);

    @GET("/businesses/{id}/events")
    Call<BusinessEventsResponse> getEventsByBusinessId(@Path("id") String id, @Query("page") String page);

    @GET("/businesses/{id}/itemtags")
    Call<EventItemsResponse> getBusinessItemTags(@Path("id") String id);

    @GET("/businesses/{id}/itemtags")
    Call<EventItemsResponse> getBusinessItemTags(@Path("id") String id, @Query("page") String page);

    @GET("/businesses/{businessId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getBusinessItemsByTagId(@Path("businessId") String businessId, @Path("tagId") String tagId);

    @GET("/businesses/{businessId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getBusinessItemsByTagId(@Path("businessId") String businessId, @Path("tagId") String tagId, @Query("page") String page);

    @GET("/businesses/{id}/posts")
    Call<EventPostsResponse> getBusinessPosts(@Path("id") String id);

    @GET("/businesses/{id}/posts")
    Call<EventPostsResponse> getBusinessPosts(@Path("id") String id, @Query("page") String page);

    //Bookmarks
    @Headers("Content-Type: application/json")
    @POST("/users/{userId}/bookmarks")
    Call<BookmarkResponse> postBookmark(@Path("userId") String userId, @Body BookmarkRequestData bookmarkRequestData);

    @GET("/users/{userId}/bookmarks")
    Call<GetBookmarksResponse> getBookmark(@Path("userId") String userId);

    @GET("/users/{userId}/bookmarks")
    Call<GetBookmarksResponse> getBookmark(@Path("userId") String userId, @Query("page") String page);

    @GET("/bookmarks/{bookmarkId}")
    Call<Void> deleteBookmark(@Path("bookmarkId") String bookmarkId);

    @GET("/items/{itemId}/locations")
    Call<ItemLocationResponse> getLocation(@Path("itemId") String id);

    @GET("/items/{itemId}/locations")
    Call<ItemLocationResponse> getLocation(@Path("itemId") String id, @Query("page") String page);


    @GET("/businesses/{businessId}/locations")
    Call<BusinessLocationResponse> getbusinesslocation(@Path("businessId") String id);

    @GET("/businesses/{businessId}/locations")
    Call<BusinessLocationResponse> getbusinesslocation(@Path("businessId") String id, @Query("page") String page);


    @GET("/locations/{locationId}")
    Call<LocationDetailResponse> getLocationDetail(@Path("locationId") String id);

    @GET("/locations/{locationId}/itemtags")
    Call<EventItemsResponse> getLocationItemTags(@Path("locationId") String id);

    @GET("/locations/{locationId}/itemtags")
    Call<EventItemsResponse> getLocationItemTags(@Path("locationId") String id, @Query("page") String page);

    @GET("/locations/{locationId}/posts")
    Call<EventPostsResponse> getlocationpost(@Path("locationId") String id);

    @GET("/locations/{locationId}/posts")
    Call<EventPostsResponse> getlocationpost(@Path("locationId") String id, @Query("page") String page);

    @GET("/locations/{locationId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getLocationItemsByTagId(@Path("locationId") String locationId, @Path("tagId") String tagId);

    @GET("/locations/{locationId}/itemtags/{tagId}/items")
    Call<ItemsByTagResponse> getLocationItemsByTagId(@Path("locationId") String locationId, @Path("tagId") String tagId, @Query("page") String page);


}
