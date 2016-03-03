package com.android.reverse.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by LCX on 2015/3/18.
 * @author LCX
 *
 * Transfer captured API to PC use socket
 */
public class CapturedAPITransfer {

    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    // Denoting if current process's socket connection to server established
    public static boolean socketEstablished = false;
    private static String deviceIP = "";
    private static String ipLocation = Environment.getExternalStorageDirectory().toString()
            + "/";

    /**
     * Establishing socket connection to PC
     */
    public static void connectToPC() {
        try {
            getIP();
            Logger.log("ip address: " + deviceIP);
            socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(deviceIP), 10006), 30000);
            Logger.log("Successfully connect to PC.");
            socketEstablished = true;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            Logger.log(e.getMessage());
            Logger.log("Error occurred during connect to server!");
        }
    }
    /**
     *
     * @param message Data need to transfer
     */
    public static void transferData(String message) {
        try {
            Logger.log(message);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getIP() {
        File ipFile = new File(ipLocation + "ip.txt");
        Logger.log("location: " + ipLocation + "ip.txt");
        if (!ipFile.exists()) {
            return;
        }
        try {
            FileReader reader = new FileReader(ipFile);
            BufferedReader br = new BufferedReader(reader);
            String temp = "";
            while ((temp = br.readLine()) != null) {
                if (temp.contains("ip")) {
                    deviceIP = temp.split("=")[1].trim();
                }
            }
            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            Logger.log("IP file does not exist.");
        } catch (IOException e) {
            Logger.log("Get ip address failed.");
        }
    }
}
