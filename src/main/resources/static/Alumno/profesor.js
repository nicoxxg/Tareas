const { createApp } = Vue

createApp({
    data() {
    return {
        label:false,
        modificarArchivo:false,
        cambiarApodo:false,
        cliente:{},
        ListCursosInscrito:null,
        nuevoApodo:null,
        formularioNuevoCurso:{
            nombre:"",
            horario:null,
            descripcion:""
        },
        editarInformacionPerfil:{
            nombre:"",
            apellido:"",
            contraseña:"",
        },
        apodoInscripcion:null,
        editarPerfil:false,
        cursoInformacion:[],
        detalleCurso:[],
        listaCursos:[],
        tareaEntregada:[],
        tareasPendientes:[],
        porCrearEntrega:false,
        tareaCursoInscrito:[],

    }
    },
    
    created(){
        this.obtenerCliente();
        this.obtenerCursoInscritoPorId()
        this.obtenerTodosCursosInscrito()
        this.obtenerTodasLasClases()
        this.redireccionarDetalleCurso()

    },
    methods:{
        activarPorCrearEntrega(){
            this.porCrearEntrega = true
        },
        desactivarPorCrearEntrega(){
            this.porCrearEntrega = false
        },
        crearEntrega(idTarea){
            const archivoInput = document.getElementById('archivoInputTarea');
            const archivo = archivoInput.files[0];
            console.log(archivoInput.files)
            const formData = new FormData();
            formData.append('files', archivo);

            const config = {
                headers: {
                'Content-Type': 'multipart/form-data'
                }
            };
            console.log(formData)
            axios.post(`/api/alumno/tarea/${idTarea}/entrega`, formData,config)
            .then(response => {
                window.location.reload()
                console.log('Archivo subido correctamente');
            })
            .catch(error => {
                console.error('Error al subir el archivo:', error);
            });
        },
        modificarArchivoTarea(id){
            const archivoInput = document.getElementById('archivoInput');
            const archivo = archivoInput.files[0];
            console.log(archivoInput.files)
            const formData = new FormData();
            formData.append('newFile', archivo);

            const config = {
                headers: {
                'Content-Type': 'multipart/form-data'
                }
            };
            console.log(formData)
            axios.patch(`/api/alumno/entrega/${id}/modificar`, formData,config)
            .then(response => {
                window.location.reload()
                console.log('Archivo subido correctamente');
            })
            .catch(error => {
                console.error('Error al subir el archivo:', error);
            });
        },
        cancelarModificar(){
            this.modificarArchivo = false
        },
        activarModificarArchivo(){
            this.modificarArchivo = true
        },
        descargarArchivo(entregaId){
            axios({
                url: `/api/alumno/entrega/${entregaId}`, // Reemplaza 123 con el ID del archivo deseado
                method: 'GET',
                responseType: 'arraybuffer' // Indica que se espera una respuesta en formato arraybuffer
              }).then(response => {
                const nombreArchivo = this.obtenerNombreArchivo(response); // Obtén el nombre del archivo según tu lógica
                
                const blob = new Blob([response.data], { type: response.headers['content-type'] });
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', nombreArchivo);
                document.body.appendChild(link);
                link.click();
              });
        },
        
        obtenerNombreArchivo(response) {
            const contentDisposition = response.headers['content-disposition'];
            const regex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
            const matches = regex.exec(contentDisposition);
            
            if (matches != null && matches[1]) {
              return matches[1].replace(/['"]/g, '');
            }
            
            return 'archivo_descargado'; // Establece un nombre predeterminado si no se puede obtener el nombre del archivo
          },
        obtenerCliente(){
            axios.get('/api/current')
            .then((response) =>{
                this.cliente = response.data
                this.tareaEntregada = response.data.tareaEntregada
                this.tareasPendientes = response.data.tareasPendientes
                console.log(this.cliente)
            }).catch((error) =>{
            })
        },
        activarCambiarApodo(){
            this.cambiarApodo = true
        },
        desactivarCambiarApodo(){
            this.cambiarApodo = false
        },
        editarApodoCurso(id){
            if (this.nuevoApodo == null || this.nuevoApodo == "") {
                this.nuevoApodo = thius.cursoInformacion.nombreAlumno
            }
            axios.patch(`/api/current/inscripcion/${id}/actualizar`,`nuevoNombre=${this.nuevoApodo}`)
            .then((response) => {
                this.obtenerCursoInscritoPorId
                this.cambiarApodo = false
                window.location.reload();
            })
            .catch((error) => {
                console.log(error)
            })
        },
        crearInscripcionCurso(idCurso){
            let id = window.location.href.split('?=')[1]

            if (this.apodoInscripcion == null) {
                this.apodoInscripcion = this.cliente.nombre + " " + this.cliente.apellido
            }
            
            axios.post("/api/Inscripcion/create",`apodo=${this.apodoInscripcion}&idCurso=${id}`)
            .then((response) => {
                window.location.href = "/Alumno/alumno.html"
            })
        },
        redireccionarDetalleCurso(){
            
            let id = window.location.href.split('?=')[1]
            axios.get(`/api/curso/${id}`)
            .then((response) => {
                
                this.detalleCurso = response.data
                
            })
        },
        obtenerTodasLasClases(){
            axios.get("/api/curso")
            .then((response) => {
                this.listaCursos = response.data
                
            })
        },
        obtenerTodosCursosInscrito(){
            axios.get("/api/current/inscripcion").then((response) =>{
                this.ListCursosInscrito = response.data
            })
        },
        obtenerCursoInscritoPorId(){
            let id = window.location.href.split('?=')[1]
            axios.get(`/api/current/inscripcion/${id}`).then((response) =>{
                this.cursoInformacion = response.data
                this.tareaCursoInscrito = response.data.curso.tarea.sort((a,b) => a.id - b.id)
                console.log(this.tareaCursoInscrito)
            })
        },
        eliminarAlumnoDelCurso(id){
            axios.patch(`/api/current/inscripcion/${id}/actualizar/apodo`)
            .then((response) =>{
                this.obtenerTodosCursosInscrito()
                this.obtenerCliente()
                
                    window.location.href = "/Alumno/alumno.html"
                
            })
        },
        
        cambiarInformacionPerfil(){
            if (this.editarInformacionPerfil.nombre == "") {
                this.editarInformacionPerfil.nombre = this.cliente.nombre
            }
            if (this.editarInformacionPerfil.apellido == "") {
                this.editarInformacionPerfil.apellido = this.cliente.apellido
            }
            if (this.editarInformacionPerfil.contraseña == "") {
                this.editarInformacionPerfil.contraseña == this.cliente.contraseña
            }

            axios.patch("/api/alumno/current/actualizar",`nuevoNombre=${this.editarInformacionPerfil.nombre}&nuevoApellido=${this.editarInformacionPerfil.apellido}&nuevaContraseoña=${this.editarInformacionPerfil.contraseña}`)
            .then(() =>{
                this.editarPerfil = false
            })
            .then(() =>{
                this.obtenerCliente()
            })
        },
        cambiarLabel(){
            if (this.label) {
                return this.label = false;
            }
            if (!this.label) {
                return this.label = true;
            }
        },
        logout(){
            axios.post("/api/logout")
            .then((response) =>{
                window.location.href = "/index.html"
            })
        }
        
        
    },
    computed:{
        filterSerchAlumnos(){
            if (this.textoFiltro == "") {
                this.listaAlumnoFiltrado = this.listaAlumno
            }else{
                this.listaAlumnoFiltrado = this.listaAlumno.filter((el) => el.nombreAlumno.toLowerCase().includes(this.textoFiltro.toLowerCase().trim()))
            }
            
        }
    }
}
).mount('#app')

