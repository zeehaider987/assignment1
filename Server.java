import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sun.rmi.runtime.NewThreadAction;

public class Server extends Application{
    public void start(Stage primaryStage){
        TextArea ta = new TextArea();
        Scene scene = new Scene(new ScrollPane(ta),450,200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        new Thread(() -> {
            try{
                ServerSocket serversocket = new ServerSocket(8000);
                platform.runlater(()->ta.appendText("Server start at : "+Date()+'\n'));
                Socket socket = new serversocket.accept();
                DataInputStream inputFormClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient =  new DataOutputStream(socket.getOutputStream());
                while(true){

                    double radius = inputFormClient.readDouble();
                    double area =radius *radius*Math.PI;
                    outputToClient.writeDouble(area);
                    platform.runlater(()->{
                        ta.appendText("radius received from client : "+radius+'\n');
                        ta.appendText("Area is : "+area+'\n');

                    });
                }
            }
            catch(IOException ex ){
                ex.printStackTrace();
            }
        }).start();


    }



}