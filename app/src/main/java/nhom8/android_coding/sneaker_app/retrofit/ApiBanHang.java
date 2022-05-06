package nhom8.android_coding.sneaker_app.retrofit;

import io.reactivex.rxjava3.core.Observable;

import nhom8.android_coding.sneaker_app.model.LoaiSPModel;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("loaisanpham.php")
    Observable<LoaiSPModel> LoaiSp();

}
