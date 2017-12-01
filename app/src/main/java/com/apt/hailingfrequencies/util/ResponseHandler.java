package com.apt.hailingfrequencies.util;

import android.util.Log;

// This class is passed to
// getTokenAndPerformHTTPRequest(final String URL, final String VERB, final ResponseHandler HANDLER)
// and the accept function is abstract so it can be re-written each time an HTTP request is made
// allowing custom functionality for each API endpoint.
public abstract class ResponseHandler {
    //
    public abstract void accept(String res);
}
