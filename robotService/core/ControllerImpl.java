package robotService.core;

import robotService.common.ExceptionMessages;
import robotService.entities.robot.FemaleRobot;
import robotService.entities.robot.MaleRobot;
import robotService.entities.robot.Robot;
import robotService.entities.services.MainService;
import robotService.entities.services.SecondaryService;
import robotService.entities.services.Service;
import robotService.entities.supplements.MetalArmor;
import robotService.entities.supplements.PlasticArmor;
import robotService.entities.supplements.Supplement;
import robotService.repositories.SupplementRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller{
    private SupplementRepository supplements;
    private Collection<Service> services;
    public ControllerImpl(){
        supplements = new SupplementRepository();
        services = new ArrayList<>();
    }
    @Override
    public String addService(String type, String name) {
        if (type.equals("MainService")){
            MainService mainService = new MainService(name);
            services.add(mainService);
        } else if (type.equals("SecondaryService")) {
            SecondaryService secondaryService = new SecondaryService(name);
            services.add(secondaryService);
        }else{
            throw new NullPointerException(ExceptionMessages.INVALID_SERVICE_TYPE);
        }
        return String.format("%s is successfully added.",type);
    }

    @Override
    public String addSupplement(String type) {
        if (type.equals("PlasticArmor")){
            PlasticArmor plasticArmor = new PlasticArmor();
            supplements.addSupplement(plasticArmor);
        } else if (type.equals("MetalArmor")) {
            MetalArmor metalArmor = new MetalArmor();
            supplements.addSupplement(metalArmor);
        }else{
            throw new IllegalArgumentException(ExceptionMessages.INVALID_SUPPLEMENT_TYPE);
        }
        return String.format("%s is successfully added.",type);
    }

    @Override
    public String supplementForService(String serviceName, String supplementType) {
        Supplement supplement = supplements.findFirst(supplementType);
        if (supplement == null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_SUPPLEMENT_FOUND,supplementType));
        }
        for (Service service : services) {
            if (service.getName().equals(serviceName)){
                service.addSupplement(supplement);
                supplements.removeSupplement(supplement);
                break;
            }
        }
        return String.format("Successfully added %s to %s.",supplementType,serviceName);
    }

    @Override
    public String addRobot(String serviceName, String robotType, String robotName, String robotKind, double price) {
        Service service = null;
        for (Service service1 : services) {
            if (service1.getName().equals(serviceName)){
                service = service1;
            }
        }
        if (robotType.equals("MaleRobot")){
            MaleRobot maleRobot = new MaleRobot(robotName,robotKind,price);
            if (service.getClass().getSimpleName().equals("SecondaryService")){
                return "Unsuitable service.";
            }else{
                service.addRobot(maleRobot);
                return "Successfully added MaleRobot to "+ serviceName +".";
            }
        } else if (robotType.equals("FemaleRobot")) {
            FemaleRobot femaleRobot = new FemaleRobot(robotName,robotKind,price);
            if (service.getClass().getSimpleName().equals("MainService")){
                return "Unsuitable service.";
            }else{
                service.addRobot(femaleRobot);
                return "Successfully added FemaleRobot to " + serviceName+".";
            }
        }else{
            throw new IllegalArgumentException(ExceptionMessages.INVALID_ROBOT_TYPE);
        }
    }

    @Override
    public String feedingRobot(String serviceName) {
        Service service = null;
        for (Service current : services) {
            if (current.getName().equals(serviceName)){
                service = current;
                break;
            }
        }
        service.feeding();
        return String.format("Robots fed: %d",service.getRobots().size());
    }

    @Override
    public String sumOfAll(String serviceName) {
        Service service = null;
        for (Service current : services) {
            if (current.getName().equals(serviceName)){
                service = current;
                break;
            }
        }
        Collection<Robot> robots = service.getRobots();
        Collection<Supplement> supplements1 = service.getSupplements();
        double sum = 0;
        for (Robot robot : robots) {
            sum+=robot.getPrice();
        }
        for (Supplement supplement : supplements1) {
            sum+=supplement.getPrice();
        }
        return String.format("The value of service %s is %.2f.",serviceName,sum);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Service service : services) {
            sb.append(String.format("%s",service.getStatistics()));
        }
        return sb.toString();
    }
}
