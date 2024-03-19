package robotService.entities.services;

import robotService.common.ExceptionMessages;
import robotService.entities.robot.BaseRobot;
import robotService.entities.robot.Robot;
import robotService.entities.supplements.Supplement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService implements Service{
    private String name;
    private int capacity;
    private int currentFullness = 0;
    private Collection<Supplement> supplements;
    private Collection<Robot> robots;

    public BaseService(String name, int capacity){
        setName(name);
        this.capacity = capacity;
        this.supplements = new ArrayList<>();
        this.robots = new ArrayList<>();
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.SERVICE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Robot> getRobots() {
        return this.robots;
    }

    @Override
    public Collection<Supplement> getSupplements() {
        return this.supplements;
    }

    @Override
    public void addRobot(Robot robot) {
        if (capacity <= currentFullness+1){
            throw new IllegalStateException("Not enough capacity for this robot.");
        }else{
            robots.add(robot);
            currentFullness++;
        }
    }

    @Override
    public void removeRobot(Robot robot) {
        currentFullness--;
        robots.remove(robot);
    }

    @Override
    public void addSupplement(Supplement supplement) {
        supplements.add(supplement);
    }

    @Override
    public void feeding() {
        for (Robot robot : robots) {
            robot.eating();
        }
    }

    @Override
    public int sumHardness() {
        int sum = 0;
        for (Supplement supplement : supplements) {
            int hardness = supplement.getHardness();
            sum+=hardness;
        }
        return sum;
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s:%n",this.name,this.getClass().getSimpleName()));
        if (robots.isEmpty()){
            sb.append(String.format("Robots: none%n"));
        }else{
            String robots = this.robots.stream().map(Robot::getName).collect(Collectors.joining(" "));
            sb.append(String.format("Robots: %s%n",robots));
        }

        sb.append(String.format("Supplements: %d Hardness: %d%n",supplements.size(),this.sumHardness()));
        return sb.toString();
    }
}
