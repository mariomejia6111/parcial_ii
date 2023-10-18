<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession" %>
<%@page import="models.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
        <link rel="stylesheet" href="css/adminlte.min.css"/>
        <style>
            #user-list > li, #team-list > li {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
        </style>
    </head>
    <body class="hold-transition dark-mode sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">
        <!-- wrapper -->
        <div class="wrapper">
            <%
                User user = null;
                HttpSession authSession = request.getSession(false);
                if (authSession == null || authSession.getAttribute("logged") == null) {
                    request.setAttribute("alert-type", "danger");
                    request.setAttribute("alert-title", "Alto");
                    request.setAttribute("alert-icon", "ban");
                    request.setAttribute("alert-message", "Inicia Sesión primero");
                    request.getRequestDispatcher("/").forward(request, response);
                } else {
                    user = (User)authSession.getAttribute("logged");
                }
            %>
            <%@include file="menu.jsp" %>
            <%@include file="sidebar.jsp" %>            
            <!-- main wrapper -->
            <div class="content-wrapper" style="margin-top: 90px;">
                <section class="content">
                    <div class="container-fluid">
                        <!-- Session Handler -->
                        <%
                            String alertTitle = (String)request.getAttribute("alert-title");
                            if (alertTitle != null) {
                                String alertIcon = (String)request.getAttribute("alert-icon");
                                String alertContent = (String)request.getAttribute("alert-message");
                                String alertType = (String)request.getAttribute("alert-type");
                        %>
                            <%@include file="alert.jsp" %>
                        <% } %>
                        
                        <!-- actual content -->
                        <div class="row">
                            <div class="col-12 col-sm-6 col-md-3">
                                <div class="info-box">
                                    <span class="info-box-icon bg-success elevation-1"><i class="fas fa-users"></i></span>

                                    <div class="info-box-content">
                                        <span class="info-box-text">Equipos</span>
                                        <span id="team-count" class="info-box-number"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-sm-6 col-md-3">
                                <div class="info-box">
                                    <span class="info-box-icon bg-info elevation-1"><i class="fas fa-user"></i></span>

                                    <div class="info-box-content">
                                        <span class="info-box-text">Usuarios</span>
                                        <span id="user-count" class="info-box-number"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">Usuarios Recientes</h3>
                                    <div class="card-tools">
                                        <span class="badge badge-danger">8 Nuevos Usuarios</span>
                                        <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                            <i class="fas fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-tool" data-card-widget="remove">
                                            <i class="fas fa-times"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body p-0">
                                    <ul id="user-list" class="users-list clearfix"></ul>
                                </div>
                                <div class="card-footer text-center">
                                    <a href="Users">Ver Usuarios</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">Equipos Recientes</h3>
                                    <div class="card-tools">
                                        <span class="badge badge-danger">8 Nuevos Equipos</span>
                                        <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                            <i class="fas fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-tool" data-card-widget="remove">
                                            <i class="fas fa-times"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body p-0">
                                    <ul id="team-list" class="users-list clearfix"> </ul>
                                </div>
                                <div class="card-footer text-center">
                                    <a href="Teams">Ver Equipos</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <%@include file="footer.jsp" %>
        </div>
        <script src="plugins/jquery/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
        <script src="js/adminlte.min.js"></script>
        <script src="plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
        <script src="plugins/raphael/raphael.min.js"></script>
        <script src="plugins/jquery-mapael/jquery.mapael.min.js"></script>
        <script src="plugins/jquery-mapael/maps/usa_states.min.js"></script>
        <script src="plugins/chart.js/Chart.min.js"></script>
        <script>
            window.addEventListener('load', () => {
                fetch('Main')                    
                    .then(res => res.json())
                    .then(data => {
                        document.getElementById('team-count').textContent = data.teamCount
                        document.getElementById('user-count').textContent = data.userCount
                        generateUsers(data.users)
                        generateTeams(data.teams)
                    })
                    .catch(err => {console.log(err)})
            })
            const generateUsers = collection => {
                const ul = document.getElementById('user-list')
                collection.forEach(item => {
                    let li = document.createElement('li')
                    let img = document.createElement('img')
                    let span = document.createElement('span')
                    img.src = './img/user.png'
                    img.width = 50
                    span.textContent = item.username
                    span.className = 'user-list-name'
                    li.appendChild(img)
                    li.appendChild(span)
                    ul.appendChild(li)
                })
            }
            const generateTeams = collection => {
                const ul = document.getElementById('team-list')
                collection.forEach(item => {
                    let li = document.createElement('li')
                    let img = document.createElement('img')
                    let span = document.createElement('span')
                    img.width = 50
                    img.src = '<%= request.getContextPath() %>' + item.teamImg
                    span.textContent = item.teamName
                    span.classname = 'user-list-name'
                    li.appendChild(img)
                    li.appendChild(span)
                    ul.appendChild(li)
                })
            }
        </script>
    </body>
</html>