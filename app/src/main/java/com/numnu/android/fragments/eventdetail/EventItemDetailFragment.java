package com.numnu.android.fragments.eventdetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.adapter.eventdetail.EventPostsAdapter;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.detail.ItemDetailFragment;
import com.numnu.android.fragments.detail.SearchBusinessDetailFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.request.BookmarkRequestData;
import com.numnu.android.network.response.BookmarkResponse;
import com.numnu.android.network.response.EventPostsResponse;
import com.numnu.android.network.response.EventItemDetailResponse;
import com.numnu.android.network.response.PostdataItem;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class EventItemDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView price, itemName, viewEventMap, eventDate, eventTime,morebutton;
    private ImageView eventImageView;
    private TextView eventDescription;
    private AppBarLayout appBarLayout;
    private PopupWindow pw;
    LinearLayout linearLayout;
    ImageView itemImageView;
    TextView ViewTxt;
    TextView ItemInfoTxt;
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    private RecyclerView mPostsRecycler;
    TextView barbTxt, catgTxt;
    ImageView BarbImg,CatgImg;
   private Boolean isExpanded = false;
    private String itemId,eventId;
    EventPostsResponse eventPostsResponse;
    EventItemDetailResponse itemDetailsResponse;
    private EventPostsAdapter eventBusinessesAdapter;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    private ProgressDialog mProgressDialog;
    // Create a storage reference from our app
    StorageReference storageRef ;
    private FirebaseStorage storage;
    int Max=4;
    private AlertDialog dialog;


    public static EventItemDetailFragment newInstance(String eventId, String itemId) {

        EventItemDetailFragment itemInfoFragment = new EventItemDetailFragment();
        Bundle args = new Bundle();
        args.putString("eventId", eventId);
        args.putString("itemId", itemId);
        itemInfoFragment.setArguments(args);
        return itemInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            itemId = bundle.getString("itemId");
            eventId = bundle.getString("eventId");
        }

        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_item_info, container, false);

        viewEventMap = view.findViewById(R.id.txt_view_event_map);
        eventDescription = view.findViewById(R.id.event_description);
        itemName = view.findViewById(R.id.item_header_name);
        price = view.findViewById(R.id.txt_amount);

        itemImageView = view.findViewById(R.id.item_image);

        barbTxt=(TextView)view.findViewById(R.id.evntbarbq_txt);
        catgTxt =(TextView)view.findViewById(R.id.evntcottage_house_txt);
        BarbImg=(ImageView) view.findViewById(R.id.evntbarbq_icon);
        CatgImg=(ImageView)view.findViewById(R.id.evntcottage_house_icon);

        recyclerView=(RecyclerView)view.findViewById(R.id.business_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        mPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mPostsRecycler.setLayoutManager(layoutManager);
        mPostsRecycler.setNestedScrollingEnabled(false);

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.item);

        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        RelativeLayout toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        ItemInfoTxt=(TextView) view.findViewById(R.id.text_terms) ;
        morebutton = view.findViewById(R.id.more_button);
        morebutton.setVisibility(View.GONE);

        setupClickListeners();
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mPostsRecycler.getContext(), LinearLayoutManager.VERTICAL);
//        mPostsRecycler.addItemDecoration(dividerItemDecoration);


        if(Utils.isNetworkAvailable(context)) {
            getItemsDetails();
            getData();
        }else {
            showAlert();
        }
        // Pagination
        mPostsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        toolbarBackIcon.setOnClickListener(this);

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showAlertshare();
            }
        });
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(this);

        return view;
    }



    private void showAlert() {
    }

    private void getItemsDetails()
    {
        showProgressDialog();
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventItemDetailResponse> call=apiServices.getEventItemDetail(eventId,itemId);
        call.enqueue(new Callback<EventItemDetailResponse>() {
            @Override
            public void onResponse(Call<EventItemDetailResponse> call, Response<EventItemDetailResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    itemDetailsResponse = response.body();
                    updateItemUI();
                }else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<EventItemDetailResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }


    private void getData()
    {
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventPostsResponse> call=apiServices.getEventItemPosts(eventId,itemId);
        call.enqueue(new Callback<EventPostsResponse>() {
            @Override
            public void onResponse(Call<EventPostsResponse> call, Response<EventPostsResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    eventPostsResponse = response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventPostsResponse> call, Throwable t) {
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
        Call<EventPostsResponse> call=apiServices.getEventItemPosts(eventId,itemId, String.valueOf(nextPage));
        call.enqueue(new Callback<EventPostsResponse>() {
            @Override
            public void onResponse(Call<EventPostsResponse> call, Response<EventPostsResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    List<PostdataItem> dataItems=response.body().getPostdata();
                    if(!response.body().getPagination().isHasMore()){
                        isLastPage = true;
                    }
                    eventBusinessesAdapter.addData(dataItems);
                    eventBusinessesAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventPostsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void updateItemUI() {


        if(!itemDetailsResponse.getItemimages().isEmpty()&&itemDetailsResponse.getItemimages().get(0).getImageurl()!=null) {
            storageRef.child(itemDetailsResponse.getItemimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(itemImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        itemName.setText(itemDetailsResponse.getItemname());
       if(!itemDetailsResponse.getItemdescription().isEmpty()){
        eventDescription.setText(itemDetailsResponse.getItemdescription());
       }else {
           eventDescription.setVisibility(View.GONE);
       }


        price.setText(String.format("%s %s", itemDetailsResponse.getCurrencycode(), itemDetailsResponse.getPriceamount()));

        barbTxt.setText(itemDetailsResponse.getEventname());
        catgTxt.setText(itemDetailsResponse.getBusinessname());

        if(eventDescription.getLineCount()>= Max){
            morebutton.setVisibility(View.VISIBLE);
        }
        morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpanded) {

                    isExpanded = false;
                    eventDescription.setMaxLines(4);
                    morebutton.setText("more");

                } else {

                    isExpanded = true;
                    eventDescription.setMaxLines(1000);
                    morebutton.setText("less");

                }

            }
        });
        if(!itemDetailsResponse.getItemtags().isEmpty()) {
            adapter = new HorizontalContentAdapter(context, itemDetailsResponse.getItemtags());
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
        }

        hideProgressDialog();
    }


    private void updateUI() {

        eventBusinessesAdapter = new EventPostsAdapter(context, eventPostsResponse.getPostdata());
        mPostsRecycler.setAdapter(eventBusinessesAdapter);
        eventBusinessesAdapter.notifyDataSetChanged();
    }


    public void showAlertshare() {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.bookmark_layout, null);
        final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        alertDialog.setView(deleteDialogView);
        final TextView shareTxt = (TextView) deleteDialogView.findViewById(R.id.share);
        final TextView BookmarkTxt = (TextView) deleteDialogView.findViewById(R.id.bookmark);
        TextView cancel = (TextView) deleteDialogView.findViewById(R.id.gender_cancel);
//        LinearLayout GenderLinLay = (LinearLayout) deleteDialogView.findViewById(R.id.genlin_lay);
//        Button ok = deleteDialogView.findViewById(R.id.ok_button);

        final android.support.v7.app.AlertDialog alertDialog1 = alertDialog.create();
        shareTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                alertDialog1.dismiss();
            }
        });
        BookmarkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(context,PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    Bundle bundle = new Bundle();
                    bundle.putString("BusinessBookmarkIntent","businessbookmark");
                    LoginFragment logFragment = new LoginFragment();
                    logFragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                    transaction.add(R.id.frame_layout, logFragment);
                    transaction.addToBackStack(null).commit();
//                    Intent intent = new Intent(context, LoginFragment.class);
//                    intent.putExtra("BusinessBookmarkIntent","businessbookmark");
//                    context.startActivity(intent);
                    alertDialog1.dismiss();
                }else {
                    postBookmark();
                    alertDialog1.dismiss();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
//        GenderLinLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog1.dismiss();
//            }
//        });

//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


        alertDialog1.setCanceledOnTouchOutside(false);
        try {
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alertDialog1.show();
//        alertDialog1.getWindow().setLayout((int)Utils.convertDpToPixel(290,
//                getActivity()),(int)Utils.convertDpToPixel(290,getActivity()));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog1.getWindow().getAttributes());
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.shareDialogAnimation;
        alertDialog1.getWindow().setAttributes(lp);
    }


    private void postBookmark()
    {
        showProgressDialog();
        String userId = PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_ID);
        BookmarkRequestData bookmarkRequestData = new BookmarkRequestData();
        bookmarkRequestData.setClientapp(Constants.CLIENT_APP);
        bookmarkRequestData.setClientip(Utils.getLocalIpAddress(context));
        bookmarkRequestData.setType(Constants.BOOKMARK_ITEM);
        bookmarkRequestData.setEntityid(itemId);
        bookmarkRequestData.setEntityname(itemName.getText().toString());

        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<BookmarkResponse> call=apiServices.postBookmark(userId,bookmarkRequestData);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                int responsecode = response.code();
                if(responsecode==201) {
                    BookmarkResponse bookmarkResponse = response.body();
                    Toast.makeText(context, "Bookmarked this page", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }else if(responsecode==422) {

                    Toast.makeText(context, "Already Bookmarked!!", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }

    private void setupClickListeners() {
        barbTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance(eventId));
                transaction.addToBackStack(null).commit();
            }
        });
        BarbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance(eventId));
                transaction.addToBackStack(null).commit();
            }
        });
        catgTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(String.valueOf(itemDetailsResponse.getBusinessuserid())));
                transaction.addToBackStack(null).commit();
            }
        });
        CatgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(String.valueOf(itemDetailsResponse.getBusinessuserid())));
                transaction.addToBackStack(null).commit();
            }
        });

        itemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ItemDetailFragment.newInstance(itemId));
                transaction.addToBackStack(null).commit();
            }
        });

        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ItemDetailFragment.newInstance(itemId));
                transaction.addToBackStack(null).commit();
            }
        });


    }

    public void showProgressDialog() {


        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        //View view = getLayoutInflater().inflate(R.layout.progress);
        alertDialog.setView(R.layout.progress);
        dialog = alertDialog.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void hideProgressDialog(){
        if(dialog!=null)
        dialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;


            case R.id.toolbar:
                mPostsRecycler.scrollTo(0,0);
                break;

        }
    }


    @Override
    public void onAttach(Context context) {

        this.context = context;
        super.onAttach(context);
    }


}

