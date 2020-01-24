package com.example.meetingscheduler;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelperClient
{
    private static Retrofit retrofit;

    private static OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS);

    private static RequestInterface requestInterface;

    private static Retrofit getRetrofitClient()
    {

        if (retrofit== null)
        {
            if (BuildConfig.DEBUG)
            {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                clientBuilder.addInterceptor(interceptor);
            }

            OkHttpClient httpClient = clientBuilder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    public static RequestInterface getRequestInterface(){

        if(retrofit == null){
            getRetrofitClient();
        }
        if (requestInterface == null) {
            requestInterface = retrofit.create(RequestInterface.class);
        }
        return requestInterface;
    }
}
