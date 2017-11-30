package nyc.c4q.asynchronoustasks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public String userEntry;
    public EditText userEditText;
    public TextView primesdisplay;
    private int number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEditText = (EditText) findViewById(R.id.edit_text);
        primesdisplay = (TextView) findViewById(R.id.text_view);
    }

    public void onButtonClick(View v) {
        userEntry = userEditText.getText().toString();
        try {
            number = Integer.parseInt(userEntry);
        } catch (NumberFormatException e) {
            primesdisplay.setText("Invalid Number");
            return;
        }
        PrimeCalculator pc = new PrimeCalculator();
        pc.execute(number);
    }

    //step 1: create Async Task class. (Parameters are *input type(range max range), *progress type(integers calculated so far), *output type)
    //Step 2: implement do in background (takes a number and returns all prime numbers)
    //Step 3 : Implement *onProgressUpdate (Displays number of prime numbers calculated so far)
    //Step4: Implement *onPostExecute (Displays the total number of primes available)
    //Optional Step: onPreExecute (to run tasks before program)

    private class PrimeCalculator extends AsyncTask<Integer, Integer, Integer> {
        ArrayList<Integer> primes = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            primes.clear();
            primesdisplay.setText("Processing Prime Numbers");

        }

        @Override
        protected Integer doInBackground(Integer... integers) {//cant do ui work in background, set texts happens in 3 and 3

            for (int i = 1; i < number; i++) {
                if (isPrime(i) == -1) {
                    primes.add(i);
                }
                publishProgress(primes.size());
            }
            return primes.size();
        }


        @Override
        protected void onProgressUpdate(Integer... n) {
            primesdisplay.setText("Primes calculated so far: " + n[0]);
        }

        @Override
        protected void onPostExecute(Integer n) {
            primesdisplay.setText("Total primes: " + n);
        }
    }

    public int isPrime(int n) {
        if (n < 1) {
            return -1;
        }
        if (n == 1) {
            return -1;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return i;
            }
        }
        return -1;
    }
}
