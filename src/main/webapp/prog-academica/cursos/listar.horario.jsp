<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<md-dialog aria-label="Formulario Curso" ng-cloak flex-gt-sm="90">
	<h1 class="template-title">Lista Curso {{ctrlFrm.seleccionado}}</h1>
	<md-dialog-content>
		<md-content flex layout-padding>
			<div layout-gt-xs="row">
				<div layout="column" flex class="table-container">
					
					<h2 ng-if="(ctrlFrm.JSONEspacioOcupacion).length == 0">No hay un horario para este curso</h2>
					<table id="tblHorario" border="1" width="100%" ng-if="!(ctrlFrm.JSONEspacioOcupacion).length == 0">
						<thead>
							<tr>
								<th>Día</th>
								<th>Hora</th>
								<th>Docente</th>
								<th>Salón</th>
								<th>Fecha Inicio</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr data-ng-repeat="eo in ctrlFrm.JSONEspacioOcupacion">
								<td>{{ eo.dia.nombre }}</td>
								<td>{{ eo.horaInicio.hora }} - {{ eo.horaFin.hora }}</td>
								<td>{{ eo.uaaPersonal.persona.nombre }} {{ eo.uaaPersonal.persona.apellido }}</td>
								<td>{{ eo.espacio.nombre }}</td>
								<td>{{ eo.fecha }}</td>
								<td>
									<md-button class="md-primary md-raised" ng-click="ctrlFrm.dialogEliminarHorario($event,eo.codigo)" >
								      Eliminar
								    </md-button>
								</td>						
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</md-content>
	</md-dialog-content>
	<md-dialog-actions layout="row"> 
	    <span flex></span>
	    <span flex></span>
		<div flex>
		<md-button class="md-raised options" data-ng-click="ctrlFrm.cancel()">
			Cancelar 
		</md-button> 
		</div>
	</md-dialog-actions>
</md-dialog>