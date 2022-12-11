/**
 * Clase que contiene la información de un Partido
 */
public class Game {
    private final int id;
    private final String date;
    private final String teamVisitor;
    private final int pointsVisitor;
    private final String teamLocal;
    private final int pointsLocal;
    private final String arena;
    private final String link;

    /**
     * Contructor de la Clase Game. Contiene la definición de las propiedades de la clase.
     * @param id ID del Partido
     * @param date Fecha
     * @param teamVisitor Equipo Visitante
     * @param pointsVisitor Puntos Equipo Visitante
     * @param teamLocal Equipo Local
     * @param pointsLocal Puntos Equipo Local
     * @param arena Ubicación
     * @param link  URL
     */
    public Game(int id, String date, String teamVisitor, int pointsVisitor, String teamLocal, int pointsLocal, String arena, String link) {
        this.id = id;
        this.date = date;
        this.teamVisitor = teamVisitor;
        this.pointsVisitor = pointsVisitor;
        this.teamLocal = teamLocal;
        this.pointsLocal = pointsLocal;
        this.arena = arena;
        this.link = link;
    }

    /**
     * Devuelve como cadena de caracteres los atributos de la clase.
     * @return Cadena con los atributos.
     */
    @Override
    public String toString() {
        return "Game{" +
                "year='" + id + '\'' +
                ", date='" + date + '\'' +
                ", teamVisitor='" + teamVisitor + '\'' +
                ", pointsVisitor=" + pointsVisitor +
                ", teamLocal='" + teamLocal + '\'' +
                ", pointsLocal=" + pointsLocal +
                ", arena='" + arena + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

}
