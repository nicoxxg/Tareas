const { createApp } = Vue

createApp({
    data() {
    return {
        register:false,
        loginFormulario:{
            email:"",
            contraseña:""
        },
        registerFormulario:{
            nombre:"",
            apellido:"",
            email:"",
            contraseña:""
        },
        model:"Alumno",

    }
    },
    created(){
        console.log('created')
    },
    methods:{
        registrarse(){
            if (this.model == "Profesor") {
                return axios.post("/api/profesor/create",`nombre=${this.registerFormulario.nombre}&apellido=${this.registerFormulario.apellido}&email=${this.registerFormulario.email}&contraseña=${this.registerFormulario.contraseña}`)
                .then((response) => {
                    this.loginFormulario.email = this.registerFormulario.email
                    this.loginFormulario.contraseña = this.registerFormulario.contraseña
                })
                .then((response) => {
                    this.login()
                })
                .catch((err) =>{
                    console.log(err.response.data)
                })
            }
            if (this.model = "Alumno") {
                return console.log("es un alumno")
            }
            if (this.model == "") {
                return console.log("no se eligio nada")
            }
            
        },
        cambiarRegister(){
            this.register = true;
        },
        cambiarLogin(){
            this.register = false;
        },
        login(){
            axios.post('/api/login', `email=${this.loginFormulario.email}&contraseña=${this.loginFormulario.contraseña}` ,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                axios.get('/api/current')
                .then((response) =>{
                    let perfil = response.data.perfiles
                    if (perfil == "Profesor") {
                        
                        return window.location.href = "/profesor/profesor.html"
                    }
                    if (perfil == "Alumno") {
                        return window.location.href = "/Alumno/alumno.html"
                    }
                    if (perfil == "Administrador") {
                        return window.location.href = "/administrador/administrador.html"
                    }

                }).catch((error) =>{
                    console.log(error)
                })
            })
            .catch(err => {
                console.log(err)
            })
        },
        
        
        
        
    },
}
).mount('#app')

