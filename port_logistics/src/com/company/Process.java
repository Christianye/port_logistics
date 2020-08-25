package com.company;

public class Process {

    private String command;
    private String header = "-^-^\n";
    private String trainSymbol = "  D i";
    private String footer = "VA_A---::%%%\n";
    private int shipStoreage = 0;
    private int channelStorage = 0;
    private int trainStorage = 0;
    private String[] ship = {" "," "," "," "," "};
    private String[] storage = {" "," "," "," "," "};
    private String[] train = {" "," "," "," "," "};
    private String crane = "|";
    private int flagProcessError = 0;
    private String processErrorMsg = "";
    private String result = "";
    private static int shiplimit = 4;
    private static int storagelimit = 5;
    private static int trainlimit = 3;


    public Process(){}

    public Process(String command){
        this.command = command;
    }

    public void show(){

        String[] singleCommand;

        singleCommand = command.split(",");

        String errorAfterCheck = commandCheck(singleCommand,command);

        if (errorAfterCheck == ""){


            for (int i = 0; i < singleCommand.length ; i = i+1){

                switch (singleCommand[i]){
                    case "receive_ship":
                        System.out.println("receive ship processed\n ");
                        receive_ship();
                        break;
                    case "unload":
                        System.out.println("unload processed\n ");
                        unload();
                        break;
                    case "train_send":
                        System.out.println("train_send processed\n ");
                        train_send();
                        break;
                    case "show":
                        System.out.println("show processed\n ");
                        showPort();
                        break;
                    default:
                        System.out.print("unknown command\n " +
                                "Please Re-RUN this application.");

                }
            }
        }
        else {
            flagProcessError = 1;
            processErrorMsg = processErrorMsg + errorAfterCheck;
        }

        if (flagProcessError != 0){
            System.out.print(processErrorMsg);
        }
        else{
            System.out.print(result);
        }

    }


    private void showPort(){

        result = result + header;

        for (int i = 0 ; i < storagelimit ; i++){
            result = result
                    + ship[i]
                    + crane
                    + storage[i]
                    + crane;

            if (i == storagelimit - 1){
                if (trainStorage != 0){
                    result = result + "XXX";
                }
                else{
                    result = result + "   ";
                }

                result = result + trainSymbol;
            }

            result = result + "\n";
        }


        result = result + footer;

        //System.out.print(result);

    }

    private void receive_ship(){

        if (shipStoreage > 0){
            flagProcessError = 1;
            processErrorMsg = processErrorMsg + "To many ships\n ";
        }
        else{
            shipStoreage = shiplimit;

            for (int i = storagelimit-1 ; i >= storagelimit - shipStoreage ; i--){
                ship[i] = "X";
            }
        }

    }

    private void unload() {

        if (channelStorage > 0){
            unloadStorage();
            unloadShip();
        }
        else{
            unloadShip();
            unloadStorage();
        }


        //channel
        if (channelStorage > 0) {
            for (int i = storagelimit - 1; i >= storagelimit - channelStorage; i--) {
                storage[i] = "X";
            }
        }
        else{
            for (int i = 0; i < storagelimit ; i++) {
                storage[i] = " ";
            }
        }

        //ship
        if (shipStoreage>0) {
            for (int i = storagelimit - 1; i >= storagelimit - shipStoreage; i--) {
                ship[i] = "X";
            }
        }
        else{
            for (int i = 0; i < storagelimit ; i++) {
                ship[i] = " ";
            }
        }
    }

    private void unloadShip() {
        //unload ship to storage
        if (shipStoreage > 0){
            if (channelStorage + shipStoreage > storagelimit){
                flagProcessError = 1;
                processErrorMsg = processErrorMsg + "To many containers\n ";
            }
            else{
                shipStoreage = shipStoreage - (storagelimit - channelStorage);
                if (shipStoreage <=0){
                    shipStoreage = 0;
                    channelStorage = channelStorage + shiplimit;
                }
                else{
                    channelStorage = channelStorage + shipStoreage;
                }

            }
        }

    }

    private void unloadStorage() {
        //unload to train
        if (trainStorage > 0 && (shipStoreage + channelStorage) > storagelimit){
            flagProcessError = 1;
            processErrorMsg = processErrorMsg + "To many containers for train\n ";
        }
        else{
            if (channelStorage >= trainlimit ) {
                trainStorage = trainlimit;
                channelStorage = channelStorage - trainlimit;
            }
            else{
                trainStorage = channelStorage;
                //channelStorage = 0;
            }
        }

    }


    void train_send(){
        trainStorage = 0;
    }

    private String commandCheck(String[] singleCommand, String command){

        String errorStart = "wrong command\n ";
        String errorEnd = "Please Re-RUN this application.\n ";
        String errorMsg = "";
        Integer exceedError = 0;

        for (int i = 0; i < singleCommand.length ; i = i+1) {
            if (singleCommand[i].equals("show") == true){
                exceedError = exceedError + 1;
            }
        }

        if (exceedError != 1){
            errorMsg = errorStart +
                    "At most only can have 'show' command\n ";
        }

        if (singleCommand[singleCommand.length - 1].equals("show") == false){
            errorMsg = errorStart +
                    "The 'show' command only can be typed in the end of all commands\n ";
        }

        if (errorMsg.length()>0)
        {
            errorMsg = errorMsg + errorEnd;
        }

        return errorMsg;

    }

}


