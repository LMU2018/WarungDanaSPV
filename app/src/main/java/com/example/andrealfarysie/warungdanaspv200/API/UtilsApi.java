package com.example.andrealfarysie.warungdanaspv200.API;

public class UtilsApi {
    //    10.0.2.2 ini adalah localhost.
//    public static final String BASE_URL_API = "http://192.168.133.66/warna2/public/api/";
//    public static final String BASE_URL_API = "http://116.197.129.148:59/warna2/public/api/";
//    public static final String BASE_URL_API = "http://116.197.129.148:59/warnatrial/public/api/";
//    public static final String BASE_URL_API = "http://192.168.139.59/warnadev/public/api/";
    public static final String BASE_URL_API = "http://116.197.129.148:66/warnaspv/public/api/";
//    public static final String BASE_URL_API = "http://116.197.129.148:66/warnaspvtrial/public/api/";
//    public static final String BASE_URL_API = "http://192.168.139.59/warnadev/public/api/";
//    public static final String BASE_URL_API = "http://192.168.139.58/warnaprod2/public/api/";


    // Mendeklarasikan Interface BaseApiService
    public static ApiEndPoint getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(ApiEndPoint.class);
    }

}
