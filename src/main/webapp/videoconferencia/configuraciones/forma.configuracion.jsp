<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
	
<md-dialog aria-label="Agenda reserva espacio" data-ng-cloak flex="60">
<form name="frmRegConfiguracion" novalidate>
	<h1 class="template-title">Configuraciones reserva espacio videoconferencia</h1>
	<md-dialog-content flex layout-padding> 
	<md-subheader class="md-warn" id="mensaje" ng-if="ctrlFrm.showHints">{{ctrlFrm.MsgError}} </md-subheader>
	<div class="md-dialog-content" data-slayer-responsivo>	
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block">
					<label>Facultad</label> 
					<input data-ng-model="ctrlFrm.configuraciones.uaa.nombre" readonly="readonly">
				</md-input-container>
			</div>
			<div layout="column" flex> 			
				<md-input-container class="md-block">
					<label>Número de días de anticipación para solicitar</label> 
					<input data-ng-model="ctrlFrm.configuraciones.diasAnticipacion" data-ng-pattern="/^[0-9]{1,3}$/" id="diasAnticipacion" name="diasAnticipacion" required>
					<div data-ng-messages="frmRegConfiguracion.diasAnticipacion.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="number">Solo números.</div>
						<div data-ng-message="pattern">Rango de número no permitido.</div>
					</div>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block">
					<label>Número máximo de solicitudes por día</label> 
					<input data-ng-model="ctrlFrm.configuraciones.maxSolicitudesDias" data-ng-pattern="/^[0-9]{1,3}$/" id="maxSolicitudesDias" name="maxSolicitudesDias" required>
					<div data-ng-messages="frmRegConfiguracion.maxSolicitudesDias.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="number">Solo números.</div>
						<div data-ng-message="pattern">Rango de número no permitido.</div>
					</div>
				</md-input-container>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block">
					<label>Número máximo de solicitudes por semana</label> 
					<input data-ng-model="ctrlFrm.configuraciones.maxSolicitudesSemana" data-ng-pattern="/^[0-9]{1,3}$/" id="maxSolicitudesSemana" name="maxSolicitudesSemana" required>
					<div data-ng-messages="frmRegConfiguracion.maxSolicitudesSemana.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="number">Solo números.</div>
						<div data-ng-message="pattern">Rango de número no permitido.</div>
					</div>
				</md-input-container>
			</div>
		</div>
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" data-ng-click="ctrlFrm.answer('ok')"
		data-ng-disabled="frmRegConfiguracion.$invalid">
	{{ ctrlFrm.accion }} </md-button> 
	<md-button class="md-raised options"
		data-ng-click="ctrlFrm.cancel()"> Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>