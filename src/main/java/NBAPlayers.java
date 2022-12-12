import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
/**
 * Clase que contiene una lista de Jugadores para facilitar el proceso a XML.
 */
@XmlRootElement(name = "NBAPlayers")
@XmlAccessorType(XmlAccessType.FIELD)
public class NBAPlayers {
    /**
     * Lista de Jugadores
     */
    @XmlElement(name = "Player")
    private List<Player> nbaPlayers = null;

    /**
     * Devuelve la lista de Jugadores.
     * @return Lista de Jugadores
     */
    public List<Player> getNbaPlayers() {return nbaPlayers;}

    /**
     * Introducir Lista de Jugadores para JAXB
     * @param playerList Lista de Jugadores
     */
    public void setTeams(List<Player> playerList) {
        this.nbaPlayers = playerList;
    }
}