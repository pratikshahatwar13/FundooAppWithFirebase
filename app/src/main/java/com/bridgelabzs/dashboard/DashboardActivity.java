package com.bridgelabzs.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_note_page.AddNoteActivity;
import com.bridgelabzs.add_note_page.adapter.NotesAdapter;
import com.bridgelabzs.add_note_page.model.Note;
import com.bridgelabzs.add_note_page.view_model.NoteViewModel;
import com.bridgelabzs.fundooapp.R;
import com.bridgelabzs.login.LoginActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import androidx.fragment.app.Fragment;


public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private AppBarConfiguration mAppBarConfiguration;
    public static final int PICK_REQUEST_CODE = 10;
    Uri imageUri;
    private ImageView profileImage;
    private Button logoutButton;
    private List<Note> noteList = new ArrayList<>();
    private List<Note> listNote;
    NoteViewModel noteViewModel;
    NotesAdapter notesAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    private int offset = 0;
    private int LIMIT = 10;
    List<Note> localNotes;
    List<Note> localNotes1;
    Note noteToDelete;
    ProgressBar progressBar;
    private CheckBox listGridView;
    private EditText serchNotes;
    private FirebaseAuth.AuthStateListener mAuthStateListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        setSupportActionBar(toolbar);

//        noteViewModel = new NoteViewModel(this);
        noteViewModel = new NoteViewModel();
        manager = new LinearLayoutManager(this);
        progressBar = findViewById(R.id.progress_bar);

        //prepareRecyclerView();

        setOnClickOnSearchNotes();
        setOnClickListenerToProfile();
        onClickLogout();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNoteIntent = new Intent(DashboardActivity.this, AddNoteActivity.class);
                startActivity(addNoteIntent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getCheckedItem();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_notes, R.id.nav_reminders, R.id.nav_add_label,
                R.id.nav_archive, R.id.nav_trash, R.id.nav_setting, R.id.nav_help)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        setOnClickOnListGridView();
        super.onStart();
    }


    private void setOnClickListenerToProfile() {
        profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
//                int permissionCheck = ContextCompat.checkSelfPermission(DashboardActivity.this,
//                        Manifest.permission.READ_EXTERNAL_STORAGE);
                int writePermissionCheck = ContextCompat.checkSelfPermission(DashboardActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(writePermissionCheck !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(DashboardActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            }, 10012);
                }
                else{
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(galleryIntent, PICK_REQUEST_CODE);
                }

            }
        });
    }

    private void setOnClickOnListGridView(){
        listGridView = findViewById(R.id.listview_gridview);
        listGridView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isListView) {
                Intent intent = new Intent();
                intent.setAction("com.bridgelabz.TEST");
                sendBroadcast(intent);

//                Fragment fragment = getFragmentManager().findFragmentById(R.id.nav_notes);
//                if(fragment != null && fragment.isAdded()) {
//                    NotesFragment notesFragment = (NotesFragment) fragment;
//                    notesFragment.setListGridView();
//                }
            }
        });
    }

    private void setOnClickOnSearchNotes(){
        serchNotes = findViewById(R.id.search_notes);
        serchNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Intent intent = new Intent();
                intent.putExtra("search",s.toString());
                intent.setAction("com.bridgelabz.search");
                sendBroadcast(intent);
            }
        });

    }

    public void filter(String text){
        listNote = noteViewModel.getAllNoteData();
        List<Note> filteredList = new ArrayList<>();
        for(Note note : listNote){
            if(note.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(note);
            }
        }
        notesAdapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_REQUEST_CODE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                profileImage.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            Glide.with(this).load(imageUri).circleCrop().into(this.profileImage);
        }
    }


    private  void onClickLogout(){
        logoutButton = findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
