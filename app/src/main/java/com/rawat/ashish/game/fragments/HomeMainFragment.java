package com.rawat.ashish.game.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rawat.ashish.game.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMainFragment extends Fragment {

    OnButtonClickListener mCallback;
    private Button wallet, learnTouse, game, referAndEarn;

    public HomeMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);
        wallet = view.findViewById(R.id.buttonHomeWallet);
        learnTouse = view.findViewById(R.id.buttonHomeLearnToUse);
        game = view.findViewById(R.id.buttonHomeGame);
        referAndEarn = view.findViewById(R.id.buttonHomeReferAndEarn);
        setClickListener(wallet, 1);
        setClickListener(learnTouse, 2);
        setClickListener(game, 3);
        setClickListener(referAndEarn, 4);

        return view;
    }

    private void setClickListener(Button button, final int id) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonSelected(id);
            }
        });
    }

    public interface OnButtonClickListener {
        void onButtonSelected(int id);
    }
}
