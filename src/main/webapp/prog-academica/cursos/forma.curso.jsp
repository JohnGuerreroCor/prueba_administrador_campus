<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">

.md-datepicker-input-container {
	width: 77%;
}

.validation-messages {
	font-size: 12px;
	color: #dd2c00;
	margin-left: 15px;
}

</style>

<md-dialog aria-label="Formulario Curso" ng-cloak flex-gt-sm="70">
<form name="frmCurso" id="frmCurso" novalidate>
	<h1 class="template-title">Formulario Curso {{ctrlFrm.seleccionado}} </h1>
	<md-dialog-content>
	<md-subheader class="md-warn hint" ng-if="ctrlFrm.showHints">{{ctrlFrm.MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="50"> 
				<label>Sede</label>
				<md-select data-ng-model="ctrlFrm.curso.sede.codigo" id="codigoSede" name="codigoSede" required>
					<md-option data-ng-repeat="sedeList in ctrlFrm.JSONSedes" value="{{sedeList.codigo}}">
						{{ sedeList.nombre }}
					</md-option> 
				</md-select>
				<div data-ng-messages="frmCurso.codigoSede.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
			<div layout="column" flex>
				<md-autocomplete class="md-block" 
					md-input-name="autoPlanAcademico"
					md-selected-item="ctrlFrm.selectedItemPlanAcademico"
					md-search-text="ctrlFrm.searchTextPlanAcademico"
					md-search-text-change="ctrlFrm.searchTextChangePlanAcademico(ctrlFrm.searchTextPlanAcademico)"
					md-selected-item-change="ctrlFrm.selectedItemChangePlanAcademico(item)"
					md-items="item in ctrlFrm.itemsPlanAcademico" 
					md-delay=3 
					md-item-text="item.display"
					md-min-length="0" 
					placeholder="Buscar Plan Académico"  
					md-autofocus="false"
					md-floating-label="Plan Académico" required>
				<md-item-template> 
					<span md-highlight-text="ctrlFrm.searchText" md-highlight-flags="^i">{{item.display}}</span>
				</md-item-template> 
					 <div ng-messages="frmCurso.autoPlanAcademico.$error">
				      	<div ng-message="required">Campo requerido.</div>
				    </div>
				</md-autocomplete>
			</div>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm=""> 
				<label>Asignatura</label>
				<md-select data-ng-model="ctrlFrm.curso.asignatura.codigo" id="codigoAsignatura"
				name="codigoAsignatura" required>
					<md-option data-ng-repeat="asignaturaList in ctrlFrm.JSONAsignatura" value="{{asignaturaList.asignatura.codigo}}">
						{{ asignaturaList.asignatura.codigo }} / {{ asignaturaList.asignatura.nombre }}
					</md-option> 
				</md-select>
				<div data-ng-messages="frmCurso.codigoAsignatura.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
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
					 <div ng-messages="frmCurso.autocompleteDocente.$error">
				      	<div ng-message="required">Campo requerido.</div>
				    </div>
				</md-autocomplete>
			</div>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm=""> 
				<label>Tipo Curso</label>
				<md-select data-ng-model="ctrlFrm.curso.tipoCurso.codigo" id="codigoTipoCurso" name="codigoTipoCurso" required>
					<md-option data-ng-repeat="tipoCursoList in ctrlFrm.JSONTipoCurso" value="{{tipoCursoList.codigo}}">
						{{ tipoCursoList.nombre }}
					</md-option> 
				</md-select>
				<div data-ng-messages="frmCurso.codigoTipoCurso.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm=""> 
				<label>Tipo Escala</label>
				<md-select data-ng-model="ctrlFrm.curso.tipoEscala.codigo" id="codigoTipoEscala" name="codigoTipoEscala" required>
					<md-option data-ng-repeat="tipoEscalaList in ctrlFrm.JSONTipoEscala" value="{{tipoEscalaList.codigo}}">
						{{ tipoEscalaList.nombre }}
					</md-option> 
				</md-select>
				<div data-ng-messages="frmCurso.codigoTipoEscala.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Cupo</label>
				<input type="number" data-ng-model="ctrlFrm.curso.cupo" name="cupo" id="cupo" required data-ng-maxlength="3" data-ng-pattern="/^[0-9]{1,3}$/">
				<div data-ng-messages="frmCurso.cupo.$error">
					<div data-ng-message="required">Campo requerido.</div>
					<div data-ng-message="number">Solo números.</div>
					<div data-ng-message="pattern">Rango de número no permitido.</div>
					<div data-ng-message="maxlength">Máximo 3 caracteres.</div>
				</div>
			</md-input-container>
			<md-datepicker class="md-block" flex-gt-sm="50" required=""
				data-ng-model="ctrlFrm.curso.fechaInicio" id="fechaInicio" name="fechaInicio"
				md-placeholder="Fecha Incio" md-max-date="maxDate">
			<div class="validation-messages"
				data-ng-messages="frmCurso.fechaInicio.$error">
				<div data-ng-message="valid">La fecha no es valida.</div>
				<div data-ng-message="required">Campo requerido</div>
				<div data-ng-message="maxdate">La fecha no puede ser superior a la actual!</div>
			</div>
			</md-datepicker>
		</div>
		<div layout-gt-xs="row">
			<!--<md-input-container class="md-block" flex-gt-sm=""> 
				<label>Número de Grupo</label>
				<input data-ng-model="ctrlFrm.curso.grupo" name="grupo" id="grupo" required data-ng-pattern="/^[0-9]{2}$/">
				<div data-ng-messages="frmCurso.grupo.$error">
					<div data-ng-message="required">Campo requerido.</div>
					<div data-ng-message="number">Solo números (ej: 02).</div>
					<div data-ng-message="pattern">Rango de número no permitido (ej: 02).</div>
					<div data-ng-message="maxlength">Máximo 2 caracteres.</div>
				</div>
			</md-input-container>-->
			<md-input-container class="md-block" flex-gt-sm="">
				<label>Semanas</label>
				<input type="number" data-ng-model="ctrlFrm.curso.semanas" name="semanas" id="semanas" required data-ng-maxlength="2" data-ng-pattern="/^[0-9]{1,2}$/">
				<div data-ng-messages="frmCurso.semanas.$error">
					<div data-ng-message="required">Campo requerido.</div>
					<div data-ng-message="number">Solo números.</div>
					<div data-ng-message="pattern">Rango de número no permitido.</div>
					<div data-ng-message="maxlength">Máximo 2 caracteres.</div>
				</div>
			</md-input-container>
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
		<md-button class="options" data-ng-click="ctrlFrm.answer('ok')" data-ng-disabled="frmCurso.$invalid && (ctrlFrm.accion=='Adicionar')">
			{{ ctrlFrm.accion }} 
		</md-button> 
		<md-button class="md-raised options" data-ng-click="ctrlFrm.cancel()">
			Cancelar 
		</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>