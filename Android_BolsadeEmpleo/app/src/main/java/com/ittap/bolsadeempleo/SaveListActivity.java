package com.ittap.bolsadeempleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ittap.bolsadeempleo.R;
import com.ittap.bolsadeempleo.api.BaseArrayAdapter;
import com.ittap.bolsadeempleo.api.BaseModel;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveListActivity extends AppCompatActivity {

    public ArrayList<BaseModel> arrayList;
    public BaseArrayAdapter arrayAdapter;
    public ListView listView;
    public EditText editText;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_list);
        usuario = SharedPrefManager.getInstance(this).getUsuario();
        init_listview();
    }

    protected  void init_listview()
    {
        arrayList = new ArrayList<BaseModel>();
        arrayAdapter = new BaseArrayAdapter( SaveListActivity.this, android.R.layout.simple_list_item_1, arrayList );
        listView = (ListView) findViewById(R.id.listViewSaves);
        registerForContextMenu( listView );
        listView.setAdapter(arrayAdapter);
        registerForContextMenu( listView);
        read_saves( null );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visualizar( position );
            }
        });

    }

    protected void visualizar( int id )
    {

        Intent i = new Intent(SaveListActivity.this, DetailsEmpleoActivity.class);
        i.putExtra("id",(long) ((Save)arrayList.get(  id )).id_empleo );
        startActivityForResult( i, 2);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle("acciones");

        menu.setHeaderTitle( arrayAdapter.getItem(((AdapterView.AdapterContextMenuInfo) menuInfo).position).toString() );

        menu.add( Menu.NONE, 1, Menu.NONE, "Eliminar");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent i;

        switch ( item.getItemId() )
        {
            case 1: eliminar( info.id);
                return true;

        }

        return super.onContextItemSelected(item);
    }

    protected void eliminar(final long id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRICULA_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        read_saves( null );
                        show_toast("Registro eliminado");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        read_saves( null );
                        show_toast("Error al intentar eliminar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id_usuario", String.valueOf(usuario.id));
                hashMap.put("tabla", "saves");
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }

    protected void show_toast( String message )
    {
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
    }

    public void read_saves(final String key_search )
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRICULA_READ,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            arrayList.clear();
                            arrayAdapter.clear();

                            JSONArray jsonArray = new JSONArray( response );
                            BaseModel model;

                            for (int i = 0; i < jsonArray.length(); ++i )
                            {
                                model = new Save();
                                model.setFromJson( jsonArray.getJSONObject(i));
                                arrayList.add( model );
                            }

                            arrayAdapter.notifyDataSetChanged();

                        } catch ( JSONException e )  { e.printStackTrace(); }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("tabla", "saves");
                hashMap.put("id_usuario", String.valueOf(usuario.id));
                return hashMap;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }
}
