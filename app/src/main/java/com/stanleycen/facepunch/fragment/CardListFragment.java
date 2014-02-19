package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.CardListAdapter;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.model.ICardListItem;
import com.stanleycen.facepunch.model.ITitleable;
import com.stanleycen.facepunch.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/17/14.
 */
public abstract class CardListFragment extends Fragment implements ITitleable {
    public static final String EXPIRED_ARGUMENTS = "__EXPIRED_ARGS";
    ListView cardListView;
    CardListAdapter cardListAdapter;
    List<ICardListItem> cards;

    public boolean isRelaunch() {
        if (getArguments() == null) return true;
        return getArguments().getBoolean(EXPIRED_ARGUMENTS, false);
    }


    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cardlist, null);

        cardListView = (ListView) root.findViewById(R.id.cardListView);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(cardListAdapter);
        swingBottomInAnimationAdapter.setAbsListView(cardListView);
        cardListView.setAdapter(swingBottomInAnimationAdapter);
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cardListAdapter.onItemClick(position);
            }
        });

        Util.setInsets(getActivity(), cardListView);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            cards = (ArrayList<ICardListItem>) savedInstanceState.getSerializable("cards");
        }
        if (cards == null || !isRelaunch()) cards = new ArrayList<>();
        cardListAdapter = new CardListAdapter(getActivity(), cards);
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Util.saveListViewState(outState, cardListView);
        outState.putSerializable("cards", (Serializable) cards);
        //TODO this is a hack
        if (getArguments() != null) {
            getArguments().putBoolean(EXPIRED_ARGUMENTS, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) EventBus.getDefault().post(new ActionBarTitleUpdateEvent(getTitle()));
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
        }
        else {
            Util.restoreListViewState(savedInstanceState, cardListView);
        }
    }

    public CardListFragment() {
    }
}
