package in.rohan.sirius.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

import in.rohan.sirius.MainActivity;
import in.rohan.sirius.R;

import in.rohan.sirius.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    Button submitButton = null;
    EditText classEditText = null;
    EditText starCharEditText = null;
    CalendarView calenderView = null;
    TextView homeStatus = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        submitButton = root.findViewById(R.id.submitButton);
        classEditText = root.findViewById(R.id.classEditText);
        starCharEditText = root.findViewById(R.id.starCharEditText);
        calenderView = root.findViewById(R.id.calendarView);
        homeStatus = root.findViewById(R.id.homeStatusTextBox);
        MainActivity mainActivity=((MainActivity) getContext());
        initializeDate();
        mainActivity.populateStudentsWithStar();

        initialize();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
            }
        });
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                StringBuilder dateString = new StringBuilder();
                dateString.append(dayOfMonth);
                dateString.append("-");
                dateString.append(month);
                dateString.append("-");
                dateString.append(year);
                mainActivity.date = dateString.toString();
                initialize();
            }
        });

        return root;
    }

    void initializeDate() {
        MainActivity mainActivity = ((MainActivity) getContext());
        Calendar myCal = Calendar.getInstance();
        StringBuilder dateString = new StringBuilder();
        dateString.append(myCal.get(Calendar.DAY_OF_MONTH));
        dateString.append("-");
        dateString.append(myCal.get(Calendar.MONTH));
        dateString.append("-");
        dateString.append(myCal.get(Calendar.YEAR));
        mainActivity.date = dateString.toString();
    }

    void initialize() {
        MainActivity mainActivity = ((MainActivity) getContext());

        String className = classEditText.getText().toString();
        String starChar = starCharEditText.getText().toString();
        if (!starChar.equals("")) {
            mainActivity.starChar = starChar;
        }
        if (!className.equals("")) {
            mainActivity.className = className;
        }

        homeStatus.setText("Their are " + mainActivity.getStudents().size() + " students, current date of operation is " + mainActivity.date + "" +
                ". The class currently working is " + mainActivity.className + " and the star character used is " + mainActivity.starChar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}