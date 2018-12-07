package com.madhax.madportscli;

import com.madhax.madportscli.controller.Controller;
import com.madhax.madportscli.view.ViewResults;

public class MADPortsCLI {

    private ViewResults viewResults = new ViewResults();
    private Controller controller = new Controller(viewResults);

    public MADPortsCLI(String scanOptions[]) {

        viewResults.setController(controller);

        String host = scanOptions[0];
        int startPort = Integer.parseInt(scanOptions[1]);
        int endPort = Integer.parseInt(scanOptions[2]);

        viewResults.displayResults(controller.multiThreadedScan(host, startPort, endPort));
    }

    public static void main(String args[]) {
        if (args.length < 3) {
            System.out.println("Syntax isn't quite right.");
            System.out.println("Usage: host startPort endPort");
        } else {
            new MADPortsCLI(args);
        }
    }
}
