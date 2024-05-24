<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" ng-controller="UaaController">
		<md-content class="fondoTabla" flex layout-padding>
		<div id="menu-fijo">
			<div layout-gt-xs="row" id="menuBtns">
			<span flex></span>
			<md-button class="md-raised options" ng-click="editar('Adicionar')">
				<i class="fa fa-plus fa-option" aria-hidden="true"></i>
				Adicionar </md-button>
				<md-button class="md-raised options" ng-click="editar('Modificar')">
				<i class="fa fa-pencil fa-option" aria-hidden="true"></i>
				Modificar </md-button>
				<md-button class="md-raised options" ng-click="dialogEliminar($event)"> 
				<i class="fa fa-trash fa-option" aria-hidden="true"></i>
				Eliminar </md-button>
			</div>
		</div>
		<table id="tblUaa" class="display responsive" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Código</th>
					<th>Tipo</th>
					<th>Nombre</th>
					<th>Nombre Corto</th>
					<th>Nombre Impresión</th>
					<th>Acrónimo</th>
					<th>Dependencia</th>
					<th>Dependencia Código</th>
					<th>Sede</th>
					<th>Municipio</th>
					<th>Telefono</th>
					<th>Fax</th>
					<th>Dirección</th>
					<th>Email</th>
					<th>Email PQR</th>
					<th>Pagina</th>
					<th>Centro Costos</th>
					<th>Estado</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Código</th>
					<th>Tipo</th>
					<th>Nombre</th>
					<th>Nombre Corto</th>
					<th>Nombre Impresión</th>
					<th>Acrónimo</th>
					<th>Dependencia</th>
					<th>Dependencia Código</th>
					<th>Sede</th>
					<th>Municipio</th>
					<th>Telefono</th>
					<th>Fax</th>
					<th>Dirección</th>
					<th>Email</th>
					<th>Email PQR</th>
					<th>Pagina</th>
					<th>Centro Costos</th>
					<th>Estado</th>
					<th></th>
					<th></th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>
	</body>
	</html>