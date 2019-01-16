package com.easylib.server;

import AnswerClasses.*;
import com.easylib.network.socket.Constants;
import com.easylib.network.socket.ServerDataHandler;
import com.easylib.server.API.GoogleBooks;
import com.easylib.server.Database.DatabaseConnection;
import com.easylib.server.Database.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException {

/**
         Connection conn = new DatabaseConnection().startConnection();
         String sql = "DELETE FROM library_1.books";

         try {
         PreparedStatement statement = conn.prepareStatement(sql);
         statement.executeUpdate();
         } catch (SQLException e) {
         e.printStackTrace();
         }
         **/

        // todo: always use SimpleDateFormat for dates since windows sucks...
        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.startConnection();

        DatabaseManager dbms = new DatabaseManager();
        // todo: always use SimpleDateFormat for dates since windows sucks...

        int id_lib = 1;
        int limit = 3;
        String schema_name = dbms.getSchemaNameLib(id_lib);
        ArrayList<News> res = dbms.getNews(schema_name, limit);

        for (News x: res){
            System.out.print(x.getTitle()+"\n");
        }

        //        Reservation reservation = new Reservation();
        //        reservation.setUser_id(4);
        //        reservation.setStart_res_date(new Date(2,2,2));
        //        reservation.setQuantity(1);
        //        reservation.setReservation_date(new Date());
        //        String schema_name = dbms.getSchemaNameLib(reservation.getIdLib());

        //        res = dbms.insertNewReservation(reservation, schema_name);
        //        GoogleBooks googleBooks = new GoogleBooks();
        //
        //        String query = "La cattedrale del mare";
        //        query = correctQueryString(query);
        //        googleBooks.apiCallAndFillDB(query, "books", "library_2");

        //        ArrayList<News> res = dbms.getAllNews("library_1", 3);
        //        printQueryResult(res);
        //        LibraryDescriptor libraryDescriptor = new LibraryDescriptor();
        //        libraryDescriptor.setLib_name("Biblioteca Lambrate");
        //        libraryDescriptor.setSchema_name("library_2");
        //        libraryDescriptor.setImage_link("qqqq");
        //        libraryDescriptor.setTelephone_number("021994492");
        //        libraryDescriptor.setAddress("Lambrate, 6");
        //        libraryDescriptor.setEmail("lambrate@poli.it");
        //        libraryDescriptor.setDescription("siamo piu forti");
        //        dbms.insertNewLibrary(libraryDescriptor, "propietary_db");
        //        GoogleBooks gb = new GoogleBooks();
        //        gb.apiCallAndFillDB(query.replaceAll("\\s", "+"), "books",
        //                "library_1");

        // TODO: ADD INTO booksreservations
//        java.util.Date dt = new java.util.Date();
//        java.text.SimpleDateFormat reservation_date =
//                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date start_res = stringToDate("2018-12-3");
//        //
//        Date end_res = stringToDate("2018-12-30");
//        LocalDateTime now = LocalDateTime.now();
//        //        System.out.print(currentTime);
//        //
//        Event event = new Event();
//        event.setIdLib(1);
//        LocalDate yourDate = LocalDate.of(2018, 12, 27);
//        LocalDate yourDate1 = LocalDate.of(2019, 1, 27);
//
//        LocalDateTime dateTime = yourDate1.atTime(LocalTime.of(17, 30));
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        //        event.setDate(dtf.format(dateTime));
//
//
//        Rating rating = new Rating();
//        rating.setBook_identifier("1408827727");
//        rating.setIdLib(1);
//        rating.setRating(1);
//        rating.setUser_id(1);
//        String schema_name = dbms.getSchemaNameLib(rating.getIdLib());
//        Boolean res = dbms.insertRating(rating, schema_name);
//
//        System.out.print(res);

//        int user_id = 1;
//        ArrayList<Integer> pref_lib_ids = dbms.getUserPreferences(user_id);
//
//        System.out.print("\nLibrary name:\n");
//        for ( Integer id : pref_lib_ids) {
//            LibraryDescriptor ld = dbms.getLibraryInfo(id);
//            LibraryContent lc = dbms.getLibraryContent(ld.getSchema_name());
//            System.out.print(ld.getLib_name() + "\n");
//
//
//            System.out.print("\nBooks:\n");
//            for (Book b : lc.getBooks())
//            System.out.print(b.getTitle() + "\n");
//
//            System.out.print("\n" + "Events:\n");
//            for (Event e : lc.getEvents())
//                System.out.print(e.getId()+"\n");
//
//            System.out.print("\n" + "News:\n");
//            for (News n : lc.getNews())
//                System.out.print(n.getTitle() + "\n");

//        }

        // TODO: DELETE FROM booksreservations
    // ciao
    //        boolean res = dbms.deleteStatementReservations("1909430188", "15",
    //                "library_1.booksreservations");
    //        System.out.print(res);
    }

    private static LibraryDescriptor getLibraryDescriptor(int id_lib, DatabaseManager dbms){
        LibraryDescriptor ld = dbms.getLibraryInfo(id_lib);
        LibraryContent lc = dbms.getLibraryContent(ld.getSchema_name());
        ld.setLibraryContent(lc);
        return ld;
    }

    private static Date stringToDate(String s) {

        Date result = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            result = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void printQueryResult(ArrayList<News> res){
        if (res.size()==0)
            System.out.print("Sorry, no book responding to this title found.");
            for (News to_ret: res){
                if ( to_ret == null)
                    continue;
                System.out.print(to_ret.getTitle()+" ");
            }
            System.out.print("\n");
        }


    /**
     * Correct the query format to make it suitable for an Google API call
     *
     * @param query string to correct
     * @return corrected string
     */
    public static String correctQueryString(String query){

        return query.replaceAll("\\s", "+");
    }
}

