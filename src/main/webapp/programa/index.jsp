<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div ng-cloak="">
	<div id="dialogContainer" ng-controller="ProgramaController">
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
		<table id="tblPrograma" class="display responsive nowrap" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Codigo</th>
					<th>Titulo Otorgado</th>
					<th>UAA</th>
					<th>Sede</th>
					<th>Nivel Academico</th>
					<th>Jornada</th>
					<th>Resolucion</th>
					<th>Fecha Creacion</th>
					<th>Modalidad</th>
					<th>Convenio</th>
					<th>Horario</th>
					<th>Estado</th>
					<th>Propio</th>
					<th>NBC</th>
					<th>Calendario</th>
					<th>Tipo UAA</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Codigo</th>
					<th>Titulo Otorgado</th>
					<th>UAA</th>
					<th>Sede</th>
					<th>Nivel Academico</th>
					<th>Jornada</th>
					<th>Resolucion</th>
					<th>Fecha Creacion</th>
					<th>Modalidad</th>
					<th>Convenio</th>
					<th>Horario</th>
					<th>Estado</th>
					<th>Propio</th>
					<th>NBC</th>
					<th>Calendario</th>
					<th>Tipo UAA</th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>
</div>