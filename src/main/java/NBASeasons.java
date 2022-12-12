import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * Clase que contiene una lista de Temporadas para facilitar el proceso a XML.
 */
@XmlRootElement(name = "NBASeasons")
@XmlAccessorType(XmlAccessType.FIELD)
public class NBASeasons {
    /**
     * Lista de Temporadas
     */
    @XmlElement(name = "Season")
    private List<Season> nbaSeasons = null;


    /**
     * Devuelve la lista de Temporadas.
     * @return Lista de Temporadas
     */
    public List<Season> getNbaSeasons() {return nbaSeasons;}

    /**
     * Introducir Lista de Temporadas para JAXB
     * @param seasonList Lista de Temporadas
     */
    public void setTeams(List<Season> seasonList) {
        this.nbaSeasons = seasonList;
    }
}