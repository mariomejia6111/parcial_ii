<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.User"%>
<%@page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <%
            boolean actionType = (request.getAttribute("action") == null) ? true : false;
            User updatingUser = null;
            HttpSession authSession = request.getSession(false);
            if (!actionType) {
                updatingUser = (User)request.getAttribute("user");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= (actionType) ? "Creación" : "Actualización" %> de Usuario</title>
        <base href="${pageContext.request.contextPath}/"/>
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="css/adminlte.min.css"/>
    </head>
    <body class="hold-transition dark-mode sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">
        <div class="wrapper">
            <% 
                if (authSession != null && authSession.getAttribute("logged") != null) {
                    User user = (User)authSession.getAttribute("logged");
            %>
                <%@include file="menu.jsp" %>
                <%@include file="sidebar.jsp" %>
            <%
                }
            %>
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
                                Formulario de Usuario
                            </div>
                            <div class="card-body">
                                <form action="<%= (actionType) ? "NewUser" : "UpdateUser" %>" method="POST">
                                    <% if (!actionType) { %> 
                                        <input type="hidden" name="id" value="<%= updatingUser.getId() %>"/>
                                    <% } %>
                                    <% if (authSession == null || authSession.getAttribute("logged") == null) { %>
                                        <input type="hidden" name="get-auth" value="1"/>
                                    <% } %>
                                    <div class="form-group">
                                        <label for="email">Correo:</label>
                                        <input type="email" class="form-control" id="email" name="email" value="<%= (actionType) ? "" : updatingUser.getEmail() %>" autocomplete="off" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="username">Nombre de Usuario:</label>
                                        <input type="text" class="form-control" id="username" name="username" value="<%= (actionType) ? "" : updatingUser.getUsername() %>" autocomplete="off" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Contraseña:</label>
                                        <input type="text" class="form-control" id="password" name="password" value="<%= (actionType) ? "" : updatingUser.getPassword() %>" autocomplete="off" required>
                                    </div>
                                    <input type="submit" class="btn btn-primary" value="<%= (actionType) ? "Registrar" : "Actualizar" %> Usuario"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%
                if (authSession != null && authSession.getAttribute("logged") != null) {
            %>
                <%@include file="./footer.jsp" %>
            <% } %>
        </div>
        <script src="plugins/jquery/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="js/adminlte.min.js"></script>
    </body>
</html>