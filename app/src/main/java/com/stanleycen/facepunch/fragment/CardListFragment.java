package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.CardListAdapter;
import com.stanleycen.facepunch.card.Card;
import com.stanleycen.facepunch.card.SubforumCard;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.model.ITitleable;
import com.stanleycen.facepunch.util.Util;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/17/14.
 */
public abstract class CardListFragment extends Fragment implements ITitleable {
    ListView cardListView;
    CardListAdapter cardListAdapter;

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cardlist, null);
        cardListView = (ListView) root.findViewById(R.id.cardListView);
        Util.setInsets(getActivity(), cardListView);
        return root;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Util.saveListViewState(outState, cardListView);
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

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 20; i++) cards.add(new SubforumCard());

        cardListAdapter = new CardListAdapter(getActivity(), cards);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(cardListAdapter);
        swingBottomInAnimationAdapter.setAbsListView(cardListView);
        cardListView.setAdapter(swingBottomInAnimationAdapter);

        if (savedInstanceState == null) {
        }
        else {
            Util.restoreListViewState(savedInstanceState, cardListView);
        }
    }

    public CardListFragment() {
    }
}
