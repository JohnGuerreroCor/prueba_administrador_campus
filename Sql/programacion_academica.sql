
SELECT eo.eso_codigo, eo.esp_codigo, eo.uaa_codigo, eo.eso_fecha, eo.hor_codigo, eo.dia_codigo, 
'H' AS tabla, 
h.hra_codigo AS codigo_hora, 
vhi.hra_codigo AS codigo_hora_inicio, 
h.hra_inicio_24h AS hora_inicio_24h,
vhf.hra_codigo AS codigo_hora_fin,
h.hra_fin_24h AS hora_fin_24h, 
eo.eso_actividad, 
eo.eso_subgrupo, eo.uap_codigo, eo.eso_estado, eo.oct_codigo, eo.eso_descripcion, eo.cdp_codigo, 
eo.ctr_codigo, eo.eso_url 
FROM espacio_ocupacion eo
INNER JOIN hora h ON eo.hra_codigo = h.hra_codigo 
INNER JOIN videoconferencias.hora vhi ON h.hra_inicio_24h = vhi.hra_hora_24h
INNER JOIN videoconferencias.hora vhf ON h.hra_fin_24h = vhf.hra_hora_24h

UNION

SELECT eov.eso_codigo, eov.esp_codigo, eov.uaa_codigo, eov.eso_fecha, eov.hor_codigo, eov.dia_codigo, 
'HV' AS tabla, 
eov.hra_codigo AS codigo_hora, 
vhi.hra_codigo AS codigo_hora_inicio, 
vhi.hra_hora_24h AS hora_inicio_24h,
vhf.hra_codigo AS codigo_hora_fin,
vhf.hra_hora_24h AS hora_fin_24h, 
eov.eso_actividad, 
eov.eso_subgrupo, eov.uap_codigo, eov.eso_estado, eov.oct_codigo, eov.eso_descripcion, eov.cdp_codigo, 
eov.ctr_codigo, eov.eso_url 
FROM espacio_ocupacion_virtual eov
INNER JOIN videoconferencias.hora vhi ON eov.hra_codigo_inicio = vhi.hra_codigo
INNER JOIN videoconferencias.hora vhf ON eov.hra_codigo_fin = vhf.hra_codigo

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO CODIGO HORAS NO FORMAL','EDUCACION_VIRTUAL_CODIGO_HORA','65')



insert into tipo_escala (tes_nombre, tes_estado) values ('Númerico (0 - 5)',1), ('Aprobado/Reprobado',1), ('Tres Niveles',1)
insert into escala (tes_codigo, esc_nombre, esc_nota_min, esc_nota_max) values (1,'Calificacion 0 a 5',0,5),(2,'Aprobado',3.0,5.0),(2,'Reprobado',0,2.9)

se agrega a la tabla curso el campo llamado tipo_escala

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO ESTADO CANCELADO CURSOS','EDUCACION_VIRTUAL_ESTADO_ELIMINADO_CURSO','2')

insert into espacio (esp_nombre, esp_nombre_corto, blo_codigo, esp_capacidad, esp_area, eti_codigo, esp_compartido, esp_descripcion, uaa_codigo_responsable, esp_luminosidad, esp_espacio_codigo)
VALUES ('AULA VIRTUAL', 'SV1',1,100,0,20,0,'AULA VIRTUAL',315, NULL, NULL)

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('TIPO ESPACIO VIRTUAL','EDUCACION_VIRTUAL_TIPO_ESPACIO_VIRTUAL','20')


USE [academia3000_lcms]
GO
/****** Object:  Table [dbo].[tipo_escala]    Script Date: 05/03/2017 16:02:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tipo_escala](
	[tes_codigo] [int] IDENTITY(1,1) NOT NULL,
	[tes_nombre] [varchar](255) NULL,
	[tes_estado] [varchar](1) NULL,
 CONSTRAINT [PK_tipo_escala] PRIMARY KEY CLUSTERED 
(
	[tes_codigo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Table [dbo].[escala]    Script Date: 05/03/2017 16:02:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[escala](
	[esc_codigo] [int] IDENTITY(1,1) NOT NULL,
	[tes_codigo] [int] NULL,
	[esc_nombre] [varchar](250) NULL,
	[esc_nota_min] [float] NULL,
	[esc_nota_max] [float] NULL,
	[esc_nota] [float] NULL,
	[esc_estado] [varchar](1) NULL,
 CONSTRAINT [PK_escala] PRIMARY KEY CLUSTERED 
(
	[esc_codigo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Table [dbo].[escala_plan_academico_asignatura]    Script Date: 05/03/2017 16:02:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[escala_plan_academico_asignatura](
	[epa_codigo] [int] IDENTITY(1,1) NOT NULL,
	[esc_codigo] [int] NULL,
	[paa_codigo] [int] NULL,
	[epa_estado] [varchar](1) NULL,
 CONSTRAINT [PK_escala_plan_academico_asignatura] PRIMARY KEY CLUSTERED 
(
	[epa_codigo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Default [DF_escala_esc_estado]    Script Date: 05/03/2017 16:02:30 ******/
ALTER TABLE [dbo].[escala] ADD  CONSTRAINT [DF_escala_esc_estado]  DEFAULT ((1)) FOR [esc_estado]
GO
/****** Object:  Default [DF_escala_plan_academico_asignatura_epa_estado]    Script Date: 05/03/2017 16:02:30 ******/
ALTER TABLE [dbo].[escala_plan_academico_asignatura] ADD  CONSTRAINT [DF_escala_plan_academico_asignatura_epa_estado]  DEFAULT ((1)) FOR [epa_estado]
GO
/****** Object:  Default [DF_tipo_escala_tes_estado]    Script Date: 05/03/2017 16:02:30 ******/
ALTER TABLE [dbo].[tipo_escala] ADD  CONSTRAINT [DF_tipo_escala_tes_estado]  DEFAULT ((1)) FOR [tes_estado]
GO
/****** Object:  ForeignKey [FK_escala_tipo_escala]    Script Date: 05/03/2017 16:02:30 ******/
ALTER TABLE [dbo].[escala]  WITH CHECK ADD  CONSTRAINT [FK_escala_tipo_escala] FOREIGN KEY([tes_codigo])
REFERENCES [dbo].[tipo_escala] ([tes_codigo])
GO
ALTER TABLE [dbo].[escala] CHECK CONSTRAINT [FK_escala_tipo_escala]
GO
/****** Object:  ForeignKey [FK_escala_plan_academico_asignatura_escala]    Script Date: 05/03/2017 16:02:30 ******/
ALTER TABLE [dbo].[escala_plan_academico_asignatura]  WITH CHECK ADD  CONSTRAINT [FK_escala_plan_academico_asignatura_escala] FOREIGN KEY([esc_codigo])
REFERENCES [dbo].[escala] ([esc_codigo])
GO
ALTER TABLE [dbo].[escala_plan_academico_asignatura] CHECK CONSTRAINT [FK_escala_plan_academico_asignatura_escala]
GO
/****** Object:  ForeignKey [FK_escala_plan_academico_asignatura_plan_academico_asignatura]    Script Date: 05/03/2017 16:02:30 ******/
ALTER TABLE [dbo].[escala_plan_academico_asignatura]  WITH CHECK ADD  CONSTRAINT [FK_escala_plan_academico_asignatura_plan_academico_asignatura] FOREIGN KEY([paa_codigo])
REFERENCES [dbo].[plan_academico_asignatura] ([paa_codigo])
GO
ALTER TABLE [dbo].[escala_plan_academico_asignatura] CHECK CONSTRAINT [FK_escala_plan_academico_asignatura_plan_academico_asignatura]
GO