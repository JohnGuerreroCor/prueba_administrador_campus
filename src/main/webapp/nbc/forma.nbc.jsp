<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<style type="text/css">
	 .validation-messages {
		font-size: 12px;
		color: #dd2c00;
		margin: 10px 0 0 25px;
	}
	</style>
<md-dialog aria-label="Formulario NNBC" data-ng-cloak flex-gt-sm="50">
<form name="frmNbc"> <!--  novalidate data-ng-submit="frmNbc.$valid && answer('ok')"> -->
	<h1 class="template-title">Formulario Núcleo Básico de Conocimiento</h1>
	<md-dialog-content>
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Nombre</label>
				<input data-ng-model="nbc.nombre" name="nombre" id="nombre" required="">
				<div data-ng-messages="frmNbc.nombre.$error">
		          <div data-ng-message="required">Campo requerido.</div>
		        </div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Área</label>
				<md-select data-ng-model="nbc.area.codigo" name="codigoArea" id="codigoArea" required>
					<md-option data-ng-repeat="listArea in JSONArea" value="{{listArea.codigo}}">{{listArea.nombre}}</md-option>
				</md-select>
				<div data-ng-messages="frmNbc.codigoArea.$error">
		          <div data-ng-message="required">Campo requerido.</div>
		        </div>
			</md-input-container>
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> 
		<span flex></span>
		<md-button class="md-raised options" data-ng-click="answer('ok')" data-ng-disabled="frmNbc.$invalid && (accion=='Adicionar')">
			{{ accion }} 
		</md-button> 
		<md-button class="md-raised options" data-ng-click="cancel()"> Cancelar </md-button>
	</md-dialog-actions>
</form>
</md-dialog>