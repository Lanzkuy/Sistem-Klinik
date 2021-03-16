$(document).ready(function () {
    console.log(sessionStorage.getItem('isLogin'));
    if (sessionStorage.getItem('isLogin')) {
        alert('You are already login');
        if(sessionStorage.getItem('role')== 1 || sessionStorage.getItem('role')== 3 || sessionStorage.getItem('role')== 4)
        {
            document.location.href = '/Klinik/Dashboard-Petugas/dashboard.html';
        }
        else if(sessionStorage.getItem('role')== 2)
        {
            document.location.href = '/Klinik/Dashboard-Pasien/dashboard-pasien.html';
        }
    }
    
    var id, pass;
    
    function getInputValue() 
    {
        id = $('#id').val();
        pass = $('#password').val();
    }
        
    $('#btnLogin').click(function ()
    {
        getInputValue();
        if(id==="")
        {
            alert("ID field must be filled");
        }
        else if(pass==="")
        {
            alert("Password field must be filled");
        }
        else
        {
            $.post('/Klinik/UserController', 
            {
                page: 'login',
                id_user: id,
                password: pass
            }, 
            function (data,status) 
            {
                console.log(data);
                if(data.id_user==="undefined")
                {
                    alert('ID or Password is wrong !!');
                }
                else if(data.deleted_at==="undefined")
                {
                    alert('This Account was Not Active !!');
                }
                else
                    {
                        if(data.id_role==="1" || data.id_role==="3" || data.id_role==="4")
                        {
                            sessionStorage.setItem('isLogin', true);
                            sessionStorage.setItem('role', data.id_role);
                            sessionStorage.setItem('id_user', data.id_user);
                            alert('Login Success !!');
                            document.location.href = '/Klinik/Dashboard-Petugas/dashboard.html';
                        }
                        else if(data.id_role==="2")
                        {
                            sessionStorage.setItem('isLogin', true);
                            sessionStorage.setItem('role', data.id_role);
                            sessionStorage.setItem('id_user', data.id_user);
                            alert('Login Success !!');
                            document.location.href = '/Klinik/Dashboard-Pasien/dashboard-pasien.html';
                        }
                    }
            });
        }
    });
});