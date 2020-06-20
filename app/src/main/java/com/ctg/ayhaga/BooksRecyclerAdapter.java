package com.ctg.ayhaga;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.ViewHolder>{

    LayoutInflater inflater;
    //List<Category> categories;
    List<Book> books;

    public BooksRecyclerAdapter(Context context, List<Book> books) {
        this.inflater = LayoutInflater.from(context);
        this.books = books;

    }

    @NonNull
    @Override
    public BooksRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.books_row_item,parent,false);
        return new BooksRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BooksRecyclerAdapter.ViewHolder holder, int position) {


        System.out.println("# # #  # # # ## # # # # # #   -- -- - - -   "+position +"   - -- - - ---  -- -  ## # 33 #  # 3 33  # ");
        holder.name.setText(books.get(position).getName());
        holder.downloadBtn.setTag(holder.getdownloadLink(books.get(position).getFile_path()));
        holder.downloadImg.setImageResource(R.drawable.download_icon);
        holder.spliterImg.setImageResource(R.color.spliter_color);

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        Button downloadBtn;
        ImageView downloadImg;
        ImageView spliterImg;
        private InterstitialAd mInterstitialAd;
        private List<String> linkArray = new ArrayList<>();


        public String getdownloadLink(String link){

            String newLink = "";
            linkArray = Arrays.asList(link.replace("\"", "").replace("\\", "").replace("[", "").replace("]", "").split(","));
            linkArray = Arrays.asList(linkArray.get(0).split(":"));
            newLink = "https://dashboard.ayhaga.app/storage/"+linkArray.get(1);

            return newLink;

        }

        public void downloadFile(String url,String title){
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle(title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title+".pdf");
            DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            request.setMimeType("application/pdf");
            request.allowScanningByMediaScanner();
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            downloadmanager.enqueue(request);
        }

        private final Context context;

        public ViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            DownloadManager downloadManager;

            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId("ca-app-pub-5405017448198481/9126989014");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            downloadImg = itemView.findViewById(R.id.downloadImg);

            name = itemView.findViewById(R.id.bookNameTxt);
            downloadBtn = itemView.findViewById(R.id.downloadBtn);
            spliterImg = itemView.findViewById(R.id.spliterImg);

            downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadBtn.getTag().toString()));
                    request.setTitle(name.getText().toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    }
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,name.getText().toString()+".pdf");
                    DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    request.setMimeType("application/pdf");
                    request.allowScanningByMediaScanner();
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                    downloadmanager.enqueue(request);
                    if (mInterstitialAd.isLoaded()) {
                        // Step 1: Display the interstitial
                        mInterstitialAd.show();
                        // Step 2: Attach an AdListener
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {

                                Log.i("Ads", "onAdClosed");
                            }
                        });
                    } else {


                    }


                }
            });
        }
    }
}


