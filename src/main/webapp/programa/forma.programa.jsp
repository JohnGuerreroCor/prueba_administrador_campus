<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
md-radio-button {
	display: -webkit-inline-box;
	display: -moz-box;
}

.md-datepicker-input-container {
	width: 80%;
}
</style>

<md-dialog aria-label="Formulario Programas" ng-cloak flex-gt-sm="95">
<form name="frmPrograma" novalidate>
	<h1 class="template-title">Formulario Programa {{seleccionado}}</h1>
	<md-dialog-content> <md-subheader class="md-warn"
		ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>Titulo otorgado</label> <input
				ng-model="programa.titulo_otorgado" name="titulo_otorgado"
				id="titulo_otorgado" ng-maxlength="100" required>
			<div data-ng-messages="frmPrograma.titulo_otorgado.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 100 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>Descripción del Horario</label> <input ng-model="programa.horario"
				id="horario" name="horario" ng-maxlength="100" required>
			<div data-ng-messages="frmPrograma.horario.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 100 caracteres.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>Resolución</label> <md-select class="md-accent"
				data-ng-model="programa.resolucion.codigo" id="codigoResolucion"
				name="codigoResolucion" required> <md-option
				data-ng-repeat="resolucion in JSONResolucion"
				value="{{resolucion.codigo}}">{{resolucion.numero}}</md-option> </md-select>
			<div data-ng-messages="frmPrograma.codigoResolucion.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Calendario</label> 
			<md-select class="md-accent" data-ng-model="programa.calendario" id="calendario" name="calendario" required>
			<md-option value="A">A(Presencial)</md-option> 
			<md-option value="C">C(Postgrado)</md-option>
			<md-option value="D">D(Distancia)</md-option>
			<md-option value="E">E(Virtual)</md-option>
			</md-select>
			<div data-ng-messages="frmPrograma.calendario.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-datepicker class="md-block" flex-gt-sm="25" required=""
				ng-model="programa.creacion" id="creacion" name="creacion"
				md-placeholder="Fecha Creación" md-max-date="maxDate"> 
				<div class="validation-messages" data-ng-messages="frmPrograma.creacion.$error">
					<div data-ng-message="valid">La fecha no es valida.</div>
					<div data-ng-message="required">Campo requerido</div>
					 <div ng-message="maxdate">La fecha no puede ser superior a la actual!</div>
				</div>	
			</md-datepicker>
			<!--  
			<div class="validation-messages" data-ng-messages="frmPrograma.creacion.$error">
				<div data-ng-message="valid">La fecha no es valida.</div>
				<div data-ng-message="required">Campo requerido</div>
			</div>
			-->
		</div>
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label><b>Unidad Académica</b></label> </md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Tipo UAA</label> <md-select
				data-ng-model="programa.uaa.uaaTipo.codigo" id="codigoTipoUaa"
				name="codigoTipoUaa" data-ng-change="obtenerUaa()" required>
			<md-option data-ng-repeat="uaaTipos in JSONUaaTipo"
				value="{{uaaTipos.codigo}}">{{uaaTipos.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmPrograma.codigoTipoUaa.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>UAA</label> <md-select data-ng-model="programa.uaa.codigo"
				id="codigoUaa" name="codigoUaa" data-ng-disabled="!JSONUaa" required>
			<md-option data-ng-repeat="uaaList in JSONUaa"
				value="{{uaaList.codigo}}">{{uaaList.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmPrograma.codigoUaa.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<!-- <md-input-container class="md-block" flex-gt-sm="25">
			<label>Tipo Admisión</label> <md-select
				data-ng-model="programa.tipoAdmision" id="tipoAdmision"
				name="tipoAdmision" required> <md-option value="0">Automatico</md-option>
			<md-option value="1">Manual</md-option> </md-select>
			<div data-ng-messages="frmPrograma.tipoAdmision.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>-->
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>NBC</label> <md-select class="md-accent"
				data-ng-model="programa.nbc.codigo" id="codigoNbc" name="codigoNbc"
				required> <md-option
				data-ng-repeat="nbcLista in JSONNbc" value="{{nbcLista.codigo}}">{{nbcLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmPrograma.codigoNbc.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Sede</label> <md-select class="md-accent"
				data-ng-model="programa.sede.codigo" id="codigoSede"
				name="codigoSede" required> <md-option
				data-ng-repeat="sedes in JSONSedes" value="{{sedes.codigo}}">{{sedes.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmPrograma.codigoSede.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Nivel Académico</label> <md-select class="md-accent"
				data-ng-model="programa.nivelAcademico.codigo"
				id="codigoNivelAcademico" name="codigoNivelAcademico" required>
			<md-option data-ng-repeat="nivelAcademico in JSONNivelAcademico"
				value="{{nivelAcademico.codigo}}">{{nivelAcademico.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmPrograma.codigoNivelAcademico.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Jornada</label> <md-select class="md-accent"
				data-ng-model="programa.jornada.codigo" id="codigoJornada"
				name="codigoJornada" required> <md-option
				data-ng-repeat="jornada in JSONJornada" value="{{jornada.codigo}}">{{jornada.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmPrograma.codigoJornada.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Convenio</label> <md-select class="md-accent"
				data-ng-model="programa.convenio.codigo" id="codigoConvenio"
				name="codigoConvenio" required> <md-option
				data-ng-repeat="convenio in JSONConvenio"
				value="{{convenio.codigo}}">{{convenio.descripcion}}</md-option> </md-select>
			<div data-ng-messages="frmPrograma.codigoConvenio.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Modalidad</label> <md-select class="md-accent"
				data-ng-model="programa.modalidad.codigo" id="codigoModalidad"
				name="codigoModalidad" required> <md-option
				data-ng-repeat="modalidad in JSONModalidad"
				value="{{modalidad.codigo}}">{{modalidad.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmPrograma.codigoModalidad.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Estado</label> <md-select class="md-accent"
				data-ng-model="programa.programaEstado.codigo" id="estado"
				name="estado" required> <md-option
				data-ng-repeat="estado in JSONEstados" value="{{estado.codigo}}">{{estado.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmPrograma.estado.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-radio-group ng-model="programa.propio" class="md-block"
				flex-gt-sm="25" required> <label>Propio</label> <md-radio-button
				value="S" class="md-accent">Si</md-radio-button> <md-radio-button
				value="N" class="md-accent">No</md-radio-button>
			<div data-ng-messages="frmAsignatura.propio.$error">
				<div data-ng-message="required">Campo obligatorio.</div>
			</div>
			</md-radio-group>
		</div>
		
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
		<md-button class="md-raised options" data-ng-disabled="frmPrograma.$invalid && (accion=='Adicionar')" data-ng-click="answer('ok')"> {{accion}} </md-button>
		<md-button class="md-raised options" ng-click="cancel()"> Cancelar </md-button> 
	</md-dialog-actions>
</form>
</md-dialog>