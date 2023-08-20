import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Dieable {
    boolean shouldDie();
    void die();
}

interface Reproducible {
    boolean shouldReproduce();
    Creature reproduce();
}

class Creature implements Dieable, Reproducible {
    private static final double REPRODUCE_CHANCE = 0.1;
    private static final double DIE_CHANCE = 0.05;
    
    private String name;
    private int age;

    public Creature(String name) {
        this.name = name;
        this.age = 0;
    }
    
    public boolean shouldDie() {
        return Math.random() < DIE_CHANCE;
    }
    
    public void die() {
        System.out.println(name + " has died.");
    }

    public boolean shouldReproduce() {
        return Math.random() < REPRODUCE_CHANCE;
    }
    
    public Creature reproduce() {
        String childName = getRandomName();
        return new Creature(childName);
    }

    private String getRandomName() {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("name.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        int randomIndex = random.nextInt(names.size());
        return names.get(randomIndex);
    }
}

class World {
    private static final double SPAWN_CREATURE_CHANCE = 0.2;

    private List<Creature> creatures = new ArrayList<>();
    
    public void createCreature() {
        if (Math.random() < SPAWN_CREATURE_CHANCE) {
            String creatureName = getRandomName();
            Creature newCreature = new Creature(creatureName);
            creatures.add(newCreature);
        }
    }
    
    public void spawnFood() {
        System.out.println("Food has been spawned in the world.");
    }

    private String getRandomName() {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("name.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        int randomIndex = random.nextInt(names.size());
        return names.get(randomIndex);
    }
    
    public void simulateTimeSteps(int numTimeSteps) {
        for (int timeStep = 1; timeStep <= numTimeSteps; timeStep++) {
            System.out.println("Time Step: " + timeStep);
            
            List<Creature> newCreatures = new ArrayList<>();
            
            for (Creature creature : creatures) {
                if (!creature.shouldDie()) {
                    newCreatures.add(creature);
                    
                    if (creature.shouldReproduce()) {
                        newCreatures.add(creature.reproduce());
                    }
                } else {
                    creature.die();
                }
            }
            
            creatures = newCreatures;
        }
    }
}

public class finalassignment {
    public static void main(String[] args) {
        World world = new World();
        
        for (int i = 0; i < 10; i++) {
            world.createCreature();
        }

        world.spawnFood();

        int numTimeSteps = 5;
        world.simulateTimeSteps(numTimeSteps);
    }
}
