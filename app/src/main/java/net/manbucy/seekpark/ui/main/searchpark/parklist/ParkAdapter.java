package net.manbucy.seekpark.ui.main.searchpark.parklist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.model.park.Park;

import java.util.List;

/**
 * ParkAdapter
 * Created by yang on 2017/7/1.
 */

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.ViewHolder> {
    private List<Park> parkList;
    private int resourceId;
    private ClickListener clickListener;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView name;
        TextView normalNum;
        TextView normalPrice;
        LinearLayout chargingPark;
        TextView chargingNum;
        TextView chargingPrice;
        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = (TextView) itemView.findViewById(R.id.item_park_info_name);
            normalNum = (TextView) itemView.findViewById(R.id.item_park_info_normal_number);
            normalPrice = (TextView) itemView.findViewById(R.id.item_park_info_normal_price);
            chargingPark = (LinearLayout) itemView.findViewById(R.id.item_park_info_charging);
            chargingNum = (TextView) itemView.findViewById(R.id.item_park_info_charging_number);
            chargingPrice = (TextView) itemView.findViewById(R.id.item_park_info_charging_price);
            address = (TextView) itemView.findViewById(R.id.item_park_info_address);
        }
    }

    public ParkAdapter(List<Park> parkList, int resourceId,ClickListener clickListener) {
        this.parkList = parkList;
        this.resourceId = resourceId;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getLayoutPosition();
                Park park = parkList.get(position);
                clickListener.onClickListener(park);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Park park = parkList.get(position);
        holder.name.setText(park.getName());
        holder.normalNum.setText(String.valueOf(park.getNumber()));
        holder.normalPrice.setText(String.valueOf(park.getPrice()));
        if (park.isHasCharging()) {
            holder.chargingPark.setVisibility(View.VISIBLE);
            holder.chargingNum.setText(String.valueOf(park.getChargingNumber()));
            holder.chargingPrice.setText(String.valueOf(park.getChargingPrice()));
        }else{
            holder.chargingPark.setVisibility(View.GONE);
        }
        holder.address.setText(park.getAddress());
    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }
    interface ClickListener{
        void onClickListener(Park park);
    }
}
