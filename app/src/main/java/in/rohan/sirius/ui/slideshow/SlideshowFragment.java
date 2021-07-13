package in.rohan.sirius.ui.slideshow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        MyListAdapter adapter=new MyListAdapter(getActivity(), ((MainActivity) getContext()).getStudents());
        ListView list=(ListView) root.findViewById(R.id.list);
        list.setAdapter(adapter);
        ImageButton imgButtonSave =root.findViewById(R.id.reloadButton);
        ImageButton uploadButton=root.findViewById(R.id.uploadButton);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        File filed=new File(getContext().getFilesDir(),"StudentFile.xls");
                        try {
                            InputStream fin=getContext().getContentResolver().openInputStream(uri);
                            FileOutputStream fout=new FileOutputStream(filed);
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

        ImageButton downloadButton=root.findViewById(R.id.downloadButton);

        ActivityResultLauncher<String> cContent = registerForActivityResult(new ActivityResultContracts.CreateDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {

                        File files=new File(getContext().getFilesDir(),"StudentFile.xls");
                        try {
                            FileInputStream fin=new FileInputStream(files);
                            OutputStream foot=getContext().getContentResolver().openOutputStream(uri);
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
                cContent.launch("StudentDetails.xls");
            }
        });
        imgButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).populateStudents();
                MyListAdapter adapter=new MyListAdapter(getActivity(), ((MainActivity) getContext()).getStudents());
                list.setAdapter(adapter);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getContext()).populateStudents();
        binding = null;
    }
}