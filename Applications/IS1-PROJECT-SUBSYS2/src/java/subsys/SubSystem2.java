package subsys;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public class SubSystem2 extends Subsystem {

    @Override
    public void init() {
        super.init("queue2");
    }
    
    @Override
    protected void handleMessageCodeCalls(String codeName) throws JMSException {
        switch(codeName) {
            case "getAll":
                handleGetAll();
                break;
            case "getQueried":
                handleGetQueried();
                break;
            case "create":
                handleCreation();
                break;
            case "update":
                handleUpdate();
                break;
            case "delete":
                handleDelete();
                break;
            case "audioKategorija":
                handleAudioKategorija();
                break;
            default:
                System.out.println("CODE: " + codeName);
                super.wrongCode();
                break;
        }
    }
    
    private void handleAudioKategorija() {
        try {
            System.out.println("Waiting for idk...");
            Message request = consumer.receive();
            
            if(!(request instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (idk)");
                return;
            }
            TextMessage idkMsg = (TextMessage) request;
            int idk = Integer.parseInt(idkMsg.getText());
            
            System.out.println("Waiting for id...");
            request = consumer.receive();
            
            if(!(request instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (ida)");
                return;
            }
            TextMessage idaMsg = (TextMessage) request;
            int ida = Integer.parseInt(idaMsg.getText());
            
            boolean created = jpaManager.addAudioKategorija(idk, ida);
            StreamMessage retMsg = context.createStreamMessage();
            retMsg.writeBoolean(created);
            retMsg.setStringProperty("MESSAGE_TYPE", "RESPONSE");
            producer.send(queue, retMsg);
            System.out.println("Sent back: " + created);
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
