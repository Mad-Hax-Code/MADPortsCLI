package com.madhax.madportscli.controller;

import com.madhax.madportsapi.PortScanner;
import com.madhax.madportsapi.model.ScanResult;
import com.madhax.madportscli.view.ViewResults;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class Controller {

    private ViewResults viewResults;
    private HashMap<Integer, CSVRecord> portData;

    public Controller(ViewResults viewResults) {
        this.viewResults = viewResults;
        this.portData = loadPortData();
    }

    private HashMap<Integer, CSVRecord> loadPortData() {

        HashMap<Integer, CSVRecord> portList = new HashMap<>();
        File portListFile = new File("formatted-ports.csv");

        try (FileReader portFileReader = new FileReader(portListFile)) {

            List<CSVRecord> ports = CSVFormat.DEFAULT
                                    .withFirstRecordAsHeader()
                                    .parse(portFileReader)
                                    .getRecords();
            for (CSVRecord port : ports) {
                portList.put(Integer.parseInt(port.get("Port")), port);
            }
            return portList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ScanResult multiThreadedScan(String host, int startPort, int endPort) {
        ScanResult scanResult = PortScanner.multiThreadedScan(host, startPort, endPort);
        return scanResult;
    }

    public HashMap<Integer, CSVRecord> getPortData() {
        return this.portData;
    }

}
