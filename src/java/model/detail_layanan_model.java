package model;

public class detail_layanan_model {
    private String id_layanan;
    private String id_detail_layanan;
    private String des_detail_layanan;
    private String keterangan;
    private double biaya_layanan;

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

    public String getDes_detail_layanan() {
        return des_detail_layanan;
    }

    public void setDes_detail_layanan(String des_detail_layanan) {
        this.des_detail_layanan = des_detail_layanan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public double getBiaya_layanan() {
        return biaya_layanan;
    }

    public void setBiaya_layanan(double biaya_layanan) {
        this.biaya_layanan = biaya_layanan;
    }
}
