package nhom8.android_coding.sneaker_app.model;

import java.io.Serializable;

public class SPMoi implements Serializable {
    int id;
    String tensp;
    String giasp;
    String hinhanh;
    String mota;
    int sizesp;
    int loai;

    public int getSizesp() {
        return sizesp;
    }

    public void setSizesp(int sizesp) {
        this.sizesp = sizesp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
}
