package com.studio.jframework.network.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by Jason
 */
public class VolleyErrorHelper {
    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error The error in on error response
     * @return The relative error message
     */
    public static String getMessage(Object error) {
        if (error instanceof TimeoutError) {
            return "generic_server_down";
        } else if (isServerProblem(error)) {
            return handleServerError(error);
        } else if (isNetworkProblem(error)) {
            return "no_internet";
        }
        return "generic_error";
    }

    /**
     * Determines whether the error is related to network
     *
     * @param error The error in on error response
     * @return The relative error message
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error The error in on error response
     * @return The relative error message
     */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param error The error in on error response
     * @return The relative error message
     */
    private static String handleServerError(Object error) {
        VolleyError volleyError = (VolleyError) error;
        NetworkResponse response = volleyError.networkResponse;
        if (response != null) {
            switch (response.statusCode) {
                case 404:
                case 422:
                case 401:
                    return volleyError.getMessage();
                default:
                    return "generic_server_down";
            }
        }
        return "generic_error";
    }
}
