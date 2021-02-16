package model;

public class kamar_model {
    private String id_kamar;
    private String nama_kamar;
    private String no_kamar;
    private String kelas;
    private String des_kamar;
    private String status;
    private double harga_perhari;
    private int kapasitas;
    private int terisi;

    public String getId_kamar() {
        return id_kamar;
    }

    public void setId_kamar(String id_kamar) {
        this.id_kamar = id_kamar;
    }

    public String getNama_kamar() {
        return nama_kamar;
    }

    public void setNama_kamar(String nama_kamar) {
        this.nama_kamar = nama_kamar;
    }

    public String getNo_kamar() {
        return no_kamar;
    }

    public void setNo_kamar(String no_kamar) {
        this.no_kamar = no_kamar;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getDes_kamar() {
        return des_kamar;
    }

    public void setDes_kamar(String des_kamar) {
        this.des_kamar = des_kamar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getHarga_perhari() {
        return harga_perhari;
    }

    public void setHarga_perhari(double harga_perhari) {
        this.harga_perhari = harga_perhari;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public int getTerisi() {
        return terisi;
    }

    public void setTerisi(int terisi) {
        this.terisi = terisi;
    }
}
