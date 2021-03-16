/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import com.google.gson.Gson;
import dao.DetailBayarObatDAO;
import dao.DetailBayarObatDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.detail_bayar_obat_model;
/**
 *
 * @author Farhan Bhezni
 */
@WebServlet(name = "DetailBayarObatController", urlPatterns = {"/DetailBayarObatController"})
public class DetailBayarObatController extends HttpServlet{

    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        DetailBayarObatDAO dao = new DetailBayarObatDAO();
        Gson gson = new Gson();

        if (page == null) {
            List<detail_bayar_obat_model> ListDetailBayarObat = dao.getData();
            String jsonDetailBayarObat = gson.toJson(ListDetailBayarObat);
            out.println(jsonDetailBayarObat);
            System.out.println("Berhasil Get Data : "+jsonDetailBayarObat);
        }

        else if ("insert".equals(page)) {
            
        }
        else if ("show".equals(page)) {
            
        }

        else if ("update".equals(page)) {
            
        }

        else if ("delete".equals(page)) {
            dao.delete(request.getParameter("id_pembelian"));

            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil dihapus");
        }
    }

}
