ld48 29 game
=============

Game for Omsk Ludum dare 29.

###[Download release](https://github.com/cat-in-the-dark-wood/old48_29_game/releases/download/v1.1/bsod_en.jar)

```java
class Game {
  public static String theme = "Beneath the surface";
  
  public final String name = "Beneath the surface of Democracy";
  public final String short_name = "BSoD";
  
  public final TeamMater[] teamMaters;
  
  public Game() {
    teamMaters = new TeamMater[] {
      new TeamMater("Blan4", TeamMater.Role.PROGRAMMER),
      new TeamMater("BOOtak", TeamMater.Role.PROGRAMMER),
      new TeamMater("Over64", TeamMater.Role.PROGRAMMER),
      new TeamMater("Eve-eveline", TeamMater.Role.PAINTER)
    };
  }
  
  public class TeamMater {
    private final String name;
    private final Role role;
    
    public enum Role {
      PROGRAMER, PAINTER
    }
    
    public TeamMater(String n, Role r) {
      this.name = n;
      this.role = r;
    }
    
    public String getName() { return this.name; }
    public Role getRole() { return this.role; }
  }
}
```

![](https://raw.githubusercontent.com/cat-in-the-dark-wood/old48_29_game/master/screenshots/1.jpg)
![](https://raw.githubusercontent.com/cat-in-the-dark-wood/old48_29_game/master/screenshots/2.png)
![](https://raw.githubusercontent.com/cat-in-the-dark-wood/old48_29_game/master/screenshots/3.png)
![](https://raw.githubusercontent.com/cat-in-the-dark-wood/old48_29_game/master/screenshots/4.png)
![](https://raw.githubusercontent.com/cat-in-the-dark-wood/old48_29_game/master/screenshots/5.png)
