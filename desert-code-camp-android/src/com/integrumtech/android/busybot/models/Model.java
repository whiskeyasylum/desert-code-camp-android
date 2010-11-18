package com.integrumtech.android.busybot.models;

import android.util.Log;
import com.desertcodecamp.android.DesertCodeCampApplication;
import com.integrumtech.android.busybot.connection.connection.Request;
import com.integrumtech.android.busybot.connection.connection.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Model implements Serializable {
    private static final long serialVersionUID = 2664088878505586275L;

    private static ArrayList<Model> cachedModels;



    protected static String GET_ALL = "";
    protected String GET_INSTANCE = null;
    protected String GET_ALL_BY_PARENT_ID = null;


    protected JSONObject jsonObject;

    /**
     * Creates an instance of JsonObject from the JSON string passed in.
     *
     * @param json a string of well formatted JSON
     * @throws JSONException if the JSON response is malformed or the model attempts to access values that don't exist
     */
    public Model(String json) throws JSONException {
        this.jsonObject = new JSONObject(json);
    }

    public static Model build(String json) throws JSONException {
        return new Model(json);
    }

    public static <T extends Model> ArrayList<T> getAll() throws UnsupportedEncodingException, JSONException {
        return getAll(GET_ALL, Model.class);
    }

    /**
     * Retrieves all instances of the specified model, if the server response
     * status code is not in the 200's or 300's, it will return an empty ArrayList
     *
     * @param path     path to retrieve all instances of this model
     * @param subclass class of the model
     * @return ArrayList of models
     * @throws java.io.UnsupportedEncodingException
     *                                thrown if one of the key/value pairs in the request are improper
     * @throws org.json.JSONException if the JSON response is malformed or the model attempts to access values that don't exist
     */
    @SuppressWarnings("unchecked")
    protected static <T extends Model> ArrayList<T> getAll(String path, Class<?> subclass) throws JSONException, UnsupportedEncodingException {
        if (Model.getCachedModels() != null)
            return (ArrayList<T>) Model.getCachedModels();

        Request request = new Request(path);
        Response response = request.execute();

        if (200 <= response.getStatusCode() && response.getStatusCode() < 400) {
            String json = response.getBody();
            JSONArray jsonArray = new JSONArray(json);

            ArrayList<T> models = new ArrayList<T>(jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Constructor<?> constructor = subclass.getConstructor(String.class);

                    T model = (T) constructor.newInstance(jsonArray.getString(i));
                    models.add(model);
                } catch (SecurityException e) {
                    Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
                } catch (NoSuchMethodException e) {
                    Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
                } catch (IllegalArgumentException e) {
                    Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
                } catch (InstantiationException e) {
                    Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
                } catch (IllegalAccessException e) {
                    Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
                } catch (InvocationTargetException e) {
                    Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
                }
            }
            setCachedModels(models);

            return models;
        }

        return new ArrayList<T>();
    }

    /**
     * Retrieves the cached models if they exist, null otherwise. Must be
     * overridden by subclass to work
     *
     * @return the cachedModels
     */
    protected static <T extends Model> ArrayList<T> getCachedModels() {
        return null;
    }

    /**
     * Sets the cached models. Must be overridden by the subclass to work
     *
     * @param models to cache
     */
    protected static void setCachedModels(ArrayList<?> models) {

    }
}
 