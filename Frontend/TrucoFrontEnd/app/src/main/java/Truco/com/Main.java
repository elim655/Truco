package Truco.com;

import Truco.com.api.ApiClientFactory;
import Truco.com.api.LoginApi;
import Truco.com.model.Login;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;

/**
 * Main Activity class for the Truco application.
 * Handles user login functionality including input validation,
 * interaction with the LoginApi, and navigation to other activities.
 */
public class Main extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewForgotPassword;
    TextView textViewSignUp;

    /**
     * Called when the activity is starting.
     * Initializes the UI components and sets up event listeners for user interactions.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI component initialization
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.loginBtn);
        textViewForgotPassword = findViewById(R.id.forgotPassword);
        textViewSignUp = findViewById(R.id.signUpText);

        // Set an OnClickListener to navigate to SignupActivity
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(Main.this, SignupActivity.class);
                startActivity(signUpIntent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if(validateInput(username, password)) {
                    // Create an instance of the Login model
                    Login loginDetails = new Login();
                    loginDetails.setUsername(username);
                    loginDetails.setPassword(password);

                    // Authenticate user using Retrofit
                    LoginApi apiService = ApiClientFactory.getLoginApi();
                    Call<ResponseBody> call = apiService.UserLogin(loginDetails);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.isSuccessful() && response.body() != null) {
                                    String responseBody = response.body().string();
                                    Log.d("Main", "Response from server: " + responseBody);

                                    if ("Successfully logged in".equalsIgnoreCase(responseBody.trim())) {
                                        Intent dashboardIntent = new Intent(Main.this, DashBoardActivity.class);
                                        dashboardIntent.putExtra("LOGGED_IN_USERNAME", username);
                                        startActivity(dashboardIntent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(Main.this, responseBody, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Main.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(Main.this, "Error parsing response.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(Main.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forgot password logic here
            }
        });
    }

    /**
     * Validates the input fields for username and password.
     *
     * @param username the username entered by the user.
     * @param password the password entered by the user.
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            return false;
        }
        return true;
    }

}
