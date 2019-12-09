package org.yoneco.ict.tithandizane.Class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yoneco.ict.tithandizane.Fragments.RecyclerViewClickListener;
import org.yoneco.ict.tithandizane.ProviderDetails;
import com.yoneco.ict.tithandizane.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moses on 9/7/2017.
 */
public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceRecyclerViewAdapter.CustomViewHolder> {

    private List<ServiceProvider> feedItemList;

    private Context mContext;

    public ServiceRecyclerViewAdapter(Context context, ArrayList<ServiceProvider> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_service, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        ServiceProvider feedItem = feedItemList.get(i);
        customViewHolder.textView.setText(feedItem.getDistrict());
        customViewHolder.organizationView.setText(feedItem.getOrganization());
        customViewHolder.focalPersonView.setText(feedItem.getFocalPerson());
        customViewHolder.contactView.setText(feedItem.getContact());
        customViewHolder.emailView.setText(feedItem.getEmail());

        //
        customViewHolder.setClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                ServiceProvider feedItem = feedItemList.get(position);
                Intent intentDetails = new Intent(mContext, ProviderDetails.class);

                Bundle providerDetails = new Bundle();
                providerDetails.putString("org", feedItem.getOrganization());
                providerDetails.putString("district", feedItem.getDistrict());
                providerDetails.putString("person", feedItem.getFocalPerson());
                providerDetails.putString("contact", feedItem.getContact());
                providerDetails.putString("lat", feedItem.getLat());
                providerDetails.putString("lng", feedItem.getLongt());
                providerDetails.putString("email", feedItem.getEmail());
                providerDetails.putString("category", feedItem.getCategory());

                intentDetails.putExtras(providerDetails);
                mContext.startActivity(intentDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected TextView organizationView;
        protected TextView focalPersonView;
        protected TextView contactView;
        protected  TextView emailView;
        protected TextView textView;

        RecyclerViewClickListener clickListener;
        public CustomViewHolder(View view) {
            super(view);
            this.organizationView = (TextView) view.findViewById(R.id.organization);
            this.focalPersonView  =(TextView) view.findViewById(R.id.focal_person);
            this.contactView      = (TextView) view.findViewById(R.id.contact);
            this.emailView        =(TextView) view.findViewById(R.id.email);
            this.textView         = (TextView) view.findViewById(R.id.district);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }
        public void setClickListener(RecyclerViewClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        public void bind(ServiceProvider serviceProvider_) {
            textView.setText(serviceProvider_.getDistrict());
            organizationView.setText(serviceProvider_.getOrganization());
            focalPersonView.setText(serviceProvider_.getFocalPerson());
            contactView.setText(serviceProvider_.getContact());
            emailView.setText(serviceProvider_.getEmail());
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
    public void setFilter(ArrayList<ServiceProvider> serviceProvider) {
        feedItemList = new ArrayList<>();
        feedItemList.addAll(serviceProvider);
        notifyDataSetChanged();
    }
}
