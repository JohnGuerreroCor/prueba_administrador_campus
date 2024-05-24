package co.edu.usco.lcms.utility;

public class Respuesta {

	public static final int EJECUCION_OK = 1;
	public static final int EJECUCION_ERROR = 0;

	public static final int PROGRAMA_NO_ADMISION_AUTOMATICA = 2;
	public static final int ADMISION_EXISTE = 3;
	public static final int ADMISION_OK = 24;

	public static final int PLAN_ESTUDIANTE = 16;
	public static final int MATRICULA_ERROR = 17;
	public static final int PLAN_ACADEMICO = 15;
	public static final int PLAN_ACADEMICO_ASIGNATURA = 20;

	public static final String ADMISION_EXITOSA = "La admisión se realizó correctamente.";
	public static final String PROGRAMA_NO_ADMISION_AUTOMATICA_ERROR = "La admisión de esta oferta no es manual.";
	public static final String ADMISION_EXISTE_ERROR = "Ya existe una admision para esta oferta.";

	public static final String OPERACION_EJECUTADA_EXITOSAMENTE = "Operación Ejecutada exitosamente.";
	public static final String ERROR_EJECUTAR_OPERACION = "Error al ejecutar la operación.";

	public static final String MATRICULA_ERROR_OPERACION = "Error al tratar de crear la matricula.";
	public static final String PLAN_ACADEMICO_ERROR = "Error al consultar el plan academico o no existe para la oferta seleccionada.";
	public static final String PLAN_ACADEMICO_ASIGNATURA_ERROR = "Error al consultar el Plan Academico Asignatura.";
	public static final String PLAN_ESTUDIANTE_ERROR = "Error al tratar de crear el plan Estudiante, intentelo de nuevo.";

	int codigo;
	boolean exito;
	String mensaje;
	Object body;

	public Respuesta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Respuesta(int tipo) {
		super();

		if (tipo == EJECUCION_OK) {
			this.codigo = EJECUCION_OK;
			this.exito = true;
			this.mensaje = OPERACION_EJECUTADA_EXITOSAMENTE;
		}

		if (tipo == EJECUCION_ERROR) {
			this.codigo = EJECUCION_ERROR;
			this.exito = false;
			this.mensaje = ERROR_EJECUTAR_OPERACION;
		}

		if (tipo == PROGRAMA_NO_ADMISION_AUTOMATICA) {
			this.codigo = PROGRAMA_NO_ADMISION_AUTOMATICA;
			this.exito = false;
			this.mensaje = PROGRAMA_NO_ADMISION_AUTOMATICA_ERROR;
		}

		if (tipo == ADMISION_EXISTE) {
			this.codigo = ADMISION_EXISTE;
			this.exito = false;
			this.mensaje = ADMISION_EXISTE_ERROR;
		}

		if (tipo == ADMISION_OK) {
			this.codigo = ADMISION_OK;
			this.exito = false;
			this.mensaje = ADMISION_EXITOSA;
		}

		if (tipo == PLAN_ESTUDIANTE) {
			this.codigo = PLAN_ESTUDIANTE;
			this.exito = true;
			this.mensaje = PLAN_ESTUDIANTE_ERROR;
		}

		if (tipo == MATRICULA_ERROR) {
			this.codigo = MATRICULA_ERROR;
			this.exito = false;
			this.mensaje = MATRICULA_ERROR_OPERACION;
		}
	}

	public Respuesta(String string) {
		// TODO Auto-generated constructor stub
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
