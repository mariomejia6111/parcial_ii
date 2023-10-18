<!-- sidebar container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <a href="dashboard.jsp" class="brand-link">
        <img src="./img/logo.png" alt="logo" class="brand-image img-circle elevation-3" style="opacity: .8">
        <span class="brand-text font-weight-light">Everlasting Goals</span>
    </a>
    <div class="sidebar">
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="./img/user.png" class="img-circle elevation-2" alt="user">
            </div>
            <div class="info">
                <p class="d-block"><%= user.getUsername() %></p>
            </div>
        </div>
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item menu-open">
                    <form action="LoginHandler" method="GET">
                        <input type="submit" class="btn btn-primary" value="Cerrar Sesión"/>
                    </form>
                </li>
            </ul>
        </nav>
    </div>
</aside>