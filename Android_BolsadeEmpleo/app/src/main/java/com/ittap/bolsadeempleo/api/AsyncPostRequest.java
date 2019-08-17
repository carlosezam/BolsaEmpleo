package com.ittap.bolsadeempleo.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;



public class AsyncPostRequest extends AsyncTask<Void, Void, HashMap<String, String> > {

    public final static int CREATE = 0;
    public final static int READ = 1;
    public final static int UPDATE = 2;
    public final static int DELETE = 3;
    public final static int VIEW = 4;

    public String acciones[] = {"create","read","update","delete","view"};

    Context context;
    String uri;
    HashMap<String,String> data;

    public int response_code;
    public String response_message;
    public String response_data;

    public AsyncPostRequest(Context context, int accion, HashMap<String,String> data)
    {
        uri = URLs.URI_CURRICULA + acciones[accion];
        this.data = data;
        this.context = context;
    }

    @Override
    protected HashMap<String, String> doInBackground(Void... params) {
        RequestHandler requestHandler = new RequestHandler();
        return requestHandler.sendPostRequest(uri, data);
    }

    protected void show_toast( String message )
    {
        Toast.makeText( context, message, Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void onPostExecute(HashMap<String, String> result) {
        super.onPostExecute(result);

        if( result == null ) { show_toast("Error desconocido"); return; }

        if(result.containsKey("exception")){ show_toast("Exception: " + result.get("exception")); return; }

        if(result.containsKey("response_code") ){
            response_code = Integer.parseInt(result.get("response_code"));
            //show_toast("Response code:" + response_code);
            Log.d("AsyncPostRequest", "Response code:" + response_code );
        }
        if(result.containsKey("response_message") ){
            response_message = result.get("response_message");
            //show_toast("Response code:" + response_code);
            Log.d("AsyncPostRequest","Response code:" + response_code);
        }

        if(!result.containsKey("response_data")){ show_toast("No se recibió ningun dato"); return; }

        response_data = result.get("response_data");
        Log.d("AsyncPostRequest", "Response data: " + response_data );
    }
}
