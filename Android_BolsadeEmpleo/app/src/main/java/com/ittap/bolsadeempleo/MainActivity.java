package com.ittap.bolsadeempleo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.ittap.bolsadeempleo.curriculum.Escolar;
import com.ittap.bolsadeempleo.curriculum.Lenguaje;
import com.ittap.bolsadeempleo.curriculum.Trabajo;
import com.ittap.bolsadeempleo.cursos.ListaCursosActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<BaseModel> arrayList;
    public BaseArrayAdapter arrayAdapter;
    public ListView listView;
    public EditText editText;
    Usuario usuario;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Buscar Empleo");

        if(!SharedPrefManager.getInstance(MainActivity.this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        usuario = SharedPrefManager.getInstance(this).getUsuario();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editText = (EditText) findViewById(R.id.key_search_empleo);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                key = ((EditText) v).getText().toString();
                //Log.w("tag---", "evento on key: " + key );

                read_empleos( key.isEmpty() ? null : key );
                return false;
            }
        });

        init_listview();
    }


    protected  void init_listview()
    {
        arrayList = new ArrayList<BaseModel>();
        arrayAdapter = new BaseArrayAdapter( MainActivity.this, android.R.layout.simple_list_item_1, arrayList );
        listView = (ListView) findViewById(R.id.listViewEmpleos);
        registerForContextMenu( listView );
        listView.setAdapter(arrayAdapter);
        registerForContextMenu( listView);
        read_empleos( null );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visualizar( id );
            }
        });

    }

    protected void visualizar( long id )
    {
        Intent i = new Intent(MainActivity.this, DetailsEmpleoActivity.class);
        i.putExtra("id", id);
        startActivityForResult( i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        key = (editText).getText().toString();
        //Log.w("tag---", "evento on key: " + key );

        read_empleos( key.isEmpty() ? null : key );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle("acciones");

        menu.setHeaderTitle( arrayAdapter.getItem(((AdapterView.AdapterContextMenuInfo) menuInfo).position).toString() );

        menu.add( Menu.NONE, 1, Menu.NONE, "Guardar");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent i;

        switch ( item.getItemId() )
        {
            case 1: guardar( info.id);
                return true;

        }

        return super.onContextItemSelected(item);
    }



    protected void show_toast(String message )
    {
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
    }
    public void guardar(final long id )
    {




        StringRequest request = new StringRequest(Request.Method.POST, URLs.CURRICULA_CREATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        show_toast("Empleo guardado");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                String err = new String( error.networkResponse.data );
                Log.e("save: ", err + "..." + id);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("tabla", "saves");
                hashMap.put("id_usuario", String.valueOf(usuario.id));
                hashMap.put("id_empleo", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( request );
    }


    public void read_empleos(final String key_search )
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URI_EMPLEOS,

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
                                model = new Empleo();
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
                hashMap.put("tabla", "empleos");
                hashMap.put("id_usuario", String.valueOf(usuario.id));
                if(key_search!=null)hashMap.put("key_search", key_search);
                return hashMap;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout)
        {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
        else if (id == R.id.nav_personal)
        {
            startActivity(new Intent(MainActivity.this, PersonalInfoActivity.class));
        }
        else if (id == R.id.nav_cursos )
        {
            startActivity(new Intent(MainActivity.this, ListaCursosActivity.class));
        }
        else if (id == R.id.nav_competencias)
        {
            Intent i = new Intent( MainActivity.this, BaseListActivity.class);
            i.putExtra("model", BaseListActivity.MODEL_COMPETENCIAS );
            startActivity( i );
        }
        else if (id == R.id.nav_educacion)
        {
            Intent i = new Intent( MainActivity.this, BaseListActivity.class);
            i.putExtra("model", BaseListActivity.MODEL_ESCOLARES );
            startActivity( i );
        }
        else if (id == R.id.nav_idiomas)
        {
            Intent i = new Intent( MainActivity.this, BaseListActivity.class);
            i.putExtra("model", BaseListActivity.MODEL_LENGUAJES );
            startActivity( i );
        }
        else if (id == R.id.nav_laboral)
        {
            Intent i = new Intent( MainActivity.this, BaseListActivity.class);
            i.putExtra("model", BaseListActivity.MODEL_TRABAJOS );
            startActivity( i );
        } else if ( id==R.id.nav_pdf)
        {

            Uri uri = Uri.parse(URLs.URI_PDF + "?id_usuario="+String.valueOf(usuario.id));
            Intent i = new Intent( Intent.ACTION_VIEW, uri);
            startActivity(i);
        } else if ( id == R.id.nav_saves )
        {
            Intent i = new Intent( MainActivity.this, SaveListActivity.class);
            startActivityForResult( i,0 );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
