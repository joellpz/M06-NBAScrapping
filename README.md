# M06-NBAScrapping
Práctica WebScrapping M06-UF1 - Basketball Reference

Esta es la práctica realizada en concepto del trabajo final de la UF1 del Módulo de M06, Acceso a datos.

Consiste en un programa que recoge datos de la web de BasketBall Reference[^1] y los introduce en ficheros con
formato XML y CSV.
Recoge datos tales como:
- Jugadores
- Temporadas
- Estadísticas de Jugadores por Temporada
- Equipos
- Estadísticas de Equipo por Temporada
- Partidos de PlayOff
- Estadísticas de los partidos de PlayOff por Jugador

<h2> Funcionamiento: </h2>

Para empezar a utilizar la herramienta de Scrapping únicamente será necesario, en una clase Main, generar dos Objetos.

El primero de ellos será el WebDriver, para este deberemos tener el módulo de GeckoDriver que es el que permite efectuar la exportación de datos de Firefox; el segundo será un WebDriverWait, que permitirá definir condicionales de cara a la búsqueda con el WebDriver.

Una vez tengamos estos generados únicamente deberemos iniciar la web en cuestión y generar a continuación un objeto de BasketballScrapper, al que le pasaremos los Drivers por parámetro. Luego arrancamos la herramienta con ".start()".

<h2> Exportación de datos: </h2>

- CVS: Para este caso se utiliza el modulo de OpenCSV[^2] de Maven. Este usando ArrayList de String[] permite generar de manera rapida documentos CSV. Cada posición del ArrayList es una fila y cada posición de String[] es una columna.

- XML: A la hora de exportar los datos a XML empleamos JAXB. Cada objeto tiene definidos los atributos y elementos del XML para posteriormente con las funciones pertinentes generar el XML de todos los datos.

<h2> Author: </h2>

- Joel López

[^1]: www.basketball-reference.com
[^2]: https://www.baeldung.com/opencsv
