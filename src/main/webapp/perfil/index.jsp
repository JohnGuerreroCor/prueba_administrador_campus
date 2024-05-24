<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div ng-cloak="">
	<div id="dialogContainer" data-ng-controller="PerfilController">
		<md-content class="fondoTabla" flex layout-padding>
		<h2>{{persona.rol}}</h2>
		<div data-slayer-responsivo>
			<form name="PerfilUsuarios">
				<!-- data-ng-disabled="true" Para desabilitar los campos-->
				<div layout="row" flex="100" layout-xs="column">
					<!-- Fila 4 -->					
						<div layout="column" flex>
							<!-- Campo Nombres -->
							<md-input-container class="md-block" flex-gt-sm>
								<label>Nombres</label>
								<input type="text" name="nombres" data-ng-model="persona.nombre" readonly="readonly" />
							</md-input-container>
							<!-- ***************************** -->
						</div>
						<div layout="column" flex>
							<!-- Campo Apellidos -->
							<md-input-container class="md-block" flex-gt-sm>
								<label>Apellidos</label> 
								<input type="text" name="apellidos" data-ng-model="persona.apellido" readonly="readonly" />
							</md-input-container>
							<!-- ***************************** -->
						</div>
					</div>
					</div>
					<!-- ***************************** -->
					<div layout="row" flex="100" layout-xs="column">
					<!--Primer columna-->
					<div layout="column" flex>
						<!-- Campo Tipo Identificacion toca ponerlo en los input-->
						<md-input-container> 
							<label>Tipo de Documento</label> 
							<input data-ng-model="persona.tipoIdentificacion.nombre" readonly="readonly" >
							</md-select>
						</md-input-container>
						<!-- ***************************** -->
					</div>
					<!-- ***************************** -->
					<!--Segunda columna-->
					<div layout="column" flex layout-xs="column">
						<md-input-container class="md-block" flex-gt-sm>
							<label>Numero De Identificacion</label> 
							<input name="numeroIdentificacion" data-ng-model="persona.identificacion" readonly="readonly"/>
						</md-input-container>
						<!-- ***************************** -->
					</div>
					<!-- ***************************** -->
				</div>
				<!-- Fila 2 -->
				<div layout="row" flex="100" layout-xs="column">
					<!--Primer columna-->
					<div layout="column" flex>
						<!-- Campo Nombre-->
						<md-input-container> 
							<label>Dirección de residencia</label>
							<input type="text" name="lugarResiden" data-ng-model="persona.direccionResidencia" readonly="readonly"/>
						</md-input-container>
						<!-- ***************************** -->
					</div>
					<!--Segunda columna-->
					<div layout="column" flex>
						<!-- Campo Numero de Telefono -->
						<md-input-container class="md-block" flex-gt-sm>
							<label>Número Teléfonico</label>
							<input name="numeroTel" data-ng-model="persona.telefono" readonly="readonly" />
						</md-input-container>
						<!-- ***************************** -->
					</div>
				</div>
				<!-- ***************************** -->
				<!-- Fila 3 -->
				<div layout="row" flex="100" layout-xs="column">
					<!--Primer columna-->
					<div layout="column" flex>
						<!-- Campo Lugar de residencia -->
						<md-input-container class="md-block" flex-gt-sm>
							<label>Email</label>
							<input type="text" name="lugarResiden" data-ng-model="persona.email" readonly="readonly" />
						</md-input-container>
						<!-- ***************************** -->
					</div>
					
				</div>
				<!-- ***************************** -->
			</form>
		</div>
		</md-content>
	</div>
</div>