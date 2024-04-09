package info;

public class infouser {
    private String username;
    private String phone;
    private String tuoi;
    private String gioiTinh;
    private String ngaySinh;

    public infouser() {
    }

    public infouser(String username, String phone, String tuoi, String gioiTinh, String ngaySinh) {
        this.username = username;
        this.phone = phone;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
