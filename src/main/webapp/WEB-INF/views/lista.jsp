<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.security.core.GrantedAuthority"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="co.edu.usco.lcms.configuration.UscoGrantedAuthority"%>
<%@ page import="co.edu.usco.lcms.configuration.User"%>
<%@ page isELIgnored="false" %> 
<%
	User user = (User) request.getAttribute("usuario");
%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="SolicitudesController as ctrl">
		<md-content class="fondoTabla" flex layout-padding>
		<sec:authorize access="hasAnyRole('${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS}', '${constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS}') and isAuthenticated()">
			<div id="menu-fijo">
				<div layout-gt-xs="row" id="menuBtns">
					<span flex></span>
				<md-button class="options md-raised" data-ng-click="ctrl.editar('Adjudicar')">
				Adjudicar </md-button>
				</div>
			</div>
		</sec:authorize>
			<table id="tblSolicitudes" class="display responsive" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Código</th>
						<th>Tema</th>
						<th>Descripción</th>
						<th>Facultad</th>
						<th>Docente</th>
						<th>Curso</th>
						<th>Cantidad Alumnos</th>
						<th>Fecha</th>
						<th>Hora Inicio</th>
						<th>Hora Fin</th>
						<th>Estado</th>
						<th>Tipo Acceso</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>Código</th>
						<th>Tema</th>
						<th>Descripción</th>
						<th>Facultad</th>
						<th>Docente</th>
						<th>Curso</th>
						<th>Cantidad Alumnos</th>
						<th>Fecha</th>
						<th>Hora Inicio</th>
						<th>Hora Fin</th>
						<th>Estado</th>
						<th>Tipo Acceso</th>
					</tr>
				</tfoot>
			</table>
		</md-content>
	</div>
	</body>
	</html>