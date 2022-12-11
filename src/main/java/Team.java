/**
 * Clase que permite definir las propiedades de un Equipo.
 */
public class Team {
    private final String name;
    private final String location;
    private final int games;
    private final int wins;
    private final int loses;
    private final int playoffAppearances;
    private final int conferenceChampions;
    private final int championships;
    private final Conference conference;

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
