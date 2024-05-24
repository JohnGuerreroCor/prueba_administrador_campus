<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
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
<md-dialog aria-label="Administración Slider" ng-cloak flex-gt-sm="65">
<form name="frmSlider" novalidate>
	<h1 class="template-title">Administración Slider</h1>
	<md-dialog-content> 
		<md-subheader class="md-warn" ng-if="showHints">{{MsgError}} </md-subheader>
		<div class="md-dialog-content">
			<div layout="row" flex="100" layout-xs="column">
				<div layout="column" flex>
					<md-input-container class="md-block"> 
						<label>Descripción</label> 
						<textarea type="text" data-ng-model="slider.descripcion" name="descripcion" required></textarea>
						<div data-ng-messages="frmSlider.descripcion.$error">
							<div data-ng-message="required">Campo requerido.</div>
						</div>
					</md-input-container>
				</div>
			</div>
			<div layout="row" flex="100" layout-xs="column">
				<div layout="column" flex>
					<md-input-container class="md-block"> 
						<label>Título</label> 
						<input type="text" data-ng-model="slider.alt" name="alt" required>
						<div data-ng-messages="frmSlider.alt.$error">
							<div data-ng-message="required">Campo requerido.</div>
						</div>
					</md-input-container>
				</div>
				<div layout="column" flex>
					<md-input-container class="md-block"> 
						<label>Secuencia</label> 
						<input type="number" data-ng-model="slider.secuencia" name="secuencia" ng-pattern="/^[0-9]{1,3}$/" required>
						<div data-ng-messages="frmSlider.secuencia.$error">
							<div data-ng-message="required">Campo requerido.</div>
							<div data-ng-message="number">Solo números.</div>
						<div data-ng-message="pattern">Rango de número no permitido.</div>
						</div>
					</md-input-container>
				</div>
			</div>
			<div layout="row" flex="100" layout-xs="column">
				<div layout="column" flex>
					<div layout="row" flex="100" layout-xs="column">
						<div layout="column" flex="70">
							<md-input-container class="md-block" flex> 
								<label>Subir Imagen</label>
								<input type="text" name="url" ng-model="imagen.name" disabled="" required>
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
				</div>
			</div>
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
		<md-button class="md-raised options" 
			data-ng-disabled="frmSlider.$invalid && (accion=='Adicionar')"
			data-ng-click="answer('ok')"> {{accion}} </md-button> 
		<md-button class="md-raised options" ng-click="cancel()"> Cancelar </md-button> 
	</md-dialog-actions>
</form>
</md-dialog>