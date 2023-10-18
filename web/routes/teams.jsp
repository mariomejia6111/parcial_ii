<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession" %>
<%@page import="models.Team" %>
<%@page import="models.User" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Equipos</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
        <link rel="stylesheet" href="css/adminlte.min.css"/>
        <style>
            td, tr {
                vertical-align: middle !important;
            }
        </style>
    </head>
    <body class="hold-transition dark-mode sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">
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
            <div class="container" style="margin-top: 90px;">
                <%
                    String alertTitle = (String)request.getAttribute("alert-title");
                    if (alertTitle != null) {
                        String alertIcon = (String)request.getAttribute("alert-icon");
                        String alertContent = (String)request.getAttribute("alert-message");
                        String alertType = (String)request.getAttribute("alert-type");
                        
                %>
                    <%@include file="alert.jsp" %>
                <% } %>
                <div class="card">
                    <div class="card-header border-transparent">
                      <h3 class="card-title">Equipos Ingresados</h3>
                    </div>
                    <div class="card-body p-0">
                      <div class="table-responsive">
                        <table class="table m-0">
                          <thead>
                          <tr>
                            <th>Nombre</th>
                            <th>Imagen</th>
                            <th>Acciones</th>
                          </tr>
                          </thead>
                          <tbody>
                              <%
                                  List<Team> teams = (List)request.getAttribute("teams");
                                    if (teams == null) {
                                        request.getRequestDispatcher("Teams").forward(request, response);
                                    }
                                    for (Team team: teams) {
                              %>
                                <tr>
                                    <td><%= team.getTeamName() %></td>
                                    <td><img width="100" src="<%= request.getContextPath() + team.getTeamImg() %>" alt="<%= team.getTeamName() %>"/></td>
                                    <td>
                                        <div class="row justify-content-center">
                                            <div class="col-md-6">
                                                <form action="UpdateTeam" method="GET">
                                                    <input type="hidden" name="id" value="<%= team.getId() %>"/>
                                                    <input type="submit" class="btn btn-primary" value="Actualizar"/>
                                                </form>
                                            </div>
                                            <div class="col-md-6">
                                                <form action="DeleteTeam" method="POST">
                                                    <input type="hidden" name="id" value="<%= team.getId() %>"/>
                                                    <input type="hidden" name="img" value="<%= team.getTeamImg() %>"/>
                                                    <input type="submit" class="btn btn-primary" value="Eliminar"/>
                                                </form>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                              <% } %>
                          </tbody>
                        </table>
                      </div>
                    </div>
                    <div class="card-footer clearfix">
                      <a href="./team_form.jsp" class="btn btn-sm btn-info float-left">Ingresar Nuevo Equipo</a>
                    </div>
                </div>
            </div>
            <%@include file="./footer.jsp" %>
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
    </body>
</html>
