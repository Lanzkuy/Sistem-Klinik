$(document).ready(function () {
    var id_pembayaran,tgl_pembayaran,id_pasien,id_resep,jenis_pembayaran, id_obat,harga,jumlah,loadBayarObat;
    
    function getInputValue()
    {
        tgl_pembayaran = $("#tgl_pembayaran").val();
        id_pasien = $("#pasien_id").val();
        id_resep = $("#resep_id").val();
        jenis_pembayaran = $("#jenis_pembayaran").children("option:selected").val();
        id_obat = $("#obat_id").val();
        harga = $("#harga").val();
        jumlah = $("#jumlah").val();
    }
    
    function loadBayarObat() 
    {
        loadBayarObat = 1;
        $.ajax({
          url: "/Klinik/BayarObatController", 
          method: "GET", 
          dataType: "json",
          success:
            function(data){
                $("#tableBuyDrugs").dataTable({
                serverside: true,
                processing: false,
                data: data,
                sort: false,
                searching: false,
                paging: false,
                bInfo:false,
                columns: 
                [
                  {'data': 'id_pembayaran'},
                  {'data': 'tgl_pembayaran'},
                  {'data': 'id_pasien'},
                  {'data': 'id_resep'},
                  {'data': 'jenis_pembayaran'},
                ]
              });
            },
            error: function(err) {
                console.log(err);
            }
        });
    }
    
    $('#pObat').click(function()
    {
        $("#drugsModal").modal('show');
        $("#titleAdd").show();
        $("#titleEdit").hide();
        if (loadBayarObat !== 1) {
            loadBayarObat();
        }
    });
    
    $('#btnDrugsCancel').click(function()
    {
        $("#drugsModal").modal('hide');
    });
    
    $('#btnDrugsSave').on('click',function()
    {
        if(tgl_pembayaran ==="")
        {
            alert("Tanggal Pembayaran must be filled");
            $("#tgl_pembayaran").focus();
        }
        else if(id_pasien==="")
        {
            alert("Pasien ID must be filled");
            $("#pasien_id").focus();
        }
        else if(id_resep==="")
        {
            alert("Resep ID must be filled");
            $("#resep_id").focus();
        }
        else if(id_obat==="")
        {
            alert("Obat ID must be filled");
            $("#obat_id").focus();
        }
        else if(harga==="")
        {
            alert("Harga must be filled");
            $("#harga").focus();
        }
        else if(jumlah==="")
        {
            alert("Jumlah must be filled");
            $("#jumlah").focus();
        }
        else if(jenis_pembayaran==="")
        {
            alert("Jenis Pembayaran must be filled");
            $("#jenis_pembayaran").focus();
        }
        else 
        {
            getInputValue();
            $.post("/Klinik/BayarObatController",
            {
                page:"insert",
                id_pasien:id_pasien,
                id_resep:id_resep,
                jenis_pembayaran:jenis_pembayaran,
                tgl_pembayaran:tgl_pembayaran,
                id_obat:id_obat,
                harga:harga,
                jumlah:jumlah,
                user_id:sessionStorage.getItem('id_user')
            },
            function(data)
            {
                alert(data);
                if(data==="Data Added")
                {
                    location.reload();
                }
            });
        }
    });
});
