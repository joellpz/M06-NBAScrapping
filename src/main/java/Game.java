import jakarta.xml.bind.annotation.*;

/**
 * Clase que contiene la información de un Partido
 */
@XmlRootElement(name = "Game")
public class Game {
    @XmlAttribute
    private int id;
    @XmlElement
    private String date;
    @XmlElement
    private String teamVisitor;
    @XmlElement
    private int pointsVisitor;
    @XmlElement
    private String teamLocal;
    @XmlElement
    private int pointsLocal;
    @XmlElement
    private String arena;
    @XmlElement
    private String link;


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
     * Constructor para JAXB
     */
    public Game() {
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
