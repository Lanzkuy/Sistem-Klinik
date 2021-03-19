$(document).ready(function() {
    var id_sup, harga, no_faktur, jumlah, tgl_fak, ket, id_obat,tgl_exp;

    function getInputValue()
    {
        id_sup = $("#id_sup").val();
        harga = $("#harga").val();
        no_faktur = $("#no_faktur").val();
        jumlah=$("#jumlah").val();
        tgl_fak = $("#tgl_fak").val();
        ket = $("#ket").val();
        id_obat = $("#id_obat").val();
        tgl_exp = $("#tgl_exp").val();
    }

    function Clear()
    {
        $("#id_sup").val("");
        $("#harga").val("");
        $("#no_faktur").val("");
        $("#jumlah").val("");
        $("#tgl_fak").val("");
        $("#ket").val("");
        $("#id_obat").val("");
        $("#tgl_exp").val("");
    }

    $('#btnSave').on('click',function()
    {
        if(harga==="")
        {
            alert("Harga must be filled");
            $("#harga").focus();
        }
        else if(id_sup==="")
        {
            alert("Id Supply must be filled");
            $("#id_sup").focus();
        }
        else if(no_faktur==="")
        {
            alert("faktur must be filled");
            $("#no_faktur").focus();
        }
        else if(jumlah==="")
        {
            alert("Jumlah must be filled");
            $("#jumlah").focus();
        }
        else if(tgl_fak==="")
        {
            alert("Tanggal faktur must be filled");
            $("#tgl_fak").focus();
        }
        else if(ket==="")
        {
            alert("Keterangan must be filled");
            $("#ket").focus();
        }
        else if(id_obat==="")
        {
            alert("id obat must be filled");
            $("#id_obat").focus();
        }
        else if(tgl_exp==="")
        {
            alert("tanggal Expired must be filled");
            $("#tgl_exp").focus();
        }
        else
        {
            if(page==="insert")
            {
                getInputValue();
                $.post("/Klinik/UserController",
                {
                    page:page,
                    id_sup:id_sup,
                    harga:harga,
                    ket:ket,
                    tgl_fak:tgl_fak,
                    jumlah:jumlah,
                    no_faktur:no_faktur,
                    id_obat:id_obat,
                    tgl_exp:tgl_exp
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
        }
    });


});
