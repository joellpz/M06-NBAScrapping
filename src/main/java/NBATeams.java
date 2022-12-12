import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * Clase que contiene una lista de equipos para facilitar el proceso a XML.
 */
@XmlRootElement(name = "NBATeams")
@XmlAccessorType(XmlAccessType.FIELD)
public class NBATeams {
    /**
     * Lista de Equipos
     */
    @XmlElement(name = "Team")
    private List<Team> nbaTeams = null;

    /**
     * Devuelve la lista de equipos.
     * @return Lista de Equipos
     */
    public List<Team> getNbaTeams() {return nbaTeams;}

    /**
     * Introducir Lista de Equipos para JAXB
     * @param teamList Lista de Equipos
     */
    public void setTeams(List<Team> teamList) {
        this.nbaTeams = teamList;
    }
}