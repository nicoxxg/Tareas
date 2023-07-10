const { createApp } = Vue

createApp({
    data() {
    return {
        crearTarea:{
            nombreTarea: "",
            descripcionTarea: "", 
            idCurso:null
        },
        listaAlumnosTareaEntregada: null,
        listaTareaEntregada:null,
        tareaCorregida:"",
        corregir: false,
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
        listaAlumnoFiltrado:[],
        cursoInformacion:[],
        tareaEntregada:[],
        detalleListaAlumnoTarea:[]

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
        corregirTarea(){
            this.corregir = true;
        },
        cancelarCorregirTarea(){
            this.corregir = false;
            this.tareaCorregida = ""
        },
        guardarCorregirTarea(idEntrega){
            axios.patch(`/api/profesor/entrega/${idEntrega}/modificar`,`nota=${this.tareaCorregida}`)
            .then(() =>{
                window.location.reload()
            })

        },
        descargarArchivo(entregaId){
            axios({
                url: `/api/profesor/entrega/${entregaId}`, // Reemplaza 123 con el ID del archivo deseado
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
            if (id) {
                axios.get(`/api/curso/${id}`).then((response) =>{
                    this.cursoInformacion = response.data
                })
            }
            
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
        crearNuevaTarea(){
            axios.post("/api/tarea", `nombreTarea=${this.crearTarea.nombreTarea}&descripcionTarea=${this.crearTarea.descripcionTarea}&idCurso=${this.crearTarea.idCurso}`).then((response) =>{
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
                this.listaTareaEntregada = this.cliente.verificacionesPorTarea
                console.log(this.listaTareaEntregada)
                console.log(this.cliente)
                this.obtenerInformacionTarea()

            }).then((response) => {

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
            this.listaAlumnosTareaEntregada = null
            
        },
        listaAlumnoTareaEntregada(id) {
            
            let tarea = this.listaTareaEntregada.find((tarea) => tarea.id === id);
            if (tarea) {
                
                this.listaAlumnosTareaEntregada = tarea.alumno;
                console.log(this.detalleListaAlumnoTarea)

            } else {
              this.listaAlumnosTareaEntregada = null;
            }
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
        },
        obtenerInformacionTarea() {
            let id = window.location.href.split('?=')[1];
            axios.get(`/api/tarea/${id}`).then((response) =>{
                this.tareaEntregada = response.data
                console.log(this.tareaEntregada)

                axios.get('/api/current')
                .then((response) =>{
                    this.listaTareaEntregada = response.data.verificacionesPorTarea;
                    console.log(this.listaTareaEntregada);

                    this.detalleListaAlumnoTarea = this.listaTareaEntregada.filter((tarea) => tarea.id === Number(id));
                    console.log(this.detalleListaAlumnoTarea);
                })
                })
        }
        
        
    },
    computed:{
        filterSerchAlumnos() {
            if (!this.textoFiltro) {
               this.listaAlumno;
            } else {
              if (this.listaAlumnosTareaEntregada) {
                 this.listaAlumnosTareaEntregada.filter((alumno) =>
                  alumno.nombre.toLowerCase().includes(this.textoFiltro.toLowerCase().trim())
                );
              } else {
                 this.listaAlumno.filter((alumno) =>
                  alumno.nombreAlumno.toLowerCase().includes(this.textoFiltro.toLowerCase().trim())
                );
              }
            }
          },
    }
}
).mount('#app')

