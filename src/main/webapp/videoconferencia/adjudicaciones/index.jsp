<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="AdjudicacionesReservaController as ctrl">
		<md-content class="fondoTabla" flex layout-padding>
		<div id="menu-fijo">
			<div layout-gt-xs="row" id="menuBtns">
				<span flex></span>
				<md-button class="md-raised options" ng-click="ctrl.dialogEliminar($event)"> 
					<i class="fa fa-trash fa-option" aria-hidden="true"></i> Eliminar 
				</md-button>
				<md-button class="md-raised options" ng-click="ctrl.editar('Grabaciones')"> 
					<i class="fa fa-video-camera fa-option" aria-hidden="true"></i> Grabaciones 
				</md-button>
			</div>
		</div>
		<table id="tblAdjudicaciones" class="display responsive"
			cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Código</th>
					<th>Tema</th>
					<th>Descripción</th>
					<th>Docente</th>
					<th>Curso</th>
					<th>Facultad</th>
					<th>Fecha</th>
					<th>Incio</th>
					<th>Fin</th>
					<th>Url</th>
					<th>Tipo Acceso</th>
					<th>codigoV</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Código</th>
					<th>Tema</th>
					<th>Descripción</th>
					<th>Docente</th>
					<th>Curso</th>
					<th>Facultad</th>
					<th>Fecha</th>
					<th>Incio</th>
					<th>Fin</th>
					<th>Url</th>
					<th>Tipo Acceso</th>
					<th>codigov</th>
				</tr>
			</tfoot>
		</table>
		</md-content>
	</div>
	</body>
	</html>