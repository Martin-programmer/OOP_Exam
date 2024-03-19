package robotService.entities.robot;

public class MaleRobot extends BaseRobot {
    private static final int KILOGRAMS_VALUE = 9;
    public MaleRobot(String name, String kind, double price) {
        super(name, kind, KILOGRAMS_VALUE, price);
    }

    @Override
    public void eating() {
        super.setKilograms(super.getKilograms()+3);
    }
}
