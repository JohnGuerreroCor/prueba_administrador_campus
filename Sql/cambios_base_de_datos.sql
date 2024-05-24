<<<<<<<<<<<Plan estudios
Se agrega en la tabla plan_academico el campo total horas (pla_total_horas tipo int).
Se agrega en la tabla sedes el registro VIRTUAL.
Se agrega en la tabla convenio el campo con_estado (tipo varchar 1) por defecto 1.
Se agrega en la tabla nivel_academico el campo nia_estado (tipo varchar 1)  por defecto 1.
Se agrega en la tabla uaa el campo uaa_propietario_codigo (tipo int FK_uaa_usuario con la tabla usuario).
Se agrega en la tabla oferta_academica el campo ofa_requiere_cupo tipo varchar(1)  y  ofa_cupo_max tipo int.
Se agrega en la tabla oferta_academica el campo ofa_crear_usuario varchar 1.
se agrega campo for_codigo por defecto 1 (Registro formal) a las tablas (uaa, resolucion, programa, asignatura, plan_academico, plan_academico_asignatura, oferta_academica).
Se agregaron los siguientes registros en la tabla web_parametro:


insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO MODALIDA LA EDUCACIÓN NO FORMAL','MODALIDAD_EDUCACION_NO_FORMAL','5');
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO NIVEL ACADÉMICO LA EDUCACIÓN NO FORMAL','NIVEL_ACADEMICO_EDUCACION_NO_FORMAL','2,3,18');
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO NUCLEO LA EDUCACIÓN NO FORMAL','NUCLEO_EDUCACION_NO_FORMAL','3');
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO TABLA FORMALIDAD REGISTRO NO FORMAL','FORMALIDAD_NO_FORMAL','2');
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO FACULTAD TABLA UAA_TIPO REGISTRO NO FORMAL','FACULTAD_UAA_TIPO_NO_FORMAL','1');
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO TIPO UAA QUE PERTENECE LA EDUCACIÓN NO FORMAL','TIPO_UAA_EDUCACION_NO_FORMAL','1,2')
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO SALAS VIRTUALES PARA LA EDUCACIÓN NO FORMAL','SALAS_VIRTUALES_NO_FORMAL','1619,1620,1621,1622,1623')




Se agregan los siguientes registros en la tabla grupo:
insert into grupo (gru_estado, gru_id, gru_nombre) VALUES (1,'ADMINISTRADOR_LCMS','Administrador LCMS no formal');
insert into grupo (gru_estado, gru_id, gru_nombre) VALUES (1,'ADMINISTRADOR_FACULTAD_LCMS','Administrador Facultad LCMS no formal');

Se creo un esquema llamado inscripciones, en donde se crearon las tablas:
oferta_informacion 
oferta_requisito
oferta_requisito_tipo
/****** Object:  Table [inscripciones].[oferta_informacion]    Script Date: 21/11/2016 10:32:41 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [inscripciones].[oferta_informacion](
	[ofi_codigo] [int] IDENTITY(1,1) NOT NULL,
	[ofa_codigo] [int] NULL,
	[ofi_titulo] [varchar](255) NULL,
	[ofi_contenido] [varchar](max) NULL,
	[ofi_estado] [tinyint] NULL,
	[ofi_orden] [tinyint] NULL,
 CONSTRAINT [PK_oferta_informacion_1] PRIMARY KEY CLUSTERED 
(
	[ofi_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


GO
SET ANSI_PADDING ON
GO
/****** Object:  Table [inscripciones].[oferta_requisito]    Script Date: 21/11/2016 10:32:42 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [inscripciones].[oferta_requisito](
	[ore_codigo] [int] IDENTITY(1,1) NOT NULL,
	[ofa_codigo] [int] NULL,
	[otr_codigo] [int] NULL,
	[ore_descripcion] [varchar](255) NULL,
 CONSTRAINT [PK_programa_requisito] PRIMARY KEY CLUSTERED 
(
	[ore_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


GO
SET ANSI_PADDING ON
GO
/****** Object:  Table [inscripciones].[oferta_requisito_tipo]    Script Date: 21/11/2016 10:32:42 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [inscripciones].[oferta_requisito_tipo](
	[otr_codigo] [int] IDENTITY(1,1) NOT NULL,
	[otr_descripcion] [varchar](255) NULL,
 CONSTRAINT [PK_programa_requisito_tipo] PRIMARY KEY CLUSTERED 
(
	[otr_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


GO
SET ANSI_PADDING ON
GO




<<<<<<< Video Conferencia
insert into grupo (gru_estado, gru_id, gru_nombre) VALUES (1,'ADMIN_VIDEOCONFERENCIA_LCMS','Administrador Videoconferencia LCMS no formal');
insert into grupo (gru_estado, gru_id, gru_nombre) VALUES (1,'ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS','Administrador Videoconferencia Facultad LCMS no formal');

insert into espacio_tipo (eti_nombre) values ('SVV - SALA VIRTUAL VIDEOCONFERENCIA')
insert into bloque (blo_nombre, sus_codigo ) values ('BLOQUE VIRTUAL',1)

insert into espacio (esp_nombre, esp_nombre_corto, blo_codigo, eti_codigo, esp_descripcion, uaa_codigo_responsable, esp_capacidad, esp_area, esp_compartido) values ('SALA VIRTUAL 1','SV1',1,21,'SALA VIRTUAL PARA VIDEOCONFERENCIA',315,100,0,0)
insert into espacio (esp_nombre, esp_nombre_corto, blo_codigo, eti_codigo, esp_descripcion, uaa_codigo_responsable, esp_capacidad, esp_area, esp_compartido) values ('SALA VIRTUAL 2','SV2',1,21,'SALA VIRTUAL PARA VIDEOCONFERENCIA',315,100,0,0)
insert into espacio (esp_nombre, esp_nombre_corto, blo_codigo, eti_codigo, esp_descripcion, uaa_codigo_responsable, esp_capacidad, esp_area, esp_compartido) values ('SALA VIRTUAL 3','SV3',1,21,'SALA VIRTUAL PARA VIDEOCONFERENCIA',315,100,0,0)
insert into espacio (esp_nombre, esp_nombre_corto, blo_codigo, eti_codigo, esp_descripcion, uaa_codigo_responsable, esp_capacidad, esp_area, esp_compartido) values ('SALA VIRTUAL 4','SV4',1,21,'SALA VIRTUAL PARA VIDEOCONFERENCIA',315,100,0,0)
insert into espacio (esp_nombre, esp_nombre_corto, blo_codigo, eti_codigo, esp_descripcion, uaa_codigo_responsable, esp_capacidad, esp_area, esp_compartido) values ('SALA VIRTUAL 5','SV5',1,21,'SALA VIRTUAL PARA VIDEOCONFERENCIA',315,100,0,0)

select * from uaa where uaa_nombre like '%universidad%'//315 universidad

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO NUMERO DE LICENCIAS ADOBE CONNECT','NUMERO_LICENCIAS_ADOBE_CONNECT','5');

Creacion esquema videoconferencia



*************************************REGISTROS HORA****************************************************
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('6','06:00 am','06:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('6','06:15 am','06:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('6','06:30 am','06:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('6','06:45 am','06:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('7','07:00 am','07:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('7','07:15 am','07:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('7','07:30 am','07:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('7','07:45 am','07:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('8','08:00 am','08:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('8','08:15 am','08:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('8','08:30 am','08:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('8','08:45 am','08:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('9','09:00 am','09:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('9','09:15 am','09:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('9','09:30 am','09:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('9','09:45 am','09:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('10','10:00 am','10:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('10','10:15 am','10:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('10','10:30 am','10:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('10','10:45 am','10:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('11','11:00 am','11:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('11','11:15 am','11:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('11','11:30 am','11:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('11','11:45 am','11:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('12','12:00 pm','12:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('12','12:15 pm','12:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('12','12:30 pm','12:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('12','12:45 pm','12:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('13','01:00 pm','13:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('13','01:15 pm','13:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('13','01:30 pm','13:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('13','01:45 pm','13:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('14','02:00 pm','14:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('14','02:15 pm','14:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('14','02:30 pm','14:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('14','02:45 pm','14:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('15','03:00 pm','15:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('15','03:15 pm','15:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('15','03:30 pm','15:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('15','03:45 pm','15:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('16','04:00 pm','16:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('16','04:15 pm','16:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('16','04:30 pm','16:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('16','04:45 pm','16:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('17','05:00 pm','17:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('17','05:15 pm','17:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('17','05:30 pm','17:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('17','05:45 pm','17:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('18','06:00 pm','18:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('18','06:15 pm','18:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('18','06:30 pm','18:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('18','06:45 pm','18:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('19','07:00 pm','19:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('19','07:15 pm','19:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('19','07:30 pm','19:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('19','07:45 pm','19:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('20','08:00 pm','20:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('20','08:15 pm','20:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('20','08:30 pm','20:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('20','08:45 pm','20:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('21','09:00 pm','21:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('21','09:15 pm','21:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('21','09:30 pm','21:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('21','09:45 pm','21:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('22','10:00 pm','22:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('22','10:15 pm','22:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('22','10:30 pm','22:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('22','10:45 pm','22:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('23','11:00 pm','23:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('23','11:15 pm','23:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('23','11:30 pm','23:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('23','11:45 pm','23:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('12','12:00 am','00:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('12','12:15 am','00:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('12','12:30 am','00:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('1','12:45 am','00:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('1','01:00 am','01:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('1','01:15 am','01:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('1','01:30 am','01:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('2','01:45 am','01:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('2','02:00 am','02:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('2','02:15 am','02:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('2','02:30 am','02:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('3','02:45 am','02:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('3','03:00 am','03:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('3','03:15 am','03:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('3','03:30 am','03:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('3','03:45 am','03:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('4','04:00 am','04:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('4','04:15 am','04:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('4','04:30 am','04:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('4','04:45 am','04:45');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('5','05:00 am','05:00');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('5','05:15 am','05:15');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('5','05:30 am','05:30');
INSERT INTO videoconferencias.hora (hra_nombre, hra_hora, hra_hora_24h) VALUES ('5','05:45 am','05:45');
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO HORA PARA LA EDUCACIÓN NO FORMAL','HORA_NO_FORMAL','3')
insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO HORARIO PARA LA EDUCACIÓN NO FORMAL','HORARIO_NO_FORMAL','20')

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO OCUPACION TIPO PARA LA EDUCACIÓN NO FORMAL','TIPO_OCUPACION_NO_FORMAL','1')

insert into espacio_tipo (eti_nombre) values ('SVV - SALA VIRTUAL VIDEOCONFERENCIA')
