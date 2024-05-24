<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<md-dialog aria-label="Agenda reserva espacio" data-ng-cloak flex="50">
<form name="frmRegConfiguracion" novalidate>
	<h1 class="template-title">
		Adjudicar Solicitud
		 <md-button class="md-icon-button" style="float: right" ng-click="ctrlFrm.cancel()" >
         	<i class="fa fa-times-circle" aria-hidden="true"></i>
         </md-button>
	</h1>
	<md-dialog-content flex layout-padding> 
	<md-subheader class="md-warn" id="mensaje" ng-if="ctrlFrm.showHints">{{ctrlFrm.MsgError}} </md-subheader>
	<div class="md-dialog-content" data-slayer-responsivo>
		<table class="receipt text-main-red" >
			<tr class="title-table-receipt">
				<td colspan="3"><strong>Solicitudes adjudicadas a este docente en este mes</strong></td>
			</tr>
			<tr class="reciept-color">
				<td><strong>Periodo</strong></td>
				<td><strong>Cantidad</strong></td>
				<td><strong>Núm. Horas</strong></td>
			</tr>
			<tr class="">
				<td>{{ctrlFrm.fechaActual | date:"MMMM"}}</td>
				<td>{{ctrlFrm.numSolicitudesMes}}</td>
				<td>{{ctrlFrm.numHorasMes}}</td>
			</tr>
			<tr class="title-table-receipt">
				<td colspan="3"><br><strong>Disponibilidad usuarios Adobe Connect</strong></td>
			</tr>			
			<tr class="reciept-color">
				<td><strong>Núm. Usuarios</strong></td>
				<td><strong>Ocupados en esta hora</strong></td>
				<td><strong>Disponibles</strong></td>
			</tr>			
			<tr class="">
				<td><strong>{{ ctrlFrm.numUsuarios }}</strong></td>
				<td><strong>{{ ctrlFrm.numUsuariosSolicitud }}</strong></td>
				<td><strong>{{ ctrlFrm.numUsuarios - ctrlFrm.numUsuariosSolicitud }}</strong></td>
			</tr>
		</table>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block">
					<p>Desea usted Adjudicar la solicitud del docente <b>{{ ctrlFrm.solicitud.uaaPersonal.persona.nombre }} {{ ctrlFrm.solicitud.uaaPersonal.persona.apellido }}</b> para la fecha <b>{{ ctrlFrm.solicitud.fecha | date:"EEEE d, MMMM, y" }}</b></p>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block">
					<label>Mensaje</label>
					<textarea ng-model="ctrlFrm.solicitud.mensaje"></textarea>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex layout-align="center center" ng-if="ctrlFrm.loading">
				<img ng-src="app/assets/img/admin/gif-load.gif" width="50">
				<p>Enviando información...</p>
			</div>
		</div>	
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	
	<md-button class="md-raised options" id="btnAdjudicar" data-ng-click="ctrlFrm.answer('Adjudicar')"
		data-ng-disabled="frmRegConfiguracion.$invalid">
	{{ ctrlFrm.accion }} </md-button> 
	
	<md-button class="md-raised options" id="btnRechazar"
		data-ng-click="ctrlFrm.answer('Rechazar');"> Rechazar </md-button> </md-dialog-actions>
</form>
</md-dialog>