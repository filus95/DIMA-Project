package com.easylib.dima.easylib.ConnectionLayer;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class SendingThread implements Runnable{
    private ObjectOutputStream out;
    private String kindMessage;
    private Object content;

    SendingThread(ObjectOutputStream out, String kindMessage, Object content) {
        this.out = out;
        this.kindMessage = kindMessage;
        this.content = content;
    }

    public void run() {
        if (content == null)
            sendMessage(kindMessage);
        else
            sendMessageWithContent(kindMessage, content);

    }

    private void sendMessage(String kindMessage) {
        try {
            out.writeObject(kindMessage);
            out.flush();
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param kindOfMessage Message from Constants class
     * @param content Object needed to the Server to perform operations
     *
     *                MESSAGE TO THE SERVER : OBJECT TO FILL
     *                - Login: user
     *                - Password forgot: user
     *                - registration: user
     *                - Library conncetion info: id library (INTEGER not int)( goes from 1 to 2 in the current state )
     *                - Insert rating: Rating
     *                - getUserPreferences: User id (int)
     *                - insert preferences: User preference
     *                - getWaitingListForAbook: Book object filled with only book identifier (ISBN) and the id of the library in which it is located
     *                - getNews: idLib (int)
     *                - getEvents: idLib (int)
     *                - insertEventPartecipant: Partecipant filled also with idLib
     *                - getUserReservations: Reservation filled with idLib and userId
     *                - insertReservation: Reservation filled with idLib and userId
     *                - getLibraryInfo: idLib (int)
     *                - getAllLibraries: only the message in Constants, no objects needed
     *                - bookQuery: Query,  filled also with idLib
     *                - getAllBooks: idLib (int)
     *
     */
    private void sendMessageWithContent(String kindOfMessage, Object content){
        try {
            sendMessage(kindOfMessage);

            out.writeObject(content);
            out.flush();
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
