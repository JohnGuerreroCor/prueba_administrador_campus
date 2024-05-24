<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="CursosController as ctrl">
		<md-content class="fondoTabla" flex layout-padding>
		<div id="menu-fijo">
			<div id="menuBtns">		
				<div layout-gt-xs="row">
					<span flex></span>
					<md-button class="md-raised options" ng-click="ctrl.editar('Adicionar')">
					<i class="fa fa-plus fa-option" aria-hidden="true"></i> Adicionar </md-button>
					<md-button class="md-raised options" ng-click="ctrl.editar('Modificar')">
					<i class="fa fa-pencil fa-option" aria-hidden="true"></i> Modificar </md-button>
					<md-button class="md-raised options"
						ng-click="ctrl.dialogEliminar($event)"> <i
						class="fa fa-trash fa-option" aria-hidden="true"></i> Eliminar </md-button>
				</div>
				<div layout-gt-xs="row">
					<div layout="column" flex>
						<md-select class="select-calendario" placeholder="Filtrar por calendario..." ng-model="selectCalendario" ng-change="ctrl.cargarCursos(selectCalendario)"> 
							<md-option ng-repeat="listCalendario in ctrl.JSONCalendario" value="{{listCalendario.codigo}}">
								{{listCalendario.nombre}} 
							</md-option> 
						</md-select>
					</div>
					<div layout="column" flex>
						<md-button class="md-raised options" ng-click="ctrl.editar('Horario')"> 
							<i class="fa fa-calendar-plus-o" aria-hidden="true"></i> Programar Horario 
						</md-button>
					</div>
					<div layout="column" flex>
						<md-button class="md-raised options" ng-click="ctrl.editar('listarHorario')"> 
							<i class="fa fa-calendar" aria-hidden="true"></i> Ver Horario 
						</md-button>
					</div>
				</div>
			</div>
		</div>
		<table id="tblCurso" class="display responsive nowrap" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Código</th>
					<th>Nombre</th>
					<th>Grupo</th>
					<th>Cupo</th>
					<th>Fecha Inicio</th>
					<th>Docente</th>
					<th>Plan Académico</th>
					<th>Calendario</th>
					<th>Programa</th>
					<th>Facultad</th>
					<th>Semanas</th>
					<th>Tipo Curso</th>
					<th>Tipo Escala</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Código</th>
					<th>Nombre</th>
					<th>Grupo</th>
					<th>Cupo</th>
					<th>Fecha Inicio</th>
					<th>Docente</th>
					<th>Plan Académico</th>
					<th>Calendario</th>
					<th>Programa</th>
					<th>Facultad</th>
					<th>Semanas</th>
					<th>Tipo Curso</th>
					<th>Tipo Escala</th>
				</tr>
			</tfoot>
		</table>
		</md-content>
	</div>
</div>