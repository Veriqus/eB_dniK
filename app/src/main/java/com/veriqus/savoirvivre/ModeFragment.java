package com.veriqus.savoirvivre;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModeFragment extends Fragment {

    onModeSelectedListener mCallback;
    View rootView;
    static String PASSED_VALUE;
    static String TYPE_VALUE = "good";

    public ModeFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface onModeSelectedListener {
        public void onModeSelected(String categoryName, String mode);
        public void onModeSelected(String categoryName, boolean article);
    }

    public interface OnArticleSelectedListener {
        public void onArticleSelected(String categoryName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_mode, container, false);

        Bundle bundle = getArguments();

        final String categoryName = bundle.getString(PASSED_VALUE);
        final String categoryID = ((MainActivity)getActivity()).getCategoryIDByName(categoryName);

        View learn = rootView.findViewById(R.id.Learning);

        TextView learnCat = (TextView) rootView.findViewById(R.id.textView2);
        learnCat.setText(categoryName);

//        View goodHabit = rootView.findViewById(R.id.goodHabitLinear);
//
//        View badHabit = rootView.findViewById(R.id.badHabitLinear);
//
//        View showAll = rootView.findViewById(R.id.showAllLinear);
//
//        learn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallback.onModeSelected(categoryName, true);
//            }
//        });
//        goodHabit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallback.onModeSelected(categoryName, "good");
//            }
//        });
//        badHabit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallback.onModeSelected(categoryName, "bad");
//            }
//        });
//        showAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallback.onModeSelected(categoryName, "all");
//            }
//        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (onModeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onModeSelectedListener");
        }
    }

}
