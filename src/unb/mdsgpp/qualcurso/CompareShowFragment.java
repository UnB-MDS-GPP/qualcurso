package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;
import java.util.HashMap;

import models.Article;
import models.Bean;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CompareShowFragment extends Fragment{
	
	private static String ID_EVALUATION_A = "idEvaluationA";
	private static String ID_EVALUATION_B = "idEvaluationB";
	BeanListCallbacks beanCallbacks;
	
	
	public CompareShowFragment() {
		// TODO Auto-generated constructor stub
		super();
		Bundle args = new Bundle();
		args.putInt(ID_EVALUATION_A, 0);
		args.putInt(ID_EVALUATION_B, 0);
		this.setArguments(args);
	}
	
	public static CompareShowFragment newInstance(int idEvaluationA, int idEvaluationB){
		CompareShowFragment fragment = new CompareShowFragment();
		Bundle args = new Bundle();
		args.putInt(ID_EVALUATION_A, idEvaluationA);
		args.putInt(ID_EVALUATION_B, idEvaluationB);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.compare_show_fragment, container,
				false);
		TextView courseNameTextView = (TextView) rootView.findViewById(R.id.compare_course_name);
		TextView firstAcronymTextView = (TextView) rootView.findViewById(R.id.compare_first_institution_acronym);
		TextView secondAcronymTextView = (TextView) rootView.findViewById(R.id.compare_second_institution_acronym);
		Evaluation evaluationA = Evaluation.get(getArguments().getInt(ID_EVALUATION_A));
		Evaluation evaluationB = Evaluation.get(getArguments().getInt(ID_EVALUATION_B));
		Course course = Course.get(evaluationA.getIdCourse());
		Institution institutionA = Institution.get(evaluationA.getIdInstitution());
		Institution institutionB = Institution.get(evaluationB.getIdInstitution());
		courseNameTextView.setText(course.getName());
		firstAcronymTextView.setText(institutionA.getAcronym());
		secondAcronymTextView.setText(institutionB.getAcronym());
		ListView compareIndicatorList = (ListView) rootView.findViewById(R.id.compare_indicator_list);
		compareIndicatorList.setAdapter(new CompareListAdapter(getActivity().getApplicationContext()
				, R.layout.compare_show_list_item, getListItems(evaluationA, evaluationB)));
		super.onCreateView(inflater, container, savedInstanceState);
		return rootView;
	}
	
	
	public ArrayList<HashMap<String, String>> getListItems(Evaluation evaluationA, Evaluation evaluationB){
		ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String,String>>();
		ArrayList<Indicator> indicators = Indicator.getIndicators();
		Book bookA = Book.get(evaluationA.getIdBooks());
		Book bookB = Book.get(evaluationB.getIdBooks());
		Article articleA = Article.get(evaluationA.getIdArticles());
		Article articleB = Article.get(evaluationB.getIdArticles());
		Bean beanA = null;
		Bean beanB = null;
		for(Indicator i : indicators){
			HashMap<String, String> hashMap = new HashMap<String, String>();
			if(evaluationA.fieldsList().contains(i.getValue())){
				beanA = evaluationA;
				beanB = evaluationB;
			}else if(bookA.fieldsList().contains(i.getValue())){
				beanA = bookA;
				beanB = bookB;
			}else if(articleA.fieldsList().contains(i.getValue())) {
				beanA = articleA;
				beanB = articleB;
			}
			if(beanA!=null){
				hashMap.put(CompareListAdapter.INDICATOR_VALUE, i.getValue());
				hashMap.put(CompareListAdapter.FIRST_VALUE, beanA.get(i.getValue()));
				hashMap.put(CompareListAdapter.SECOND_VALUE, beanB.get(i.getValue()));
				hashList.add(hashMap);
			}
		}
		return hashList;
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
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

}