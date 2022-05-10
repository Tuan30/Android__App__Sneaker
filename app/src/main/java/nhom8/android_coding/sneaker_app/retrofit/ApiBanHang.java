package nhom8.android_coding.sneaker_app.retrofit;

import io.reactivex.rxjava3.core.Observable;

import nhom8.android_coding.sneaker_app.model.LoaiSPModel;
//import nhom8.android_coding.sneaker_app.model.SPMoiModel;
import nhom8.android_coding.sneaker_app.model.SPMoiModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("loaisanpham.php")
    Observable<LoaiSPModel> LoaiSp();

    @GET("spmoi.php")
    Observable<SPMoiModel> getSPMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SPMoiModel> getProdcuts(
            @Field("page") int page,
            @Field("loai") int loai
    );
}
