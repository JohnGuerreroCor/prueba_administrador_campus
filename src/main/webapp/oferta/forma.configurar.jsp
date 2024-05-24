<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<md-dialog aria-label="Formulario Oferta Académica" data-ng-cloak
	flex="60">
<form name="frmConfiguracion" novalidate enctype="multipart/form-data">
	<h1 class="template-title">Formulario Configuración Oferta Académica {{seleccionado}}</h1>
	<md-dialog-content flex layout-padding> <md-subheader
		class="md-warn" id="mensaje" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content" data-slayer-responsivo>
	
	<div layout-gt-xs="row" id="container-list-subject">
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" data-ng-repeat="listaConfig in JSONOfertaConfiguracion">
	          <!-- <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span> -->
	          <div class="md-list-item-text" layout="column" style="margin-top: 5px;margin-bottom: 3px;">
	            <h3>Tipo Usuario: <span data-ng-repeat="listaUsuario in listaConfig.usuarios"> {{ listaUsuario }}, </span></h3>
	            <h3>Dependencia: <span data-ng-repeat="listaUaa in listaConfig.uaa"> {{ listaUaa }}, </span></h3>
	          </div>
	          <!--<i class="fa fa-trash btn-delete" id="{{listaInfo.codigo}}" aria-hidden="true" data-ng-click="eliminarInformacion($event)">
	          	 <md-tooltip md-direction="left">
		          Eliminar
		        </md-tooltip> 
	          </i>-->
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list>
		<!-- 
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" data-ng-repeat="listaConfig in JSONOfertaConfiguracion" ng-if="$odd">
	          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
	          <div class="md-list-item-text" layout="column">
	            <h3>Tipo Usuario: <span data-ng-repeat="listaUsuario in listaConfig.usuarios"> {{ listaUsuario }}, </span></h3>
	            <h3>Dependencia: <span data-ng-repeat="listaUaa in listaConfig.uaa"> {{ listaUaa }}, </span></h3>
	          </div>
	          <i class="fa fa-trash btn-delete" id="{{listaInfo.codigo}}" aria-hidden="true" data-ng-click="eliminarInformacion($event)">
	          	<md-tooltip md-direction="left">
		          Eliminar
		        </md-tooltip>
	          </i>
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list> -->
	</div>			
	
	
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block"> <label>Tipo de Usuarios</label>
				<md-select data-ng-model="ofertaConfiguracion.tipoUsuario"
					id="tipoUsuario" name="tipoUsuario" required="" multiple>
					<md-option value="2">Estudiantes </md-option> 
					<md-option value="3">Docentes </md-option>
					<md-option value="1">Administrativos </md-option>
					<md-option value="6">Proveedores </md-option>
				</md-select>
				<div data-ng-messages="frmConfiguracion.tipoUsuario.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
		</div>
		
		<div layout-gt-sm="row">					
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Tipo UAA</label> <md-select
				data-ng-model="uaa.codigoTipoUaaDep" id="codigoTipoUaa"
				name="codigoTipoUaa" data-ng-change="obtenerUaa()">
			<md-option data-ng-repeat="uaaTipos in JSONUaaTipoFac"
				value="{{uaaTipos.codigo}}">{{uaaTipos.nombre}}</md-option> </md-select>
			
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
			<label>UAA</label> 
			<md-select data-ng-model="ofertaConfiguracion.uaa" id="dependencia" name="dependencia" data-ng-disabled="!JSONUaa" multiple="true">
			<md-option data-ng-repeat="uaaList in JSONUaa" value="{{uaaList.codigo}}">{{uaaList.nombre}}</md-option> </md-select>
			</md-input-container>
		</div>
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" data-ng-click="answer('Configurar')"
		data-ng-disabled="frmConfiguracion.$invalid && (accion=='Configurar')">
	{{ accion }} </md-button> <md-button class="md-raised options"
		data-ng-click="cancel()"> Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>