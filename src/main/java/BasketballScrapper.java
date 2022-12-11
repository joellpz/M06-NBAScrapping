
import com.opencsv.CSVWriter;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * Classe que contiene los métodos para recoger lka información de nuestra WEB.
 */
public class BasketballScrapper {
    protected WebDriver basketDriver;
    protected WebDriver auxDriver;
    public WebDriverWait myWaitVar;

    /**
     * Constructor de la herramienta de Scrapping
     * @param basketDriver WebDriver
     * @param myWaitVar WebDriverWait
     */
    public BasketballScrapper(WebDriver basketDriver, WebDriverWait myWaitVar) {
        this.basketDriver = basketDriver;
        this.myWaitVar = myWaitVar;
    }

    /**
     * Empieza a Scrapper nuestra Web.
     */
    public void start() {
        LinkedHashMap<String, String> listMenu = getMenu();
        getPlayers(listMenu.get("Players"));
        //getSeasons(listMenu.get("Seasons"));
        getTeams(listMenu.get("Teams"));
        auxDriver.quit();
    }

    /**
     * Esta función recoge el menu de la parte superior de nuestra web. Para que posteriormente
     * obtengamos todos las direcciones URL de donde tenemos que recoger datos.
     *
     * @return LinkedHasMap en el que la clave es el nombre de lo que contiene y el value es la dirección URL.
     */
    public LinkedHashMap<String, String> getMenu() {
        LinkedHashMap<String, String> listMenu = new LinkedHashMap<>();
        myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("css-47sehv")));
        basketDriver.findElement(By.className("css-47sehv")).click();

        //Encuentra los elementos del Menú y los pone en un Map (Apartado , URL).
        basketDriver.findElements(By.xpath("//ul[@id='main_nav']/li/a")).forEach(a -> listMenu.put(a.getText(), a.getAttribute("href")));
        return listMenu;
    }

    /**
     * Lista todos los jugadores con cada letra y guarda su URL.
     *
     * @param playerMenuLink URL de pagina de Jugadores del Menu
     */
    public void getPlayers(String playerMenuLink) {
        ArrayList<String> playerLinks = new ArrayList<>();
        ArrayList<String[]> playerList = new ArrayList<>();
        ArrayList<String[]> seasonsPerPlayer = new ArrayList<>();

        basketDriver.navigate().to(playerMenuLink);

        //Si queremos que recoja todos los jugadores cambiar por  for (char c = 'a'; c <= 'z'; c++) {
        for (char c = 'a'; c == 'a'; c++) {
            System.out.println("--- " + c + " ---");
            basketDriver.get(playerMenuLink.concat(c + "/"));
            System.out.println(playerMenuLink);

            //Recoge las direcciones personales de los Jugadores para luego entrar en sus páginas.
            basketDriver.findElements(By.xpath("//table[@id='players']/tbody/tr/th//a")).forEach(a -> playerLinks.add(a.getAttribute("href")));

            try { // Mira si el desplegable de la información está activo o no.
                basketDriver.navigate().to(playerLinks.get(0));
                myWaitVar.until(ExpectedConditions.presenceOfElementLocated(By.id("meta_more_button")));
                basketDriver.findElement(By.id("meta_more_button")).click();
            } catch (Exception e) {
                System.out.println("Element not found. We will Continue its not relevant!");
            }

            //Llamamos a la Función GetPlayerInfo para recoger la información de cada jugador.
            playerLinks.forEach(link -> getPlayerInfo(link).forEach((k, v) -> {
                playerList.add(k.toOpenCSV());
                seasonsPerPlayer.addAll(v);
            }));
            playerLinks.clear();
        }

        //TODO player.csv
        //TODO playerSeasons.csv
        //Llamamos a las funciones para escribir los valores de los arrays en ficheros.
        writeToCSV(playerList, "players.csv");
        writeToXML(playerList, "players.xml");
        writeToCSV(seasonsPerPlayer, "playerSeasons.csv");
        writeToXML(seasonsPerPlayer, "playerSeasons.xml");

        System.out.println("---- Players Finished ----");
    }

    /**
     * Dirige al driver a la URL del jugador para recoger la información del mismo.
     *
     * @param playerLink URL del jugador
     * @return LinkedHashMap Player,ArrayList de String[]
     */
    public @NotNull LinkedHashMap<Player, ArrayList<String[]>> getPlayerInfo(String playerLink) {
        LinkedHashMap<Player, ArrayList<String[]>> infoPlayer = new LinkedHashMap<>();

        //De vez en cuando el Driver se bloquea, a continuación forzamos un Refresh de la página para que lea los datos.
        try {
            basketDriver.navigate().to(playerLink);
        } catch (TimeoutException e) {
            basketDriver.navigate().refresh();
        }
        Player p = getPersonalInfo();
        infoPlayer.put(p, getPlayerSeasons(p.getName()));
        return infoPlayer;
    }

    /**
     * Recolecta y filtra la información Personal del Jugador.
     *
     * @return Player
     */
    public @NotNull Player getPersonalInfo() {
        String name, position = "none", college = "none", draftTeam = "none", draftPos = "none", born = "none";
        int age = 0, draftYear = 0, expCareer = 0;

        //Filtramos sobre la información del jugador la que nos interese a partir del inicio de cada frase o String.
        name = basketDriver.findElement(By.xpath("//div[@id='meta']//span[1]")).getText();
        List<WebElement> playerPersonalInfo = basketDriver.findElements(By.xpath("//div[@id='meta']/div/p"));
        for (WebElement element : playerPersonalInfo) {
            String info = element.getText();
            if (info.startsWith("Position:")) {
                position = info.substring(info.indexOf(": ") + 1, info.indexOf(" ▪ ")).trim();
            } else if (info.startsWith("Born:")) {
                try {
                    born = basketDriver.findElement(By.id("necro-birth")).getAttribute("data-birth");
                } catch (NoSuchElementException e) {
                    born = info.substring(info.indexOf(":") + 1).trim();
                }
                if (info.contains("Age")) {
                    age = Integer.parseInt(info.substring(info.indexOf("Age: ") + 4, info.indexOf("-")).trim());
                }
            } else if (info.startsWith("Died")) {
                age = Integer.parseInt(info.substring(info.indexOf("Aged") + 5, info.indexOf("-")).trim());
            } else if (info.startsWith("College")) {
                college = info.substring(info.indexOf(":") + 1).trim();
            } else if (info.startsWith("Draft:")) {
                String[] draftInfo = info.split(", ");
                draftTeam = draftInfo[0].substring(draftInfo[0].indexOf(":") + 1).trim();
                draftPos = draftInfo[1].substring(draftInfo[1].indexOf("(") + 1);
                draftYear = Integer.parseInt(draftInfo[draftInfo.length - 1].substring(0, draftInfo[draftInfo.length - 1].indexOf(" NBA")));
            } else if (info.startsWith("Career Length:")) {
                expCareer = Integer.parseInt(info.substring(info.indexOf(":") + 1, info.indexOf("year")).trim());
            }
        }
        return new Player(name, position, college, draftTeam, draftPos, born, age, draftYear, expCareer);
    }

    /**
     * Recoge los datos sobre el jugador en cada temporada.
     *
     * @param name Nombre del Jugador
     * @return ArrayList (filas) de String[] (columnas).
     */
    public @NotNull ArrayList<String[]> getPlayerSeasons(String name) {
        ArrayList<String[]> seasonsPlayer = new ArrayList<>();

        basketDriver.findElements(By.xpath("//*[@id='totals']/tbody/tr")).forEach(tr -> {
            String[] playerStats = new String[32];
            Arrays.fill(playerStats, "none");

            playerStats[0] = name;

            tr.findElements(By.xpath("*")).forEach(stat -> {
                String type = stat.getAttribute("data-stat");
                String value = stat.getText();
                if (value.equals("")) value = "none";
                switch (type) {
                    case "season" -> playerStats[1] = value;
                    case "age" -> playerStats[2] = value;
                    case "team_id" -> playerStats[3] = value;
                    case "lg_id" -> playerStats[4] = value;
                    case "pos" -> playerStats[5] = value;
                    case "g" -> playerStats[6] = value;
                    case "gs" -> playerStats[7] = value;
                    case "mp" -> playerStats[8] = value;
                    case "fg" -> playerStats[9] = value;
                    case "fga" -> playerStats[10] = value;
                    case "fg_pct" -> playerStats[11] = value;
                    case "fg3" -> playerStats[12] = value;
                    case "fg3a" -> playerStats[13] = value;
                    case "fg3_pct" -> playerStats[14] = value;
                    case "fg2" -> playerStats[15] = value;
                    case "fg2a" -> playerStats[16] = value;
                    case "fg2_pct" -> playerStats[17] = value;
                    case "efg_pct" -> playerStats[18] = value;
                    case "ft" -> playerStats[19] = value;
                    case "fta" -> playerStats[20] = value;
                    case "ft_pct" -> playerStats[21] = value;
                    case "orb" -> playerStats[22] = value;
                    case "drb" -> playerStats[23] = value;
                    case "trb" -> playerStats[24] = value;
                    case "ast" -> playerStats[25] = value;
                    case "stl" -> playerStats[26] = value;
                    case "blk" -> playerStats[27] = value;
                    case "tov" -> playerStats[28] = value;
                    case "pf" -> playerStats[29] = value;
                    case "pts" -> playerStats[30] = value;
                    case "trp_dbl" -> playerStats[31] = value;
                    default -> {
                    }
                }
            });
            seasonsPlayer.add(playerStats);
        });

        return seasonsPlayer;
    }


    //TODO CHECK que funciona y falta comentar.

    /**
     * Recoge todos los datos sobre las diferentes Temporadas que se han realizado en la NBA.
     *
     * @param seasonsMenuLink URL to Seasons Index Page
     */
    public void getSeasons(String seasonsMenuLink) {
        ArrayList<String[]> listSeason = new ArrayList<>();
        ArrayList<String[]> listSeasonTeam = new ArrayList<>();

        ArrayList<Season> listObjSeasons = new ArrayList<>();
        String[] statsSeason = new String[10];
        final int[] cont = {0};

        basketDriver.navigate().to(seasonsMenuLink);
        basketDriver.findElements(By.xpath("//table[@id='stats']/tbody/tr")).forEach(tr -> {
            cont[0] = 1;
            tr.findElements(By.tagName("th")).forEach(th -> th.findElements(By.tagName("a")).forEach(a -> statsSeason[0] = a.getAttribute("href")));
            tr.findElements(By.xpath("*")).forEach(stat -> {
                if (stat.getText().equals("")) statsSeason[cont[0]] = "none";
                else statsSeason[cont[0]] = stat.getText();
                cont[0]++;
            });
            listSeason.add(statsSeason);
            listObjSeasons.add(new Season(statsSeason[1], statsSeason[2], statsSeason[3], statsSeason[4], statsSeason[5], statsSeason[6], statsSeason[7], statsSeason[8], statsSeason[9], statsSeason[0]));

        });
        listObjSeasons.remove(0);
        listObjSeasons.forEach(s -> listSeasonTeam.addAll(getTeamSeason(s.getLink(), s.getYear())));

        //TODO seasons.csv
        //TODO seasonsPerTeam.csv
        writeToCSV(listSeason, "seasons.csv");
        writeToXML(listSeason, "seasons.xml");
        writeToCSV(listSeasonTeam, "seasonsPerTeam.csv");
        writeToXML(listSeasonTeam, "seasonsPerTeam.xml");

        basketDriver.navigate().to(listObjSeasons.get(1).getLink());
        basketDriver.findElement(By.xpath("//*[@id='inner_nav']/ul/li[9]/a")).click();
        getGamesURL(basketDriver.findElement(By.xpath("//*[@id='inner_nav']/ul/li[3]/a")).getAttribute("href"));

    }

    //TODO Comprobar y Comentar

    /**
     * Recoge toda la información sobre las estadísticas de los equipos que participaron en la temporada.
     *
     * @param seasonLink URL de la Temporada
     * @param seasonYear Año de la Temporada
     * @return Devuelve un ArrayList (filas) de un String[] (columnas).
     */
    public @NotNull ArrayList<String[]> getTeamSeason(String seasonLink, String seasonYear) {
        ArrayList<String[]> seasonTeamStats = new ArrayList<>();
        final int[] cont = {0};

        basketDriver.navigate().to(seasonLink);

        basketDriver.findElement(By.id("confs_standings_E")).findElements(By.xpath("tbody/tr")).forEach(tr -> {
            String[] teamStats = new String[10];
            teamStats[0] = seasonYear;
            teamStats[9] = "Eastern";
            cont[0] = 1;
            tr.findElements(By.xpath("*")).forEach(stat -> {
                if (stat.getText().equals("—")) teamStats[cont[0]] = "none";
                else teamStats[cont[0]] = stat.getText();
                cont[0]++;
            });
            seasonTeamStats.add(teamStats);
        });
        basketDriver.findElement(By.id("confs_standings_W")).findElements(By.xpath("tbody/tr")).forEach(tr -> {
            String[] teamStats = new String[10];
            teamStats[0] = seasonYear;
            teamStats[9] = "Western";
            cont[0] = 1;
            tr.findElements(By.xpath("*")).forEach(stat -> {
                if (stat.getText().equals("—")) teamStats[cont[0]] = "none";
                else teamStats[cont[0]] = stat.getText();
                cont[0]++;
            });
            seasonTeamStats.add(teamStats);
        });

        return seasonTeamStats;
    }

    /**
     * Recopila datos de la tabla de cada equipo e de su ficha personal
     *
     * @param teamsMenuLink URL del TeamsIndex
     */
    public void getTeams(String teamsMenuLink) {
        auxDriver = new FirefoxDriver(new FirefoxOptions());
        ArrayList<String> teamLinks = new ArrayList<>();
        ArrayList<String> eastConferenceTeams = new ArrayList<>(Arrays.asList(
                "New Orleans Pelicans",
                "Phoenix Suns",
                "Memphis Grizzlies",
                "Denver Nuggets",
                "Sacramento Kings",
                "Utah Jazz",
                "Dallas Mavericks",
                "Portland Trail Blazers",
                "Los Angeles Clippers",
                "Golden State Warriors",
                "Minnesota Timberwolves",
                "Oklahoma City Thunder",
                "Los Angeles Lakers",
                "Houston Rockets",
                "San Antonio Spurs"));
        ArrayList<String> infoNotImportant = new ArrayList<>(Arrays.asList("lg_id", "year_min", "year_max", "years", "games", "win_loss_pct", "years_division_champion"));
        ArrayList<String[]> teamList = new ArrayList<>();
        ArrayList<Team> teamListObj = new ArrayList<>();
        final int[] cont = {0, 0};
        basketDriver.navigate().to(teamsMenuLink);
        basketDriver.findElements(By.xpath("//table[@id='teams_active']/tbody/tr/th/a")).forEach(a -> teamLinks.add(a.getAttribute("href")));
        String[] links = teamLinks.toArray(new String[teamLinks.size()]);
        basketDriver.findElements(By.xpath("//table[@id='teams_active']/tbody/tr[th/a]")).forEach(tr -> {
            String[] teamInfo = new String[9];
            tr.findElements(By.tagName("a")).forEach(str -> teamInfo[0] = str.getText());
            if (eastConferenceTeams.contains(teamInfo[0])) teamInfo[8] = "Eastern";
            else teamInfo[8] = "Western";

            auxDriver.navigate().to(links[cont[0]]);
            teamInfo[1] = auxDriver.findElement(By.xpath("//*[@id='meta']/div[2]/p[1]")).getText();
            teamInfo[1] = teamInfo[1].substring(teamInfo[1].indexOf(": ") + 1).trim();
            cont[0]++;
            cont[1] = 2;
            tr.findElements(By.tagName("td")).forEach(s -> {
                if (!infoNotImportant.contains(s.getAttribute("data-stat"))) {
                    teamInfo[cont[1]] = s.getText();
                    cont[1]++;
                }
            });
            teamList.add(teamInfo);
        });

        teamList.forEach(strings -> {
            if (strings[8].equals("Eastern"))
                teamListObj.add(new Team(strings[0], strings[1], Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]), Integer.parseInt(strings[7]), Conference.EASTERN));
            else
                teamListObj.add(new Team(strings[0], strings[1], Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]), Integer.parseInt(strings[7]), Conference.WESTERN));
        });

        //TODO teams.csv
        writeToCSV(teamList, "teams.csv");
        writeToXML(teamList, "teams.xml");
        auxDriver.quit();
    }

    /**
     * Determina a partir de un path la URL generica y posteriormente recoge por cada PlayOff
     * los partidos realizados.
     * @param path URL generica de partidos de PlayOff
     */
    public void getGamesURL(@NotNull String path) {
        auxDriver = new FirefoxDriver(new FirefoxOptions());
        ArrayList<String> gamesLinks = new ArrayList<>();
        ArrayList<Game> gamesInfoObj = new ArrayList<>();
        ArrayList<String> dataNotImport = new ArrayList<>(Arrays.asList("game_start_time", "overtimes", "attendance", "game_remarks"));

        ArrayList<String[]> gamesInfo = new ArrayList<>();
        ArrayList<String[]> playersPerGame = new ArrayList<>();

        final int[] cont = {0, 1};
        int minYear = 2015; //Año mínimo 1950 ;
        int maxYear = Integer.parseInt(path.substring(path.indexOf("_") + 1, path.lastIndexOf("_")));
        for (int i = minYear; i <= maxYear; i++) {
            gamesLinks.add(path.replace(maxYear + "", i + ""));
        }
        gamesLinks.forEach(link -> {
            basketDriver.navigate().to(link);
            basketDriver.findElements(By.xpath("//*[@id='schedule']/tbody/tr[td]")).forEach(tr -> {
                String[] info = new String[8];
                cont[0] = 1;
                //info[0] = link.substring(path.indexOf("_")+1,path.lastIndexOf("_"));
                info[0] = cont[1] + "";
                cont[1]++;
                tr.findElements(By.xpath("*")).forEach(data -> {
                    if (!dataNotImport.contains(data.getAttribute("data-stat"))) {
                        if (data.getAttribute("data-stat").equals("box_score_text")) {
                            data.findElements(By.tagName("a")).forEach(a -> info[7] = a.getAttribute("href"));
                        } else {
                            info[cont[0]] = data.getText();
                            cont[0]++;
                        }
                    }
                });
                gamesInfo.add(info);
                gamesInfoObj.add(new Game(Integer.parseInt(info[0]), info[1], info[2], Integer.parseInt(info[3]), info[4], Integer.parseInt(info[5]), info[6], info[7]));
                playersPerGame.addAll(getPlayersForGame(info[7], info[0]));
            });
        });

        //TODO games.csv
        //TODO playerPerGame.csv
        writeToCSV(gamesInfo, "games.csv");
        writeToXML(gamesInfo, "games.xml");
        writeToCSV(playersPerGame, "playerPerGame.csv");
        writeToXML(playersPerGame, "playerPerGame.xml");
        auxDriver.quit();
    }

    /**
     * Recoge la información sobre las estadisticas de cada jugador en el partido.
     * @param gameLink URL del Partido
     * @param idGame ID del Partido
     * @return Array (filas) de String[] (columnas) con cada jugador y su info.
     */
    public ArrayList<String[]> getPlayersForGame(String gameLink, String idGame) {
        ArrayList<String[]> playersGame = new ArrayList<>();
        ArrayList<String> teams = new ArrayList<>();
        final int[] cont = {0};

        auxDriver.navigate().to(gameLink);
        teams.add(auxDriver.findElement(By.xpath("//*[@id='div_other_scores']/div/div[1]/table/tbody/tr[2]//a")).getText());
        teams.add(auxDriver.findElement(By.xpath("//*[@id='div_other_scores']/div/div[1]/table/tbody/tr[3]//a")).getText());

        teams.forEach(team -> auxDriver.findElements(By.xpath("//*[@id='box-" + team + "-game-basic']/tbody/tr[td]")).forEach(tr -> {
            String[] statsPlayer = new String[23];
            statsPlayer[0] = idGame;
            statsPlayer[1] = team;
            cont[0] = 2;
            tr.findElements(By.xpath("*")).forEach(stat -> {
                statsPlayer[cont[0]] = stat.getText();
                cont[0]++;
            });
            playersGame.add(statsPlayer);
        }));
        playersGame.forEach(strings -> {
            for (String s : strings) {
                System.out.print(s + ", ");
            }
            System.out.println();
        });
        return playersGame;
    }

    /**
     * Imprime linea por linea String[], separando por comas cada posición.
     * @param list Lista Strings[]
     * @param path Dirección donde Guardarla
     */
    public static void writeToCSV(ArrayList<String[]> list, @NotNull String path) {
        try {
            //TODO WRITER
            String[] intro;
            switch (path) {
                case "players.csv" ->
                        intro = new String[]{"Name", "Position", "College", "Draft Team", "Draft Position", "Birthday", "Age", "Draft Year", "Career Experience"};
                case "playerSeasons.csv" ->
                        intro = new String[]{"Player", "Season", "Age", "Team", "League", "Position", "Games", "Games Starter",
                                "Minutes Played", "Field Goals", "Field Attempts", "Field Percent", "3PTS Goals",
                                "3PTS Attempts", "3PTS Percent", "2PTS Goals", "2PTS Attempts", "2PTS Percent",
                                "Effective Goal Percent", "Free Throw", "Free Throw Attempts", "Free Throw Percent",
                                "Offensive Rebounds", "Defensive Rebounds", "Total Rebounds", "Assists", "Steals",
                                "Blocks", "Turnovers", "Fouls", "Points", "Triples Dobles"};
                case "seasons.csv" ->
                        intro = new String[]{"Years", "League", "Champion", "MVP", "RookieOTY", "Points Leader", "Rebounds Leader", "Assists Leader", "Win Shares Leader", "Link"};
                case "seasonsPerTeam.csv" -> intro = new String[]{};
                case "teams.csv" ->
                        intro = new String[]{"Name", "Location", "Games", "Wins", "Loses", "Playoff Appearances", "Conference Champions", "NBA Champions", "Conference"};
                case "games.csv" ->
                        intro = new String[]{"Game ID", "Date", "Visitor", "Points Visitor", "Local", "Points Local", "Arena", "Link"};
                case "playerPerGame.csv" ->
                        intro = new String[]{"Game ID", "Team ID", "Player", "Minutes Played", "Field Goals", "FG Attempts", "FG Perc", "3P Field Goals",
                                "3PFG Attempts", "3PFG Perc", "Free Throws", "FT Attempts", "FT Perc", "Offensive Rebounds", "Defensive Rebounds"
                                , "Total Rebounds", "Assists", "Steals", "Blocks", "Turnovers", "Personal Fouls", "Points", "Punctuation"};
                default -> intro = new String[]{"Not defined..."};
            }
            CSVWriter writer = new CSVWriter(new FileWriter("info/CSV/" + path));
            writer.writeNext(intro);
            writer.writeAll(list);
            System.out.println("********Import Finished*****+");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Imprime en formato XML la lista, partiendo de un padre (NBA) y luego de hijos, estos dependientes del contenido
     * de la lista.
     * @param list Lista de String[]
     * @param path Dirreción donde Guardar
     */
    public static void writeToXML(ArrayList<String[]> list, String path) {
        try {
            String[] intro;
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Root element
            Element rootElement = document.createElement("NBA");
            document.appendChild(rootElement);

            switch (path) {
                case "player.xml" -> {
                    intro = new String[]{"Name", "Position", "College", "Draft Team", "Draft Position", "Birthday", "Age", "Draft Year", "Career Experience"};
                    // player element
                    list.forEach(strings -> {
                        Element parent = document.createElement("Player");
                        rootElement.appendChild(parent);
                        for (int i = 0; i < intro.length; i++) {
                            Element newElement = document.createElement(intro[i]);
                            newElement.appendChild(document.createTextNode(strings[i]));
                            parent.appendChild(newElement);
                        }
                    });
                }
                case "playerSeasons.xml" -> {
                    intro = new String[]{"Player", "Season", "Age", "Team", "League", "Position", "Games", "Games Starter",
                            "Minutes Played", "Field Goals", "Field Attempts", "Field Percent", "3PTS Goals",
                            "3PTS Attempts", "3PTS Percent", "2PTS Goals", "2PTS Attempts", "2PTS Percent",
                            "Effective Goal Percent", "Free Throw", "Free Throw Attempts", "Free Throw Percent",
                            "Offensive Rebounds", "Defensive Rebounds", "Total Rebounds", "Assists", "Steals",
                            "Blocks", "Turnovers", "Fouls", "Points", "Triples Dobles"};
                    list.forEach(strings -> {
                        Element parent = document.createElement(intro[0]);
                        Attr attr = document.createAttribute("name");
                        attr.setValue(strings[0]);
                        parent.setAttributeNode(attr);
                        rootElement.appendChild(parent);

                        Element season = document.createElement(intro[1]);
                        Attr years = document.createAttribute("years");
                        years.setValue(strings[1]);
                        season.setAttributeNode(years);
                        parent.appendChild(season);

                        for (int i = 2; i < intro.length; i++) {
                            Element newElement = document.createElement(intro[i]);
                            newElement.appendChild(document.createTextNode(strings[i]));
                            season.appendChild(newElement);
                        }

                    });
                }
                case "seasons.xml" -> {
                    intro = new String[]{"Years", "League", "Champion", "MVP", "RookieOTY", "Points Leader", "Rebounds Leader", "Assists Leader", "Win Shares Leader", "Link"};
                    list.forEach(strings -> {
                        Element parent = document.createElement("Season");
                        Attr attr = document.createAttribute("year");
                        attr.setValue(strings[0]);
                        parent.setAttributeNode(attr);
                        rootElement.appendChild(parent);

                        for (int i = 1; i < intro.length; i++) {
                            Element newElement = document.createElement(intro[i]);
                            newElement.appendChild(document.createTextNode(strings[i]));
                            parent.appendChild(newElement);
                        }
                    });
                }
                //TODO ESTO
                case "seasonsPerTeam.xml" -> {
                    intro = new String[]{};
                    list.forEach(strings -> {
                        Element parent = document.createElement("Player");
                        rootElement.appendChild(parent);
                        for (int i = 0; i < intro.length; i++) {
                            Element newElement = document.createElement(intro[i]);
                            newElement.appendChild(document.createTextNode(strings[i]));
                        }
                    });
                }
                case "teams.xml" -> {
                    intro = new String[]{"Name", "Location", "Games", "Wins", "Loses", "Playoff Appearances", "Conference Champions", "NBA Champions", "Conference"};
                    list.forEach(strings -> {
                        Element parent = document.createElement("Team");
                        rootElement.appendChild(parent);

                        for (int i = 0; i < intro.length; i++) {
                            Element newElement = document.createElement(intro[i]);
                            newElement.appendChild(document.createTextNode(strings[i]));
                            parent.appendChild(newElement);
                        }
                    });
                }
                case "games.xml" -> {
                    intro = new String[]{"id", "Date", "Visitor", "Points Visitor", "Local", "Points Local", "Arena", "Link"};
                    list.forEach(strings -> {
                        Element parent = document.createElement("Game");
                        Attr attr = document.createAttribute(intro[0]);
                        attr.setValue(strings[0]);
                        parent.setAttributeNode(attr);
                        rootElement.appendChild(parent);

                        for (int i = 1; i < intro.length; i++) {
                            Element newElement = document.createElement(intro[i]);
                            newElement.appendChild(document.createTextNode(strings[i]));
                            parent.appendChild(newElement);
                        }
                    });
                }
                case "playerPerGame.xml" -> {
                    intro = new String[]{"id", "Team ID", "Player", "Minutes Played", "Field Goals", "FG Attempts", "FG Perc", "3P Field Goals",
                            "3PFG Attempts", "3PFG Perc", "Free Throws", "FT Attempts", "FT Perc", "Offensive Rebounds", "Defensive Rebounds"
                            , "Total Rebounds", "Assists", "Steals", "Blocks", "Turnovers", "Personal Fouls", "Points", "Punctuation"};

                    /*ArrayList<String[]> sameGame = new ArrayList<>();
                    ArrayList<String[]> sameTeam = new ArrayList<>();
                    for (int i = 0; i <= Integer.parseInt(list.get(list.size()-1)[0]); i++) {
                        sameGame = (ArrayList<String[]>) list.clone();
                        String finalI = i+"";
                        sameGame.removeIf(strings -> !strings[0].equals(finalI));
                        sameTeam = (ArrayList<String[]>) sameGame.clone();
                    }*/

                    list.forEach(strings -> {
                        Element parent = document.createElement("Game");
                        Attr attr = document.createAttribute(intro[0]);
                        attr.setValue(strings[0]);
                        parent.setAttributeNode(attr);
                        rootElement.appendChild(parent);

                        for (int i = 1; i < intro.length; i++) {
                            Element newElement = document.createElement(intro[i]);
                            newElement.appendChild(document.createTextNode(strings[i]));
                            parent.appendChild(newElement);
                        }
                    });
                }
                default -> intro = new String[]{"Not defined..."};
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("info/XML/" + path));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
