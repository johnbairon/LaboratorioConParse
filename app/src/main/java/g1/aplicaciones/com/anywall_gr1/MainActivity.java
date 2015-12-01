package g1.aplicaciones.com.anywall_gr1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    //Máximo de resultados por carga
    private static final int MAX_POST_SEARCH_RESULTS=20;
    //Adapter para los query
    private ParseQueryAdapter <AnywallPost> postQueryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();

        String struser = currentUser.getUsername().toString();

        TextView txtUser = (TextView) findViewById(R.id.txtuser);
        txtUser.setText("You are logged in as " + struser);

        //configura query personalizado
        ParseQueryAdapter.QueryFactory<AnywallPost> factory= new ParseQueryAdapter.QueryFactory<AnywallPost>(){
            public ParseQuery<AnywallPost> create(){
                ParseQuery<AnywallPost> query=AnywallPost.getQuery();
                query.include("user");
                query.orderByDescending("creadedAt");
                query.setLimit(MAX_POST_SEARCH_RESULTS);
                return query;
            }
        };

        //configura el adapter
        postQueryAdapter=new ParseQueryAdapter<AnywallPost>(this,factory){
            @Override
            public View getItemView(AnywallPost post, View view, ViewGroup parent){
                if(view == null){
                    view= View.inflate(getContext(),R.layout.anywall_post_item,null);
                }
                TextView contentView =(TextView) view.findViewById(R.id.content_view);
                TextView usernameView = (TextView) view.findViewById(R.id.username_view);
                contentView.setText(post.getText());
                usernameView.setText(post.getUser().getUsername());
                return view;
            }
        };

       //Desactiva carga automaticamente cuando el adapter está unido a una vista
        postQueryAdapter.setAutoload(false);

        //pone el query en la vista
        ListView postsListView= (ListView)findViewById(R.id.posts_listview);
        postsListView.setAdapter(postQueryAdapter);

        //Accion botón post
        Button postButton=(Button) findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,PostActivity.class);
                startActivity(intent);
            }
        });
        //Acción botón logout
        Button logoutButton=(Button)findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent= new Intent(MainActivity.this,LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        //Actualiza vista.
        doListQuery();
    }

    //actualiza vista
    private void doListQuery(){
        postQueryAdapter.loadObjects();
    }
}
