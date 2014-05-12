package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Course;
import models.Institution;
import android.database.SQLException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CourseListFragment extends ListFragment{

	private static final String ID_INSTITUTION = "idInstitution";
	BeanListCallbacks beanCallbacks;
	
	
	public CourseListFragment() {
		super();
		Bundle args = new Bundle();
		args.putInt(ID_INSTITUTION, 0);
		this.setArguments(args);
	}

	public static CourseListFragment newInstance(int id){
		CourseListFragment fragment = new CourseListFragment();
		Bundle args = new Bundle();
		args.putInt(ID_INSTITUTION, id);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list, container,
				false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		try {
			rootView.setAdapter(new ArrayAdapter<Course>(
			        getActionBar().getThemedContext(),
			        R.layout.custom_textview,
			        getCoursesList(getArguments().getInt(ID_INSTITUTION))));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rootView;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if(getArguments().getInt(ID_INSTITUTION) == 0){
			
			beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(((Course)l.getAdapter().getItem(position)).getId()));
		}else{
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.newInstance(getArguments().getInt(ID_INSTITUTION), ((Course)l.getAdapter().getItem(position)).getId()));
		}
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
	
	private ArrayList<Course> getCoursesList(int idInstitution) throws SQLException{
		if(idInstitution == 0){
			return Course.getAll();
		}else{
			return Institution.get(idInstitution).getCourses();
		}
	}
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
	
}
