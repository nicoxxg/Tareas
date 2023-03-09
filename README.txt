las entidades son:

Profesor{
 id:clave primaria
 nombre:string
 apellido:string
 email:string
 contraseña:string
 perfiles:enum
}
que tiene una relacion de 1 a muchos con Curso lo que significa que un Profesor puede tener muchos cursos
las Propiedades que tiene curso son:
Curso{
 id:clave primaria
 numeroGrado:string
 Descripcion:string
 turnos:enum
}
y Curso tiene una relacion de una a muchos con Inscripcion para que a un Curso se puedan inscribir muchas Personas
Inscripcion{
 id:clave primaria
 nombreAlumno:string
 activo:boolean
}
Aca usamos la propiedad activa para saber si la persina que se inscribio(en este caso el Alumno) para saber si fue expulsado o el mismo se salio.
si llegaramos a borrarlo directamente entonces perderiamos el historial de las personas que estuvieron en el curso y no queremos eso, para eso usamos este booleano,
como no queremos perder el historial de las personas que estuvieron en la clase lo que hacemos en cuando pedimos las inscripciones decimos que 
solamente nos traigan las inscripciones activadas y las desactivadas no apareceran pero siguen ahi.
Ahora.Inscriopcion tiene una relacion de muchos a uno con la entidad Alumno, lo cual hace que un alumno pueda tener muchas inscripciones.
podiendo estar en multiples clases sin ningun prolema alguno
las propiedades que tiene Alumno son
Alumno{
 id:clave primaria
 nombre:string
 apellido:string
 email:string
 contraseña:string
 perfiles:enum
suspendido:boolean
}
como el caso anterior como nosotros queremos borrar al alumno o suspenderlo por un tiempo pero no queremos perder la informacion de este
 lo que hacemos es usar un booleano para poder traer a todos los alumnos activados, 
tambien sirve para bloquearle el acceso para iniciar sesion a los alumnos bloqueados