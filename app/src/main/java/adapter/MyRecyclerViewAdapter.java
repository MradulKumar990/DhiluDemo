package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.demo.demofacebook.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import model.UserInfo;

/**
 * Created by Mradul on 6/7/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<UserInfo> userInfos;
    private Context context;
    public  int cardType[];

    public static final int STATUS = 0;
    public static final int PHOTO = 1;
    public static final int VIDEO = 2;

    public MyRecyclerViewAdapter (ArrayList<UserInfo> data, Context context, int type[]){
        userInfos = data;
        this.context = context;
        cardType  = type;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        Log.i("viewtype ::", String.valueOf(viewType));
        if(viewType == STATUS){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_cardview,parent, false);
            return new StatusHolder(view);
        }
        if(viewType == PHOTO){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent, false);
            return new PhotoHolder(view);
        }if(viewType == VIDEO){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_cardview,parent, false);
            return new VideoHolder(view) {
            };
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder uHolder, int position) {
        if(uHolder.getItemViewType() == PHOTO) {
            UserInfo data = userInfos.get(position);
            ((PhotoHolder)uHolder).title.setText(data.getTitle());
            ((PhotoHolder)uHolder).desc.setText(data.getDesc());
            ((PhotoHolder)uHolder).image.setImageResource(data.getImage());
            ((PhotoHolder)uHolder).sImage.setImageResource(data.getImage());

            Log.i("listinfo => ",data.getTitle()+" :: "+ data.getDesc()+ " ::"+String.valueOf(data.getImage()));

            final BitmapDrawable bitmapDrawable = (BitmapDrawable) ((PhotoHolder)uHolder).image.getDrawable();

            ((PhotoHolder)uHolder).image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);

                    intent.setDataAndType(Uri.parse(path), "image");
                    context.startActivity(intent);
                }
            });
        }

        if(uHolder.getItemViewType() == VIDEO){
            UserInfo data = userInfos.get(position);
            ((VideoHolder)uHolder).title.setText(data.getTitle());
            ((VideoHolder)uHolder).desc.setText(data.getDesc());
            ((VideoHolder)uHolder).image.setImageResource(data.getImage());
            ((VideoHolder)uHolder).sVideo.setVideoURI(Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.nature));
            ((VideoHolder)uHolder).sVideo.setMediaController(new MediaController(context));
            ((VideoHolder)uHolder).sVideo.requestFocus();
            ((VideoHolder)uHolder).sVideo.start();

        }

    }

    @Override
    public int getItemViewType(int position) {
        return cardType[position];
    }


    @Override
    public int getItemCount() {
        Log.i("listdata ::", String.valueOf(userInfos.size()));
        return userInfos.size();
    }

    public static class StatusHolder extends RecyclerView.ViewHolder {
        public StatusHolder(View v) {
            super(v);
        }
    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {
            ImageView image;
            ImageView sImage;
            TextView title;
            TextView desc;
            public PhotoHolder(View v) {
                super(v);
                title = (TextView) itemView.findViewById(R.id.pTitle);
                desc = (TextView) itemView.findViewById(R.id.pDesc);
                image = (ImageView) itemView.findViewById(R.id.pImage);
                sImage = (ImageView) itemView.findViewById(R.id.sImage);
            }
        }
    public static class VideoHolder extends RecyclerView.ViewHolder {
        ImageView image;
        VideoView sVideo;
        TextView title;
        TextView desc;
        public VideoHolder(View v) {
            super(v);
            title = (TextView) itemView.findViewById(R.id.pTitle);
            desc = (TextView) itemView.findViewById(R.id.pDesc);
            image = (ImageView) itemView.findViewById(R.id.sImage);
            sVideo = (VideoView) itemView.findViewById(R.id.pImage);
        }
    }

    }
