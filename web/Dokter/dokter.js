$(document).ready(function() {
    var iddokter, nama_dokter, id_poli, tgl_lahir, specialis, jkel, no_hp, no_ktp, no_npwp, email, password ,alamat, page;
    
    function getInputValue()
    {
        nama_dokter = $("#nama_dokter").val();
        id_poli = $("#id_poli").val();
        tgl_lahir=$("#tgl_lahir").val();
        specialis = $("#spesialis").val();
        jkel = $("#gender").children("option:selected").val();
        no_hp = $("#no_hp").val();
        no_ktp = $("#no_ktp").val();
        no_npwp = $("#no_npwp").val();
        email = $("#email").val();
        password = $("#password").val();
        alamat = $("#alamat").val();
    }
    
    function Clear()
    {
        $("#nama_dokter").val("");
        $("#id_poli").val("");
        $("#tgl_lahir").val("");
        $("#spesialis").val("");
        $("#no_hp").val("");
        $("#no_ktp").val("");
        $("#no_npwp").val("");
        $("#email").val("");
        $("#password").val("");
        $("#alamat").val("");
    }
    
    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#bodyTableDokter tr").filter(function() {
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
        if(nama_dokter==="")
        {
            alert("Name must be filled");
            $("#nama_dokter").focus();
        }
        else if(id_poli==="")
        {
            alert("Poli must be filled");
            $("#id_poli").focus();
        }
        else if(email==="")
        {
            alert("Email must be filled");
            $("#id_poli").focus();
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
        else if(no_ktp==="")
        {
            alert("No KTP must be filled");
            $("#no_ktp").focus();
        }
        else if(no_npwp==="")
        {
            alert("NPWP must be filled");
            $("#no_npwp").focus();
        }
        else if(specialis==="")
        {
            alert("Specialis must be filled");
            $("#specialis").focus();
        }
        else if(password==="")
        {
            alert("Password must be filled");
            $("#password").focus();
        }
        else
        {
            if(page==="insert")
            {
                getInputValue();
                $.post("/Klinik/UserController",
                {
                    page:page,
                    nama_user:nama_dokter,
                    no_hp:no_hp,
                    alamat:alamat,
                    no_ktp:no_ktp,
                    password:password,
                    id_role:"3"
                },
                function(data)
                {
                    alert(data);
                    if(data!=="")
                    {
                        alert("Data Added");
                        $.post("/Klinik/DokterController",
                        {
                            page:page,
                            nama_dokter:nama_dokter,
                            tgl_lahir:tgl_lahir,
                            id_poli:id_poli,
                            jenis_kelamin:jkel,
                            no_hp:no_hp,
                            alamat:alamat,
                            no_ktp:no_ktp,
                            no_npwp:no_npwp,
                            specialis:specialis,
                            password:password,
                            email:email,
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
                $.post("/Klinik/DokterController",
                {
                    page:page,
                    id_dokter:iddokter,
                    nama_dokter:nama_dokter,
                    tgl_lahir:tgl_lahir,
                    id_poli:id_poli,
                    jenis_kelamin:jkel,
                    no_hp:no_hp,
                    alamat:alamat,
                    no_ktp:no_ktp,
                    no_npwp:no_npwp,
                    specialis:specialis,
                    password:password,
                    email:email,
                    user_id:sessionStorage.getItem('id_user'),
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
    
    $("#tableDokter tbody").on('click','#btnEdit',function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").hide();
        $("#titleEdit").show(); 
        page='byid';
        
        var row=$(this).closest('tr');
        var dokter_id=row.find("td:eq(0)").text();
        
        $.post("/Klinik/DokterController",
        {
            page:page,
            id_dokter:dokter_id
        },
        function(data)
        {
            iddokter=data.id_dokter;
            $("#nama_dokter").val(data.nama_dokter);
            $("#tgl_lahir").val(data.tgl_lahir);
            $("#jenis_kelamin").val(data.jenis_kelamin);
            $("#no_hp").val(data.no_hp);
            $("#no_ktp").val(data.no_ktp);
            $("#no_npwp").val(data.no_npwp);
            $("#spesialis").val(data.specialis);
            $("#email").val(data.email);
            $("#password").val(data.password);
            $("#id_poli").val(data.id_poli);
            $("#alamat").val(data.alamat);
        });
        page="update";
    });
    
    $("#tableDokter tbody").on('click','#btnDel',function()
    {
        var row=$(this).closest('tr');
        var id_dokter=row.find("td:eq(0)").text();
        page="delete";
        
        if(confirm("Want to delete : "+id_dokter+" ?"))
        {
            $.post("/Klinik/DokterController",
            {
                page:page,
                id_dokter:id_dokter
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
        url: "/Klinik/DokterController",
        method: "GET",
        dataType: "json",
        success:
          function(data)
          {   
            $("#tableDokter").dataTable({
              serverside: true,
              processing: false,
              data: data,
              sort: true,
              searching: false,
              paging: true,
              bInfo:false,
              columns: 
              [
                {'data': 'id_dokter'},
                {'data': 'nama_dokter'},
                {'data': 'tgl_lahir'}, 
                {'data': 'jenis_kelamin'},
                {'data': 'no_hp'},
                {'data': 'no_ktp'},
                {'data': 'no_npwp'},
                {'data': 'specialis'},
                {'data': 'email'},
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
