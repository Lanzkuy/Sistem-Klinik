package model;

public class obat_model {
    private String id_obat;
    private String nama_obat;
    private String satuan;
    private String no_faktur;
    private String waktu;
    private String user_id;
    private double harga_jual;
    private double stok; 

    public String getId_obat() {
        return id_obat;
    }

    public void setId_obat(String id_obat) {
        this.id_obat = id_obat;
    }

    public String getNama_obat() {
        return nama_obat;
    }

    public void setNama_obat(String nama_obat) {
        this.nama_obat = nama_obat;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getNo_faktur() {
        return no_faktur;
    }

    public void setNo_faktur(String no_faktur) {
        this.no_faktur = no_faktur;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(double harga_jual) {
        this.harga_jual = harga_jual;
    }

    public double getStok() {
        return stok;
    }

    public void setStok(double stok) {
        this.stok = stok;
    }
}
