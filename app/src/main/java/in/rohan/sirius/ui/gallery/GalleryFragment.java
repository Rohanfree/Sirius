package in.rohan.sirius.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import in.rohan.sirius.MainActivity;
import in.rohan.sirius.R;

import in.rohan.sirius.databinding.FragmentGalleryBinding;
import in.rohan.sirius.ui.CustomListView.MyListAdapter;
import in.rohan.sirius.ui.CustomListView.StarListAdapter;
import in.rohan.sirius.ui.Student;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private MainActivity mainActivity;
    private Button saveButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText studentIDEditText= root.findViewById(R.id.studentIDEdittext);
        TextView statusTextView=root.findViewById(R.id.statusTextView);
        mainActivity=((MainActivity)getContext());
        EditText starCountEditText= root.findViewById(R.id.starCountEditText);
        studentIDEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Student student=mainActivity.getStudentWithID(studentIDEditText.getText().toString());
                if(student != null) {
                    statusTextView.setText(student.getName());
                }else{
                    statusTextView.setText("User not found");
                }
                return false;
            }
        });
        studentIDEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    starCountEditText.requestFocus();
                    return true;
                }
                return false;
            }
        });
        Button addButton=root.findViewById(R.id.addButton);
        starCountEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    studentIDEditText.requestFocus();
                    addButton.callOnClick();
                    studentIDEditText.setText("");
                    return true;
                }
                return false;
            }
        });



        StarListAdapter adapter=new StarListAdapter(getActivity(), mainActivity.getStarStudents());

        ListView list=(ListView) root.findViewById(R.id.starList);
        list.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=studentIDEditText.getText().toString();
                String stars= starCountEditText.getText().toString();


                if(!stars.equals("") && !id.equals("")){

                    Student student=mainActivity.getStarStudentWithID(id);
                    if(student != null){
                        student.setStarCount(Integer.valueOf(stars));
                        adapter.notifyDataSetChanged();
                    }else {
                        student=mainActivity.getStudentWithID(id);
                        if(student != null){
                            student.setStarCount(Integer.valueOf(stars));
                            adapter.add(student);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        saveButton=root.findViewById(R.id.starSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.saveStarStudents();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveButton.callOnClick();
        binding = null;
    }
}