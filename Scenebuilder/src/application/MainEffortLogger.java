package application;

//Interface for all prototypes
interface Prototype {
 void execute();
}

//Example prototype implementations
class PrototypeA implements Prototype {
 @Override
 public void execute() {
     System.out.println("Executing Prototype A");
 }
}

class PrototypeB implements Prototype {
 @Override
 public void execute() {
     System.out.println("Executing Prototype B");
 }
}

class PrototypeC implements Prototype {
 @Override
 public void execute() {
     System.out.println("Executing Prototype C");
 }
}

//Mainline system to coordinate prototypes

public class MainEffortLogger {

}
