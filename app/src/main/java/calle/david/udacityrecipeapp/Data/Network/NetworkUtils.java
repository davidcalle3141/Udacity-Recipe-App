package calle.david.udacityrecipeapp.Data.Network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final String  RECIPE_NETWORK_SOURCE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String  RANDOM_BAKING_IMAGE_URL = "http://source.unsplash.com/collection/1162740/1600x900/";
    public static String getStringNetworkResponse(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringJSONRecipeNetworkResponse() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(RECIPE_NETWORK_SOURCE_URL)
                .build();

        try(Response response = client.newCall(request).execute()){

            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //72904
    public static String getBakingStringImageNetworkResponse(String description) {
        Uri buildUri = Uri.parse(RANDOM_BAKING_IMAGE_URL).buildUpon()
                .query(description).build();
        URL url= null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try(Response response = client.newCall(request).execute()){

            Log.d("URL",  description);
            return String.valueOf(response.request().url());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
