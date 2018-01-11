package com.numnu.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.BookmarksAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.BookmarkdataItem;
import com.numnu.android.network.response.GetBookmarksResponse;
import com.numnu.android.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class BookmarksFragment extends Fragment {

    private RecyclerView businessRecyclerView;
    private Context context;
    private String userId,type;
    GetBookmarksResponse bookmarksResponse;
    private BookmarksAdapter bookmarksAdapter;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;

    public static BookmarksFragment newInstance(String userId,String type) {

        BookmarksFragment eventBusinessFragment = new BookmarksFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("type", type);
        eventBusinessFragment.setArguments(args);

        return eventBusinessFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
            type = bundle.getString("type");
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view= inflater.inflate(R.layout.fragment_bookmarks, container, false);

        businessRecyclerView = view.findViewById(R.id.business_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        businessRecyclerView.setLayoutManager(layoutManager);
        businessRecyclerView.setNestedScrollingEnabled(false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(businessRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        businessRecyclerView.addItemDecoration(dividerItemDecoration);
            if(Utils.isNetworkAvailable(context)) {
                getBookmarkDetails();
            }else {
                showAlert();
            }
        // Pagination
        businessRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });
        return view;

    }

    private void showAlert() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        builder.setMessage("No Internet connection");
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        builder.create().show();
    }


    private void getBookmarkDetails()
    {
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<GetBookmarksResponse> call=apiServices.getBookmark(userId);
        call.enqueue(new Callback<GetBookmarksResponse>() {
            @Override
            public void onResponse(Call<GetBookmarksResponse> call, Response<GetBookmarksResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                     bookmarksResponse = response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<GetBookmarksResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void loadMoreItems()
    {
        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<GetBookmarksResponse> call=apiServices.getBookmark(userId, String.valueOf(nextPage));
        call.enqueue(new Callback<GetBookmarksResponse>() {
            @Override
            public void onResponse(Call<GetBookmarksResponse> call, Response<GetBookmarksResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                      List<BookmarkdataItem> dataItems=response.body().getBookmarkdata();
                      if(!response.body().getPagination().isHasMore()){
                          isLastPage = true;
                      }
                    bookmarksAdapter.addData(Utils.bookmarkFilter(dataItems,type));
                    bookmarksAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<GetBookmarksResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void updateUI() {

        List<BookmarkdataItem> bookmarkdata = bookmarksResponse.getBookmarkdata();
        if(bookmarkdata!=null)
        bookmarksAdapter = new BookmarksAdapter(context,userId,type, Utils.bookmarkFilter(bookmarkdata,type));
        businessRecyclerView.setAdapter(bookmarksAdapter);
        bookmarksAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


}

