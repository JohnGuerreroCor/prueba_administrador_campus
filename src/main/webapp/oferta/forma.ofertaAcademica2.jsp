<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.md-datepicker-input-container {
	width: 85%;
	margin: auto;
}

.md-datepicker-input {
	width: 100%;
}
input[type="file"]{
	z-index: 999;
	line-height: 0;
	font-size: 0px;
	position: absolute;
	opacity: 0;
	filter: alpha(opacity = 0);-ms-filter: "alpha(opacity=0)";
	margin: 0;
	padding:0;
	left:0;
}
#btnCargue{
	text-align: center;
	background-color: brown;
	color: #fff;
	display: block;
	border-radius: 3px;
	width: 100%;
	height: 30px;
	font-size: 18px;
	float: left;
	line-height: 1.6em;
}
</style>
<md-dialog aria-label="Formulario Oferta Académica" data-ng-cloak
	flex="60">
<form name="frmOfertaAcademica" novalidate enctype="multipart/form-data">
	<h1 class="template-title">Formulario Oferta Académica {{seleccionado}}</h1>
	<md-dialog-content flex layout-padding> <md-subheader
		class="md-warn" id="mensaje" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content" data-slayer-responsivo>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block"> <label>Sede</label>
				<md-select data-ng-model="ofertaAcademica.programa.sede.codigo"
					id="codigoSede" name="codigoSede"
					data-ng-change="obtenerProgramas()" required=""> <md-option
					data-ng-repeat="sedes in JSONSedes" value="{{sedes.codigo}}">
				{{sedes.nombre}} </md-option> </md-select>
				<div data-ng-messages="frmOfertaAcademica.codigoSede.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block"> <label>Programa
					Académico</label> <md-select
					data-ng-model="ofertaAcademica.programa.codigo" id="codigoPrograma"
					name="codigoPrograma" data-ng-disabled="!JSONProgramas" required="">
				<md-option data-ng-repeat="programa in JSONProgramas"
					value="{{programa.codigo}}">
				{{programa.titulo_otorgado}} </md-option> </md-select>
				<div data-ng-messages="frmOfertaAcademica.codigoPrograma.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block"> <label>Requiere
					Pago Inscripción</label> <md-select  data-ng-model="ofertaAcademica.pago" id="pago"
					name="pago"> <md-option value="1">Si</md-option>
				<md-option value="0">No</md-option> </md-select>
				<div data-ng-messages="frmOfertaAcademica.pago.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>			
			<div layout="column" flex>
				<div class="md-block">
					<md-input-container class="md-block">
						<label>Valor</label> <input type="number"
							data-ng-model="ofertaAcademica.valor"
							name="valor" id="valor" data-ng-disabled="ofertaAcademica.pago == 0"
							data-ng-pattern="/^[0-9]{5,8}$/">
						<div data-ng-messages="ofertaAcademica.valor.$error">
							<div data-ng-message="required">Campo requerido.</div>
							<div data-ng-message="number">Solo números.</div>
							<div data-ng-message="pattern">Rango de número no permitido.</div>
						</div>
					</md-input-container>
				</div>
			</div>
		</div>


		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block md-input-has-placeholder"
					flex-gt-sm="50"> <label>Fecha Inicio de Inscripción</label> <input mdc-datetime-picker="" date="true" time="true"
					type="text" placeholder="YYYY-MM-DD HH:MM:SS" min-date="minDate"
					ng-model="ofertaAcademica.inscripcionFechaInicio" class=" md-input"
					name="inscripcionFechaInicio"> </md-input-container>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block md-input-has-placeholder"
					flex-gt-sm="50"> <label>Fecha Fin de Inscripción</label> <input mdc-datetime-picker="" date="true" time="true"
					type="text" placeholder="YYYY-MM-DD HH:MM:SS"
					min-date="ofertaAcademica.inscripcionFechaInicio" class=" md-input"
					data-ng-model="ofertaAcademica.inscripcionFechaFin"
					name="inscripcionFechaFin"> </md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block md-input-has-placeholder"
					flex-gt-sm="50"> <label>Fecha Inicio del Curso</label> <input
					mdc-datetime-picker="" date="true" time="true" type="text"
					placeholder="YYYY-MM-DD HH:MM:SS"
					min-date="ofertaAcademica.inscripcionFechaFin"
					ng-model="ofertaAcademica.fechaInicio" class=" md-input"
					name="fechaInicio"> </md-input-container>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block md-input-has-placeholder"
					flex-gt-sm="50"> <label>Fecha Fin del Curso</label> <input
					mdc-datetime-picker="" date="true" time="true" type="text"
					placeholder="YYYY-MM-DD HH:MM:SS"
					min-date="ofertaAcademica.fechaInicio" class=" md-input"
					data-ng-model="ofertaAcademica.fechaFin" name="fechaFin"> </md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block"> <label>Tipo
					Admisión</label> <md-select data-ng-model="ofertaAcademica.tipoAdmision"
					id="tipoAdmision" name="tipoAdmision" required> <md-option
					value="1">Manual</md-option> <md-option value="0">Automática</md-option>
				</md-select>
				<div data-ng-messages="frmOfertaAcademica.tipoAdmision.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
			<div layout="column" flex>
				<div layout="row" flex="100" layout-xs="column">
					<div layout="column" flex="70">
						<md-input-container class="md-block" flex> 
							<label>Subir Imagen (640px X 425px)</label>
							<input type="text" name="url" ng-model="imagen.name" disabled="">
					 	</md-input-container>
					</div>
					<div layout="column" flex="30">
					<md-input-container class="md-block"  flex>
						<div id="btnCargue" class="options">Seleccionar</div>
						<input type="file" name="imagen" accept="image/jpeg,image/jpg,image/png" 
							data-ng-model="imagen" 
							file-model="imagen">
						 </md-input-container>
					</div>
				</div>
				  
				  <!--<md-input-container class="md-block"> 
				<label>Imagen</label>
				<input type="file" name="imagen" accept="image/jpeg,image/jpg,image/png" 
						data-ng-model="imagen" 
						file-model="imagen">
				 </md-input-container>-->
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container class="md-block"> <label>Estado</label>
				<md-select data-ng-model="ofertaAcademica.ofertaAcademicaEstado.codigo"
					id="codigoEstado" name="codigoEstado"required=""> <md-option
					data-ng-repeat="estadosOferta in JSONEstadosOferta" value="{{estadosOferta.codigo}}">
				{{estadosOferta.nombre}} </md-option> </md-select>
				<div data-ng-messages="frmOfertaAcademica.codigoEstado.$error">
					<div data-ng-message="required">Campo requerido.</div>
				</div>
				</md-input-container>
			</div>
		</div>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<div class="md-block">
					<md-switch class="md-accent"
						ng-model="ofertaAcademica.crearUsuario" name="crearUsuario"
						aria-label="Crear Usuario" ng-true-value="1" ng-false-value="0">
					Crear usuario</md-switch>
				</div>
			</div>
			<div layout="column" flex>
				<div class="md-block">
					<md-switch class="md-accent"
						ng-model="ofertaAcademica.requiereCupo" name="requiereCupo"
						aria-label="Requiere Cupo" ng-true-value="1" ng-false-value="0">
					Requiere Cupo</md-switch>
				</div>
			</div>
			<div layout="column" flex>
				<md-input-container class="md-block"> <label>Número
					de cupo máximo</label> <input type="number"
					data-ng-disabled="ofertaAcademica.requiereCupo == 0;"
					data-ng-model="ofertaAcademica.cupoMax" name="cupoMax" id="cupoMax">
				</md-input-container>
			</div>
		</div>
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" data-ng-click="answer('ok')"
		data-ng-disabled="frmOfertaAcademica.$invalid && (accion=='Adicionar')">
	{{ accion }} </md-button> <md-button class="md-raised options"
		data-ng-click="cancel()"> Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>