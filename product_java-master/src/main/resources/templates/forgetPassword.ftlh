<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Boot Email using FreeMarker</title>
    <style>
       .logo
       {
           margin-top:10px;
           background:#008080;
           color:white;
           font-size:24px;
           box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
           font-weight:bold;
       }
       .msg
       {
         font-weight:bold;
       }

    </style>
</head>
<body>
        <div class="logo"style="">HIVE BY HASHEDIN</div>
        <div class="msg" style="font-size:16px;">Hi ${name} </div>
        <div class="msg" style="font-size:14px;">Your new Password to access HIVE is </b></div>
        <h3>${password}</h3>
        <h4>Dont forget to change your password after login</h4>
        <h4>You can login in your account</h4>
        <br/>
        <a href="https://hivefrontend-dot-hu18-groupb-java.et.r.appspot.com/mainscreen/home">Go to your profile</a>
        <div class="msg"> Have a nice day..!</div>
</body>
</html>