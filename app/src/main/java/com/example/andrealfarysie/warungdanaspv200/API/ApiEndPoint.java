package com.example.andrealfarysie.warungdanaspv200.API;

import com.example.andrealfarysie.warungdanaspv200.Response.ActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.AdditionalContactDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.ContactDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.DetailLoan;
import com.example.andrealfarysie.warungdanaspv200.Response.DetailUnit;
import com.example.andrealfarysie.warungdanaspv200.Response.DetailUserAgreement;
import com.example.andrealfarysie.warungdanaspv200.Response.ListActivityTemplate;
import com.example.andrealfarysie.warungdanaspv200.Response.Login;
import com.example.andrealfarysie.warungdanaspv200.Response.OrderDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.OrderProductDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.OrderSuretyDetail;
import com.example.andrealfarysie.warungdanaspv200.Response.RespActivitySchedule;
import com.example.andrealfarysie.warungdanaspv200.Response.RespCounterLead;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListActivityTemplate;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListActivityUser;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListAddress;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListCfa;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOrder;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOrderStatus;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListOutlet;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListPhone;
import com.example.andrealfarysie.warungdanaspv200.Response.RespListType;
import com.example.andrealfarysie.warungdanaspv200.Response.RespPost;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @FormUrlEncoded
    @POST("spv_login")
    Call<Login> loginrequest(@Field("npm") String npm,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("user_agreement_check")
    Call<DetailUserAgreement> eulaCheck(@Field("id_cms_users") Integer idUser);

    @FormUrlEncoded
    @POST("user_agreement_create")
    Call<ResponseBody> eulaCreate(@Field("eula") String eula,
                                  @Field("id_cms_users") Integer idUser);

    @FormUrlEncoded
    @POST("user_logs_create")
    Call<ResponseBody> userLogs(@Field("useragent") String userAgent,
                                @Field("description") String desc,
                                @Field("id_cms_users") Integer idUser);

    @GET("spv_outlet_list")
    Call<RespListOutlet> listOutlet(@Query("id_mst_branch") Integer idMstBranch);

    @GET("spv_order_hari_counter")
    Call<RespCounterLead> spv_order_hari_counter(@Query("id_mst_outlet") Integer id_mst_outlet,
                                                 @Query("created_at") String  created_at,
                                                 @Query("id_order_mst_status")Integer id_order_mst_status);

    @GET("spv_order_reason_listing")
    Call<RespListOrderStatus> spv_order_reason_listing(@Query("id_order_mst_status") Integer idOrderStatus);

    @GET("spv_counter_order")
    Call<RespCounterLead> orderCounterOutlet(@Query("id_mst_outlet") Integer id);

    @GET("spv_booking_counter")
    Call<RespCounterLead> bookingCounterOutlet(@Query("id_mst_outlet") Integer id);

    @GET("spv_activity_schedule_listing")
    Call<RespActivitySchedule> listActivitySchedule(@Query("id_cms_users") Integer idUser,
                                                    @Query("start_date") String startDate);

    @GET("spv_activity")
    Call<ActivitySchedule> ActivitySchedule(@Query("id") Integer id);

    @GET("spv_activity_user_listing")
    Call<RespListActivityUser> ListActivityUser(@Query("id_activity_schedule") Integer idActivitySchedule);

    @GET("spv_order_listing")
    Call<RespListOrder> listOrder(@Query("id_mst_branch") Integer id_mst_branch,
                                  @Query("id_order_mst_status") Integer idStatus,
                                  @Query("limit") Integer limit);

    @GET("spv_order_listing")
    Call<RespListOrder> listOrder2(@Query("id_mst_branch") Integer id_mst_branch,
                                   @Query("id_order_mst_status") Integer idStatus,
                                   @Query("limit") Integer limit,
                                   @Query("offset") Integer offset);

    @GET("spv_mst_unit")
    Call<DetailUnit> detailUnit(@Query("id") Integer id);

    @GET("spv_activity_template_delete")
    Call<ResponseBody> spv_activity_template_delete(@Query("id") Integer id);

    @GET("spv_order_loan_detail")
    Call<DetailLoan> detailLoan(@Query("id_order") Integer idOrder);

    @GET("spv_activity_template_listing")
    Call<RespListActivityTemplate> activityTemplateList(@Query("id_cms_users") Integer idUser,
                                                        @Query("id_mst_outlet") Integer idOutlet);

    @GET("spv_activity_type_listing")
    Call<RespListType> activityType();

    @GET("spv_order_status_listing")
    Call<RespListOrderStatus> orderStatus();

    @GET("spv_list_cfa")
    Call<RespListCfa> listCFA(@Query("id_cms_privileges") Integer idprivileges,
                              @Query("id_mst_outlet") Integer idOutlet);

    @FormUrlEncoded
    @POST("spv_add_activity")
    Call<RespPost> addActivityTemplate(@Field("id_mst_outlet") Integer id_mst_outlet,
                                       @Field("id_activity_mst_type") Integer id_activity_mst_type,
                                       @Field("id_cms_users") Integer id_cms_users,
                                       @Field("lat") String lat,
                                       @Field("lng") String lng,
                                       @Field("location") String location);

    @FormUrlEncoded
    @POST("spv_create_activity_schedule")
    Call<RespPost> addActivitySchedule(@Field("id_activity") Integer id_activity,
                                       @Field("start_date") String start_date,
                                       @Field("end_date") String end_date,
                                       @Field("started") String started,
                                       @Field("ended") String ended,
                                       @Field("id_cms_users") Integer id_cms_users,
                                       @Field("note") String note);

    @FormUrlEncoded
    @POST("spv_order_loan_update")
    Call<ResponseBody> orderLoanUpdate(@Field("id") Integer id,
                                       @Field("id_order") Integer id_order,
                                       @Field("plafond") Integer plafond,
                                       @Field("down_payment") Integer down_payment,
                                       @Field("installment") Integer installment,
                                       @Field("tenor") Integer tenor,
                                       @Field("id_cms_users") Integer id_cms_users,
                                       @Field("updated_by") Integer updated_by,
                                       @Field("need") String need);

    @FormUrlEncoded
    @POST("spv_activity_user_create")
    Call<ResponseBody> activityUserCreate(@Field("id_activity_schedule") Integer idActivity,
                                          @Field("id_cms_users") Integer iduser);

    @FormUrlEncoded
    @POST("spv_order_update_status")
    Call<ResponseBody> orderUpdate(@Field("id") Integer id,
                                   @Field("id_order_mst_status") Integer id_order_mst_status,
                                   @Field("id_order_mst_reason") Integer id_order_mst_reason,
                                   @Field("updated_by") Integer updated_by);


    @GET("spv_order_detail")
    Call<OrderDetail> spv_order_detail(@Query("id") Integer id);

    @GET("spv_contact_detail")
    Call<ContactDetail> spv_contact_detail(@Query("id") Integer id);

    @GET("spv_additional_contact_detail")
    Call<AdditionalContactDetail> spv_additional_contact_detail(@Query("id_contact") Integer id_contact);

    @GET("spv_order_surety_detail")
    Call<OrderSuretyDetail> spv_order_surety_detail(@Query("id_order") Integer id_order);

    @GET("spv_order_product_detail")
    Call<OrderProductDetail> spv_order_product_detail(@Query("id_order") Integer id_order);

    @GET("spv_contact_address_listing")
    Call<RespListAddress> spv_contact_address_listing(@Query("id_contact") Integer id_contact);

    @GET("spv_contact_phone_listing")
    Call<RespListPhone> spv_contact_phone_listing(@Query("id_contact") Integer id_contact);

    @GET("spv_activity_schedule_delete")
    Call<ResponseBody> spv_activity_schedule_delete(@Query("id") Integer id);

    @GET("spv_activity_user_delete")
    Call<ResponseBody> spv_activity_user_delete(@Query("id_activity_schedule") Integer id_activity_schedule);
}
