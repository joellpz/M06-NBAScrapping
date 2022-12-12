import jakarta.xml.bind.annotation.*;

/**
 * Clase que contiene las propiedades sobre una Temporada
 */
@XmlRootElement(name = "Season")

public class Season {
    @XmlAttribute
    private String year;
    @XmlElement
    private String league;
    @XmlElement
    private String champion;
    @XmlElement
    private String MVP;
    @XmlElement
    private String ROTY;
    @XmlElement
    private String PPG_Leader;
    @XmlElement
    private String RGP_Leader;
    @XmlElement
    private String APG_Leader;
    @XmlElement
    private String WS_Leader;
    @XmlElement
    private String link;

    /**
     * Permite definir las caracteristicas de la temporada en su objeto
     * @param year  Año
     * @param league Liga
     * @param champion Campeón
     * @param MVP Mejor Jugador
     * @param ROTY Mejor Rookie
     * @param PPG_Leader Líder Puntos
     * @param RGP_Leader Líder Rebotes
     * @param APG_Leader Líder Asistencias
     * @param WS_Leader Líder Victorias
     * @param link Link
     */
    public Season(String year, String league, String champion, String MVP, String ROTY, String PPG_Leader, String RGP_Leader, String APG_Leader, String WS_Leader, String link) {
        this.year = year;
        this.league = league;
        this.champion = champion;
        this.MVP = MVP;
        this.ROTY = ROTY;
        this.PPG_Leader = PPG_Leader;
        this.RGP_Leader = RGP_Leader;
        this.APG_Leader = APG_Leader;
        this.WS_Leader = WS_Leader;
        this.link = link;
    }

    /**
     * Constructor para JAXB
     */
    public Season() {
    }

    /**
     * Devuelve como cadena de caracteres los atributos de la clase.
     * @return Cadena con los atributos.
     */
    @Override
    public String toString() {
        return "Season{" +
                "year='" + year + '\'' +
                ", league='" + league + '\'' +
                ", champion='" + champion + '\'' +
                ", MVP='" + MVP + '\'' +
                ", ROTY='" + ROTY + '\'' +
                ", PPG_Leader='" + PPG_Leader + '\'' +
                ", RGP_Leader='" + RGP_Leader + '\'' +
                ", APG_Leader='" + APG_Leader + '\'' +
                ", WS_Leader='" + WS_Leader + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    /**
     * Devuelve el año de la Temporada
     * @return Año
     */
    public String getYear() {
        return year;
    }


    /**
     * Devuelve la Liga de la Temporada.
     * @return String Liga
     */
    public String getLeague() {
        return league;
    }

    /**
     * Devuelve la URL de la Temporada
     * @return Link
     */
    public String getLink() {
        return link;
    }

}
