<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<md-dialog aria-label="Lista Grabaciones" flex="90">
	<h1 class="template-title">Lista Grabaciones</h1>
	<md-dialog-content>
		
	<md-subheader class="md-warn" data-ng-if="ctrl.msgLista">No hay grabaciones registradas a esta videoconferencia.</md-subheader>
		
	<div layout-gt-xs="row" id="container-list-subject">
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" ng-repeat="listGrabaciones in ctrl.JSONGrabaciones" ng-if="$even">
	          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
	          <div class="md-list-item-text" layout="column">
	            <h3>Videoconferencia: {{ listGrabaciones.title }}</h3>
	            <h4>
	            	Fecha Inicio:{{ listGrabaciones.start }}<br>
	            	Fecha Fin: {{ listGrabaciones.end }} 
	            </h4>
	            <h4>
	            	<b>Url: {{ listGrabaciones.mensaje }}</b>
	            </h4>
	          </div>
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list>	
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" ng-repeat="listGrabaciones in ctrl.JSONGrabaciones" ng-if="$odd">
	          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
	          <div class="md-list-item-text" layout="column">
	            <h3>Videoconferencia: {{ listGrabaciones.title }}</h3>
	            <h4>
	            	Fecha Inicio:{{ listGrabaciones.start }}<br>
	            	Fecha Fin: {{ listGrabaciones.end }} 
	            </h4>
	            <h4>
	            	<b>Url: {{ listGrabaciones.mensaje }}</b>
	            </h4>
	          </div>
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list>					
	</div>			
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	 <md-button class="md-raised md-primary" data-ng-click="ctrl.hide($event)">
		Cerrrar 
	</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>