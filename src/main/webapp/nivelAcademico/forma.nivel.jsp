<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<md-dialog aria-label="Formulario Nivel Academico" ng-cloak flex-gt-sm="50">
<form name="frmNivelAcademico" id="frmNivelAcademico" novalidate>
	<h1 class="template-title">Formulario Nivel Académico</h1>
	<md-dialog-content>
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm=""> 
				<label>Nombre</label>
				<input ng-model="nivel.nombre" id="nombre" name="nombre" required ng-maxlength="50">
				<div data-ng-messages="frmNivelAcademico.nombre.$error">
					<div data-ng-message="required">Campo requerido.</div>
					<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
				</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Tipo de Formalidad</label>
				<md-select data-ng-model="nivel.formalidad.codigo" id="codigoFormalidad"
				name="codigoFormalidad" required>
					<md-option data-ng-repeat="formalidadList in JSONFormalidad" value="{{formalidadList.codigo}}">
						{{formalidadList.nombre}}
					</md-option> 
				</md-select>
			<div data-ng-messages="frmNivelAcademico.codigoFormalidad.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div> 
			</md-input-container>
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
		<md-button class="options" data-ng-click="answer('ok')" data-ng-disabled="frmNivelAcademico.$invalid && (accion=='Adicionar')">
			{{ accion }} 
		</md-button> 
		<md-button class="md-raised options" data-ng-click="cancel()">
			Cancelar 
		</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>