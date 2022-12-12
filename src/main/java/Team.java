import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Clase que permite definir las propiedades de un Equipo.
 */
@XmlRootElement(name = "Team")
@XmlAccessorType(XmlAccessType.FIELD)
public class Team {
    private String name;
    private String location;
    private int games;
    private int wins;
    private int loses;
    private int playoffAppearances;
    private int conferenceChampions;
    private int championships;
    private Conference conference;

    /**
     * Define atributos clase Team
     * @param name Nombre
     * @param location Ubicación
     * @param games Partidos
     * @param wins Ganados
     * @param loses Perdidos
     * @param playoffAppearances Apariciones PlayOff
     * @param conferenceChampions Campeón de Conferencia
     * @param championships Campeón NBA
     * @param conference Conferencia
     */
    public Team(String name, String location, int games, int wins, int loses,
                int playoffAppearances, int conferenceChampions, int championships, Conference conference) {
        this.name = name;
        this.location = location;
        this.games = games;
        this.wins = wins;
        this.loses = loses;
        this.playoffAppearances = playoffAppearances;
        this.conferenceChampions = conferenceChampions;
        this.championships = championships;
        this.conference = conference;
    }

    /**
     * Constructor para JAXB
     */
    public Team() {
    }

    /**
     * Devuelve como cadena de caracteres los atributos de la clase.
     * @return Cadena con los atributos.
     */
    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", games=" + games +
                ", wins=" + wins +
                ", loses=" + loses +
                ", playoffAppearances=" + playoffAppearances +
                ", conferenceChampions=" + conferenceChampions +
                ", championships=" + championships +
                ", conference=" + conference +
                '}';
    }
}
