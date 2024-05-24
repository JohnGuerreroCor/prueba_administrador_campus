<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.md-datepicker-input-container {
	width: 80%;
}
</style>
<md-dialog aria-label="Formulario Convenios" ng-cloak flex-gt-sm="80">
<form name="frmConvenio" novalidate >
	<h1 class="template-title">Formulario Convenio</h1>
	<md-dialog-content>
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="100"> 
				<label>Descripcion</label>
				<input data-ng-model="convenio.descripcion" id="descripcion" name="descripcion" required>
				<div data-ng-messages="frmConvenio.descripcion.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>  
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="50">
				<label>Documento</label>
				<md-select data-ng-model="convenio.resolucion.codigo" id="codigoResolucion" name="codigoResolucion" required>
					<md-option data-ng-repeat="resolucion in JSONResolucion" value="{{resolucion.codigo}}">{{resolucion.numero}}</md-option>
				</md-select>
				<div data-ng-messages="frmConvenio.codigoResolucion.$error">
		          <div data-ng-message="required">Campo requerido.</div>
		        </div>
			</md-input-container>
			<md-datepicker class="md-block md-accent" flex-gt-sm="25" required
				data-ng-model="convenio.fecha_creacion" id="fecha_creacion" 
				name="fecha_creacion" md-placeholder="Fecha Creación" md-max-date="maxDate"> 
			</md-datepicker>
			<md-datepicker class="md-block md-accent" flex-gt-sm="25" required 
				data-ng-model="convenio.fecha_terminacion" id="fecha_terminacion" 
				name="fecha_terminacion" md-placeholder="Fecha Terminación" md-min-date="convenio.fecha_creacion"> 
			</md-datepicker>
		</div>
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="20">
				<label><b>Unidad Responsable</b></label>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="30">
				<label>Tipo UAA</label>
				<md-select data-ng-model="convenio.uaa.uaaTipo.codigo" id="codigoTipoUaa" name="codigoTipoUaa" data-ng-change="obtenerUaa()" required>
					<md-option data-ng-repeat="uaaTipos in JSONUaaTipo" value="{{uaaTipos.codigo}}">{{uaaTipos.nombre}}</md-option>					
				</md-select>
				<div data-ng-messages="frmConvenio.codigoTipoUaa.$error">
			          <div data-ng-message="required">Campo requerido.</div>
			    </div> 
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="50">
				<label>UAA</label> 
				<md-select data-ng-model="convenio.uaa.codigo" id="codigoUaa" name="codigoUaa" data-ng-disabled="!JSONUaa" required>
					<md-option data-ng-repeat="uaaList in JSONUaa"
						value="{{uaaList.codigo}}">{{uaaList.nombre}}</md-option>
				</md-select>
				<div data-ng-messages="frmConvenio.codigoUaa.$error">
			          <div data-ng-message="required">Campo requerido.</div>
			    </div> 
			</md-input-container>
		</div>
		<!--  
		<div layout-gt-xs="row">
			<md-input-container class="md-block"> 
				<label>Entidad</label>
				<input ng-model="convenio.entidad" id="entidad"> 
			</md-input-container>
		</div>
		-->
		<div layout-gt-xs="row">
			
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" data-ng-click="answer('ok')" data-ng-disabled="frmConvenio.$invalid && (accion=='Adicionar')">
	{{ accion }} </md-button> 
	<md-button class="md-raised options" data-ng-click="cancel()">
	Cancelar </md-button> 
	</md-dialog-actions>
</form>
</md-dialog>