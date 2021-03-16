/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PembelianObatDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pembelian_obat_model;

/**
 *
 * @author Farhan Bhezni
 */
@WebServlet(name = "PembelianObatController", urlPatterns = {"/PembelianObatController"})
public class PembelianObatController extends HttpServlet{

    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        PembelianObatDAO dao = new PembelianObatDAO();
        Gson gson = new Gson();

        if (page == null) {
            List<pembelian_obat_model> ListPembelianObat = dao.getData();
            String jsonPembelianObat = gson.toJson(ListPembelianObat);
            out.println(jsonPembelianObat);
            System.out.println("Berhasil Get Data : "+jsonPembelianObat);
        }

        else if ("insert".equals(page)) {
            
        }
        else if ("show".equals(page)) {
            
        }

        else if ("update".equals(page)) {
            pembelian_obat_model po = new pembelian_obat_model();
            po.setId_trans(request.getParameter("id_trans"));
            po.setId_supplier(request.getParameter("id_supplier"));
            po.setNo_faktur(request.getParameter("no_faktur"));
            po.setTgl_faktur(request.getParameter("tgl_faktur"));
            po.setId_obat(request.getParameter("id_obat"));
            po.setHarga_beli(Double.parseDouble(request.getParameter("harga_beli")));
            po.setJumlah(Integer.parseInt(request.getParameter("jumlah")));
            po.setKeterangan(request.getParameter("keterangan"));
            po.setTgl_expired(request.getParameter("tgl_expired"));
            po.setId_user(request.getParameter("id_user"));
            dao.save(po, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil diupdate");
        }

        else if ("delete".equals(page)) {
            dao.delete(request.getParameter("id_trans"));

            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil dihapus");
        }
    }
}
