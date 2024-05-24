<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
	
<md-dialog aria-label="Agenda reserva espacios" data-ng-cloak flex="60">
<form name="frmReservaEspacio" novalidate>
	<h1 class="template-title">Formulario reserva espacio videoconferencia</h1>
	<md-dialog-content flex layout-padding> 
	<md-subheader class="md-warn" id="mensaje" ng-if="ctrlFrm.showHints">{{ctrlFrm.MsgError}} </md-subheader>
	<h1 id="titleEvento">{{ ctrlFrm.fecha | date:"EEEE d MMM y" }}</h1>
	<div class="md-dialog-content" data-slayer-responsivo>	
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-autocomplete class="md-block" 
					md-input-name="autocompleteDocente"
					md-selected-item="ctrlFrm.selectedItemDoc"
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
					 <div ng-messages="frmReservaEspacio.autocompleteDocente.$error">
				      	<div ng-message="required">Campo requerido.</div>
				    </div>
				</md-autocomplete>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block" flex-gt-sm="100">
				<label>Curso</label> <md-select
					data-ng-model="ctrlFrm.solicitud.curso.codigo" id="curso"
					name="curso" required> <md-option
					data-ng-repeat="cursos in ctrlFrm.JSONCurso"
					value="{{cursos.codigo}}">{{cursos.codigo}} - {{cursos.asignatura.nombre}} ( grupo {{cursos.grupo}})</md-option> </md-select>
				<div data-ng-messages="frmReservaEspacio.curso.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block">
					<label>Hora Inicio</label> 
					<md-select	data-ng-model="ctrlFrm.solicitud.horaInicio.codigo" id="horaInicio"
						name="horaInicio" required>
					<md-option data-ng-repeat="horas in ctrlFrm.JSONHoras"
						value="{{horas.codigo}}">{{horas.hora}}</md-option> 
					</md-select>
					<div data-ng-messages="frmReservaEspacio.horaInicio.$error">
						<div data-ng-message="required">Campo requerido.</div>
					</div>
				</md-input-container>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block">
					<label>Hora Fin</label> 
					<md-select	data-ng-model="ctrlFrm.solicitud.horaFin.codigo" id="horaFin"
						name="horaFin" required>
					<md-option data-ng-repeat="horas in ctrlFrm.JSONHoras"
						value="{{horas.codigo}}">{{horas.hora}}</md-option> 
					</md-select>
					<div data-ng-messages="frmReservaEspacio.horaFin.$error">
						<div data-ng-message="required">Campo requerido.</div>
					</div>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block"> 
				<label>Tema</label> 
				<input  data-ng-model="ctrlFrm.solicitud.tema" id="tema" name="tema" required>
				<div data-ng-messages="frmReservaEspacio.tema.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block"> 
				<label>Tipo de Acceso</label> 
				<md-select  data-ng-model="ctrlFrm.solicitud.tipoAcceso" id="tipoAcceso" name="tipoAcceso" required> 
					<md-option value="0">Público</md-option>
					<md-option value="1">Con invitados</md-option>
					<md-option value="2">Solo estudiantes registrados</md-option>
				</md-select>
				<div data-ng-messages="frmReservaEspacio.tipoacceso.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block"> 
				<label>Descripción</label> 
				<textarea data-ng-model="ctrlFrm.solicitud.descripcion" id="descripcion" name="descripcion" required></textarea>
				<div data-ng-messages="frmReservaEspacio.descripcion.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
		</div>
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" data-ng-click="ctrlFrm.answer('ok')"
		data-ng-disabled="frmReservaEspacio.$invalid && (ctrlFrm.accion=='Adicionar')">
	{{ ctrlFrm.accion }} </md-button> <md-button class="md-raised options"
		data-ng-click="ctrlFrm.cancel()"> Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>