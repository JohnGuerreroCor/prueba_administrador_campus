Se agrega en la tabla plan academico el campo total horas;
Se agrega en la tabla sedes el registro VIRTUAL
Se agrega aen la tabla oferta_academica el campo ofa_crear_usuario
se agrega en la tabla convenio el campo con_estado por defecto 1
se agrega en la tabla nivel_academico el campo nia_estado por defecto 1
se agrega en la tabla uaa el campo uaa_propietario_codigo
Se agrega en la tabla oferta_academica elcampo ofa_requiere_cupo varchar(1) 1 si - 0 no y  ofa_cupo_max int
se agrega campo for_codigo default 1 a as tablas (uaa, resolucion, programa, asignatura, plan_academico, plan_academico_asignatura, oferta_academica)

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO TIPO UAA QUE PERTENECE LA EDUCACIÓN NO FORMAL','TIPO_UAA_EDUCACION_NO_FORMAL','1,2') 

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO MODALIDA LA EDUCACIÓN NO FORMAL','MODALIDAD_EDUCACION_NO_FORMAL','5');

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO NIVEL ACADÉMICO LA EDUCACIÓN NO FORMAL','NIVEL_ACADEMICO_EDUCACION_NO_FORMAL','2,3,18');

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO NUCLEO LA EDUCACIÓN NO FORMAL','NUCLEO_EDUCACION_NO_FORMAL','3');

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO TABLA FORMALIDAD REGISTRO NO FORMAL','FORMALIDAD_NO_FORMAL','2');

insert into web_parametro (wep_descripcion, wep_nombre, wep_valor) values ('PARAMETRO FACULTAD TABLA UAA_TIPO REGISTRO NO FORMAL','FACULTAD_UAA_TIPO_NO_FORMAL','1');
 G O  insert into grupo (gru_estado, gru_id, gru_nombre) VALUES (1,'ADMINISTRADOR_LCMS','Administrador LCMS no formal');
insert into grupo (gru_estado, gru_id, gru_nombre) VALUES (1,'ADMINISTRADOR_LCMS','Administrador LCMS no formal');
insert into grupo (gru_estado, gru_id, gru_nombre) VALUES (1,'ADMINISTRADOR_FACULTAD_LCMS','Administrador Facultad LCMS no formal');



 / * * * * * *   O b j e c t :     T a b l e   [ i n s c r i p c i o n e s ] . [ p r o g r a m a _ r e q u i s i t o _ t i p o ]         S c r i p t   D a t e :   0 8 / 0 1 / 2 0 1 6   1 6 : 0 6 : 2 2   * * * * * * /  se
 S E T   A N S I _ N U L L S   O N  
 selectG O  
 S E T   Q U O T E D _ I D E N T I F I E R   O N  
 G O  
 S E T   A N S I _ P A D D I N G   O N  
 G O  
 C R E A T E   T A B L E   [ i n s c r i p c i o n e s ] . [ p r o g r a m a _ r e q u i s i t o _ t i p o ] (  
 	 [ p t r _ c o d i g o ]   [ i n t ]   I D E N T I T Y ( 1 , 1 )   N O T   N U L L ,  
 	 [ p t r _ d e s c r i p c i o n ]   [ v a r c h a r ] ( 2 5 5 )   N U L L ,  
   C O N S T R A I N T   [ P K _ p r o g r a m a _ r e q u i s i t o _ t i p o ]   P R I M A R Y   K E Y   C L U S T E R E D    
 (  
 	 [ p t r _ c o d i g o ]   A S C  
 ) W I T H   ( P A D _ I N D E X     =   O F F ,   S T A T I S T I C S _ N O R E C O M P U T E     =   O F F ,   I G N O R E _ D U P _ K E Y   =   O F F ,   A L L O W _ R O W _ L O C K S     =   O N ,   A L L O W _ P A G E _ L O C K S     =   O N )   O N   [ P R I M A R Y ]  
 )   O N   [ P R I M A R Y ]  
 G O  
 S E T   A N S I _ P A D D I N G   O F F  
 G O  
 / * * * * * *   O b j e c t :     T a b l e   [ i n s c r i p c i o n e s ] . [ p r o g r a m a _ r e q u i s i t o ]         S c r i p t   D a t e :   0 8 / 0 1 / 2 0 1 6   1 6 : 0 6 : 2 2   * * * * * * /  
 S E T   A N S I _ N U L L S   O N  
 G O  
 S E T   Q U O T E D _ I D E N T I F I E R   O N  
 G O  
 S E T   A N S I _ P A D D I N G   O N  
 G O  
 C R E A T E   T A B L E   [ i n s c r i p c i o n e s ] . [ p r o g r a m a _ r e q u i s i t o ] (  
 	 [ p r e _ c o d i g o ]   [ i n t ]   I D E N T I T Y ( 1 , 1 )   N O T   N U L L ,  
 	 [ p r o _ c o d i g o ]   [ i n t ]   N U L L ,  
 	 [ p t r _ c o d i g o ]   [ i n t ]   N U L L ,  
 	 [ p r e _ d e s c r i p c i o n ]   [ v a r c h a r ] ( 2 5 5 )   N U L L ,  
   C O N S T R A I N T   [ P K _ p r o g r a m a _ r e q u i s i t o ]   P R I M A R Y   K E Y   C L U S T E R E D    
 (  
 	 [ p r e _ c o d i g o ]   A S C  
 ) W I T H   ( P A D _ I N D E X     =   O F F ,   S T A T I S T I C S _ N O R E C O M P U T E     =   O F F ,   I G N O R E _ D U P _ K E Y   =   O F F ,   A L L O W _ R O W _ L O C K S     =   O N ,   A L L O W _ P A G E _ L O C K S     =   O N )   O N   [ P R I M A R Y ]  
 )   O N   [ P R I M A R Y ]  
 G O  
 S E T   A N S I _ P A D D I N G   O F F  
 G O  
 