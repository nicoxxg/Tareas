const { createApp } = Vue

createApp({
    data() {
    return {
        label:false,
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
        obtenerCliente(){
            axios.get('/api/current')
            .then((response) =>{
                this.cliente = response.data
            }).catch((error) =>{
            })
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

