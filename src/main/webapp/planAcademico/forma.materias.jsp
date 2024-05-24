<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<md-dialog aria-label="Formulario Asignaturas" data-ng-cloak=""
	flex="90">
<form name="frmAsignatura" novalidate
	data-ng-submit="frmAsignatura.$valid && ctrl.agregarAsignatura()">
	<h1 class="template-title">Adiccionar Asignatura  {{ctrl.seleccionado}}</h1>
	<md-dialog-content>
	<md-subheader class="md-warn" data-ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<h2 class="md-block" flex-gt-sm="20">Buscar asignatura</h2>
			<md-autocomplete flex-gt-sm="80" md-selected-item="ctrl.selectedItem"
				md-search-text="ctrl.searchText"
				md-search-text-change="ctrl.searchTextChange(ctrl.searchText)"
				md-selected-item-change="ctrl.selectedItemChange(item)"
				md-items="item in ctrl.items" md-delay=3 md-item-text="item.display"
				md-min-length="0" placeholder="Buscar asignatura">
			<md-item-template> <span
				md-highlight-text="ctrl.searchText" md-highlight-flags="^i">{{item.display}}</span>
			</md-item-template> </md-autocomplete>
		</div>
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Componente</label> <md-select
				data-ng-model="ctrl.asignatura.componente.codigoComponente"
				id="codigoComponente" name="codigoComponente" required>
			<md-option data-ng-repeat="componente in ctrl.listaComponentes"
				value="{{componente.codigoComponente}}">{{componente.nombreComponente}}</md-option>
			</md-select>
			<div data-ng-messages="frmAsignatura.codigoComponente.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Intensidad (Horas)</label> <input type="number"
				data-ng-model="ctrl.asignatura.intensidad" id="intensidad"
				name="intensidad" required ng-pattern="/^[0-9]{1,3}$/">
			<div data-ng-messages="frmAsignatura.intensidad.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="pattern">Rango de número no permitido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-sm="row">
	
	
	
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Nota minima</label> <input type="number"
				data-ng-model="ctrl.asignatura.notaMinima" id="notaMinima"
				name="notaMinima" required ng-pattern="/^[0-9.]{1,3}$/">
			<div data-ng-messages="frmAsignatura.notaMinima.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="pattern">Rango de número no permitido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Módulo</label> 
				<md-select	data-ng-model="ctrl.asignatura.semestre" id="semestre" name="semestre">
					 <md-option	value="1" ng-selected="true">Primero</md-option> 
					 <md-option value="2" >Segundo</md-option>
					 <md-option	value="3" >Tercero</md-option> 
					 <md-option value="4" >Cuarto</md-option>
					 <md-option	value="5" >Quinto</md-option> 
					 <md-option value="6" >Sexto</md-option>
					 <md-option	value="7" >Séptimo</md-option> 
					 <md-option value="8" >Octavo</md-option>
					 <md-option	value="9" >Noveno</md-option> 
					 <md-option value="10" >Décimo</md-option>
					 <md-option	value="11" >Undécimo</md-option> 
					 <md-option value="12" >Duodécimo</md-option>
					 <md-option	value="13" >Decimotercero</md-option> 
					 <md-option value="14" >Decimocuarto</md-option>
					 <md-option	value="15" >Decimoquinto</md-option> 
				</md-select>
				<div data-ng-messages="frmAsignatura.creditosPrerequisito.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
		</div>
		<fieldset  style="display: none">
			<div layout-gt-sm="row">
				<div class="md-block" flex-gt-sm="">
					<md-switch class="md-accent" data-ng-model="ctrl.asignatura.pertenece"
						name="pertenece" aria-label="Pertenece al planacademico"
						ng-true-value="1" ng-false-value="1" class="md-primary">
					Pertenece al plan Académico </md-switch>
				</div>
				<div class="md-block" flex-gt-sm="">
					<md-switch class="md-accent" ng-model="ctrl.asignatura.programable"
						name="programable" aria-label="Es Programable" ng-true-value="1"
						ng-false-value="1" class="md-primary"> Es Programable </md-switch>
				</div>
				<div class="md-block" flex-gt-sm="">
					<md-switch class="md-accent" ng-model="ctrl.asignatura.requisito"
						name="requisito" aria-label="Es requisito de grado"
						ng-true-value="1" ng-false-value="0" class="md-primary">
					Es requisito de grado </md-switch>
				</div>
			</div>
		</fieldset>
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>Horas de Trabajo por Módulo</label> <input type="number"
				data-ng-model="ctrl.asignatura.trabajoIndependiente"
				id="trabajoIndependiente" name="trabajoIndependiente" required ng-pattern="/^[0-9]{1,3}$/" >
			<div data-ng-messages="frmAsignatura.trabajoIndependiente.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="number">Solo números.</div>
				<div data-ng-message="pattern">Rango de número no permitido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>Creditos</label> <input type="number"
				data-ng-model="ctrl.asignatura.creditos"
				id="creditos" name="creditos" required ng-pattern="/^[0-9]{1,3}$/" >
			<div data-ng-messages="frmAsignatura.creditos.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="number">Solo números.</div>
				<div data-ng-message="pattern">Rango de número no permitido.</div>
			</div>
			</md-input-container>
		</div>
		<!-- 
		<div layout-gt-sm="row">
			<div class="md-block" flex-gt-sm="">
				<md-switch class="md-accent" ng-model="ctrl.asignatura.programable"
					name="programable" aria-label="Es Programable" ng-true-value="1"
					ng-false-value="0" class="md-primary"> Es Programable </md-switch>
			</div>
			<div class="md-block" flex-gt-sm="">
				<md-switch class="md-accent" ng-model="ctrl.asignatura.electiva"
					name="electiva" aria-label="Requisito Electiva" ng-true-value="1"
					ng-false-value="0" class="md-primary"> Requisito
				Electiva </md-switch>
			</div>
			<div class="md-block" flex-gt-sm="">
				<md-switch class="md-accent" ng-model="ctrl.asignatura.activo"
					name="activo" aria-label="Activo" ng-true-value="1"
					ng-false-value="0" class="md-primary"> Activo </md-switch>
			</div>
		</div>
		 -->
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button type="submit" class="md-raised options" ng-disabled="frmAsignatura.$invalid">
	Adicionar </md-button> 
	<md-button class="md-raised options"	ng-click="ctrl.finish($event)" style="margin-right:20px;">
	Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>
