$(document).ready(function() {
    var iduser, nama_user, password, no_ktp, alamat, no_hp, id_role;
    
    function getInputValue()
    {
        nama_user = $("#nama_user").val();
        password = $("#password").val();
        no_ktp=$("#no_ktp").val();
        alamat = $("#alamat").val();
        no_hp = $("#no_hp").val();
        id_role = $("#id_role").val();
    }
    
    function Clear()
    {
        $("#nama_user").val("");
        $("#password").val("");
        $("#no_ktp").val("");
        $("#alamat").val("");
        $("#no_hp").val("");
        $("#id_role").val("");
    }
    
    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#bodyTableUsers tr").filter(function() {
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
        if(nama_user==="")  
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
        else if(id_role==="")
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
                    nama_user:nama_user,
                    no_hp:no_hp,
                    alamat:alamat,
                    no_ktp:no_ktp,
                    password:password,
                    id_role:id_role
                },
                function(data)
                {
                    if(Object.keys(data).length!==0)
                    {
                        alert("Data Added")
                        Clear();
                        location.reload();
                    }
                });  
            }
            else if(page==="update")
            {
                getInputValue();
                $.post("/Klinik/UserController",
                {
                    page:page,
                    id_user:iduser,
                    nama_user:nama_user,
                    no_hp:no_hp,
                    alamat:alamat,
                    no_ktp:no_ktp,
                    password:password,
                    id_role:id_role
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
    
    $("#tableUsers tbody").on('click','#btnEdit',function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").hide();
        $("#titleEdit").show(); 
        page='byid';
        
        var row=$(this).closest('tr');
        var user_id=row.find("td:eq(0)").text();
        
        $.post("/Klinik/UserController",
        {
            page:page,
            id_user:user_id
        },
        function(data)
        {
            iduser=data.id_user;
            $("#nama_user").val(data.nama_user);
            $("#no_hp").val(data.no_hp);
            $("#alamat").val(data.alamat);
            $("#no_ktp").val(data.no_ktp);
            $("#password").val(data.password);
            $("#id_role").val(data.id_role);
        });
        page="update";
    });
    
    $("#tableUsers tbody").on('click','#btnDel',function()
    {
        var row=$(this).closest('tr');
        var id_user=row.find("td:eq(0)").text();
        page="delete";
        
        if(confirm("Want to delete : "+id_user+" ?"))
        {
            $.post("/Klinik/UserController",
            {
                page:page,
                id_user:id_user
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
        url: "/Klinik/UserController",
        method: "GET",
        dataType: "json",
        success:
          function(data)
          {   
            $("#tableUsers").dataTable({
              serverside: true,
              processing: false,
              data: data,
              sort: true,
              searching: false,
              paging: true,
              bInfo:false,
              columns: 
              [
                {'data': 'id_user'},
                {'data': 'nama_user'},
                {'data': 'no_ktp'}, 
                {'data': 'alamat'},
                {'data': 'no_hp'},
                {'data': 'nama_role'},
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
