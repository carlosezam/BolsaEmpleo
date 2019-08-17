package com.ittap.bolsadeempleo;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ittap.bolsadeempleo.api.BaseArrayAdapter;
import com.ittap.bolsadeempleo.api.BaseModel;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;
import com.ittap.bolsadeempleo.curriculum.Competencia;
import com.ittap.bolsadeempleo.curriculum.CompetenciaActivity;
import com.ittap.bolsadeempleo.curriculum.Escolar;
import com.ittap.bolsadeempleo.curriculum.EscolarActivity;
import com.ittap.bolsadeempleo.curriculum.Lenguaje;
import com.ittap.bolsadeempleo.curriculum.LenguajeActivity;
import com.ittap.bolsadeempleo.curriculum.Trabajo;
import com.ittap.bolsadeempleo.curriculum.TrabajoActivity;
import com.ittap.bolsadeempleo.cursos.ListaCursosActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseListActivity extends AppCompatActivity {

    public final static int MODEL_COMPETENCIAS = 0;
    public final static int MODEL_LENGUAJES = 1;
    public final static int MODEL_TRABAJOS = 2;
    public final static int MODEL_ESCOLARES = 3;

    public final static String MODELS[] = {"competencias","lenguajes","trabajos","escolares"};
    public final static Class FORM_ACTIVITY[] = {CompetenciaActivity.class, LenguajeActivity.class, TrabajoActivity.class, EscolarActivity.class};
    public ArrayList<BaseModel> arrayList;
    public BaseArrayAdapter arrayAdapter;
    public ListView listView;
    public Usuario usuario;
    public int index_model;
    Class formularioActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);

        Intent i = getIntent();
        index_model = i.getIntExtra("model", 0);
        setTitle( MODELS[ index_model] );

        usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario();


        initListView( index_model );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listas, menu);
        //Drawable drawable = menu.getItem( 0 ).getIcon();
        //drawable.mutate();

        //drawable.setColorFilter( , PorterDuff.Mode.SRC_IN);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.action_lista_nuevo )
        {
            Intent i = new Intent(BaseListActivity.this, FORM_ACTIVITY[ index_model ]);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle("acciones");

        menu.setHeaderTitle( arrayAdapter.getItem(((AdapterView.AdapterContextMenuInfo) menuInfo).position).toString() );

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        read_items( index_model );
    }

    protected void visualizar(long id )
    {
        Intent i = new Intent(BaseListActivity.this, FORM_ACTIVITY[ index_model ]);
        i.putExtra("action","read");
        i.putExtra("id", id);
        startActivityForResult( i, 2);
    }

    protected void crear( long id )
    {
        Intent i = new Intent(BaseListActivity.this, FORM_ACTIVITY[ index_model ]);
        i.putExtra("action","create");
        startActivityForResult( i, 2);
    }

    protected void editar( long id )
    {
        Intent i = new Intent(BaseListActivity.this, FORM_ACTIVITY[ index_model ]);
        i.putExtra("action","update");
        i.putExtra("id", id);
        startActivityForResult( i, 2);
    }

    protected void borrar(final long id )
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRICULA_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        read_items( index_model );
                        show_toast("Registro eliminado");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        read_items( index_model );
                        show_toast("Error al intentar eliminar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id_usuario", String.valueOf(usuario.id));
                hashMap.put("tabla", MODELS[index_model] );
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }

    protected void initListView( int model )
    {

        arrayList = new ArrayList<BaseModel>();
        arrayAdapter = new BaseArrayAdapter( BaseListActivity.this, android.R.layout.simple_list_item_1, arrayList );
        listView = (ListView) findViewById(R.id.baseListView);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu( listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visualizar( id );
            }
        });

        read_items( model );
    }


    private void show_toast(String message )
    {
        Toast.makeText(BaseListActivity.this, message, Toast.LENGTH_LONG );
    }

    public void read_items(final int tabla )
    {
        arrayList.clear();
        arrayAdapter.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRICULA_READ,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONArray jsonArray = new JSONArray( response );
                            BaseModel model;

                            for (int i = 0; i < jsonArray.length(); ++i )
                            {

                                if( tabla == MODEL_COMPETENCIAS )
                                {
                                    model = new Competencia();
                                } else if( tabla == MODEL_LENGUAJES )
                                {
                                    model = new Lenguaje();
                                } else if( tabla == MODEL_ESCOLARES )
                                {
                                    model = new Escolar();
                                } else if( tabla == MODEL_TRABAJOS )
                                {
                                    model = new Trabajo();
                                }
                                else
                                {
                                    throw new RuntimeException("tabla no reconocida");
                                }

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
                hashMap.put("id_usuario", String.valueOf(usuario.id));
                hashMap.put("tabla", MODELS[tabla]);
                return hashMap;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }


}
