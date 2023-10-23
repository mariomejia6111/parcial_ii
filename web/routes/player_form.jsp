<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession" %>
<%@page import="models.Player"%>
<%@page import="models.User" %>
<!DOCTYPE html>
<html>
    <head>
        <%
            boolean actionType = (request.getAttribute("action") == null) ? true : false;
            Player player = null;
            if (!actionType) {
                player = (Player)request.getAttribute("player");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= (actionType) ? "Registro" : "Edición" %> de jugador</title>
        <base href="${pageContext.request.contextPath}/"/>
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="css/adminlte.min.css"/>
        <style>
            #team-img {
                opacity: 0;
            }
            #player-team-id > option:hover > #team-img {
                opacity: 1;
            }
            #tooltip {
                display: none;
                position: absolute;
                transform: translateY(-135px);
            }
            .cloud {
                width: 100px; height: 100px;
                background-image: linear-gradient(rgb(54, 55, 59) 50%, rgb(43, 44, 49) 50%, rgb(34, 34, 36) 75%);
                clip-path: polygon(50% 100%, 0% 80%, 0% 0%, 100% 0%, 100% 80%);
            }
            #team {
                transform: translateX(25px) translateY(-80px);
            }
        </style>
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
                        <div class="card" style="margin: 75px 0px;">
                            <div class="card-header">
                                Formulario de Jugador
                            </div>
                            <div class="card-body">
                                <form id="player-form" action="${pageContext.request.contextPath}/<%= (actionType) ? "NewPlayer" : "UpdatePlayer" %>" method="POST" enctype="multipart/form-data">
                                    <% if (!actionType) { %> 
                                        <input type="hidden" name="id" value="<%= player.getId() %>"/>
                                    <% } %>
                                    <div>
                                        <h6>Datos básicos del jugador</h6>
                                        <hr style="border: 1px solid black"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-name-id" class="form-label">Nombre</label>
                                        <input type="text" class="form-control" id="player-name-id" name="player-name" value="<%= (actionType) ? "Peter" : player.getFirstName() %>" autocomplete="off" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-surname-id" class="form-label">Apellido</label>
                                        <input type="text" class="form-control" id="player-surname-id" name="player-surname" value="<%= (actionType) ? "Cheeks" : player.getLastName() %>" autocomplete="off" required>
                                    </div>
                                    <% if (!actionType) { %>
                                        <div class="form-group">
                                            <h5>Fotografía Actual:</h5>
                                            <img width="100" src="<%= request.getContextPath() + player.getPhoto() %>"/>
                                        </div>
                                    <% } %>
                                    <div class="form-group">
                                        <label for="player-photo-id" class="form-label">Fotografía</label>
                                        <input type="file" class="form-control" id="player-photo-id" name="player-photo" accept="image/*" <%= (actionType) ? "required" : "" %>/>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-position-id" class="form-label">Posición</label>
                                        <select class="form-control" id="player-position-id" name="player-position">
                                            <% 
                                                String[] playerPositions = {"Portero", "Lateral", "Defensa Central", "Centrocampista defensivo", "Centrocampista ofensivo", "Delantero lateral", "Centrodelantero"}; 
                                                for (String playerPosition: playerPositions) { %>
                                                <option value="<%= playerPosition %>"><%= playerPosition %></option>
                                            <%  } %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-jsnb-id" class="form-label">Número de Dorsal:</label>
                                        <input type="text" class="form-control" id="player-jsnm-id" name="player-jersey-number" value="<%= (actionType) ? "25" : player.getJerseyNumber() %>" autocomplete="off" required>
                                    </div>
                                    <div>
                                        <h6>Datos básicos del jugador</h6>
                                        <hr style="border: 2px solid black"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-speed-id" class="form-label">Velocidad</label>
                                        <input type="text" class="form-control" id="player-speed-id" name="player-speed" value="<%= (actionType) ? "85" : player.getSpeed() %>" autocomplete="off" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-strength-id" class="form-label">Fuerza</label>
                                        <input type="text" class="form-control" id="player-strength-id" name="player-strength" value="<%= (actionType) ? "150" : player.getStrength() %>" autocomplete="off" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-endurance-id" class="form-label">Resistencia</label>
                                        <input type="text" class="form-control" id="player-endurance-id" name="player-endurance" value="<%= (actionType) ? "25" : player.getEndurance() %>" autocomplete="off" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-dribble-id" class="form-label">Arrebate</label>
                                        <input type="text" class="form-control" id="player-dribble-id" name="player-dribble" value="<%= (actionType) ? "75" : player.getDribble() %>" autocomplete="off" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-control-id" class="form-label">Control</label>
                                        <select class="form-control" id="player-control-id" name="player-control">
                                            <% 
                                                String[] controls = { "Parada", "Semi-parada", "Amortiguamiento", "Orientado", "Dominio" }; 
                                                for (String control: controls) { %>
                                                <option value="<%= control %>"><%= control %></option>
                                            <%  } %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="player-team-id" class="form-label">Equipo</label>
                                        <select class="form-control" id="player-team-id" name="player-team"></select>
                                        <div id="tooltip">
                                            <div class="cloud"></div>
                                            <img width="50" id="team"/>
                                        </div>
                                        <!--<img src="{pageContext.request.contextPath}/img/user.png" id="team-img" width="30"/>-->
                                    </div>
                                    <input type="submit" class="btn btn-primary" value="<%= (actionType) ? "Registrar" : "Actualizar" %> Jugador"/>
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
        <script>
            const controls = document.getElementById('player-control-id')
            const positions = document.getElementById('player-position-id')
            window.addEventListener('load', async () => {
                await fetch('${pageContext.request.contextPath}/Teams?x=0')
                    .then(res => res.json())
                    .then(async (data) => {
                        if (data) await setUpTeams(data)
                        else {
                            alert('No hay equipos suficientes...')
                            history.back()
                        }
                    })
                    .catch(() => {
                        alert('Ocurrió un error...')
                        history.back()
                    })
                <% if (actionType) { %>
                    positions.options[0].selected = true
                    controls.options[0].selected = true
                <% } else { %>
                    await setPlayerPosition()
                    await setPlayerControl()
                <% } %>
            })
            const setPlayerPosition = () => {
                Array.from(positions).forEach((opt, i) => {if (opt.value === '${player.getPosition()}') positions.selectedIndex = i })
            }
            const setPlayerControl = () => {
                Array.from(controls).forEach((opt, i) => {if (opt.value === '${player.getControl()}') controls.selectedIndex = i})
            }
            const setUpTeams = (teams) => {
                if (!teams) return
                const select = document.getElementById('player-team-id')
                const img = document.getElementById('team')
                const tooltip = document.getElementById('tooltip')
                teams.forEach(team => {
                    let option = document.createElement('option')
                    option.textContent = team.teamName
                    option.value = team.id
                    option.dataset.img = team.teamImg
                    if (team.teamName === '${player.getTeam().getTeamName()}') option.selected = true
                    select.appendChild(option)
                })
                select.options[0].selectedIndex = 0
                select.addEventListener('click', e => {
                    let img = e.target.dataset.img
                    if (img) animateTooltip(img)
                })
                const spawnKeyFrames = () => {
                    const config = [0.3, 0.5, 0.7, 1]
                    const keyframes = [{offset: 0, opacity: 0}]
                    config.forEach((x, i) => {
                        keyframes.push({transform: 'translateX(40px) translateY(-6' + (i % 2 === 0) ? '3' : '0' + 'px)', opacity: x})
                    })
                    return keyframes
                }
                const animateTooltip = val => {
                    img.src = '<%= request.getContextPath() %>' + val
                    tooltip.style.display = 'block'
                    tooltip.animate(
                        spawnKeyFrames(),
                        {
                            duration: 1000,
                            direction: 'alternate'
                        }
                    ).addEventListener('finish', () => {
                        setTimeout(() => tooltip.style.display = 'none', 3000)
                    })
                }
            }
        </script>
    </body>
</html>