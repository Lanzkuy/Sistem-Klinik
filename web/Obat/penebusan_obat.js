$(document).ready(function() {
    var jen_pembayaran, harga, tgl_pem, jumlah, id_pasien, id_resep, id_obat;

    function getInputValue()
    {
        jen_pembayaran = $("#jen_pembayaran").val();
        harga = $("#harga").val();
        tgl_pem = $("#tgl_pem").val();
        jumlah=$("#jumlah").val();
        id_pasien = $("#id_pasien").val();
        id_resep = $("#id_resep").val();
        id_obat = $("#id_obat").val();
    }

    function Clear()
    {
        $("#jen_pembayaran").val("");
        $("#harga").val("");
        $("#tgl_pem").val("");
        $("#jumlah").val("");
        $("#id_pasien").val("");
        $("#id_resep").val("");
        $("#id_obat").val("");
    }

    $('#btnSave').on('click',function()
    {
        if(harga==="")
        {
            alert("Harga must be filled");
            $("#harga").focus();
        }
        else if(jen_pembayaran==="")
        {
            alert("Id Supply must be filled");
            $("#jen_pembayaran").focus();
        }
        else if(tgl_pem==="")
        {
            alert("faktur must be filled");
            $("#tgl_pem").focus();
        }
        else if(jumlah==="")
        {
            alert("Jumlah must be filled");
            $("#jumlah").focus();
        }
        else if(id_pasien==="")
        {
            alert("Tanggal faktur must be filled");
            $("#id_pasien").focus();
        }
        else if(id_resep==="")
        {
            alert("Keterangan must be filled");
            $("#id_resep").focus();
        }
        else if(id_obat==="")
        {
            alert("id obat must be filled");
            $("#id_obat").focus();
        }
        else
        {
            if(page==="insert")
            {
                getInputValue();
                $.post("/Klinik/UserController",
                {
                    page:page,
                    jen_pembayaran:jen_pembayaran,
                    harga:harga,
                    id_resep:id_resep,
                    id_pasien:id_pasien,
                    jumlah:jumlah,
                    tgl_pem:tgl_pem,
                    id_obat:id_obat,
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
