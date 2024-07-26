package msg.sending;

import javax.jms.TextMessage;

import msg.sending.producer.ProducerProcess;
import msg.sending.producer.ProducerSendingDateObj;
import msg.sending.producer.Textmessage;

public class MainClass {
    public static void main(String args[]) throws Exception {
        // ProducerProcess producer=new ProducerProcess();
        // ProducerSendingDateObj producerSendingObj=new ProducerSendingDateObj();
        // producer.modifyAndSendMsgForConsuming();
        // producerSendingObj.sendingObjToQueue();
        Textmessage textmessage=new Textmessage();
        textmessage.sendTextMessage();
    }
}