---
description: This guide will show you have to add your very own VictoryDance
---

# Victory Dances

Creating a custom VictoryDance is a fairly straight forward process. First of all, ensure you have the API dependency loaded into your project and set WhaleSkyWars as a depend in your plugin.yml file.

Create your victorydance class that extends `VictoryDance`. In this example, we shall make a class called `FireWorkDance` that will spew out fireworks at the players location whenever they win a game

```java
public class FireWorkDance extends VictoryDance {}
```

You will need to create a constructor matching the super class. The IDE you use may prompt you to do so.\
Inside the `super(...)` you will add the dance name, id and cost. In this example, it's called "Fireworks".

```java
public class FireworkDance extends VictoryDance {
    /**
     * Initializes a new instance of the VictoryDance class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the victory dance.
     * @param id The unique identifier of the victory dance.
     * @param cost The cost of the victory dance.
     */
    public FireworkDance() {
        super("Fireworks", 0, 0);
    }
```

Next, we'll need to add the following methods: `start`, `stop`and `clone`.\
And a private `BukkitTask`variable.

```java
public class FireWorkDance extends VictoryDance { 

    private BukkitTask task = null;
      
    /**
     * Initializes a new instance of the VictoryDance class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the victory dance.
     * @param id   The unique identifier of the victory dance.
     * @param cost The cost of the victory dance.
     */
    public FireWorkDance() {
        super("Fireworks", 0, 0);
    }

    @Override
    public void start(IGamePlayer player) {
        // Called when the game starts the dance
    }

    @Override
    public void stop(IGamePlayer player) {
        // Called when the game stops the dance
    }

    @Override
    public VictoryDance clone() {
        // Return a new instance of the FireWorkDance class
        // Unless you want to do your own cloning
        // This will be suficient enough
        return new FireWorkDance(); 
    }
}
```

Finally, we'll implement the logic to actually launch the Fireworks.

```java
public class FireWorkDance extends VictoryDance {

    private BukkitTask task = null;

    /**
     * Initializes a new instance of the VictoryDance class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the victory dance.
     * @param id   The unique identifier of the victory dance.
     * @param cost The cost of the victory dance.
     */
    public FireWorkDance() {
        super("Fireworks", 0, 0);
    }

    @Override
    public void start(IGamePlayer player) {
        // Called when the game starts the dance
        final IGame game = player.getGame();

        final int duration = 10;

        task = new BukkitRunnable() {
            int counter = duration * 20 / 30;
            @Override
            public void run() {
                // Check if the players current game is the same as the game
                if (player.getGame() != game) {
                    cancel();
                    task = null;
                    return;
                }
                // Check if the player is still in the game
                if (!game.getPlayers().contains(player)) {
                    cancel();
                    task = null;
                    return;
                }

                // Check if the counter is less than or equal to 0
                if (counter <= 0) {
                    cancel();
                    task = null;
                    return;
                }

                // Launch a firework at the players location
                player.getPlayer().ifPresent(player -> {
                    Location location = player.getLocation().clone().add(0, 2, 0);
                    launchFirework(location);
                });
                counter--;

            }
        }.runTaskTimer(PLUGIN_INSTANCE, 0, 20L);

    }

    @Override
    public void stop(IGamePlayer player) {
        // Called when the game stops the dance
        if (task != null) task.cancel();
    }

    @Override
    public VictoryDance clone() {
        // Return a new instance of the FireWorkDance 
        // Unless you want to do your own cloning
        // This will be suficient enough
        return new FireWorkDance();
    }

    private void launchFirework(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.RED, Color.WHITE, Color.BLUE, Color.ORANGE, Color.AQUA)
                .flicker(true)
                .trail(true)
                .build();
        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(1);
        firework.setFireworkMeta(fireworkMeta);
        Bukkit.getScheduler().runTaskLater(PLUGIN_INStANCE, firework::detonate, 2); // Delayed explosion after 2 ticks
    }

}
```

## Reigstering the VictoryDance

Simply build the jar file and put it in the `WhaleSkyWars/addons`folder :)
