package model;

public class resep_model {
    private String id_resep;
    private String id_dokter;
    private String tgl_resep;
    private String id_poli;
    private String user_id;

    public String getId_resep() {
        return id_resep;
    }

    public void setId_resep(String id_resep) {
        this.id_resep = id_resep;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getTgl_resep() {
        return tgl_resep;
    }

    public void setTgl_resep(String tgl_resep) {
        this.tgl_resep = tgl_resep;
    }

    public String getId_poli() {
        return id_poli;
    }

    public void setId_poli(String id_poli) {
        this.id_poli = id_poli;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
