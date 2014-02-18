package com.stanleycen.facepunch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.stanleycen.facepunch.card.CardItemTypes;
import com.stanleycen.facepunch.model.ICardListItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/17/14.
 */
public class CardListAdapter extends BaseAdapter {
    public LayoutInflater inflater;
    public Context context;
    protected List<ICardListItem> cards;

    @Override
    public int getViewTypeCount() {
        return CardItemTypes.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).isSelectable();
    }

    public CardListAdapter(Context context, List<ICardListItem> cards) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.setCards(cards);
    }

    @Override
    public int getCount() {
        return getCards().size();
    }

    @Override
    public ICardListItem getItem(int position) {
        return getCards().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(inflater, convertView, position, context);
    }

    public void add(ICardListItem item) {
        getCards().add(item);
        notifyDataSetChanged();
    }

    /**
     * Inserts the specified element at the specified position in the list.
     */
    public void add(int position, ICardListItem item) {
        getCards().add(position, item);
        notifyDataSetChanged();
    }

    /**
     * Appends all of the elements in the specified collection to the end of the
     * list, in the order that they are returned by the specified collection's
     * Iterator.
     */
    public void addAll(Collection<? extends ICardListItem> items) {
        getCards().addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Appends all of the elements to the end of the list, in the order that
     * they are specified.
     */
    public void addAll(ICardListItem... items) {
        Collections.addAll(getCards(), items);
        notifyDataSetChanged();
    }

    /**
     * Inserts all of the elements in the specified collection into the list,
     * starting at the specified position.
     */
    public void addAll(int position, Collection<? extends ICardListItem> items) {
        getCards().addAll(position, items);
        notifyDataSetChanged();
    }

    /**
     * Inserts all of the elements into the list, starting at the specified
     * position.
     */
    public void addAll(int position, ICardListItem... items) {
        for (int i = position; i < (items.length + position); i++) {
            getCards().add(i, items[i]);
        }
        notifyDataSetChanged();
    }


    public void clear() {
        getCards().clear();
        notifyDataSetChanged();
    }


    public void set(int position, ICardListItem item) {
        getCards().set(position, item);
        notifyDataSetChanged();
    }

    public void remove(ICardListItem item) {
        getCards().remove(item);
        notifyDataSetChanged();
    }


    public void remove(int position) {
        getCards().remove(position);
        notifyDataSetChanged();
    }


    public void removePositions(Collection<Integer> positions) {
        ArrayList<Integer> positionsList = new ArrayList<Integer>(positions);
        Collections.sort(positionsList);
        Collections.reverse(positionsList);
        for (int position : positionsList) {
            getCards().remove(position);
        }
        notifyDataSetChanged();
    }

    public void removeAll(Collection<ICardListItem> cards) {
        cards.removeAll(cards);
        notifyDataSetChanged();
    }


    public void retainAll(Collection<ICardListItem> cards) {
        cards.retainAll(cards);
        notifyDataSetChanged();
    }


    public int indexOf(ICardListItem item) {
        return getCards().indexOf(item);
    }

    public List<ICardListItem> getCards() {
        return cards;
    }

    public void setCards(List<ICardListItem> cards) {
        this.cards = cards;
    }

    public void onItemClick(int position) {
        getItem(position).onClick();
    }
}
