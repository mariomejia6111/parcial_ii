<%@page import="models.User" %>
<%@page import="models.Player" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jugadores</title>
        <base href="${pageContext.request.contextPath}/"/>
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="css/adminlte.min.css"/>
        <style> td, tr { vertical-align: middle !important; } .full-option-width { width: 100%; } </style>
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
            <!--<@include file="sidebar.jsp" %>-->
            <div class="container" style="margin-top: 90px; margin-bottom: 90px;">
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
                      <h3 class="card-title">Jugadores Registrados</h3>
                    </div>
                    <div class="card-body p-0">
                      <div class="table-responsive">
                        <table class="table m-0">
                          <thead>
                          <tr>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>Fotografía</th>
                            <th>Posición</th>
                            <th>Dorsal</th>
                            <th>Velocidad</th>
                            <th>Fuerza</th>
                            <th>Resistencia</th>
                            <th>Arrebate</th>
                            <th>Control</th>
                            <th>Equipo</th>
                            <th>Acciones</th>
                          </tr>
                          </thead>
                          <tbody>
                              <%
                                  List<Player> players = (List)request.getAttribute("players");
                                    if (players == null) {
                                        request.getRequestDispatcher("Players").forward(request, response);
                                    }
                                    for (Player player: players) {
                              %>
                                <tr>
                                    <td><%= player.getFirstName() %></td>
                                    <td><%= player.getLastName() %></td>
                                    <td><img width="100" src="<%= request.getContextPath() + player.getPhoto() %>" alt="<%= player.getFirstName() + " " + player.getLastName() %>"/></td>
                                    <td><%= player.getPosition() %></td>
                                    <td><%= player.getJerseyNumber() %></td>
                                    <td><%= player.getSpeed() %></td>
                                    <td><%= player.getStrength() %></td>
                                    <td><%= player.getEndurance() %></td>
                                    <td><%= player.getDribble() %></td>
                                    <td><%= player.getControl() %></td>
                                    <td><%= player.getTeam().getTeamName() %></td>
                                    <td style="width: 200px;">
                                        <div style="display: flex; flex-direction: column; gap: 10px; align-items: center;">
                                            <div class="full-option-width">
                                                <form action="${pageContext.request.contextPath}/UpdatePlayer" method="GET">
                                                    <input type="hidden" name="id" value="<%= player.getId() %>"/>
                                                    <input type="submit" class="btn btn-primary" value="Actualizar"/>
                                                </form>
                                            </div>
                                            <div class="full-option-width">
                                                <form action="${pageContext.request.contextPath}/DeletePlayer" method="POST">
                                                    <input type="hidden" name="id" value="<%= player.getId() %>"/>
                                                    <input type="hidden" name="img" value="<%= player.getPhoto() %>"/>
                                                    <input style="width: 100%;" type="submit" class="btn btn-primary" value="Eliminar"/>
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
                      <a href="routes/team_form.jsp" class="btn btn-sm btn-info float-left">Nuevo Jugador</a>
                    </div>
                </div>
            </div>
            <%@include file="./footer.jsp" %>
        </div>
        <script src="plugins/jquery/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="js/adminlte.min.js"></script>
    </body>
</html>