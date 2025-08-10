package main;

import javax.jms.JMSException;
import subsys.SubSystem3;
import subsys.Subsystem;

public class Main {

    public static void main(String[] args) {
        Subsystem subsys = new SubSystem3();
        subsys.init();
        
        try {
            subsys.listen();
        } catch (JMSException ex) {
            System.out.println("[JMS_EXCEPTION]");
        }
    }
    
}
