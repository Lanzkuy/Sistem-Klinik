$(document).ready(function() {
    var id_layanan, des_layanan;
    
    function getInputValue()
    {
        id_layanan = $("#id_layanan").val();
        des_layanan = $("#des_layanan").val();
    }

    function Clear()
    {
        $("#id_layanan").val("");
        $("#des_layanan").val("");
    }

    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#bodyTableLayanan tr").filter(function() {
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
        if(id_layanan==="")
        {
            alert("Id must be filled");
            $("#id_layanan").focus();
        }
        else if(des_layanan==="")
        {
            alert("Description must be filled");
            $("#des_layanan").focus();
        }
        else
        {
            if(page==="insert")
            {
                getInputValue();
                $.post("/Klinik/LayananController",
                {
                    page:page,
                    id_layanan:id_layanan,
                    des_layanan:des_layanan
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
                $.post("/Klinik/LayananController",
                {
                    page:page,
                    id_layanan:id_layanan,
                    des_layanan:des_layanan
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

    $("#tableLayanan tbody").on('click','#btnEdit',function()
    {
        $("#myModal").modal('show');
        $("#titleAdd").hide();
        $("#titleEdit").show(); 
        page='byid';

        var row=$(this).closest('tr');
        var layanan_id=row.find("td:eq(0)").text();
        
        $.post("/Klinik/LayananController",
        {
            page:page,
            id_layanan:layanan_id
        },
        function(data)
        {
            id_layanan=data.id_layanan;
            $("#id_layanan").val(data.id_layanan);
            $("#des_layanan").val(data.des_layanan);
        });
        page="update";
    });

    $("#tableLayanan tbody").on('click','#btnDel',function()
    {
        var row=$(this).closest('tr');
        var layanan_id=row.find("td:eq(0)").text();
        page="delete";
        
        if(confirm("Want to delete : "+layanan_id+" ?"))
        {
            $.post("/Klinik/LayananController",
            {
                page:page,
                id_layanan:layanan_id
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
        url: "/Klinik/LayananController",
        method: "GET",
        dataType: "json",
        success:
          function(data)
          {   
            $("#tableLayanan").dataTable({
              serverside: true,
              processing: false,
              data: data,
              sort: true,
              searching: false,
              paging: true,
              bInfo:false,
              columns: 
              [
                {'data': 'id_layanan'},
                {'data': 'des_layanan'},
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