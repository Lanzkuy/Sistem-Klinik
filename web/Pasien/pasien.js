$(document).ready(function() {
    var idpasien, nama_pasien, tgl_lahir, jkel, no_ktp, alamat, no_hp, gol_darah, password;
    
    function getInputValue()
    {
        nama_pasien = $("#nama_pasien").val();
        tgl_lahir = $("#tgl_lahir").val();
        jkel = $("#gender").children("option:selected").val();
        no_ktp=$("#no_ktp").val();
        alamat = $("#alamat").val();
        no_hp = $("#no_hp").val();
        gol_darah = $("#gol_darah").val();
        password = $("#password").val();
    }
    
    function Clear()
    {
        $("#nama_pasien").val("");
        $("#tgl_lahir").val("");
        $("#no_ktp").val("");
        $("#alamat").val("");
        $("#no_hp").val("");
        $("#gol_darah").val("");
        $("#password").val("");   
    }
    
    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#bodyTablePasien tr").filter(function() {
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
        if(nama_pasien==="")
        {
            alert("Name must be filled");
            $("#nama_user").focus();
        }
        else if(password==="")
        {
            alert("Password must be filled");
            $("#password").focus();
        }
        else if(no_ktp==="")
        {
            alert("No KTP must be filled");
            $("#no_ktp").focus();
        }
        else if(alamat==="")
        {
            alert("Alamat must be filled");
            $("#alamat").focus();
        }
        else if(no_hp==="")
        {
            alert("No Hp must be filled");
            $("#no_hp").focus();
        }
        else if(gol_darah==="")
        {
            alert("Role must be filled");
            $("#id_role").focus();
        }
        else
        {
            if(page==="insert")
            {
                getInputValue();
                $.post("/Klinik/UserController",
                {
                    page:page,
                    nama_user:nama_pasien,
                    no_hp:no_hp,
                    alamat:alamat,
                    no_ktp:no_ktp,
                    password:password,
                    id_role:"4"
                },
                function(data)
                {
                    if(data!=="")
                    {
                        $.post("/Klinik/PasienController",
                        {
                            page:page,
                            nama_pasien:nama_pasien,
                            tgl_lahir:tgl_lahir,
                            no_hp:no_hp,
                            alamat:alamat,
                            no_ktp:no_ktp,
                            jenis_kelamin:jkel,
                            password:password,
                            gol_darah:gol_darah,
                            user_id:data
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
            }   
            else if(page==="update")
            {
                getInputValue();
                $.post("/Klinik/PasienController",
                {
                    page:page,
                    id_pasien:idpasien,
                    nama_pasien:nama_pasien,
                    tgl_lahir:tgl_lahir,
                    no_hp:no_hp,
                    alamat:alamat,
                    no_ktp:no_ktp,
                    jenis_kelamin:jkel,
                    password:password,
                    gol_darah:gol_darah,
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
    
    $("#tablePasien tbody").on('click','#btnEdit',function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").hide();
        $("#titleEdit").show(); 
        page='byid';
        
        var row=$(this).closest('tr');
        var pasien_id=row.find("td:eq(0)").text();
        
        $.post("/Klinik/PasienController",
        {
            page:page,
            id_pasien:pasien_id
        },
        function(data)
        {
            idpasien=data.id_pasien;
            $("#nama_pasien").val(data.nama_pasien);
            $("#no_hp").val(data.no_hp);
            $("#alamat").val(data.alamat);
            $("#tgl_lahir").val(data.tgl_lahir);
            $("#no_ktp").val(data.no_ktp);
            $("#password").val(data.password);
            $("#gol_darah").val(data.gol_darah);
        });
        page="update";
    });
    
    $("#tablePasien tbody").on('click','#btnDel',function()
    {
        var row=$(this).closest('tr');
        var id_pasien=row.find("td:eq(0)").text();
        page="delete";
        
        if(confirm("Want to delete : "+id_pasien+" ?"))
        {
            $.post("/Klinik/PasienController",
            {
                page:page,
                id_pasien:id_pasien
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
        url: "/Klinik/PasienController",
        method: "GET",
        dataType: "json",
        success:
          function(data)
          {   
            $("#tablePasien").dataTable({
              serverside: true,
              processing: false,
              data: data,
              sort: false,
              searching: false,
              paging: true,
              bInfo:false,
              columns: 
              [
                {'data': 'id_pasien'},
                {'data': 'nama_pasien'},
                {'data': 'tgl_lahir'}, 
                {'data': 'jenis_kelamin'},
                {'data': 'no_ktp'},
                {'data': 'no_hp'},
                {'data': 'gol_darah'},
                {'data':null, 'className':'dt-right',
                    'mRender':function(o)
                    {
                        return "<div class='text-center'><a class='btn btn-outline-success btn-sm'"
                                +" id='btnEdit'>Update</a> "
                                +"&nbsp;&nbsp;"
                                +"<a class='btn btn-outline-danger btn-sm' "
                                +"id='btnDel'>Delete</a></div>";
                    }
                }
              ]
            });
        }
    });
});
