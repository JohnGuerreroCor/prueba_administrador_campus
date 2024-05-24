<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="app/assets/css/inscritos.css" />
<div ng-cloak>
	<div id="dialogContainer" ng-controller="InscritosController">
		<md-content class="fondoTabla" flex layout-padding>
		<div layout-gt-xs="row">
			<span flex></span>
			<md-button class="options md-raised _250" data-ng-click="editar('Admitir')">
			Verificar Información </md-button>
		</div>
		<div layout-gt-xs="row">
			<md-select 
				placeholder="Seleccionar el Programa para obtener los inscritos..."
				ng-model="UsersInscritoCtrl.oferta">
				<md-option ng-repeat="oferta in JSONUsuariosInscritos" 
					ng-click="cargarInscritos(oferta.codigo)"
					value="{{oferta.codigo}}">
						{{oferta.nombre}} ({{oferta.nombreUaa}})
				</md-option>
			</md-select>
		</div>
		<table id="tblInscritos" class="display responsive" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Código</th>
					<th>Identificación</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Programa</th>					
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Código</th>
					<th>Identificación</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Programa</th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>
	</body>
	</html>