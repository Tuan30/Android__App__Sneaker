package nhom8.android_coding.sneaker_app.utils;

import java.util.ArrayList;
import java.util.List;

import nhom8.android_coding.sneaker_app.model.GioHang;
import nhom8.android_coding.sneaker_app.model.User;

public class Utils {

    public static final String BASE_URL = "http://192.168.1.22/Sneaker/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
}
