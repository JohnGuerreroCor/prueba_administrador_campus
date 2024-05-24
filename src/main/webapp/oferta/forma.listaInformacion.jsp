<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<md-dialog aria-label="Lista Oferta Información" ng-cloak flex="90">
	<h1 class="template-title">Lista Oferta Información {{seleccionado}}</h1>
	<md-dialog-content>
	<md-subheader class="md-warn" data-ng-if="msgLista">No hay información de la oferta.</md-subheader>
	<div layout-gt-xs="row" id="container-list-subject">
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" data-ng-repeat="listaInfo in JSONOfertaInformacion" ng-if="$even">
	          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
	          <div class="md-list-item-text" layout="column">
	            <h3>Título:{{ listaInfo.titulo }}</h3>
	            <h3>Orden:{{ listaInfo.orden }}</h3>
	            <h4>
	            	<i>Contenido: {{ listaInfo.contenido }}</i>
	            </h4>	           
	          </div>
	          <i class="fa fa-trash btn-delete" id="{{listaInfo.codigo}}" aria-hidden="true" data-ng-click="eliminarInformacion($event)">
	          	<md-tooltip md-direction="left">
		          Eliminar
		        </md-tooltip>
	          </i>
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list>
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" data-ng-repeat="listaInfo in JSONOfertaInformacion" ng-if="$odd">
	          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
	          <div class="md-list-item-text" layout="column">
	            <h3>Título:{{ listaInfo.titulo }}</h3>
	            <h3>Orden:{{ listaInfo.orden }}</h3>
	            <h4>
	            	<i>Contenido: {{ listaInfo.contenido }}</i>
	            </h4>	           
	          </div>
	          <i class="fa fa-trash btn-delete" id="{{listaInfo.codigo}}" aria-hidden="true" data-ng-click="eliminarInformacion($event)">
	          	<md-tooltip md-direction="left">
		          Eliminar
		        </md-tooltip>
	          </i>
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list>
	</div>			
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	 <md-button class="md-raised md-primary" data-ng-click="hide($event)">
		Cerrar 
	</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>