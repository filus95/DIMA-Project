package com.easylib.server.Database;

import com.easylib.server.Database.AnswerClasses.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class PasswordManager {

    /**
     * A utility class to hash passwords and check passwords vs hashed values. It uses a combination of hashing and unique
     * salt. The algorithm used is PBKDF2WithHmacSHA1 which, although not the best for hashing password (vs. bcrypt) is
     * still considered robust and <a href="https://security.stackexchange.com/a/6415/12614"> recommended by NIST </a>.
     * The hashed value has 256 bits.
     */

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private Connection conn;
    private DatabaseManager dbms;

    public PasswordManager(Connection conn, DatabaseManager databaseManager) {
        this.conn = conn;
        this.dbms = databaseManager;
    }


    byte[] generatePassword(String psw, byte[] salt){
        return hash(psw.toCharArray(), salt);
    }

    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a 16 bytes random salt
     */
    byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a salted and hashed password using the provided hash.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password the password to be hashed
     * @param salt     a 16 bytes salt, ideally obtained with the getNextSalt method
     *
     * @return the hashed password with a pinch of salt
     */
    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Returns true if the given password and salt match the hashed value, false otherwise.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @return true if the given password and salt match the hashed value, false otherwise
     */
    boolean isExpectedPassword(User user) throws SQLException {

        String sql = "SELECT username, salt, hashed_psd FROM propietary_db.users WHERE username = ?";

        PreparedStatement st = this.conn.prepareStatement(sql);
        st.setString(1, user.getUsername());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            byte[] hash_pass = rs.getBytes("hashed_psd");
            byte[] salt = rs.getBytes("salt");

            byte[] pwdHash = hash(user.getPlainPassword().toCharArray(), salt);
            Arrays.fill(user.getPlainPassword().toCharArray(), Character.MIN_VALUE);

            //check if the stored hashed password is the same of that one inserted
            for (int i = 0; i < pwdHash.length; i++) {
                if (pwdHash[i] != hash_pass[i])
                    return false;
            }
        } else return false;

        return true;
    }

    boolean changeForgottenPassword(String username, String email ){
        MailClass mail = new MailClass();
        String newPassword = generateRandomPassword(16);
        byte[] salt = getNextSalt();
        byte[] newHashPass = generatePassword( newPassword, salt);

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("salt", salt);
        map.put("email", email);
        map.put("hashed_pd", newHashPass);

        String schemaName = "propietary_db";
        String tableName = "users";
        dbms.insertStatement(map, tableName, schemaName);
        mail.sendMail(email, newPassword);
        return true;
    }
    /**
     * Generates a random password of a given length, using letters and digits.
     *
     * @param length the length of the password
     *
     * @return a random password
     */
    private static String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int c = RANDOM.nextInt(62);
            if (c <= 9) {
                sb.append(String.valueOf(c));
            } else if (c < 36) {
                sb.append((char) ('a' + c - 10));
            } else {
                sb.append((char) ('A' + c - 36));
            }
        }
        return sb.toString();
    }

}
