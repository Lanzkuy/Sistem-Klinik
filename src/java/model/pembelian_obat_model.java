package model;

public class pembelian_obat_model {
    private String id_trans;
    private String id_supplier;
    private String no_faktur;
    private String tgl_faktur;
    private String id_obat;
    private String keterangan;
    private String tgl_expired;
    private String id_user;
    private double harga_beli;
    private double jumlah;

    public String getId_trans() {
        return id_trans;
    }

    public void setId_trans(String id_trans) {
        this.id_trans = id_trans;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNo_faktur() {
        return no_faktur;
    }

    public void setNo_faktur(String no_faktur) {
        this.no_faktur = no_faktur;
    }

    public String getTgl_faktur() {
        return tgl_faktur;
    }

    public void setTgl_faktur(String tgl_faktur) {
        this.tgl_faktur = tgl_faktur;
    }

    public String getId_obat() {
        return id_obat;
    }

    public void setId_obat(String id_obat) {
        this.id_obat = id_obat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTgl_expired() {
        return tgl_expired;
    }

    public void setTgl_expired(String tgl_expired) {
        this.tgl_expired = tgl_expired;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public double getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(double harga_beli) {
        this.harga_beli = harga_beli;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }
}
