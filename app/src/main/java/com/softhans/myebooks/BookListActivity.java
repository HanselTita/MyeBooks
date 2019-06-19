package com.softhans.myebooks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class BookListActivity extends AppCompatActivity {


    private ProgressBar mLoadingProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mLoadingProgress = (ProgressBar)findViewById(R.id.pb_loading);


        /**
         * The most common method used in Android Studio is try-catch. As well as in java programming,
         * that consists of making the node "try" to include the routine that may fall into an error.
         * Then, with the node "catch" you tell the application what to do when catching a specific exception.
         */

        try {
            // build the URL for a book called "cooking"
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new  BooksQueryTask().execute(bookUrl);

        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    /**Extend ASyncTask.. which helps to not cancel any idea from the user that your apps is delaying
     * This equally prevents the android system from asking the user to stop your apps or not
     */
    public class BooksQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try{
                result = ApiUtil.getJason(searchUrl);
            }
            catch (IOException e) {
                Log. e("Error", e.getMessage());
            }
            return result;
        }



        //Implement an onPostExecuteMethod, which will be called when the doInBackground has completed


        @Override
        protected void onPostExecute(String result) {

            TextView tvResult = (TextView) findViewById(R.id.tvResponse);
            TextView tvError = (TextView)findViewById(R.id.tv_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
               tvResult.setVisibility(View.INVISIBLE);
               tvError.setVisibility(View.VISIBLE);
            }else
            {
                tvResult.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            tvResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }



}
