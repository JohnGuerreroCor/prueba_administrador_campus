<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style type="text/css">
.md-datepicker-input-container {
	width: 80%;
}
</style>
<div ng-controller="PlanAcademicoController as ctrlPlan" data-ng-cloak>
	<div flex layout-padding>
		<div data-slayer-responsivo>
			<form name="frmPlanAcademico" novalidate>
				<div layout="row" flex="100" layout-xs="column">
					<div layout="column" flex>
						<md-input-container class="md-block"> <label>Nombre
							plan académico</label> <input
							data-ng-model="ctrlPlan.planAcademico.nombre" name="nombre"
							id="nombre" required ng-maxlength="10">
						<div data-ng-messages="frmPlanAcademico.nombre.$error">
							<div data-ng-message="required">Campo requerido.</div>
							<div data-ng-message="maxlength">Máximo 10 caracteres.</div>
						</div>
						</md-input-container>
					</div>
					<div layout="column" flex>
						<md-input-container class="md-block"> <label>Sede</label>
						<md-select data-ng-model="ctrlPlan.planAcademico.sede.codigo"
							id="codigoSede" name="codigoSede"
							data-ng-change="ctrlPlan.obtenerProgramas()">
						<md-option data-ng-repeat="sedes in ctrlPlan.JSONSedes"
							value="{{sedes.codigo}}">{{sedes.nombre}}</md-option> </md-select>
						<div data-ng-messages="frmPlanAcademico.codigoSede.$error">
							<div data-ng-message="required">Campo requerido.</div>
						</div>
						</md-input-container>
					</div>
					<div layout="column" flex>
						<md-input-container class="md-block" flex-gt-sm="">
						<label>Programa Academico</label> <md-select
							data-ng-model="ctrlPlan.planAcademico.programa.codigo"
							id="codigoPrograma" name="codigoPrograma"
							data-ng-disabled="!ctrlPlan.JSONProgramas"> <md-option
							data-ng-repeat="programa in ctrlPlan.JSONProgramas"
							value="{{programa.codigo}}">{{programa.uaa.nombre}}</md-option>
						</md-select>
						<div data-ng-messages="frmPlanAcademico.codigoPrograma.$error">
							<div data-ng-message="required">Campo requerido.</div>
						</div>
						</md-input-container>
					</div>
				</div>
				<div layout="row" flex="100" layout-xs="column">
					<div layout="column" flex>
						<md-input-container class="md-block"> <label>Resolución</label>
						<md-select
							data-ng-model="ctrlPlan.planAcademico.resolucion.codigo"
							id="codigoResolucion" name="codigoResolucion" required>
						<md-option data-ng-repeat="resolucion in ctrlPlan.JSONResolucion"
							value="{{resolucion.codigo}}">{{resolucion.numero}}</md-option> </md-select>
						<div data-ng-messages="frmPrograma.codigoResolucion.$error">
							<div data-ng-message="required">Campo requerido.</div>
						</div>
						</md-input-container>
					</div>
					<div layout="column" flex>
						<md-input-container class="md-block"> <label>Total
							Horas</label> <input type="number"
							data-ng-model="ctrlPlan.planAcademico.totalHoras" id="totalHoras"
							name="totalHoras" required ng-pattern="/^[0-9]{1,3}$/">
						<div data-ng-messages="frmPlanAcademico.totalHoras.$error">
							<div data-ng-message="required">Campo requerido.</div>
							<div data-ng-message="pattern">Rango de número no
								permitido.</div>
						</div>
						</md-input-container>
					</div>
					<div layout="column" flex>
						<md-datepicker class="md-block" required=""
							data-ng-model="ctrlPlan.planAcademico.fecha" id="fecha"
							name="fecha" md-placeholder="Fecha"
							md-max-date="ctrlPlan.maxDate">
						<div class="validation-messages"
							data-ng-messages="frmPlanAcademico.fecha.$error">
							<div data-ng-message="valid">La fecha no es valida.</div>
							<div data-ng-message="required">Campo requerido</div>
						</div>
						</md-datepicker>
					</div>
				</div>
				<div layout="row" flex="100" layout-xs="column">
					<div layout="column" flex>
						<md-input-container class="md-block"> <label>Total
							Creditos</label> <input type="number"
							data-ng-model="ctrlPlan.planAcademico.totalCreditos" id="totalCreditos"
							name="totalCreditos" required ng-pattern="/^[0-9]{1,3}$/">
						<div data-ng-messages="frmPlanAcademico.totalCreditos.$error">
							<div data-ng-message="required">Campo requerido.</div>
							<div data-ng-message="pattern">Rango de número no
								permitido.</div>
						</div>
						</md-input-container>
					</div>
					<div layout="column" flex>
						<md-input-container class="md-block" flex-gt-sm="30">
						<label>Plan Actual</label> <md-select
							data-ng-model="ctrlPlan.planAcademico.planActual" id="planActual"
							name="planActual" required> <md-option value="1">Activo</md-option>
						<md-option value="0">Inactivo</md-option> </md-select>
						<div data-ng-messages="frmPrograma.planActual.$error">
							<div data-ng-message="required">Campo requerido.</div>
						</div>
						</md-input-container>
					</div>
				</div>
				<div layout="row" flex="100" layout-xs="column"
					class="contenidoAsignaturas">
					<h2 flex>Asignaturas</h2>
					<md-button id="btn-asignaturas" class="md-raised options"
						data-ng-click="ctrlPlan.modalmaterias($event)">
					Agregar Asignatura </md-button>
				</div>
				<div layout-gt-xs="row" id="container-list-subject"
					class="altura-list-subject">
					<md-list class="list-subjects" flex> <md-list-item
						class="md-3-line"
						ng-repeat="selAsig in ctrlPlan.planAcademico.asignaturas"
						ng-if="$even"> <span class="md-avatar">{{$index+1}}
						<i class="fa fa-book" aria-hidden="true"></i>
					</span>
					<div class="md-list-item-text" layout="column">
						<h3>{{ selAsig.codigo }} - {{ selAsig.nombre }} - {{
							selAsig.nombreCorto }}</h3>
						<h4>Módulo:{{ selAsig.semestre }} Nota Minima: {{
							selAsig.notaMinima }}</h4>
						<p>Intensidad:{{ selAsig.intensidad }} Horas. Trabajo
							Independiente:{{ selAsig.trabajoIndependiente }} Horas</p>
					</div>
					<i class="fa fa-trash btn-delete" aria-hidden="true"
						data-ng-click="ctrlPlan.eliminarAsignatura('{{$index}}')"> <md-tooltip
							md-direction="left"> Eliminar </md-tooltip>
					</i> <md-divider></md-divider> </md-list-item> </md-list>
					<md-list class="list-subjects" flex> <md-list-item
						class="md-3-line"
						ng-repeat="selAsig in ctrlPlan.planAcademico.asignaturas"
						ng-if="$odd"> <span class="md-avatar">{{$index+1}}
						<i class="fa fa-book" aria-hidden="true"></i>
					</span>
					<div class="md-list-item-text" layout="column">
						<h3>{{ selAsig.codigo }} - {{ selAsig.nombre }} - {{
							selAsig.nombreCorto }}</h3>
						<h4>Módulo:{{ selAsig.semestre }} Nota Minima: {{
							selAsig.notaMinima }}</h4>
						<p>Intensidad:{{ selAsig.intensidad }} Horas. Trabajo
							Independiente:{{ selAsig.trabajoIndependiente }} Horas</p>
					</div>
						<i class="fa fa-trash btn-delete" 
							aria-hidden="true"
							data-ng-click="ctrlPlan.eliminarAsignatura('{{$index}}')"> 
							<md-tooltip md-direction="left"> Eliminar </md-tooltip>
						</i>
						<md-divider></md-divider>
				 	</md-list-item> 
				 </md-list>
				</div>
				<md-actions layout="row"> 
					<span flex></span>
					<md-button
						class="md-raised options"
						data-ng-disabled="frmPlanAcademico.$invalid"
						data-ng-click="ctrlPlan.guardar()"> 
						Guardar plan académico
					</md-button> 
				</md-actions>
			</form>
		</div>
	</div>
</div>