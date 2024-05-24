<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<md-dialog aria-label="Lista Asignaturas" flex="90">
	<h1 class="template-title">Lista Asignatura {{ctrl.seleccionado}}</h1>
	<md-dialog-content>
	
	
	<md-subheader class="md-warn" data-ng-if="ctrl.msgLista">No hay asignaturas registradas a este Plan Academico</md-subheader>
		
	<div layout-gt-xs="row" id="container-list-subject">
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" ng-repeat="listAsisPlan in ctrl.JSONListaAsignaturas" ng-if="$even">
	          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
	          <div class="md-list-item-text" layout="column">
	            <h3>{{ listAsisPlan.asignatura.codigo }} - {{ listAsisPlan.asignatura.nombre }} - {{ listAsisPlan.asignatura.nombreCorto }}</h3>
	            <h4>
	            	Módulo:{{ listAsisPlan.semestre }}
	            	Nota Minima: {{ listAsisPlan.notaMinima }} 
	            	<!--Programable: <span data-ng-if="listAsisPlan.programable==1">Si</span><span data-ng-if="listAsisPlan.programable==0">No</span>--> 
	            </h4>
	            <h4>
	            	<i>Nucleo: {{ listAsisPlan.nucleo.nombre }}</i>
	            	<i> Componente: {{ listAsisPlan.componente.nombreComponente }}</i>
	            	<i> Creditos: {{ listAsisPlan.creditos }}</i>
	            </h4>
	            <p>Intensidad:{{ listAsisPlan.intensidad }} Horas. Trabajo por Módulo:{{ listAsisPlan.trabajoIndependiente }} Horas</p>
	          </div>
	          <i class="fa fa-trash btn-delete" id="{{listAsisPlan.codigo}}" aria-hidden="true" data-ng-click="ctrl.eliminarPlanAsignatura($event)">
	          	<md-tooltip md-direction="left">
		          Eliminar
		        </md-tooltip>
	          </i>
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list>					
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line" ng-repeat="listAsisPlan in ctrl.JSONListaAsignaturas" ng-if="$odd">
	          <span class="md-avatar">{{$index+1}} <i class="fa fa-book" aria-hidden="true"></i></span>
	          <div class="md-list-item-text" layout="column">
	            <h3>{{ listAsisPlan.asignatura.codigo }} - {{ listAsisPlan.asignatura.nombre }} - {{ listAsisPlan.asignatura.nombreCorto }}</h3>
	            <h4>
	            	Módulo:{{ listAsisPlan.semestre }}
	            	Nota Minima: {{ listAsisPlan.notaMinima }} 
	            	<!--Programable: <span data-ng-if="listAsisPlan.programable==1">Si</span><span data-ng-if="listAsisPlan.programable==0">No</span> -->
	            </h4>
	            <h4>
	            	<i>Núcleo: {{ listAsisPlan.nucleo.nombre }}</i>
	            	<i>Componente: {{ listAsisPlan.componente.nombreComponente }}</i>
	            	<i>Creditos: {{ listAsisPlan.creditos }}</i>
	            </h4>
	            <p>Intensidad:{{ listAsisPlan.intensidad }} Horas. Trabajo por Módulo:{{ listAsisPlan.trabajoIndependiente }} Horas</p>
	          </div>
	          <i class="fa fa-trash btn-delete" id="{{listAsisPlan.codigo}}" aria-hidden="true" data-ng-click="ctrl.eliminarPlanAsignatura($event)">
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
	 <md-button class="md-raised md-primary" data-ng-click="ctrl.hide($event)">
		Cerrrar 
	</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>