package com.apt.hailingfrequencies.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.function.Consumer;

import cz.msebera.android.httpclient.Header;

public class Communicator {
    public static final String PUT = "put";
    public static final String GET = "get";
    public static final String POST = "post";

    public void getTokenAndPerformHTTPRequest(final String URL, final RequestParams PARAMS, final String VERB, final ResponseHandler HANDLER) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Task task = user.getIdToken(true);

        task.addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    String idToken = task.getResult().getToken();
                    Log.v("MY TOKEN", idToken);
                    // Send token to your backend via HTTPS
                    HTTPRequest(idToken, URL, PARAMS, VERB, HANDLER);
                } else {
                    // Handle error -> task.getException();
                    Exception exception = task.getException();
                }
            }
        });
    }

    // Using Android Async Http Client
    // https://github.com/codepath/android_guides/wiki/Using-Android-Async-Http-Client

    // Pass an abstract class ResponseHandler
    // This allows for custom handling of each response
    // for each unique API Endpoint.
    public void HTTPRequest(String token, String url, RequestParams params, String verb, final ResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + token);

        // GET
        if (verb == GET) {
            client.get(url, params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            // called when response HTTP status is "200 OK"
                            handler.accept(res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Log.v("FAILURE", res);
                        }
                    }
            );
        }

        // PUT
        else if (verb == PUT) {
            client.put(url, params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            // called when response HTTP status is "200 OK"
                            handler.accept(res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Log.v("FAILURE", res);
                        }
                    }
            );
        }

        // POST
        else if (verb == POST) {
            client.post(url, params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            // called when response HTTP status is "200 OK"
                            handler.accept(res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Log.v("FAILURE", res);
                        }
                    }
            );
        }

        else {
            Log.e("error", "verb isn't 'put', 'post', or 'get'");
        }


    }

}
