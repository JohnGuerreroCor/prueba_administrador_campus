<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" ng-controller="ResolucionesController">	
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
		<table id="tblResoluciones" class="display responsive" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Código</th>
					<th>dependencia Código</th>
					<th>Número</th>
					<th>Descripción</th>
					<th>fecha</th>
					<th></th>
					<th>dependencia</th>
				</tr>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Código</th>
					<th>dependencia Código</th>
					<th>Número</th>
					<th>Descripción</th>
					<th>fecha</th>
					<th></th>
					<th>dependencia</th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>
</div>