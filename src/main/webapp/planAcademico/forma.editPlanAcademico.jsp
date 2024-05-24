<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<md-dialog aria-label="Formulario Plan Académico" ng-cloak	flex-gt-sm="70">
<form name="frmPlanAcademico" id="frmPlanAcademico">
	<h1 class="template-title">Formulario Plan Académico  {{ctrl.seleccionado}}</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn" data-ng-if="ctrl.showHints">{{ctrl.MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Nombre plan académico</label> <input
				data-ng-model="ctrl.planAcademico.nombre" name="nombre"
				id="nombre" required ng-maxlength="10">
			<div data-ng-messages="frmPlanAcademico.nombre.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 10 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Sede</label> <md-select
				data-ng-model="ctrl.planAcademico.programa.sede.codigo" id="codigoSede"
				name="codigoSede" data-ng-change="ctrl.obtenerProgramas()">
			<md-option data-ng-repeat="sedes in ctrl.JSONSedes"
				value="{{sedes.codigo}}">{{sedes.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmPlanAcademico.codigoSede.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Programa Academico</label> <md-select
				data-ng-model="ctrl.planAcademico.programa.codigo"
				id="codigoPrograma" name="codigoPrograma"
				data-ng-disabled="!ctrl.JSONProgramas"> <md-option
				data-ng-repeat="programa in ctrl.JSONProgramas"
				value="{{programa.codigo}}">{{programa.uaa.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmPlanAcademico.codigoPrograma.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Resolución</label> <md-select
				data-ng-model="ctrl.planAcademico.resolucion.codigo"
				id="codigoResolucion" name="codigoResolucion" required>
			<md-option data-ng-repeat="resolucion in ctrl.JSONResolucion"
				value="{{resolucion.codigo}}">{{resolucion.numero}}</md-option> </md-select>
			<div data-ng-messages="frmPrograma.codigoResolucion.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Total Horas</label> 
				<input type="number" data-ng-model="ctrl.planAcademico.totalHoras" id="totalHoras"
					name="totalHoras" required ng-pattern="/^[0-9]{1,3}$/">
				<div data-ng-messages="frmPlanAcademico.totalHoras.$error">
					<div data-ng-message="required">Campo requerido.</div>
					<div data-ng-message="pattern">Rango de número no permitido.</div>
				</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Creditos</label> 
				<input type="number" data-ng-model="ctrl.planAcademico.totalCreditos" id="creditos"
					name="creditos" required ng-pattern="/^[0-9]{1,3}$/">
				<div data-ng-messages="frmPlanAcademico.creditos.$error">
					<div data-ng-message="required">Campo requerido.</div>
					<div data-ng-message="pattern">Rango de número no permitido.</div>
				</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Plan Actual</label> <md-select
					data-ng-model="ctrl.planAcademico.planActual" id="planActual"
					name="planActual" required> <md-option value="1">Activo</md-option>
				<md-option value="0">Inactivo</md-option> </md-select>
				<div data-ng-messages="frmPrograma.planActual.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
			<md-datepicker class="md-block" flex-gt-sm="30" required=""
				ng-model="ctrl.planAcademico.fecha" id="fecha" name="fecha"
				md-placeholder="Fecha"> </md-datepicker>
			<div class="validation-messages"
				data-ng-messages="frmPeriodo.fecha.$error">
				<div data-ng-message="valid">La fecha no es valida.</div>
				<div data-ng-message="required">Campo requerido</div>
			</div>
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
		<md-button class="md-raised options" data-ng-click="ctrl.answer('ok')" data-ng-disabled="frmPlanAcademico.$invalid && (ctrl.accion!='Modificar')">
			{{ ctrl.accion }} 
		</md-button> 
		<md-button class="md-raised options" data-ng-click="ctrl.cancel()">
			Cancelar 
		</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>