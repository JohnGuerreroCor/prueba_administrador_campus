<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="NbcController">
		<md-content flex layout-padding>
			<div layout="row" flex="100" layout-xs="column">
				<div layout="column" flex>
					<md-input-container class="md-block"> <label>Identificacion</label> 
						<input name="txtId" id="txtId" required="">
					</md-input-container>
				</div>
				<div layout="column" flex>
					<md-button class="md-raised options" onClick="buscar()"> 
					<i class="fa fa-trash fa-option" aria-hidden="true"></i>
					Buscar </md-button>
				</div>
			</div>	
			<div layout="row" flex="100" layout-xs="column">
				<div layout="column" flex>
					<md-input-container class="md-block"> <label>Nombre</label> 
					<input type="hidden" name="txtCodigo" id="txtCodigo">
						<input name="txtNombre" id="txtNombre" required="">
					</md-input-container>
				</div>
				<div layout="column" flex>
					<md-input-container class="md-block"> <label>Correo</label> 
						<input name="txtCorreo" id="txtCorreo" required="">
					</md-input-container>
				</div>
			</div>
			<div layout="row" flex="100" layout-xs="column">
				<div layout="column" flex>
					<md-button class="md-raised options" onClick="guardar()"> 
					<i class="fa fa-trash fa-option" aria-hidden="true"></i>
					Guardar </md-button>
				</div>
			</div>			
		</md-content>
	</div>
</div>

