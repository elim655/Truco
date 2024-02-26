package Truco.com.model;
/**
 * Represents a user's login information.
 * This class is used to store and retrieve the login details like ID, username, and password.
 */
public class Login {


    private int id;
    private String username;
    private String password;


    /**
     * Default constructor for Login class.
     */
    public Login() {
        // Default constructor
    }

    /**
     * Retrieves the ID of the user.
     *
     * @return the ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the ID to be set for the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to be set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Retrieves the password of the user.
     *
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password of the user.
     *
     * @param password the password to be set for the user.
     */

    public void setPassword(String password) {
        this.password = password;
    }


}
