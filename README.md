# Librería Hojearte 📚
Hojearte es una aplicación Java que te permite consultar información sobre libros utilizando la API de Gutendex. Puedes obtener detalles haciendo consultas por título, listar libros registrados 
en la base de datos, listar autores, consultar autores vivos en un año determinado y filtrar libros por un idioma.

## 🔨 Funcionalidades
- `1. Buscar libro por título:` Ingresa el nombre del libro que deseas buscar, preferiblemente en ingles y si este existe en la API será registrado en la base de datos.
- `2. Listar libros registrados:` Muestra todos los libros registrados en la base de datos.
- `3. Listar libros por idioma:` Se ingresará el idioma abreviado(en, es, fr, pt) y mostrará los libros en ese idioma que se encuentren en la base de datos.
- `4. Listar autores registrados:` Muestra todos los libros registrados en la base de datos.
- `5. Listar autores vivos en un determinado año:` Se ingresa el año y listará los autores que se encuentren vivos en ese año específico.

## 📁 Acceso al proyecto
Para poder utilizar la aplicación de hojearte se debe de tener instalado el <strong>[JDK Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
</strong>, y un IDE de su preferencia <strong>([Intellij Idea](https://www.jetbrains.com/idea/download/?section=windows), [Netbeans](https://netbeans.apache.org/front/main/download/))</strong>
<br><br>
Luego de haber instalado Java 17 y el IDE continue con la descarga del proyecto [Aquí](https://github.com/user-attachments/files/16007520/hojearte.zip) o haga un git clone del repositorio:

```sh
git clone https://github.com/QuinSDev/hojearte.git
```

También deberás configurar la base de datos PostgresSQL o usa la de tu preferencia. Y configura el archivo `application.properties`

 ## 🛠️ Abra y ejecute el proyecto
Si descargaste el proyecto en .zip deberás descomprimirlo antes de poder buscar y abrir el proyecto. Si lo clonaste solo busca el proyecto y abrelo.

Abre el IDE y dirigete a la opción File, y debes de dar click en Open.
![openProject](https://github.com/QuinSDev/currency-converter/assets/132032504/76381837-b9fa-4087-a466-aed9fbbc0270)

Ahora busca la ubicación del proyecto y le das click en Ok.
![openFileProject](https://github.com/QuinSDev/currency-converter/assets/132032504/5b837a3c-14df-4ea5-a4fa-36632547f6ea)

Cuando hayas abierto el proyecto debes pararte sobre la clase Main.java y darle doble click. Luego debe ejecutar la aplicación dando click en el botón run o con el comando de teclas shift+F10.
![Main](https://github.com/QuinSDev/currency-converter/assets/132032504/b382ab9d-e056-4164-8055-1b105e90aac9)

## 💻 Herramientas utilizadas
- Java 17
- Spring Boot
- PostgreSQL
- Intellij Idea
- Gutendex API
