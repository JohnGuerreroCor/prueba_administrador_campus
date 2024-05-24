<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
.md-datepicker-input-container {
	width: 83%;
}

.validation-messages {
	font-size: 12px;
	color: #dd2c00;
	margin-left: 15px;
}

.disable {
	background: #8F141B;
    color: #8F141B;
    border: 1px solid #000;
}
.seleccionado{
	background: #C7B363;
	color: #C7B363;
    border: 1px solid #000;
}
.propio{
	background: #4D626C!important;	
	color: #4D626C;
    border: 1px solid #000;
} 

.listaRangos ul{
	list-style-type: none;
}
.listaRangos ul li {
	float:left!important;
	background: #e0e0e0;
    padding: 2px;
    border-radius: 11px;
    margin-right: 2px;
    margin-bottom: 2px;
    font-size: 11px;
}
</style>

<md-dialog aria-label="Formulario Curso" ng-cloak flex-gt-sm="90">
<form name="frmEspacioOcupacion" id="frmEspacioOcupacion" novalidate>
	<h1 class="template-title">Formulario Curso {{ctrlFrm.seleccionado}}</h1>
	<md-dialog-content>
	<md-subheader class="md-warn hint" ng-if="ctrlFrm.showHints">{{ctrlFrm.MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<h2>Seleccione la fecha, luego el docente y finalmente el salón</h2>
		</div>
		<br><br>
		<div layout-gt-xs="row">
			<md-datepicker 
				ng-change="ctrlFrm.cargarHorasDocente()"
				class="md-block" flex-gt-sm="" 
				data-ng-model="ctrlFrm.espacioOcupacion.fecha"
				id="fechaInicioEspacio" 
				name="fechaInicioEspacio"
				md-placeholder="Fecha Incial">
				<div class="validation-messages" data-ng-messages="frmEspacioOcupacion.fechaInicioEspacio.$error">
					<div data-ng-message="valid">La fecha no es valida.</div>
					<div data-ng-message="required">Campo requerido</div>
				</div>
			</md-datepicker>
			<div layout="column" flex>
				<md-autocomplete class="md-block" 
					md-input-name="autocompleteDocente"
					md-selected-item="ctrlFrm.selectedItemDoc"
					ng-disabled="!ctrlFrm.espacioOcupacion.fecha"
					md-search-text="ctrlFrm.searchTextDoc"
					md-search-text-change="ctrlFrm.searchTextChangeDoc(ctrlFrm.searchTextDoc)"
					md-selected-item-change="ctrlFrm.selectedItemChangeDoc(item)"
					md-items="item in ctrlFrm.itemsDoc"
					md-delay=3 
					md-item-text="item.display"
					md-min-length="0" 
					placeholder="Buscar Docente"  
					md-autofocus="false"
					md-floating-label="Docente" required>
				<md-item-template> 
					<span md-highlight-text="ctrlFrm.searchText" md-highlight-flags="^i">{{item.display}}</span>
				</md-item-template> 
					 <div ng-messages="frmEspacioOcupacion.autocompleteDocente.$error">
				      	<div ng-message="required">Campo requerido.</div>
				    </div>
				</md-autocomplete>
			</div>
			<div layout="column" flex>
				<md-autocomplete class="md-block"
					md-input-name="autoEspacio"
					ng-disabled="!ctrlFrm.espacioOcupacion.fecha"
					md-selected-item="ctrlFrm.selectedItemEspacio"
					md-search-text="ctrlFrm.searchTextEspacio"
					md-search-text-change="ctrlFrm.searchTextChangeEspacio(ctrlFrm.searchTextEspacio)"
					md-selected-item-change="ctrlFrm.selectedItemChangeEspacio(item)"
					md-items="item in ctrlFrm.itemsEspacio" md-delay=3
					md-item-text="item.display" md-min-length="0"
					placeholder="Buscar Espacio" md-autofocus="false"
					md-floating-label="Buscar Espacio" required> <md-item-template>
				<span md-highlight-text="ctrlFrm.searchTextEspacio"
					md-highlight-flags="^i">{{item.display}}</span> </md-item-template>
				<div ng-messages="frmEspacioOcupacion.autoEspacio.$error">
					<div ng-message="required">Campo requerido.</div>
				</div>
				</md-autocomplete>
			</div>
		</div>
		 <div layout-gt-xs="row">
					 <div flex>
				 		<span>Horas del curso</span>
				        <i class="fa fa-circle-thin propio" aria-hidden="true"></i>
					</div>
					 <div flex>
				 		<span>Horas Ocupadas</span>
				        <i class="fa fa-circle-thin disable" aria-hidden="true"></i>
				 	 </div>
					<div flex>
				 		<span >Horas Selecionadas</span>
				        <i class="fa fa-circle-thin seleccionado" aria-hidden="true"></i>
				 	</div>
					</div>
		 <div layout-gt-xs="row">
			 <div layout="column" flex class="listaRangos">
				 <ul>
				 	<li ng-repeat="item in ctrlFrm.rangos"> 
				 		<strong>{{item.diaText}}</strong>
				 		<em>({{item.inicio.horaText}} - {{item.fin.horaText}})</em>
					 	<button class="md-primary borrar-rango" ng-click="ctrlFrm.eliminarRango($index)">
				        	<i class="fa fa-times-circle" aria-hidden="true"></i>
				      	</button>
				 	</li>
				 </ul>
			 </div>
		 </div>
		 
			<div layout-gt-xs="row" class="table-container">
				<!-- <div layout="column" flex="30">
					<div layout-gt-xs="row">
					<div layout="column" flex>
						<md-datepicker 
							ng-change="ctrlFrm.cargarHorasDocente()"
							class="md-block" flex-gt-sm="" 
							data-ng-model="ctrlFrm.espacioOcupacion.fecha"
							id="fechaInicioEspacio" 
							name="fechaInicioEspacio"
							md-placeholder="Fecha Incial">
							<div class="validation-messages" data-ng-messages="frmEspacioOcupacion.fechaInicioEspacio.$error">
								<div data-ng-message="valid">La fecha no es valida.</div>
								<div data-ng-message="required">Campo requerido</div>
							</div>
						</md-datepicker>
						<br>
						</div>
					</div>
					<div layout-gt-xs="row">
					<div layout="column" flex>
						<md-autocomplete class="md-block" 
							md-input-name="autocompleteDocente"
							md-selected-item="ctrlFrm.selectedItemDoc"
							ng-disabled="!ctrlFrm.espacioOcupacion.fecha"
							md-search-text="ctrlFrm.searchTextDoc"
							md-search-text-change="ctrlFrm.searchTextChangeDoc(ctrlFrm.searchTextDoc)"
							md-selected-item-change="ctrlFrm.selectedItemChangeDoc(item)"
							md-items="item in ctrlFrm.itemsDoc"
							md-delay=3 
							md-item-text="item.display"
							md-min-length="0" 
							placeholder="Buscar Docente"  
							md-autofocus="false"
							md-floating-label="Docente" required>
						<md-item-template> 
							<span md-highlight-text="ctrlFrm.searchText" md-highlight-flags="^i">{{item.display}}</span>
						</md-item-template> 
							 <div ng-messages="frmEspacioOcupacion.autocompleteDocente.$error">
						      	<div ng-message="required">Campo requerido.</div>
						    </div>
						</md-autocomplete>
						</div>
					</div>
					<div layout-gt-xs="row">
					<div layout="column" flex>
						<md-autocomplete class="md-block"
							md-input-name="autoEspacio"
							ng-disabled="!ctrlFrm.espacioOcupacion.fecha"
							md-selected-item="ctrlFrm.selectedItemEspacio"
							md-search-text="ctrlFrm.searchTextEspacio"
							md-search-text-change="ctrlFrm.searchTextChangeEspacio(ctrlFrm.searchTextEspacio)"
							md-selected-item-change="ctrlFrm.selectedItemChangeEspacio(item)"
							md-items="item in ctrlFrm.itemsEspacio" md-delay=3
							md-item-text="item.display" md-min-length="0"
							placeholder="Buscar Espacio" md-autofocus="false"
							md-floating-label="Buscar Espacio" required> <md-item-template>
						<span md-highlight-text="ctrlFrm.searchTextEspacio"
							md-highlight-flags="^i">{{item.display}}</span> </md-item-template>
						<div ng-messages="frmEspacioOcupacion.autoEspacio.$error">
							<div ng-message="required">Campo requerido.</div>
						</div>
						</md-autocomplete>
						</div>
					</div>
				 	<ul>
					 	<li> 
					 		<strong>Horas del curso</strong>
						 	<button class="md-primary borrar-rango propio">
					        	<i class="fa fa-circle-thin" aria-hidden="true"></i>
					      	</button>
					 	</li>
					 	<li> 
					 		<strong>Horas Ocupadas</strong>
						 	<button class="md-primary borrar-rango disable">
					        	<i class="fa fa-circle-thin" aria-hidden="true"></i>
					      	</button>
					 	</li>
					 	<li> 
					 		<strong>Horas Selecionadas</strong>
						 	<button class="md-primary borrar-rango seleccionado">
					        	<i class="fa fa-circle-thin" aria-hidden="true"></i>
					      	</button>
					 	</li>
					 </ul>
				 </div> -->
				
					<table id="tblHorario" border="1" width="100%">
						<thead>
							<tr>
								<th>Hora</th>
								<th>Lunes</th>
								<th>Martes</th>
								<th>Miercoles</th>
								<th>Jueves</th>
								<th>Viernes</th>
								<th>Sabado</th>
								<th>Domingo</th>
							</tr>
						</thead>
						<tbody>
							<tr data-ng-repeat="(key, horaList) in ctrlFrm.JSONHora" ng-if="!((ctrlFrm.JSONHora).length == $index+1)">
								<td >{{ horaList.hora }} - {{ctrlFrm.JSONHora[$index+1].hora}}</td>
								<td data-ng-repeat="(keyd, dia) in ctrlFrm.JSONDia" 
									data-ng-class="{'disable':(horaList.codigo===ctrlFrm.JSONDiaConHorasOcupadas[keyd].listaHoras[key].codigo && ctrlFrm.JSONDiaConHorasOcupadas[keyd].listaHoras[key].ocupado===true),'propio':ctrlFrm.JSONHorasCurso[keyd].listaHoras[key].ocupado}"
									id="{{keyd + 1}}{{key}}"
									ng-init="fila=keyd; columna= key"
									data-ng-click="ctrlFrm.JSONDiaConHorasOcupadas[keyd].listaHoras[key].ocupado===false && ctrlFrm.funcionHora(horaList, dia, fila, columna, ctrlFrm.JSONHora[key+1].hora)"
									>
								</td>						
							</tr>
						</tbody>
					</table>
				
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
		<md-button class="options" data-ng-click="ctrlFrm.answer('ok')" data-ng-disabled="frmEspacioOcupacion.$invalid && (ctrlFrm.accion=='Horario')">
			Guardar
		</md-button> 
		<md-button class="md-raised options" data-ng-click="ctrlFrm.cancel()">
			Cancelar 
		</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>