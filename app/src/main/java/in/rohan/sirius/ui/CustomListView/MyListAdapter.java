package in.rohan.sirius.ui.CustomListView;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import in.rohan.sirius.R;
import in.rohan.sirius.ui.Student;

public class MyListAdapter extends ArrayAdapter<Student> {

    private final Activity context;

    public List<Student> getStudents() {
        return students;
    }

    private List<Student> students=null;


    public MyListAdapter(Activity context,List<Student> students) {
        super(context, R.layout.mylist,students);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.students=students;



    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        EditText titleText = (EditText) rowView.findViewById(R.id.title);

        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        ImageButton imb= (ImageButton) rowView.findViewById(R.id.imageButton3);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView= (ListView) v.getParent().getParent().getParent();
                MyListAdapter dataset= (MyListAdapter) listView.getAdapter();

                dataset.remove(getStudents().get(position));
                dataset.notifyDataSetChanged();

//                listView.setAdapter(dataset);
//                listView.deferNotifyDataSetChanged();

            }
        });

        titleText.setText(students.get(position).getName());

        subtitleText.setText(students.get(position).getId()+"");

        return rowView;

    };
}