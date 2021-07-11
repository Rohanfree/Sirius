package in.rohan.sirius.ui.gallery;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText studentIDEditText= root.findViewById(R.id.studentIDEdittext);
        TextView statusTextView=root.findViewById(R.id.statusTextView);

        studentIDEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Student student=((MainActivity)getContext()).getStudentWithID(studentIDEditText.getText().toString());
                if(student != null) {
                    statusTextView.setText(student.getName());
                }else{
                    statusTextView.setText("User not found");
                }
                return false;
            }
        });
        Button addButton=root.findViewById(R.id.addButton);
        EditText starCountEditText= root.findViewById(R.id.starCountEditText);
        StarListAdapter adapter=new StarListAdapter(getActivity(), ((MainActivity) getContext()).getStarStudents());

        ListView list=(ListView) root.findViewById(R.id.starList);
        list.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=studentIDEditText.getText().toString();
                String stars= starCountEditText.getText().toString();


                if(!stars.equals("") && !id.equals("")){

                    Student student=((MainActivity)getContext()).getStarStudentWithID(id);
                    if(student != null){
                        student.setStarCount(Integer.valueOf(stars));
                        adapter.notifyDataSetChanged();
                    }else {
                        student=((MainActivity)getContext()).getStudentWithID(id);
                        if(student != null){
                            student.setStarCount(Integer.valueOf(stars));
                            adapter.add(student);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        Button saveButton=root.findViewById(R.id.starSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).saveStarStudents();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}