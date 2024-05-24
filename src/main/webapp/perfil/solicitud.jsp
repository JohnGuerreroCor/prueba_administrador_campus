<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div ng-cloak="">
	<form name="formulario" action="CorreoServlet" method="post">
		<md-content class="fondoTabla" flex layout-padding>
		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container> <label>destinatario</label>
				<input type="email" name="destinatario" ng-model="destinatario"
					required /> </md-input-container>
			</div>
		</div>

		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<md-input-container> <label>asunto</label> <input
					type="text" name="asunto" ng-model="asunto" required /> </md-input-container>
			</div>
		</div>

		<div layout="row" flex="100" layout-xs="column">
			<div layout="column" flex>
				<fieldset id="c-text">
					<legend> Actualización del campo</legend>
					<div id="cont-cjt">
						<textarea name="mensaje" id="cjt"required"></textarea>
						<div class="errors" ng-messages="formulario.mensaje.$error">
							<div ng-message="required">Required</div>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		</md-content>
		<md-actions layout="row" layout-align="end center"> 
			<md-button class="md-primary md-raised" type="submit" data-ng-disabled="formulario.$invalid"> Enviar </md-button>
		</md-actions>
	</form>
</div>