package in.rohan.sirius.ui.CustomListView;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import in.rohan.sirius.MainActivity;
import in.rohan.sirius.R;
import in.rohan.sirius.ui.Student;

public class StarListAdapter extends ArrayAdapter<Student> {

    private final MainActivity mainActivity;

    public List<Student> getStudents() {
        return studentsWithStar;
    }

    private List<Student> studentsWithStar=null;


    public StarListAdapter(Activity context,List<Student> studentsWithStar) {
        super(context, R.layout.mylist,studentsWithStar);
        // TODO Auto-generated constructor stub

        this.mainActivity = (MainActivity) context;
        this.studentsWithStar=studentsWithStar;



    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater= mainActivity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        EditText titleText = (EditText) rowView.findViewById(R.id.title);

        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        ImageButton imb= (ImageButton) rowView.findViewById(R.id.imageButton3);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView= (ListView) v.getParent().getParent().getParent();
                StarListAdapter dataset= (StarListAdapter) listView.getAdapter();
                mainActivity.getStudentWithID(dataset.getStudents().get(position).getId()+"").setStarCount(0);
                dataset.remove(dataset.getStudents().get(position));
                dataset.notifyDataSetChanged();
            }
        });

        titleText.setText(studentsWithStar.get(position).getName());
        TextView starTextView=rowView.findViewById(R.id.starTextView);

        subtitleText.setText(studentsWithStar.get(position).getId()+"");
        if(studentsWithStar.get(position).getStarCount() >=0 ) {
            StringBuilder startext=new StringBuilder();
            for(int i=0;i<studentsWithStar.get(position).getStarCount();i++){
                startext.append(((MainActivity)getContext()).getStarChar());
            }
            starTextView.setText(startext.toString());
        }
        return rowView;

    };
}