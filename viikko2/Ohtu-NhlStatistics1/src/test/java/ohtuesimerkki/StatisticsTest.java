package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {

    Statistics stats;
    Reader readerStub;

    public StatisticsTest() {
        readerStub = new Reader() {
            public List<Player> getPlayers() {
                ArrayList<Player> players = new ArrayList<Player>();

                players.add(new Player("Semenko", "EDM", 4, 12));
                players.add(new Player("Lemieux", "PIT", 45, 54));
                players.add(new Player("Kurri", "EDM", 37, 53));
                players.add(new Player("Yzerman", "DET", 42, 56));
                players.add(new Player("Gretzky", "EDM", 35, 89));

                return players;
            }
        };
    }

    @Before
    public void setUp() {
        stats = new Statistics(readerStub);
    }

    @Test
    public void searchWorks1() {
        Player p = stats.search("Kurri");
        assertEquals("Kurri", p.getName());
    }

    @Test
    public void searchWorks2() {
        Player p = stats.search("Yzeri");
        assertTrue(p == null);
    }

    @Test
    public void returnsAllPlayersInATeam() {
        List<Player> team = stats.team("EDM");
        assertEquals(3, team.size());
        boolean rightMembers = true;
        for (Player p : team) {
            if (!p.getName().equals("Semenko") && !p.getName().equals("Kurri") && !p.getName().equals("Gretzky")) {
                rightMembers = false;
            } 
        }
        assertTrue(rightMembers);
    }
    
    @Test
    public void returnsTopScorers() {
        List<Player> top = stats.topScorers(2);
        assertEquals(3, top.size());
        assertEquals("Gretzky", top.get(0).getName());
    }
}
