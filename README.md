# M06-NBAScrapping
Practica WebScrapping M06-UF1 - Basketball Reference

Esta es la practica realizada en concepto del trabajo final de la UF1 del Módulo de M06, Accéso a datos.

Consiste en un programa que recoge datos de la web de www.basketball-reference.com y los introduce en  ficheros con
formato XML y CSV.
Recoge datos tales como:
  - Jugadores
  - Temporadas
  - Estadisticas de Jugadores por Temporada
  - Equipos
  - Estaditicas de Equipo por Temporada
  - Partidos de PlayOff
  - Estadisticas de los partidos de PlayOff por Jugador

<h4>Funcionamiento:</h4>

Para empezar a utilizar la herramienta de Scrapping unicamente será necesario, en una clase Main, generar dos Objetos.
El primero de ellos será el WebDriver, para este deberemos tener el modulo de GeckoDriver que es el que permite realizar la exportació de datos de Firefox; el segundo sera un WebDriverWait, que permitira definir condicionales de cara a la busqueda con el WebDriver.
Una vez tengamos estos generados unicamente deberemos iniciar la web en cuestión y generar a continuación un objeto de BasketballScrapper, al que le pasaremos los Drivers por parametro. Luego iniciamos la herramienta con ".start()".

<h4>Exportación de datos:</h4>
  - CVS:  Para este caso se utiliza el modulo de OpenCSV (https://www.baeldung.com/opencsv) de Maven. Este usando ArrayList de String[] permite generar de manera rapida documentos CSV. Cada posición del ArrayList es una fila y cada posición de String[] es una columna.
  
  - XML: A la hora de exportar los datos a XML usamos JAXB. Cada objeto tiene definidos los atributos y elementos del XML para posteriormente con las funciones pertienentes generar el XML de todos los datos.

<h4>Author:</h4>
<h6> - Joel López</h6>
