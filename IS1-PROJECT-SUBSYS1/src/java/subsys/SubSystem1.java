package subsys;

import javax.jms.JMSException;

public class SubSystem1 extends Subsystem {

    @Override
    public void init() {
        super.init("queue1");
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
            default:
                System.out.println("CODE: " + codeName);
                super.wrongCode();
                break;
        }
    }
    
    

}
