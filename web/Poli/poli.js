$(document).ready(function() {
    var idpoli, nama_poli;
    
    function getInputValue()
    {
        nama_poli = $("#nama_poli").val();
    }
    
    function Clear()
    {
        $("#nama_poli").val("");
    }
    
    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#bodyTablePoli tr").filter(function() {
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
        if(nama_poli==="")
        {
            alert("Name must be filled");
            $("#nama_poli").focus();
        }
        else
        {
            if(page==="insert")
            {
                getInputValue();
                $.post("/Klinik/PoliController",
                {
                    page:page,
                    nama_poli:nama_poli,
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
                $.post("/Klinik/PoliController",
                {
                    page:page,
                    id_poli:idpoli,
                    nama_poli:nama_poli,
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
    
    $("#tablePoli tbody").on('click','#btnEdit',function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").hide();
        $("#titleEdit").show(); 
        page='byid';
        
        var row=$(this).closest('tr');
        var id_poli=row.find("td:eq(0)").text();
        
        $.post("/Klinik/PoliController",
        {
            page:page,
            id_poli:id_poli
        },
        function(data)
        {
            idpoli=data.id_poli;
            $("#nama_poli").val(data.nama_poli);
        });
        page="update";
    });
    
    $("#tablePoli tbody").on('click','#btnDel',function()
    {
        var row=$(this).closest('tr');
        var id_poli=row.find("td:eq(0)").text();
        page="delete";
        
        if(confirm("Want to delete : "+id_poli+" ?"))
        {
            $.post("/Klinik/PoliController",
            {
                page:page,
                id_poli:id_poli
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
        url: "/Klinik/PoliController",
        method: "GET",
        dataType: "json",
        success:
          function(data)
          {   
            $("#tablePoli").dataTable({
              serverside: true,
              processing: false,
              data: data,
              sort: false,
              searching: false,
              paging: true,
              bInfo:false,
              columns: 
              [
                {'data': 'id_poli'},
                {'data': 'nama_poli'},
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
