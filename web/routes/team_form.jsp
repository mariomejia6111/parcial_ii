<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession" %>
<%@page import="models.Team"%>
<%@page import="models.User" %>
<!DOCTYPE html>
<html>
    <head>
        <%
            boolean actionType = (request.getAttribute("action") == null) ? true : false;
            Team team = null;
            if (!actionType) {
                team = (Team)request.getAttribute("team");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= (actionType) ? "Registro" : "Edición" %> de equipo</title>
        <base href="${pageContext.request.contextPath}/"/>
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="css/adminlte.min.css"/>
    </head>
    <body class="hold-transition dark-mode sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">
        <div class="wrapper">
            <%
                /*User user = null;
                HttpSession authSession = request.getSession(false);
                if (authSession == null || authSession.getAttribute("logged") == null) {
                    request.setAttribute("alert-type", "danger");
                    request.setAttribute("alert-title", "Alto");
                    request.setAttribute("alert-icon", "ban");
                    request.setAttribute("alert-message", "Inicia Sesión primero");
                    request.getRequestDispatcher("/").forward(request, response);
                } else {
                    user = (User)authSession.getAttribute("logged");
                }*/
            %>
            <%@include file="menu.jsp" %>
            <!--< %@include file="sidebar.jsp" %>-->
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
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                Formulario de Equipo
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/<%= (actionType) ? "NewTeam" : "UpdateTeam" %>" method="POST" enctype="multipart/form-data">
                                    <% if (!actionType) { %> 
                                        <input type="hidden" name="id" value="<%= team.getId() %>"/>
                                    <% } %>
                                    <div class="form-group">
                                        <label for="team-name-id" class="form-label">Nombre del equipo:</label>
                                        <input type="text" class="form-control" id="teamName" name="team-name" value="<%= (actionType) ? "" : team.getTeamName() %>" autocomplete="off" required>
                                    </div>
                                    <% if (!actionType) { %>
                                        <div class="form-group">
                                            <h5>Imagen Actual:</h5>
                                            <img width="100" src="<%= request.getContextPath() + team.getTeamImg() %>"/>
                                        </div>
                                    <% } %>
                                    <div class="form-group">
                                        <label for="team-img-id" class="form-label">Imagen del equipo:</label>
                                        <input type="file" class="form-control" id="teamImage" name="team-img" accept="image/*" <%= (actionType) ? "required" : "" %>>
                                    </div>
                                    <input type="submit" class="btn btn-primary" value="<%= (actionType) ? "Registrar" : "Editar" %> Equipo"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
        <script src="plugins/jquery/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="js/adminlte.min.js"></script>
    </body>
</html>