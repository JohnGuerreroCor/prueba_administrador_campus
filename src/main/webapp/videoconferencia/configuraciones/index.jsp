<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="ConfiguracionReservaController as ctrl">
		<md-content class="fondoTabla" flex layout-padding>
			<div id="menu-fijo">
				<div layout-gt-xs="row" id="menuBtns">
					<span flex></span>
					<md-button class="options-config md-raised" data-ng-click="ctrl.editar('Modificar')">
				Configurar </md-button>
				</div>
			</div>
			<table id="tblConfiguraciones" class="display responsive" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Código</th>
						<th>Uaa</th>
						<th>Calendario</th>
						<th>Días anticipación</th>
						<th>Máximo Solicitudes Días</th>
						<th>Máximo Solicitudes Semana</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>Código</th>
						<th>Uaa</th>
						<th>Calendario</th>
						<th>Días anticipación</th>
						<th>Máximo Solicitudes Días</th>
						<th>Máximo Solicitudes Semana</th>
					</tr>
				</tfoot>
			</table>
		</md-content>
	</div>
	</body>
	</html>