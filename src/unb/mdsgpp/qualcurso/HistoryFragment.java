package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import models.Course;
import models.Institution;
import models.Search;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HistoryFragment extends Fragment {

	BeanListCallbacks beanCallbacks;

	public HistoryFragment() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_history, container, false);
		final ListView history = (ListView) rootView.findViewById(R.id.listHistory) ; 
		ArrayList<Search> searches = Search.getAll();
		Collections.reverse(searches);
		ListHistoryAdapter histotyAdapter = new ListHistoryAdapter(this.getActivity().getApplicationContext(), R.id.listHistory, searches);

		history.setAdapter((ListAdapter)histotyAdapter);
		
		history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Search search = (Search) parent.getItemAtPosition(position);
				if(search.getOption() == Search.INSTITUTION){
					beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(Institution.getInstitutionsByEvaluationFilter(search), search));
				}else if(search.getOption() == Search.COURSE){
					beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(Course.getCoursesByEvaluationFilter(search), search));
				}
			}
		});

		return rootView;
	}
}
