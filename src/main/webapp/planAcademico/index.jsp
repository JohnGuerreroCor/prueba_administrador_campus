<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div ng-cloak>
	<div id="dialogContainer"
		ng-controller="PlanAcademicoListaController as ctrlPlan">
		<md-content class="fondoTabla" flex layout-padding>
		<div id="menu-fijo">
			<div layout-gt-xs="row" id="menuBtns">
				<span flex></span>
				<md-button class="options md-raised" ng-cloak data-ng-click="ctrlPlan.verAsignaturas()">
				<i class="fa fa-eye" aria-hidden="true"></i>
				Ver Asignaturas</md-button>
				<md-button class="options md-raised" data-ng-click="ctrlPlan.editar('Agregar')">
				<i class="fa fa-plus fa-option" aria-hidden="true"></i>
				Agregar Asignaturas</md-button>
				<md-button class="options md-raised" data-ng-click="ctrlPlan.editar('Modificar')">
				<i class="fa fa-pencil fa-option" aria-hidden="true"></i>
				Modificar</md-button>
				<md-button class="options md-raised" data-ng-click="ctrlPlan.dialogEliminar($event)">
				<i class="fa fa-trash fa-option" aria-hidden="true"></i>
				Eliminar</md-button>
			</div>
		</div>
		<table id="tblPlanAcademico" class="display responsive" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Codigo</th>
					<th>Nombre</th>
					<th>UAA</th>
					<th>Total Horas</th>
					<th>Estado</th>
					<th>Fecha</th>
					<th>Resolucion</th>
					<th>Plan Actual</th>
					<th>Creditos</th>
					<th>Titulo Otorgado</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Codigo</th>
					<th>Nombre</th>
					<th>UAA</th>
					<th>Total Horas</th>
					<th>Estado</th>
					<th>Fecha</th>
					<th>Resolucion</th>
					<th>Plan Actual</th>
					<th>Creditos</th>
					<th>Titulo Otorgado</th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>
</div>

