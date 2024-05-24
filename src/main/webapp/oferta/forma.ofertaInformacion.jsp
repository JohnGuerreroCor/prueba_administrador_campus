<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.md-datepicker-input-container {
	width: 85%;
	margin: auto;
}

.md-datepicker-input {
	width: 100%;
}
</style>
<md-dialog aria-label="Formulario Oferta Académica" data-ng-cloak flex="80">
<form name="frmOfertaInformacion" novalidate>
	<h1 class="template-title">Formulario Oferta Académica {{seleccionado}}</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="80">
				<label>Título</label> 
				<input data-ng-model="ofertaInformacion.titulo" id="titulo" name="titulo" required="">
				<div data-ng-messages="frmOfertaInformacion.titulo.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="20">
				<label>Orden</label>
				<input type="number" data-ng-model="ofertaInformacion.orden" id="orden" name="orden" required="">
				<div data-ng-messages="frmOfertaInformacion.orden.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
				<textarea ui-tinymce="tinymceOptions" flex-gt-sm="" data-ng-model="ofertaInformacion.contenido" id="contenido" name="contenido" required=""></textarea>
				<div data-ng-messages="frmOfertaInformacion.contenido.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
		</div>
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
		<md-button class="md-raised options" data-ng-click="answer('ok')" data-ng-disabled="frmOfertaInformacion.$invalid && (accion=='Informacion')">
		Guardar </md-button>
		<md-button class="md-raised options" data-ng-click="cancel()"> Cancelar </md-button> 
	</md-dialog-actions>
</form>
</md-dialog>