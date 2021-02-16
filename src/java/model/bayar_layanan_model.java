package model;

public class bayar_layanan_model {
    private String id_bayar_layanan;
    private String id_layanan;
    private String id_detail_layanan;
    private String id_pasien;
    private String tgl_layanan;
    private String keterangan;

    public String getId_bayar_layanan() {
        return id_bayar_layanan;
    }

    public void setId_bayar_layanan(String id_bayar_layanan) {
        this.id_bayar_layanan = id_bayar_layanan;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getId_detail_layanan() {
        return id_detail_layanan;
    }

    public void setId_detail_layanan(String id_detail_layanan) {
        this.id_detail_layanan = id_detail_layanan;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getTgl_layanan() {
        return tgl_layanan;
    }

    public void setTgl_layanan(String tgl_layanan) {
        this.tgl_layanan = tgl_layanan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
