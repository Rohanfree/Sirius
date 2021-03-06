package in.rohan.sirius.ui.slideshow;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.rohan.sirius.MainActivity;
import in.rohan.sirius.R;
import in.rohan.sirius.databinding.FragmentSlideshowBinding;
import in.rohan.sirius.ui.CustomListView.MyListAdapter;
import in.rohan.sirius.ui.ExcelUtils;
import in.rohan.sirius.ui.Student;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    private MainActivity mainActivity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainActivity=((MainActivity) getContext());
        MyListAdapter adapter = new MyListAdapter(getActivity(), mainActivity.getStudents());
        ListView list = (ListView) root.findViewById(R.id.list);
        list.setAdapter(adapter);
        Button imgButtonReload = root.findViewById(R.id.reloadButton);
        Button uploadButton = root.findViewById(R.id.fileUpload);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        File filed = new File(getContext().getFilesDir(), mainActivity.className+"StudentDetails.xls");
                        try {
                            InputStream fin = getContext().getContentResolver().openInputStream(uri);
                            FileOutputStream fout = new FileOutputStream(filed);
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = fin.read(buf)) > 0) {
                                fout.write(buf, 0, len);
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("application/vnd.ms-excel");
            }
        });

        Button downloadButton = root.findViewById(R.id.fileDownloadButton);

        ActivityResultLauncher<String> cContent = registerForActivityResult(new ActivityResultContracts.CreateDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {

                        File files = new File(getContext().getFilesDir(), mainActivity.className+"StudentDetails.xls");
                        try {
                            FileInputStream fin = new FileInputStream(files);
                            OutputStream foot = getContext().getContentResolver().openOutputStream(uri);
                            byte[] buf = new byte[1024];
                            int len;

                            while ((len = fin.read(buf)) > 0) {
                                foot.write(buf, 0, len);
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cContent.launch(((MainActivity) getContext()).className+"StudentDetails.xls");
            }
        });
        imgButtonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.populateStudents();
                MyListAdapter adapter = new MyListAdapter(getActivity(), mainActivity.getStudents());
                list.setAdapter(adapter);
            }
        });
        Button addNewStudntButton=root.findViewById(R.id.addNewStudentsButton);
        EditText studentID=root.findViewById(R.id.editTextNewStudentID);
    EditText studentName=root.findViewById(R.id.editTextNewStudentName);
        addNewStudntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!studentName.getText().toString().equals("") && mainActivity.getStudentWithID(studentID.getText().toString()) == null){
                    Student student=new Student();
                    student.setName(studentName.getText().toString());
                    student.setId(Integer.valueOf(studentID.getText().toString()));
                    MyListAdapter dataset= (MyListAdapter) list.getAdapter();
                    dataset.add(student);
                    dataset.notifyDataSetChanged();
                    studentID.setText("");
                    studentName.setText("");

                }

             }
        });
        Button imgButtonSave=root.findViewById(R.id.saveButton);
        imgButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyListAdapter dataset= (MyListAdapter) list.getAdapter();
                ExcelUtils.setStudents(dataset.getStudents());
                ExcelUtils.writeStudentsToExcelSheet(new File(getContext().getFilesDir(), mainActivity.className+"StudentDetails.xls"));

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainActivity.populateStudents();
        binding = null;
    }
}