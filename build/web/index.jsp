<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de Sesión</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
        <link rel="stylesheet" href="css/adminlte.min.css"/>
    </head>
    <body class="hold-transition dark-mode login-page">
        <!-- Session Handler -->
        <%
            HttpSession authSession = request.getSession(false);
            if (authSession != null && authSession.getAttribute("logged") != null) {
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            }
            String alertTitle = (String)request.getAttribute("alert-title");
                if (alertTitle != null) {
                    String alertIcon = (String)request.getAttribute("alert-icon");
                    String alertContent = (String)request.getAttribute("alert-message");
                    String alertType = (String)request.getAttribute("alert-type");
        %>
            <%@include file="alert.jsp" %>
        <% } %>
        <div class="login-box">
            <div class="login-logo">
                <img width="150" src="./img/logo.png" alt="APP LOGO"/>
            </div>
            <div class="card">
                <div class="card-body login-card-body">
                    <p class="login-box-msg">Inicio de Sesión</p>
                    <form action="LoginHandler" method="POST">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="Username" name="username" required autocomplete="off">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" class="form-control" placeholder="Password" name="password" required autocomplete="off">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <input type="submit" value="Iniciar Sesión" class="btn btn-primary btn-block">
                            </div>
                        </div>
                    </form>
                    <div>
                        <a href="./user_form.jsp">Registrarse</a>
                    </div>
                </div>
            </div>
        </div>
        <script src="plugins/jquery/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
        <script src="js/adminlte.min.js"></script>
        <script src="plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
        <script src="plugins/raphael/raphael.min.js"></script>
        <script src="plugins/jquery-mapael/jquery.mapael.min.js"></script>
        <script src="plugins/jquery-mapael/maps/usa_states.min.js"></script>
        <script src="./js/adminlte.min.js"></script>
    </body>
</html>