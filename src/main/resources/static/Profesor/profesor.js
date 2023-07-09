const { createApp } = Vue

createApp({
    data() {
    return {
        label:false,
        listCurso:null,
        cliente:{},
        listaAlumno:null,
        idCursoSelecionado:null,
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
        editarInformacionCurso:{
            nombre:"",
            horario:null,
            descripcion:""
        },
        editarPerfil:false,
        editarCurso:false,
        todosAlumnos:null,
        informacionAlumno:null,
        textoFiltro:"",
        listaAlumnoFiltrado:null,
        cursoInformacion:[],

    }
    },
    
    created(){
        this.obtenerCliente();
        this.obtenerCursosCliente()
        this.listaTodosAlumnos()
        this.obtenerDetalleAlumnno()
        this.obtenerInformacionCurso()
    },
    methods:{
        cambiarInformacionCurso(){
            
            let id = window.location.href.split('?=')[1]
            if (this.editarInformacionCurso.nombre == "") {
                this.editarInformacionCurso.nombre = this.cursoInformacion.numeroGrado
            }
            if (this.editarInformacionCurso.descripcion == "") {
                this.editarInformacionCurso.descripcion = this.cursoInformacion.descripcion
            }
            if (this.editarInformacionCurso.horario == null) {
                this.editarInformacionCurso.horario = this.cursoInformacion.turno
            }
            axios.patch(`/api/curso/${id}/actualizar`,`numeroGrado=${this.editarInformacionCurso.nombre}&descripcion=${this.editarInformacionCurso.descripcion}&nuevoTurnmo=${this.editarInformacionCurso.horario}`)
            .then((response) =>{
                this.editarCurso = false
                this.obtenerInformacionCurso()
            })
        },
        obtenerInformacionCurso(){
            let id = window.location.href.split('?=')[1]
            axios.get(`/api/curso/${id}`).then((response) =>{
                this.cursoInformacion = response.data
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

            axios.patch("/api/profesor/current/actualizar",`nuevoNombre=${this.editarInformacionPerfil.nombre}&nuevoApellido=${this.editarInformacionPerfil.apellido}&nuevaContraseoña=${this.editarInformacionPerfil.contraseña}`)
            .then(() =>{
                this.editarPerfil = false
            })
            .then(() =>{
                this.obtenerCliente()
            })
        },
        suspenderAlumno(id){
            axios.patch(`/api/alumno/${id}/suspender`).then((response) => {
                window.location.href = `/Profesor/profesor.html`
            })

        },
        redireccionarDetalleAlumno(id){
            window.location.href = `/Profesor/detalleAlumno.html?=${id}` 
        },
        obtenerDetalleAlumnno(){
            let id = window.location.href.split('?=')[1]
            axios.get(`/api/alumno/${id}`)
            .then((response) =>{
                this.informacionAlumno = response.data
            })
            .catch(() =>{
                console.log("no existe el alumno");
            })
        },
        listaTodosAlumnos(){
            axios.get('/api/alumno').then((response) => {
                this.todosAlumnos = response.data
            })
        },

        crearNuevaClase(){
        console.log(this.formularioNuevoCurso.turno)
        console.log(this.formularioNuevoCurso.nombre)
        console.log(this.formularioNuevoCurso.descripcion)
        axios.post("/api/curso/create",`numeroGrado=${this.formularioNuevoCurso.nombre}&descripcion=${this.formularioNuevoCurso.descripcion}&turno=${this.formularioNuevoCurso.turno}`).then((response) =>{
            window.location.href = "/Profesor/profesor.html"
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
                console.log(this.cliente)
            }).catch((error) =>{
            })
        },
        obtenerCursosCliente(){
            axios.get('/api/current/curso')
            .then((response) =>{
                this.listCurso = response.data
            });
        },
        listaAlumnosCurso(id){
            this.idCursoSelecionado = id;
            let curso = this.listCurso.filter((curso) => curso.id === id)
            this.listaAlumno = curso[0].inscripciones
            console.log(this.listaAlumno)
            
        },
        eliminarAlumnoDelCurso(idAlumno){
            axios.patch(`/api/alumno/${idAlumno}/inscripcion/${this.idCursoSelecionado}/actualizar`)
            .then((response) =>{
                this.obtenerCursosCliente()
                this.listaAlumnosCurso(this.idCursoSelecionado)
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

