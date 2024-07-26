package msgreceiver;

import msgreceiver.consumer.ObjReceiver;

public class MainClass {
    public static void main(String[] args) throws Exception {
        ObjReceiver objReceiver = new ObjReceiver();
        objReceiver.objConsuming();

        // ActiveMqConsumer activemq=new ActiveMqConsumer();
        // activemq.objConsuming();

        // CallMethodUsingClassComponent callMethodUsingClassComponent=new
        // CallMethodUsingClassComponent();
        // callMethodUsingClassComponent.sendingMessage();

        // CallMethodUsingBeanComponent callMethodUsingBeanComponent=new
        // CallMethodUsingBeanComponent();
        // callMethodUsingBeanComponent.sendingMessage();

    }
}