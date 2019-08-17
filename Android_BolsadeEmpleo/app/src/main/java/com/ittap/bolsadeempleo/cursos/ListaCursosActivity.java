package com.ittap.bolsadeempleo.cursos;

import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.ittap.bolsadeempleo.LoginActivity;
import com.ittap.bolsadeempleo.R;
import com.ittap.bolsadeempleo.VolleySingleton;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaCursosActivity extends AppCompatActivity  {


    private static final String TAG = ListaCursosActivity.class.getSimpleName();

    public ArrayList<Curso> cursoArrayList = new ArrayList<>();
    public CursosArrayAdapter cursosArrayAdapter;
    private ListView listView;

    private int id_usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cursos);
        setTitle("Cursos");

        id_usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario().id;

        listView = (ListView) findViewById(android.R.id.list);
        cursosArrayAdapter = new CursosArrayAdapter( ListaCursosActivity.this, cursoArrayList );
        listView.setAdapter( cursosArrayAdapter );

        //read_cursos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visualizar( id );
            }
        });


        registerForContextMenu( listView );


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle("acciones");

        menu.setHeaderTitle( cursosArrayAdapter.getItem(((AdapterView.AdapterContextMenuInfo) menuInfo).position).nombre );

        menu.add( Menu.NONE, 1, Menu.NONE, "Modificar");
        menu.add( Menu.NONE, 2, Menu.NONE, "Eliminar");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent i;

        switch ( item.getItemId() )
        {
            case 1: editar( info.id);
                return true;
            case 2: borrar( info.id );
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void editar(long id )
    {

        Intent i = new Intent(ListaCursosActivity.this, FormularioCursosActivity.class);
        i.putExtra("action","update");
        i.putExtra("id", id);
        startActivityForResult( i, 1);
    }

    private void borrar(final long id )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de eliminar este curso?");
        builder.setCancelable(false);

        builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete_curso( id );

            }
        });

        builder.setNegativeButton("no", null);
        builder.show();
    }

    private void visualizar(long id )
    {
        Intent i = new Intent(ListaCursosActivity.this, FormularioCursosActivity.class);
        i.putExtra("action","read");
        i.putExtra("id", id);
        startActivityForResult( i, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.action_lista_nuevo )
        {
            Intent i = new Intent(ListaCursosActivity.this, FormularioCursosActivity.class);
            i.putExtra("action","create");
            startActivityForResult(i,0);

        }
        if( id == R.id.action_lista_back )
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        read_cursos();
    }

    private void show_toast(String message )
    {
        Toast.makeText(ListaCursosActivity.this, message, Toast.LENGTH_LONG );
    }

    public void delete_curso(final long id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRICULA_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        read_cursos();
                        show_toast("Registro eliminado");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        read_cursos();
                        show_toast("Error al intentar eliminar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id_usuario", String.valueOf(id_usuario));
                hashMap.put("tabla", "cursos");
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }
    public void read_cursos()
    {
        cursoArrayList.clear();
        cursosArrayAdapter.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRICULA_READ,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONArray jsonArray = new JSONArray( response );

                            for (int i = 0; i < jsonArray.length(); ++i )  cursoArrayList.add( new Curso( jsonArray.getJSONObject(i) ) );

                            cursosArrayAdapter.notifyDataSetChanged();

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
                hashMap.put("id_usuario", String.valueOf(id_usuario));
                hashMap.put("tabla", "cursos");
                return hashMap;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }


}


