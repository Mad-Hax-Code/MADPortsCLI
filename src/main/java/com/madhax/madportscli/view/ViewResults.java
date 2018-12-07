package com.madhax.madportscli.view;

import com.madhax.madportsapi.model.PortResult;
import com.madhax.madportsapi.model.ScanResult;
import com.madhax.madportscli.controller.Controller;
import org.apache.commons.csv.CSVRecord;

public class ViewResults {

    private final int PORT_STATE_TO_DISPLAY = 1;
    private Controller controller;

    public void displayResults(ScanResult scanResult) {

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" +
                "\n" +
                "   _____      _____  ________    __________              __          \n" +
                "  /     \\    /  _  \\ \\______ \\   \\______   \\____________/  |_  ______\n" +
                " /  \\ /  \\  /  /_\\  \\ |    |  \\   |     ___/  _ \\_  __ \\   __\\/  ___/\n" +
                "/    Y    \\/    |    \\|    `   \\  |    |  (  <_> )  | \\/|  |  \\___ \\ \n" +
                "\\____|__  /\\____|__  /_______  /  |____|   \\____/|__|   |__| /____  >\n" +
                "        \\/         \\/        \\/                                   \\/ \n" +
                "\n");
        stringBuilder.append("Host: " + scanResult.HOSTNAME + "\n");
        stringBuilder.append("Start Port: " + scanResult.START_PORT + "\n");
        stringBuilder.append("End Port: " + scanResult.END_PORT + "\n");
        stringBuilder.append("Total Ports:" + scanResult.getTotalPortsScanned() + "\n");
        stringBuilder.append("Threads: " + scanResult.THREADS + " @ ~ ");
        stringBuilder.append(scanResult.getTotalPortsScanned() / scanResult.THREADS + " per thread\n");
        stringBuilder.append("Scan Time: " + scanResult.getScanTimeSeconds() + " seconds\n");
        stringBuilder.append("+---------------------------------------------------------------------------------------+\n");
        stringBuilder.append("| State  | Port  | Service      | Description                                           |\n");
        stringBuilder.append("+---------------------------------------------------------------------------------------+\n");

        scanResult.getScanResults().stream()
                .filter(r -> r.getState() == PORT_STATE_TO_DISPLAY)
                .forEach(r -> stringBuilder.append(portReport(r)));

        stringBuilder.append("+---------------------------------------------------------------------------------------+\n");

        System.out.println(stringBuilder.toString());
    }

    private String portReport(PortResult portResult) {
        CSVRecord portDetails = controller.getPortData().get(portResult.getPort());
        String state;
        if (portResult.getState() == 1) {
            state = "Open";
        } else {
            state = "Closed";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("| %-6s   %5d   %-12s   %-53s |%n",
                state,
                portResult.getPort(),
                portDetails.get("Service"),
                portDetails.get("Description")));

        return stringBuilder.toString();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
