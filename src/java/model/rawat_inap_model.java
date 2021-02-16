package model;
 
public class rawat_inap_model {
    private String id_rawat;
    private String id_pasien;
    private String id_kamar;
    private String nama_ruang;
    private String tgl_checkin;
    private String tgl_checkout;
    private String keterangan;

    public String getId_rawat() {
        return id_rawat;
    }

    public void setId_rawat(String id_rawat) {
        this.id_rawat = id_rawat;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getId_kamar() {
        return id_kamar;
    }

    public void setId_kamar(String id_kamar) {
        this.id_kamar = id_kamar;
    }

    public String getNama_ruang() {
        return nama_ruang;
    }

    public void setNama_ruang(String nama_ruang) {
        this.nama_ruang = nama_ruang;
    }

    public String getTgl_checkin() {
        return tgl_checkin;
    }

    public void setTgl_checkin(String tgl_checkin) {
        this.tgl_checkin = tgl_checkin;
    }

    public String getTgl_checkout() {
        return tgl_checkout;
    }

    public void setTgl_checkout(String tgl_checkout) {
        this.tgl_checkout = tgl_checkout;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
