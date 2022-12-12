import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Clase que contiene las propiedades de un Jugador.
 */
@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {

    private String name;
    private String position;
    private String college;
    private String draftTeam;
    private String draftPos;
    private String born;
    private int age;
    private int draftYear;
    private int expCareer;

    /**
     * Permite definir los atributos de la clase
     *
     * @param name      Nombre
     * @param position  Posicion
     * @param college   Universidad
     * @param draftTeam Equipo del Draft
     * @param draftPos  Posicion del Draft
     * @param born      Fecha de Nacimiento
     * @param age       Edad
     * @param draftYear Año del Draft
     * @param expCareer Carrera
     */
    public Player(String name, String position, String college, String draftTeam, String draftPos, String born, int age, int draftYear, int expCareer) {
        this.name = name;
        this.position = position;
        this.college = college;
        this.draftTeam = draftTeam;
        this.draftPos = draftPos;
        this.born = born;
        this.age = age;
        this.draftYear = draftYear;
        this.expCareer = expCareer;
    }

    /**
     * Constructor para JAXB
     */
    public Player() {
    }

    /**
     * Devuelve como cadena de caracteres los atributos de la clase.
     * @return Cadena con los atributos.
     */
    @Override
    public String toString() {

        return "Player{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", college='" + college + '\'' +
                ", draftTeam='" + draftTeam + '\'' +
                ", draftPos='" + draftPos + '\'' +
                ", born='" + born + '\'' +
                ", age=" + age +
                ", draftYear=" + draftYear +
                ", expCareer=" + expCareer +
                '}';
    }

    /**
     * Devuelve los atributos en un formato util para imprimir con OpenCSV
     *
     * @return Array de String
     */
    public String[] toOpenCSV() {
        return new String[]{name, position, college, draftTeam, draftPos, born, age + "", draftYear + "", expCareer + ""};
    }

    /**
     * Devuelve el Nombre del Jugador
     *
     * @return Nombre
     */
    public String getName() {
        return name;
    }
}
