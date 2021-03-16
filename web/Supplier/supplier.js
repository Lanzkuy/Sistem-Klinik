    $(document).ready(function() {
    var idsupplier, nama_supplier, alamat, no_hp, email;
    
    function getInputValue()
    {
        nama_supplier = $("#nama_supplier").val();
        alamat = $("#alamat").val();
        no_hp=$("#no_telepon").val();
        email = $("#email").val();
    }
    
    function Clear()
    {
        $("#nama_supplier").val("");
        $("#alamat").val("");
        $("#no_telepon").val("");
        $("#email").val("");
    }
    
    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#bodyTableSupplier tr").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
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
        if(nama_supplier==="")
        {
            alert("Name must be filled");
            $("#nama_user").focus();
        }
        else if(no_hp==="")
        {
            alert("Password must be filled");
            $("#password").focus();
        }
        else if(alamat==="")
        {
            alert("Alamat must be filled");
            $("#alamat").focus();
        }
        else if(email==="")
        {
            alert("No Hp must be filled");
            $("#no_hp").focus();
        }
        else
        {
            if(page==="insert")
            {
                getInputValue();
                
                $.post("/Klinik/SupplierController",
                {
                    page:page,
                    nama_supplier:nama_supplier,
                    no_telepon:no_hp,
                    alamat:alamat,
                    email:email,
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
                $.post("/Klinik/SupplierController",
                {
                    page:page,
                    id_supplier:idsupplier,
                    nama_supplier:nama_supplier,
                    no_telepon:no_hp,
                    alamat:alamat,
                    email:email,
                    user_id:sessionStorage.getItem('user_id')
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
    
    $("#tableSupplier tbody").on('click','#btnEdit',function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").hide();
        $("#titleEdit").show(); 
        page='byid';
        
        var row=$(this).closest('tr');
        var id_supplier=row.find("td:eq(0)").text();
        
        $.post("/Klinik/SupplierController",
        {
            page:page,
            id_supplier:id_supplier
        },
        function(data)
        {
            idsupplier=data.id_supplier;
            $("#nama_supplier").val(data.nama_supplier);
            $("#no_telepon").val(data.no_telepon);
            $("#alamat").val(data.alamat);
            $("#email").val(data.email);
        });
        page="update";
    });
    
    $("#tableSupplier tbody").on('click','#btnDel',function()
    {
        var row=$(this).closest('tr');
        var id_supplier=row.find("td:eq(0)").text();
        page="delete";
        
        if(confirm("Want to delete : "+id_supplier+" ?"))
        {
            $.post("/Klinik/SupplierController",
            {
                page:page,
                id_supplier:id_supplier
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
        url: "/Klinik/SupplierController",
        method: "GET",
        dataType: "json",
        success:
          function(data)
          {   
            $("#tableSupplier").dataTable({
              serverside: true,
              processing: false,
              data: data,
              sort: false,
              searching: false,
              paging: false,
              columns: 
              [
                {'data': 'id_supplier'},
                {'data': 'nama_supplier'},
                {'data': 'alamat'}, 
                {'data': 'no_telepon'},
                {'data': 'email'},
                {'data':null, 'className':'dt-right',
                    'mRender':function(o)
                    {
                        return "<div class='text-center'><a class='btn btn-outline-success btn-sm'"
                                +" id='btnEdit'>Edit</a> "
                                +"&nbsp;&nbsp;"
                                +"<a class='btn btn-outline-danger btn-sm' "
                                +"id='btnDel'>Hapus</a></div>";
                    }
                }
              ]
            });
        }
    });
});
