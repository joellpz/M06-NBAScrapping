/**
 * Clase que contiene las propiedades de un Jugador.
 */
public class Player {


    private final String name;
    private final String position;
    private final String college;
    private final String draftTeam;
    private final String draftPos;
    private final String born;
    private final int age;
    private final int draftYear;
    private final int expCareer;

    /**
     * Permite definir los atributos de la clase
     * @param name Nombre
     * @param position Posicion
     * @param college Universidad
     * @param draftTeam Equipo del Draft
     * @param draftPos Posicion del Draft
     * @param born Fecha Nacimiento
     * @param age Edad
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
     * Devuelve el Nombre del Jugador
     * @return Nombre
     */
    public String getName() {
        return name;
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
        /*return name + ", " +
                position + ", " +
                college + ", " +
                draftTeam + ", " +
                draftPos + ", " +
                born + ", " +
                age + ", " +
                draftYear + ", " +
                expCareer;
         */
    }

    /**
     * Devuelve los atributos en un formato util para imprimir con OpenCSV
     * @return Array de String
     */
    public String[] toOpenCSV() {
        return new String[]{name, position, college, draftTeam, draftPos, born, age + "", draftYear + "", expCareer + ""};

    }
}
