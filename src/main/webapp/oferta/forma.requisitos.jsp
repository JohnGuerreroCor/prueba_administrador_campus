<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<md-dialog aria-label="Formulario Requisitos Programas" ng-cloak flex-gt-sm="75">
<form name="frmRequisitos" novalidate>
	<h1 class="template-title">Formulario Requisitos Oferta {{seleccionado}}</h1>
	<md-dialog-content>
	<md-subheader class="md-warn" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="60">
			<label>Descripción</label> <input
				ng-model="ofertaRequisito.descripcion" name="descripcion"
				id="descripcion" maxlength="250" required>
			<div data-ng-messages="frmRequisitos.descripcion.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="30">
			<label>Tipo Archivo</label> <md-select
				data-ng-model="ofertaRequisito.ofertaRequisitoTipo.codigo"
				id="codigoTipo" name="codigoTipo" required> <md-option
				data-ng-repeat="tipos in JSONRequisitoTipo" value="{{tipos.codigo}}">{{tipos.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmRequisitos.codigoTipo.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="10">
				<md-button type="submit" class="md-raised options"
				data-ng-disabled="!frmRequisitos.$valid && (accion=='Requisitos')"
				data-ng-click="answer('ok')">
					 Agregar 
				</md-button> 
			</md-input-container>
		</div>
		<div layout-gt-xs="row" id="container-list-subject">
			<md-list class="list-subjects" flex>
				<md-list-item class="md-3-line" data-ng-repeat="requisitos in JSONRequisitoOferta" data-ng-if="$even">
		          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
		          <div class="md-list-item-text" layout="column">
		            <h3>{{ requisitos.descripcion }}</h3>
		            <h4>Tipo Archivo:{{requisitos.ofertaRequisitoTipo.nombre}}</h4>
		          </div>		          
		          <i class="fa fa-trash btn-delete" aria-hidden="true" id="{{requisitos.codigo}}" data-ng-click="eliminarRequisito($event)">
		          	<md-tooltip md-direction="left">
			          Eliminar
			        </md-tooltip>
		          </i>		          
		          <md-divider></md-divider>
		        </md-list-item>
			</md-list>		
			<md-list class="list-subjects" flex>
				<md-list-item class="md-3-line" data-ng-repeat="requisitos in JSONRequisitoOferta" data-ng-if="$odd">
		          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
		          <div class="md-list-item-text" layout="column">
		            <h3>{{ requisitos.descripcion }}</h3>
		            <h4>Tipo Archivo:{{requisitos.ofertaRequisitoTipo.nombre}}</h4>
		          </div>
		          <i class="fa fa-trash btn-delete" aria-hidden="true" id="{{requisitos.codigo}}" data-ng-click="eliminarRequisito($event)">
		          	<md-tooltip md-direction="left">
			          Eliminar
			        </md-tooltip>
		          </i>
		          <md-divider></md-divider>
		        </md-list-item>
			</md-list>					
		</div>
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> 
		<md-button class="md-primary md-raised" data-ng-click="cancel()"> Cerrar </md-button>
	</md-dialog-actions>
</form>
</md-dialog>
