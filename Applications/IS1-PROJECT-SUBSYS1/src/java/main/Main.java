package main;

import javax.jms.JMSException;
import subsys.SubSystem1;
import subsys.Subsystem;

public class Main {
    
    public static void main(String[] args) {
        Subsystem subsys = new SubSystem1();
        subsys.init();
        
        try {
            subsys.listen();
        } catch (JMSException ex) {
            System.out.println("[JMS_EXCEPTION]");
        }
    }
}