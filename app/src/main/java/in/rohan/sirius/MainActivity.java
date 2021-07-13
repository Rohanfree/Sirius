package in.rohan.sirius;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import in.rohan.sirius.databinding.ActivityMainBinding;
import in.rohan.sirius.ui.Student;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    public String getStarChar() {
        return starChar;
    }

    public void setStarChar(String starChar) {
        this.starChar = starChar;
    }

    public String starChar = "⭐";
    public String className="ClassA";
    public String date="";

    private List<Student> students = null;

    public List<Student> getStarStudents() {
        return starStudents;
    }

    public void setStarStudents(List<Student> starStudents) {
        this.starStudents = starStudents;
    }

    private List<Student> starStudents = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void populateStudents() {
        List<Student> students = new ArrayList<>();
        Student student = null;
        for (int i = 0; i < 47; i++) {
            student = new Student();
            student.setId(i + 1);
            student.setName("Student" + i);
            students.add(student);
        }
        this.students = students;

    }

    public void populateStudentsWithStar() {
        populateStudents();
        this.starStudents = new ArrayList<>();
        this.students.get(0).setStarCount(5);
        this.starStudents.add(students.get(0));
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public Student getStudentWithID(String id) {
        for (Student student : students) {
            if (String.valueOf(student.getId()).equals(id))
                return student;
        }
        return null;
    }
    public Student getStarStudentWithID(String id) {
        for (Student student : starStudents) {
            if (String.valueOf(student.getId()).equals(id))
                return student;
        }
        return null;
    }
    public void saveStarStudents(){

        StringBuilder students=new StringBuilder();
        for(Student student : getStarStudents()){
            StringBuilder star=new StringBuilder();
            for(int i=0;i<student.getStarCount();i++){
                star.append(getStarChar());
            }
            students.append(student.getName());
            students.append(":");
            students.append(star.toString());
            students.append("\n");
        }
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", students.toString());
        clipboard.setPrimaryClip(clip);

    }
}