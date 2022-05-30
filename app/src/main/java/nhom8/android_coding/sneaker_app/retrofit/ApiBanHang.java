package nhom8.android_coding.sneaker_app.retrofit;

import io.reactivex.rxjava3.core.Observable;

import nhom8.android_coding.sneaker_app.model.DonHangModel;
import nhom8.android_coding.sneaker_app.model.LoaiSPModel;
//import nhom8.android_coding.sneaker_app.model.SPMoiModel;
import nhom8.android_coding.sneaker_app.model.MessageModel;
import nhom8.android_coding.sneaker_app.model.SPMoiModel;
import nhom8.android_coding.sneaker_app.model.UserModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("loaisanpham.php")
    Observable<LoaiSPModel> LoaiSp();

    @GET("spmoi.php")
    Observable<SPMoiModel> getSPMoi();

//POST DATA
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SPMoiModel> getProdcuts(
            @Field("page") int page,
            @Field("loai") int loai
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> Dangki(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("uid") String uid
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> Dangnhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );


    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitet
    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SPMoiModel> search(
            @Field("search") String search
    );

    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token
    );
}
