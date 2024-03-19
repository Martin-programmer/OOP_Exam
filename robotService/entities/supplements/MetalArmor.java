package robotService.entities.supplements;

public class MetalArmor extends BaseSupplement{
    private static final int HARDNESS_VALUE = 5;
    private static final double PRICE_VALUE = 15;
    public MetalArmor() {
        super(HARDNESS_VALUE, PRICE_VALUE);
    }
}
