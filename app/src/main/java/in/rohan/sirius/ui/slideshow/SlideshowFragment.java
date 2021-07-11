package in.rohan.sirius.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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