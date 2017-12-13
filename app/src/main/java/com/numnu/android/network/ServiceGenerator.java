package com.numnu.android.network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.numnu.android.utils.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 Created by rajesh on 22/07/16.
*/
public class ServiceGenerator {

    private static final String BASE_URL = "https://numnu-server-dev.appspot.com/";

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

//    static GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
    static  Gson gson = new GsonBuilder().create();
    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));



    public static  <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
    public static  <S> S createServiceHeader(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                // Request customization: add request headers
                                Request.Builder requestBuilder = original.newBuilder()
                                        .header("Authorization","Bearer "+Constants.FIREBASE_TOKEN )
                                        .header("Accept-Language", "en-US")
                                        .header("Content-Type","application/json")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = builder.client(okClient).build();
        return retrofit.create(serviceClass);
    }


}
