$(document).ready(function() {
    var id_obat, nama_obat, satuan, stok, harga_jual, no_faktur, user_id, 
    loadPembelianObat, supplier_id, no_fakturPO, id_obatPO, tgl_faktur, harga_beli, jumlah, keterangan, tgl_expired;
    
    function getInputValue()
    {
        nama_obat = $("#nama_obat").val();
        satuan = $("#satuan").val();
        stok = $("#stok").val();
        harga_jual = $("#harga_jual").val();
        no_faktur = $("#no_faktur").val();
        
        supplier_id = $("#supplier_id").val();
        no_fakturPO = $("#no_fakturPO").val();
        id_obatPO = $("#obat_idPO").val();
        tgl_faktur = $("#tgl_faktur").val();
        harga_beli = $("#harga_beli").val();
        jumlah = $("#jumlah").val();
        keterangan = $("#keterangan").val();
        tgl_expired = $("#tgl_expired").val();
    }

    function Clear()
    {
        $("#nama_obat").val("");
        $("#satuan").val("");
        $("#stok").val("");
        $("#harga_jual").val("");
        $("#no_faktur").val("");
    }
    
    function loadPembelianObat() 
    {
        loadPembelianObat = 1;
        $.ajax({
          url: "/Klinik/PembelianObatController", 
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
                  {'data': 'id_trans'},
                  {'data': 'nama_supplier'},
                  {'data': 'no_faktur'},
                  {'data': 'tgl_faktur'},
                  {'data': 'nama_obat'},
                  {'data': 'harga_beli'},
                  {'data': 'jumlah'},
                  {'data': 'keterangan'},
                  {'data': 'tgl_expired'}
                ]
              });
            },
            error: function(err) {
                console.log(err);
            }
        });
    }

    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#bodyTableObat tr").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
    
    $('#btnBuy').click(function()
    {
        $("#drugsModal").modal('show');
        $("#titleAdd").show();
        if (loadPembelianObat !== 1) {
            loadPembelianObat();
        }
    });
    
    $('#btnDrugsCancel').click(function()
    {
        Clear();
        $("#drugsModal").modal('hide');
    });
    
    $('#btnDrugsSave').on('click',function()
    {
        if(supplier_id==="")
        {
            alert("Supplier ID must be filled");
            $("#nama_obat").focus();
        }
        else if(no_fakturPO ==="")
        {
            alert("No Faktur must be filled");
            $("#satuan").focus();
        }
        else if(id_obatPO==="")
        {
            alert("ID Obat must be filled");
            $("#stok").focus();
        }
        else if(harga_beli==="")
        {
            alert("Harga Beli must be filled");
            $("#harga_jual").focus();
        }
        else if(jumlah==="")
        {
            alert("Jumlah must be filled");
            $("#no_faktur").focus();
        }
        else if(keterangan==="")
        {
            alert("Keterangan must be filled");
            $("#no_faktur").focus();
        }
        else 
        {
            getInputValue();
            $.post("/Klinik/PembelianObatController",
            {
                page:"insert",
                id_supplier:supplier_id,
                no_faktur:no_fakturPO,
                tgl_faktur:tgl_faktur,
                id_obat:id_obatPO,
                harga_beli:harga_beli,
                jumlah:jumlah,
                keterangan:keterangan,
                tgl_expired:tgl_expired,
                user_id:sessionStorage.getItem('id_user')
            },
            function(data)
            {
                alert(data);
                if(data==="Data Added")
                {
                    Clear();
                    location.reload();
                }
            });
        }
    });

    $('#btnAdd').click(function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").show();
        $("#titleEdit").hide();
        page="insert";
    });

    $('#btnCancel').click(function()
    {
        Clear();
        $("#myModal").modal('hide');
    });

    $('#btnSave').on('click',function()
    {
        if(nama_obat==="")
        {
            alert("Nama Obat must be filled");
            $("#nama_obat").focus();
        }
        else if(satuan ==="")
        {
            alert("Satuan must be filled");
            $("#satuan").focus();
        }
        else if(stok==="")
        {
            alert("Stok must be filled");
            $("#stok").focus();
        }
        else if(harga_jual==="")
        {
            alert("Harga Jual must be filled");
            $("#harga_jual").focus();
        }
        else if(no_faktur==="")
        {
            alert("Password must be filled");
            $("#no_faktur").focus();
        }
        else 
        {
            if(page==="insert")
            {
                getInputValue();
                $.post("/Klinik/ObatController",
                {
                    page:page,
                    nama_obat:nama_obat,
                    satuan:satuan,
                    stok:stok,
                    harga_jual:harga_jual,
                    no_faktur:no_faktur,
                    user_id:sessionStorage.getItem('id_user')
                },
                function(data)
                {
                    alert(data);
                    if(data==="Data Added")
                    {
                        Clear();
                        location.reload();
                    }
                });
            }
            else if(page==="update")
            {
                
                getInputValue();
                $.post("/Klinik/ObatController",
                {
                    page:page,
                    id_obat:id_obat,
                    nama_obat:nama_obat,
                    satuan:satuan,
                    stok:stok,
                    harga_jual:harga_jual,
                    no_faktur:no_faktur,
                    user_id:sessionStorage.getItem('id_user')
                },
                function(data)
                {
                    alert(data);
                    if(data==="Data Updated")
                    {
                        Clear();
                        location.reload();
                    }
                });
            }
        }
    });

    $("#tableObat tbody").on('click','#btnEdit',function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").hide();
        $("#titleEdit").show(); 
        page='byid';

        var row=$(this).closest('tr');
        var obat_id=row.find("td:eq(0)").text();
        
        $.post("/Klinik/ObatController",
        {
            page:page,
            id_obat:obat_id
        },
        function(data)
        {
            id_obat=data.id_obat;
            $("#nama_obat").val(data.nama_obat);
            $("#satuan").val(data.satuan);
            $("#stok").val(data.stok);
            $("#harga_jual").val(data.harga_jual);
            $("#no_faktur").val(data.no_faktur);
            $("#id_user").val(data.user_id);
        });
        page="update";
    });

    $("#tableObat tbody").on('click','#btnDel',function()
    {
        var row=$(this).closest('tr');
        var obat_id=row.find("td:eq(0)").text();
        page="delete";
        
        if(confirm("Want to delete : "+obat_id+" ?"))
        {
            $.post("/Klinik/ObatController",
            {
                page:page,
                id_obat:obat_id
            },
            function(data)
            {
                alert(data);
                location.reload();
            });
        }
    });

    $.ajax 
    ({
        url: "/Klinik/ObatController",
        method: "GET",
        dataType: "json",
        success:
          function(data)
          {   
            $("#tableObat").dataTable({
              serverside: true,
              processing: false,
              data: data,
              sort: false,
              searching: false,
              paging: true,
              bInfo:false,
              columns: 
              [
                {'data': 'id_obat'},
                {'data': 'nama_obat'},
                {'data': 'satuan'},
                {'data': 'stok'},
                {'data': 'harga_jual'},
                {'data': 'no_faktur'},
                {'data': 'user_id'},
                {'data':null, 'className':'dt-right',
                    'mRender':function(o)
                    {
                        return "<div class='text-center'><a class='btn btn-outline-success btn-sm'"
                                +" id='btnEdit'>Update</a> "
                                +"&nbsp;&nbsp;"
                                +"<a class='btn btn-outline-danger btn-sm' "
                                +"id='btnDel'>Delete</a><div>";
                    }
                }
              ]
            });
        }
    });
});