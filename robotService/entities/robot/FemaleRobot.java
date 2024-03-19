package robotService.entities.robot;

public class FemaleRobot extends BaseRobot{
    private static final int KILOGRAMS_VALUE = 7;
    public FemaleRobot(String name, String kind,double price) {
        super(name, kind, KILOGRAMS_VALUE, price);
    }

    @Override
    public void eating() {
        super.setKilograms(super.getKilograms()+1);
    }
}
