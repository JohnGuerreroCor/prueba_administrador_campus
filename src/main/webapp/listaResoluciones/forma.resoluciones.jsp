<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.md-datepicker-input-container {
	width: 77%;
}

.validation-messages {
	font-size: 12px;
	color: #dd2c00;
	margin-left: 15px;
}
</style>
<md-dialog aria-label="Formulario Resolucion" data-ng-cloak flex="50">
<form name="frmResolucion" novalidate>
	<h1 class="template-title">Formulario Resoluciones {{ seleccionado }}</h1>
	<md-dialog-content> <md-subheader class="md-warn"
		ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>Tipo UAA</label> <md-select
				data-ng-model="resolucion.uaa.uaaTipo.codigo" id="codigoTipoUaa"
				name="codigoTipoUaa" data-ng-change="obtenerUaa()" required>
			<md-option data-ng-repeat="uaaTipos in JSONUaaTipo"
				value="{{uaaTipos.codigo}}">{{uaaTipos.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmResolucion.codigoTipoUaa.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>UAA</label> <md-select data-ng-model="resolucion.uaa.codigo"
				data-ng-disabled="!JSONUaa" id="dependencia" name="dependencia"
				required> <md-option data-ng-repeat="uaaList in JSONUaa"
				value="{{uaaList.codigo}}">{{uaaList.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmResolucion.dependencia.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-datepicker class="md-block" flex-gt-sm="50" required=""
				data-ng-model="resolucion.fecha" id="fecha" name="fecha"
				md-placeholder="Fecha" md-max-date="maxDate">
			<div class="validation-messages"
				data-ng-messages="frmResolucion.fecha.$error">
				<div data-ng-message="valid">La fecha no es valida.</div>
				<div data-ng-message="required">Campo requerido</div>
				<div data-ng-message="maxdate">La fecha no puede ser superior a la actual!</div>
			</div>
			</md-datepicker>
			<!-- <md-input-container class="md-block" flex-gt-sm="50">
			<label>fecha</label> <input type="date" ng-model="resolucion.fecha" id="fecha"
				name="fecha" required >
			<div data-ng-messages="frmResolucion.fecha.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="date">Máximo 50 caracteres.</div>
			</div>
			</md-input-container> -->
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>Número</label> <input ng-model="resolucion.numero" id="numero"
				name="numero" required ng-maxlength="50">
			<div data-ng-messages="frmResolucion.numero.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="100">
			<label>Descripción</label> <textarea
				ng-model="resolucion.descripcion" id="descripcion"
				name="descripcion" required ng-maxlength="200"></textarea>
			<div data-ng-messages="frmResolucion.descripcion.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 200 caracteres.</div>
			</div>
			</md-input-container>
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="frmResolucion.$invalid && (accion=='Adicionar')">
	{{ accion }} </md-button> <md-button class="md-raised options" ng-click="cancel()"
		style="margin-right:20px;"> Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>