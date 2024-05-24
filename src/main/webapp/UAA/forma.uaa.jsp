<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<md-dialog aria-label="Formulario UAA" data-ng-cloak flex="90">
<form name="frmUaa" novalidate>
	<h1 class="template-title">Formulario UAA {{ seleccionado }}</h1>
	<md-dialog-content> <md-subheader class="md-warn"
		ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<form name="userForm">
		<fieldset class="fieldset-uaa">
			<legend>Programa a crear</legend>
				<div layout-gt-sm="row">
					<md-input-container class="md-block" flex-gt-sm="30">
					<label>Tipo UAA</label> <md-select
						data-ng-model="uaa.uaaTipo.codigo" id="codigoTipo"
						name="codigoTipo" required> <md-option
						data-ng-repeat="uaaTipos in JSONUaaTipo"
						value="{{uaaTipos.codigo}}">{{uaaTipos.nombre}}</md-option> </md-select>
					<div data-ng-messages="frmUaa.codigoTipo.$error">
						<div data-ng-message="required">Campo requerido.</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="70">
					<label>Nombre</label> <input data-ng-model="uaa.nombre" id="nombre"
						name="nombre" ng-maxlength="200" required>
					<div data-ng-messages="frmUaa.nombre.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="maxlength">Máximo 200 caracteres</div>
					</div>
					</md-input-container>
				</div>
				<div layout-gt-sm="row">
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Nombre Corto</label> <input data-ng-model="uaa.nombreCorto"
						id="nombreCorto" name="nombreCorto" ng-maxlength="100" required>
					<div data-ng-messages="frmUaa.nombreCorto.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="maxlength">Máximo 100 caracteres</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Nombre Impresión</label> <input
						data-ng-model="uaa.nombreImpresion" id="nombreImpresion"
						name="nombreImpresion" ng-maxlength="20" required>
					<div data-ng-messages="frmUaa.nombreImpresion.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="maxlength">Máximo 20 caracteres</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
						<label>Sede</label> 
						<md-select data-ng-model="uaa.sede.codigo" id="codigoSede" name="codigoSede" required> <md-option
							data-ng-repeat="sedes in JSONSedes" value="{{sedes.codigo}}">{{sedes.nombre}}</md-option>
						</md-select>
						<div data-ng-messages="frmUaa.codigoSede.$error">
							<div data-ng-message="required">Campo requerido.</div>
						</div>
					</md-input-container>
				</div>
			</fieldset>
			<fieldset class="fieldset-uaa">
				<legend>Unidad Responsable</legend>
				<div layout-gt-sm="row">					
					<md-input-container class="md-block" flex-gt-sm="25">
					<label>Tipo UAA</label> <md-select
						data-ng-model="uaa.codigoTipoUaaDep" id="codigoTipoUaa"
						name="codigoTipoUaa" data-ng-change="obtenerUaa()" required>
					<md-option data-ng-repeat="uaaTipos in JSONUaaTipoFac"
						value="{{uaaTipos.codigo}}">{{uaaTipos.nombre}}</md-option> </md-select>
					<div data-ng-messages="frmUaa.codigoTipoUaa.$error">
						<div data-ng-message="required">Campo requerido.</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>UAA</label> 
					<md-select data-ng-model="uaa.dependencia" id="dependencia" name="dependencia" data-ng-disabled="!JSONUaa" required>
					<md-option data-ng-repeat="uaaList in JSONUaa" value="{{uaaList.codigo}}">{{uaaList.nombre}}</md-option> </md-select>
					<div data-ng-messages="frmUaa.dependencia.$error">
						<div data-ng-message="required">Campo requerido.</div>
					</div>
					</md-input-container>
				</div>
			</fieldset>
			<fieldset class="fieldset-uaa">
			<legend>Información general</legend>
				<div layout-gt-sm="row">
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Email</label> <input type="email" data-ng-model="uaa.email"
						id="email" name="email">
					<div data-ng-messages="frmUaa.email.$error">
						<div data-ng-message="email">Email no valido.</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Email PQR</label> <input type="email"
						data-ng-model="uaa.emailPqr" id="emailPqr" name="emailPqr">
					<div data-ng-messages="frmUaa.emailPqr.$error">
						<div data-ng-message="email">Email no valido.</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Página</label> <input data-ng-model="uaa.pagina" id="pagina">
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Dirección</label> <input data-ng-model="uaa.direccion"
						id="direccion"> </md-input-container>
				</div>
				<div layout-gt-sm="row">
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Telefono</label> <input data-ng-model="uaa.telefono"
						id="telefono" name="telefono" ng-maxlength="50">
					<div data-ng-messages="frmUaa.telefono.$error">
						<div data-ng-message="maxlength">Máximo 50 caracteres</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Fax</label> <input data-ng-model="uaa.fax" id="fax"
						name="fax" ng-maxlength="50">
					<div data-ng-messages="frmUaa.fax.$error">
						<div data-ng-message="maxlength">Máximo 50 caracteres</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Centro Costos</label> <input onkeyup="javascript:this.value=this.value.toUpperCase();" data-ng-model="uaa.centroCostos"
						id="centroCostos" name="centroCostos" md-maxlength="3" required>
					<div data-ng-messages="frmUaa.centroCostos.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="md-maxlength">Máximo tres caracteres.</div>
					</div>
					</md-input-container>
					<md-input-container class="md-block" flex-gt-sm="">
					<label>Acrónimo</label> <input onkeyup="javascript:this.value=this.value.toUpperCase();" data-ng-model="uaa.acronimo"
						id="acronimo" name="acronimo" md-maxlength="2" required>
					<div data-ng-messages="frmUaa.acronimo.$error">
						<div data-ng-message="required">Campo requerido.</div>
						<div data-ng-message="md-maxlength">Se requiere dos
							caracteres.</div>
					</div>
					</md-input-container>
				</div>
			</fieldset>
		</form>
	</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="frmUaa.$invalid && (accion=='Adicionar')"> {{
	accion }} </md-button> <md-button class="md-raised options" ng-click="cancel()"
		style="margin-right:20px;"> Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>