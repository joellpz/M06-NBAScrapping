import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;


/**
 * Clase que contiene una lista de Partidos para facilitar el proceso a XML.
 */
@XmlRootElement(name = "NBAGames")
@XmlAccessorType(XmlAccessType.FIELD)
public class NBAGames {
    /**
     * Lista de Partidos
     */
    @XmlElement(name = "Game")
    private List<Game> nbaGames = null;

    /**
     * Devuelve la lista de Partidos.
     * @return Lista de Partidos
     */
    public List<Game> getNbaGames() {return nbaGames;}

    /**
     * Introducir Lista de Partidos para JAXB
     * @param gameList Lista de Partidos
     */
    public void setTeams(List<Game> gameList) {
        this.nbaGames = gameList;
    }
}