<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.security.core.GrantedAuthority"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="co.edu.usco.lcms.configuration.UscoGrantedAuthority"%>
<%@ page import="co.edu.usco.lcms.configuration.User"%>
<%@ page isELIgnored="false"%>
<%
	User user = (User) request.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es" ng-app="administrador">
<head>
<title>Administrador Universidad Surcolombiana</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="Campus virtual Universidad Surcolombiana">
<meta name="viewport" content="initial-scale=1" />
<link rel="apple-touch-icon" sizes="57x57" href="app/assets/img/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="app/assets/img/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="app/assets/img/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="app/assets/img/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="app/assets/img/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="app/assets/img/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="app/assets/img/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="app/assets/img/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="app/assets/img/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192" href="app/assets/img/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="app/assets/img/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="app/assets/img/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="app/assets/img/favicon/favicon-16x16.png">
<link rel="manifest" href="app/assets/img/favicon/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="app/assets/img/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto:400,500,700,400italic'>
<link rel="stylesheet" href="app/assets/libs/font-awesome/css/font-awesome.min.css">

<!--[if lte IE 9]>
      <link rel="stylesheet" type="text/css" href="/app/assets/css/ie-only.css">
<![endif]-->

<link rel="stylesheet" href="app/assets/libs/angular-material/angular-material.css" />
<link rel="stylesheet" href="app/assets/css/general.css" />
<link rel="stylesheet" href="app/assets/css/administrador.css" />
<link rel="stylesheet" href="app/assets/libs/datatables/media/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="app/assets/libs/datatables/media/css/responsive.dataTables.min.css">
<link rel="stylesheet" href="app/assets/libs/angular-material-datetimepicker/angularjs-datetime-picker.css">
<link rel="stylesheet" href="app/assets/libs/full-calendar/css/fullcalendar.css">
<link rel="stylesheet" href="app/assets/libs/alertify/css/alertify.min.css">
<link rel="stylesheet" href="app/assets/css/planEstudio.css" />
<link rel="stylesheet" href="app/assets/css/videoconferencia.css" />

<script src="app/assets/libs/angular/angular.js"></script>
<script type="text/javascript">
	angular.element(document.getElementsByTagName('head')).append(angular.element('<base href="' + window.location.pathname + '" />'));
</script>
</head>
<body layout="column" ng-controller="MenuController as ul" ng-cloak>
	<div class="ie-only">
		<img src="https://www.usco.edu.co/imagen-institucional/logo/universidad-surcolombiana-v.png" alt="Universidad Surcolombiana" /> 
		<br><br>
		<h1>Mensaje de error: este navegador web no es compatible</h1>
		<br>
		<p>Esta plataforma no es soportada por Internet Explorer versiones 9 o menores, actualice su navegador ó utilice otro navegador.</p>
		<br> 
		<a	class="see-more-course text-main-white bg-main-red mas-programas btn-to-gc"
			target="blank"
			href="https://www.google.com/chrome/browser/desktop/index.html?utm_source%3Dgoogle%26utm_medium%3Dsem%26utm_campaign%3D1001342%257cChromeWin10%257cUS%257cen%257cHybrid%257cText%257cBKWS~TopKWDS-Exact%26brand%3DCHBD">
			Descargar Google Chrome </a>
	</div>
	<md-toolbar 
		id="toolbar-admin" 
		layout="row" 
		class="md-whiteframe-1dp"
		class="toolbar-admin"
		layout-gt-sm="myStyle={'background-color':'white'}">
	<div class="logo-admin" flex hide-sm hide-xs></div>
	<img class="logo-mobile"
		src="app/assets/img/admin/universidad-surcolombiana-mobile-2.png"
		alt="Universidad Surcolombiana" title="Universidad Surcolombiana"
		hide-gt-sm /> <span flex></span>
	<h3 class="md-accent role" hide-xs hide-sm>
		<%
			String rol = "BIENVENIDO";
			if (!user.getAuthorities().isEmpty()) {
				for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
					UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;

					rol = uscoGrantedAuthority.getRole();
				}
				rol = rol.replace("_", " ");
				rol = rol.replace("ROLE", "");
			}
		%>
		<%=rol%>
	</h3>

	<md-button class="menu" ng-click="ul.toggleMenu()"
		aria-label="Show Menu List"> <md-tooltip
		md-direction="bottom">Menú</md-tooltip> <md-icon md-svg-icon="menu"></md-icon>
	</md-button> <!-- 
       <md-button class="menu" hide-gt-sm ng-click="ul.toggleMenu()" aria-label="Show Menu List">
        <md-icon md-svg-icon="menu" ></md-icon>
      </md-button>
       --> <md-button class="more-buttom" aria-label="More"
		ng-click="ul.salir()" class="more-menu" hide-xs hide-sm> <md-tooltip
		md-direction="bottom">Salir</md-tooltip> <i class="fa fa-sign-out"
		aria-hidden="true"> </i> </md-button> </md-toolbar>

	<div flex layout="row">
		<md-sidenav id="sidenav-usco" md-component-id="right"
			class="md-sidenav-right md-whiteframe-1dp sidenav-usco"
			layout="column" flex>

		<div layout="row">
			<div class="sidenav-imagen">
				<md-button class="md-whiteframe-1dp close-sidenav"
					aria-label="cerrar" ng-transclude
					ng-click="$mdSidenav('right').close();ul.toggleMenu();">
				<i class="fa fa-arrow-right" aria-hidden="true"></i> </md-button>
				<!-- img src="app/assets/img/admin/user/icono.jpg" class="avatar"-->
			</div>
		</div>
		<a ng-href="#perfil"
			ng-click="ul.toggleMenu();ul.selected.name='Perfil del Usuario'"
			class="profile-buttom" data-ma-action="profile-menu-toggle">
			<div class="profile-menu">
				<md-tooltip md-direction="left">Perfil</md-tooltip>
				<c:out value="${usuario.persona.nombre}"></c:out>
				<c:out value="${usuario.persona.apellido}"></c:out>
				<i class="zmdi zmdi-caret-down"></i> <small class="small-role"><i
					class="fa fa-cog" aria-hidden="true"></i></small>
			</div>
		</a> <md-list> <md-list-item ng-repeat="it in ul.menu"
			layout="column"> 
			<sec:authorize access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Oferta Académica'"
				ng-href="#ofertaAcademica" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-ticket"
				aria-hidden="true"></i> Oferta Académica </md-button>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Correo'"
				ng-href="#correo" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-ticket"
				aria-hidden="true"></i> Correo </md-button>
		</sec:authorize>
		 <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-button ng-click="it.show?it.show=false:it.show=true;"
				class="menu-button" ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'toggle'"> <i class="fa fa-list"
				aria-hidden="true"></i> Plan de Estudio <i
				class="fa fa-plus-circle more-links" aria-hidden="true"
				ng-if="it.type == 'toggle' && it.show== false || it.show == undefined"></i>
			<i class="fa fa-minus-circle more-links" aria-hidden="true"
				ng-show="it.show"></i> </md-button>
		</sec:authorize> <md-list id="sub-menu" class="sub-menu" ng-if="it.type == 'toggle'"
			ng-show="it.show" ng-animate="animate"> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='UAA'"
				ng-href="#uaaLista" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> UAA </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Resoluciones'"
				ng-href="#resolucionesLista" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Resoluciones </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Programa'"
				ng-href="#programaLista" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Programa </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Asignatura'"
				ng-href="#asignatura" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Asignatura </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Nuevo Plan Académico'"
				ng-href="#nuevoPlanAcademico" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-file-o" aria-hidden="true"></i> Nuevo Plan Académico </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Lista Plan Académico'"
				ng-href="#planAcademicoLista" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-list" aria-hidden="true"></i> Lista Plan Académico </md-button> </md-list-item>
		</sec:authorize> <!-- <sec:authorize access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Nivel Académico'"
				ng-href="#nivelAcademico" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-list" aria-hidden="true"></i> Nivel Académico </md-button> </md-list-item>
		</sec:authorize> --> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Convenio'"
				ng-href="#convenioLista" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Convenio </md-button> </md-list-item>
		</sec:authorize>
		<!--  <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Núcleo Básico de Conocimiento'"
				ng-href="#nbc" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> NBC </md-button> </md-list-item>
		</sec:authorize> --> 
		</md-list> 
		<!-- 
		<sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS}','${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}','${constantes.WEP_ROLE_DOCENTE}') and isAuthenticated()">
			<md-button ng-click="it.showv?it.showv=false:it.showv=true;"
				class="menu-button" ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'toggle'"> <i class="fa fa-list"
				aria-hidden="true"></i> Videoconferencia <i
				class="fa fa-plus-circle more-links" aria-hidden="true"
				ng-if="it.type == 'toggle' && it.showv== false || it.showv == undefined"></i>
			<i class="fa fa-minus-circle more-links" aria-hidden="true"
				ng-show="it.showv"></i> </md-button>
		</sec:authorize> <md-list id="sub-menu" class="sub-menu" ng-if="it.type == 'toggle'"
			ng-show="it.showv" ng-animate="animate"> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS}','${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Configuraciones'"
				ng-href="#videoconferencia/configuracion" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Configuraciones </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS}','${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}','${constantes.WEP_ROLE_DOCENTE}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.cargarCalendar();ul.selected.name='Realizar solicitud'"
				ng-href="#videoconferencia/agendar" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Realizar solicitud </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS}','${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}','${constantes.WEP_ROLE_DOCENTE}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.cargarCalendar();ul.selected.name='Solicitudes'"
				ng-href="#videoconferencia/listaSolicitudes" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Solicitudes </md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS}','${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}','${constantes.WEP_ROLE_DOCENTE}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.cargarCalendar();ul.selected.name='Adjudicadas'"
				ng-href="#videoconferencia/SolicitudesAdjudicadas"
				class="menu-button" ng-class="{'selected' : subIt === ul.selected }">
			<i class="fa fa-book" aria-hidden="true"></i> Adjudicadas </md-button> </md-list-item>
		</sec:authorize> </md-list>
		 -->
		 <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_PROG_ACADEMICA_LCMS}','${constantes.WEP_ROLE_ADMIN_FACULTAD_PROG_ACADEMICA_LCMS}') and isAuthenticated()">
			<md-button ng-click="it.showp?it.showp=false:it.showp=true;"
				class="menu-button" ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'toggle'"> <i class="fa fa-list"
				aria-hidden="true"></i> Programación Académica <i
				class="fa fa-plus-circle more-links" aria-hidden="true"
				ng-if="it.type == 'toggle' && it.showp== false || it.showp == undefined"></i>
			<i class="fa fa-minus-circle more-links" aria-hidden="true"
				ng-show="it.showp"></i> </md-button>
		</sec:authorize> <md-list id="sub-menu" class="sub-menu" ng-if="it.type == 'toggle'"
			ng-show="it.showp" ng-animate="animate"> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_PROG_ACADEMICA_LCMS}','${constantes.WEP_ROLE_ADMIN_FACULTAD_PROG_ACADEMICA_LCMS}') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Cursos'"
				ng-href="#programacion-academica/cursos" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-book" aria-hidden="true"></i> Cursos </md-button> </md-list-item>
		</sec:authorize>
		</md-list> <sec:authorize
			access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Administración Slider'"
				ng-href="#sliderAdmin" class="menu-button"
				ng-class="{'selected' : it === ul.selected }" a
				ng-if="it.type == 'link'"> <i class="fa fa-ticket"
				aria-hidden="true"></i> Administración Slider </md-button>
		</sec:authorize> 
		<sec:authorize access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Usuarios Inscritos'"
				ng-href="#usuariosInscritos" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-ticket"
				aria-hidden="true"></i> Usuarios Inscritos </md-button>
		</sec:authorize> 
		<sec:authorize access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">	<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Usuarios Matriculados'"
				ng-href="#usuariosMatriculados" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-ticket"
				aria-hidden="true"></i> Usuarios Matriculados </md-button>
		</sec:authorize> 
		<sec:authorize access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}','${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}') and isAuthenticated()">	<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Certificados'"
				ng-href="#certificadosxMatriculados" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-ticket"
				aria-hidden="true"></i> Certificados</md-button>
		</sec:authorize>
		 <md-button ng-click="ul.portal()" class="menu-button"
			ng-class="{'selected' : it === ul.selected }"
			ng-if="it.type == 'link'"> <i class="fa fa-ticket"
			aria-hidden="true"></i> Regresar al Portal </md-button> </md-list-item> <md-list-item
			layout="column" hide-gt-sm> <md-button ng-click=""
			class="menu-button"> <i class="fa fa-info"
			aria-hidden="true" class="menu-button"></i> Acerca </md-button> </md-list-item> <md-list-item
			layout="column" hide-gt-sm> <md-button
			ng-click="ul.salir()" class="menu-button"> <i
			class="fa fa-info" aria-hidden="true" class="menu-button"></i> Salir
		</md-button> </md-list-item> </md-list> </md-sidenav>
		<md-content flex id="content">
		<div class="card">
			<h1 class="template-title" ng-if="ul.selected.name == null">Administrador
				de servicios del campus virtual de la USCO</h1>
			<h1 class="template-title" ng-if="ul.selected.name">{{ul.selected.name}}</h1>
			<div class="template-content" ng-view></div>
		</div>
		</md-content>
	</div>

	<script src="app/assets/libs/angular-animate/angular-animate.js"></script>
	<script src="app/assets/libs/angular-aria/angular-aria.js"></script>
	<script src="app/assets/libs/angular-messages/angular-messages.min.js"></script>
	<script src="app/assets/libs/angular-material/angular-material.js"></script>
	<script src="app/assets/libs/angular-route/angular-route.min.js"></script>
	<script src="app/assets/libs/angular-resource/angular-resource.js"></script>
	<script src="app/assets/libs/angular-tinymce/tinymce-dist/tinymce.js"></script>
	<script	src="app/assets/libs/angular-tinymce/angular-ui-tinymce/src/tinymce.js"></script>
	<script	src="app/assets/libs/angular-material-datetimepicker/moment.min.js"></script>
	<script	src="app/assets/libs/angular-material-datetimepicker/angularjs-datetime-picker.js"></script>
	<script src="app/assets/libs/jquery/dist/jquery.min.js"></script>
	<script	src="app/assets/libs/datatables/media/js/jquery.dataTables.min.js"></script>
	<script	src="app/assets/libs/datatables/media/js/dataTables.responsive.min.js"></script>
	<script src="app/assets/libs/full-calendar/js/fullcalendar.js"></script>
	<script src="app/assets/libs/full-calendar/js/gcal.js"></script>
	<script src="app/assets/libs/full-calendar/js/lang-all.js"></script>
	<script src="app/assets/libs/full-calendar/js/calendar.js"></script>
	<script src="app/assets/libs/full-calendar/js/angular-locale_es-co.js"></script>
	<script src="app/assets/libs/alertify/js/alertify.min.js"></script>

	<script src="app/app.modules.js"></script>
	<script src="app/app.routes.js"></script>
	<script src="app/services/menu/MenuService.js"></script>
	<script src="app/controllers/menu/MenuController.js"></script>

	<sec:authorize
		access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}',
		'${constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS}',
		'${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS}',
		'${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}',
		'${constantes.WEP_ROLE_DOCENTE}',
		'${constantes.WEP_ROLE_ADMIN_PROG_ACADEMICA_LCMS}',
		'${constantes.WEP_ROLE_ADMIN_FACULTAD_PROG_ACADEMICA_LCMS}') and isAuthenticated()">
		<script src="app/controllers/asignatura/AsignaturaController.js"></script>
		<script src="app/controllers/resoluciones/ResolucionesController.js"></script>
		<script src="app/controllers/planAcademico/PlanAcademicoController.js"></script>
		<script	src="app/controllers/planAcademico/PlanAcademicoListaController.js"></script>
		<script src="app/controllers/uaa/UaaController.js"></script>
		<script src="app/controllers/programa/ProgramaController.js"></script>
		<script src="app/controllers/oferta/FileModel.js"></script>
		<script src="app/controllers/oferta/FileUpload.js"></script>
		<script src="app/controllers/oferta/OfertaAcademicaController.js"></script>
		<script	src="app/controllers/nivelAcademico/NivelAcademicoController.js"></script>
		<script src="app/controllers/convenio/ConvenioController.js"></script>
		<script src="app/controllers/inscritos/InscritosController.js"></script>
		<script src="app/controllers/matriculados/MatriculadosController.js"></script>
		<script	src="app/controllers/certificados/CertificadosController.js"></script>
		<script src="app/controllers/perfil/PerfilController.js"></script>
		<script src="app/controllers/nbc/NbcController.js"></script>
		<script src="app/controllers/slider/SliderController.js"></script>
		<!-- Script solicitud videoconferencia -->
		<script	src="app/controllers/videoconferencia/agendaSala/AgendaSalaController.js"></script>
		<script	src="app/controllers/videoconferencia/asignarHorasFacultad/AsignarHorasFacultadController.js"></script>
		<script	src="app/controllers/videoconferencia/configuraciones/ConfiguracionesController.js"></script>
		<script	src="app/controllers/videoconferencia/solicitudes/SolicitudesController.js"></script>
		<script	src="app/controllers/videoconferencia/Adjudicaciones/AdjudicacionesController.js"></script>
		<script	src="app/controllers/prog-academica/cursos/CursosController.js"></script>
		
	</sec:authorize>

	<sec:authorize
		access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}') and isAuthenticated()">
		<script src="app/controllers/videoconferencia/alertas.js"></script>
	</sec:authorize>

<sec:authorize
		access="hasAnyRole('${constantes.WEP_ROLE_ADMINISTRADOR_LCMS}') and isAuthenticated()">
		<script src="app/controllers/correo.js"></script>
	</sec:authorize>


	<script type="text/javascript">
		angular.element(document.getElementsByTagName('head')).append(angular.element('<base href="' + window.location.pathname + '" />'));
	</script>
</body>
</html>
