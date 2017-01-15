package me.sugarkawhi.switcherview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.sugarkawhi.switcherview.bean.SwitcherItem;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private ImageSwitcher mImgSwitcher1,mImgSwitcher2,mImgSwitcher3;
    private TextSwitcher mTextSwitcher1,mTextSwitcher2,mTextSwitcher3;
    private TextView mTextView;

    private SparseArray<SwitcherItem> mSparseArray  = new SparseArray<>();
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simulateDatas();
        initViews();
        doChangeGroup();
    }

    private void initViews(){
        View switcher1 = findViewById(R.id.switcher1);
        mImgSwitcher1 = (ImageSwitcher) switcher1.findViewById(R.id.imgSwitcher);
        mTextSwitcher1 = (TextSwitcher) switcher1.findViewById(R.id.textSwitcher);

        View switcher2 = findViewById(R.id.switcher2);
        mImgSwitcher2 = (ImageSwitcher) switcher2.findViewById(R.id.imgSwitcher);
        mTextSwitcher2 = (TextSwitcher) switcher2.findViewById(R.id.textSwitcher);

        View switcher3 = findViewById(R.id.switcher3);
        mImgSwitcher3 = (ImageSwitcher) switcher3.findViewById(R.id.imgSwitcher);
        mTextSwitcher3 = (TextSwitcher) switcher3.findViewById(R.id.textSwitcher);

        mImgSwitcher1.setFactory(imageSwitcherFactory);
        mImgSwitcher1.setInAnimation(this,R.anim.switcher_in);
        mImgSwitcher1.setOutAnimation(this,R.anim.switcher_out);

        mImgSwitcher2.setFactory(imageSwitcherFactory);
        mImgSwitcher2.setInAnimation(this,R.anim.switcher_in);
        mImgSwitcher2.setOutAnimation(this,R.anim.switcher_out);

        mImgSwitcher3.setFactory(imageSwitcherFactory);
        mImgSwitcher3.setInAnimation(this,R.anim.switcher_in);
        mImgSwitcher3.setOutAnimation(this,R.anim.switcher_out);

        mTextSwitcher1.setFactory(textSwitcherFactory);
        mTextSwitcher1.setInAnimation(this,R.anim.switcher_in);
        mTextSwitcher1.setOutAnimation(this,R.anim.switcher_out);

        mTextSwitcher2.setFactory(textSwitcherFactory);
        mTextSwitcher2.setInAnimation(this,R.anim.switcher_in);
        mTextSwitcher2.setOutAnimation(this,R.anim.switcher_out);

        mTextSwitcher3.setFactory(textSwitcherFactory);
        mTextSwitcher3.setInAnimation(this,R.anim.switcher_in);
        mTextSwitcher3.setOutAnimation(this,R.anim.switcher_out);

        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doChangeGroup();
            }
        });
    }


    private ViewSwitcher.ViewFactory imageSwitcherFactory = new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            RoundedImageView riv = new RoundedImageView(MainActivity.this);
            riv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            riv.setCornerRadius(6.0f);
            riv.setBorderWidth(0.1f);
            riv.setBorderColor(Color.GRAY);
            riv.mutateBackground(true);
            riv.setTileModeX(Shader.TileMode.REPEAT);
            riv.setTileModeY(Shader.TileMode.REPEAT);
            riv.setLayoutParams(new ImageSwitcher.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            riv.setBackgroundColor(Color.TRANSPARENT);
            return riv;
        }
    };

    private ViewSwitcher.ViewFactory textSwitcherFactory = new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            TextView textView = new TextView(MainActivity.this);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
            textView.setGravity(Gravity.START);
            textView.setMaxLines(2);
            textView.setLines(2);
            textView.setLineSpacing(2.0f, 1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            return textView;
        }
    };


    /**
     * simulateDatas
     */
    private void  simulateDatas(){
        SwitcherItem item0 = new SwitcherItem("http://img.hb.aicdn.com/597f37992eabd3f9f671688c52765c339bb3e253144a4-gn46Li_sq320",
                "苏寒的插画");
        SwitcherItem item1 = new SwitcherItem("http://img.hb.aicdn.com/a3d5fcf377f25c2937920fe58a79c7f879cd8846a01a-1xlNIi_sq320",
                "迷失的鹿");
        SwitcherItem item2 = new SwitcherItem("http://img.hb.aicdn.com/673d4e19afc0a6607b359deb8c5a01b703bb222519555-DOhNkj_sq320",
                "一个人一座城");
        SwitcherItem item3 = new SwitcherItem("http://img.hb.aicdn.com/a6b00bbdeeed21c79af74d043ba4b7505cbe11bf2225b-wsXC96_sq320",
                "裙角生香，风凉袖轻挽");
        SwitcherItem item4 = new SwitcherItem("http://img.hb.aicdn.com/8a00d52c0a2cc4367c8ed99aa891a1a0fc597882ba26-YvNH9V_sq320",
                "向日葵");
        SwitcherItem item5 = new SwitcherItem("http://img.hb.aicdn.com/e8502c35e3decbb3d843b51ac9fc51ff73d6e5a8cb9cb-XdlFrD_sq320",
                "我就是爱那个无脸男");
        SwitcherItem item6 = new SwitcherItem("http://img.hb.aicdn.com/4558af4f425efb7bfd3e336bacc90af49359fbec29026-Eyvg5w_sq320",
                "[平面]炫酷");

        mSparseArray.append(0,item0);
        mSparseArray.append(1,item1);
        mSparseArray.append(2,item2);
        mSparseArray.append(3,item3);
        mSparseArray.append(4,item4);
        mSparseArray.append(5,item5);
        mSparseArray.append(6,item6);

    }


    /**
     * click btn change group
     * doChange
     */
    private void doChangeGroup(){

        int total = mSparseArray.size();

        final int random1=   (int)(Math.random()*total);
        int random2=   (int)(Math.random()*total);
        while (random2 ==random1){
            random2=  (int)(Math.random()*total);
            if (random2!=random1)
            break;
        }
         int random3=   (int)(Math.random()*total);
        while (random3 ==random1 || random3==random2){
            random3=  (int)(Math.random()*total);
            if (random3!=random1 && random3!=random2)
            break;
        }
        final SparseArray<String> array = new SparseArray<>();
        array.append(0,mSparseArray.valueAt(random1).imgUrl);
        array.append(1,mSparseArray.valueAt(random2).imgUrl);
        array.append(2,mSparseArray.valueAt(random3).imgUrl);

        final int finalRandom2 = random2;
        final int finalRandom3 = random3;

        Subscription subscription = Observable.just(array)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SparseArray<String>, List<Bitmap>>() {
                    @Override
                    public List<Bitmap> call(SparseArray<String> array) {
                        List<Bitmap> bitmaps = new ArrayList<>();
                        for (int i=0;i<array.size();i++){
                            bitmaps.add(getUrlBitmap(array.valueAt(i)));
                        }
                        return bitmaps;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bitmap>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                               }

                               @Override
                               public void onNext(List<Bitmap> bitmaps) {

                                   if (bitmaps == null)
                                       return;
                                   for (int i = 0; i < bitmaps.size(); i++) {
                                       final Bitmap bitmap = bitmaps.get(i);
                                       switch (i) {
                                           case 0:
                                               if (bitmap == null) {
                                                   mImgSwitcher1.setImageResource(R.mipmap.ic_launcher);
                                               } else {
                                                   mImgSwitcher1.setImageDrawable(new BitmapDrawable(getResources(),
                                                           bitmap));
                                               }
                                               mTextSwitcher1.setText(mSparseArray.valueAt(random1).title);
                                               break;
                                           case 1:
                                               if (bitmap == null) {
                                                   mImgSwitcher2.setImageResource(R.mipmap.ic_launcher);
                                               } else {
                                                   mImgSwitcher2.postDelayed(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           mImgSwitcher2.setImageDrawable(new BitmapDrawable(getResources(),
                                                                   bitmap));
                                                       }
                                                   }, 100);
                                               }
                                               mTextSwitcher2.postDelayed(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       mTextSwitcher2.setText(mSparseArray.valueAt(finalRandom2).title);
                                                   }
                                               }, 100);
                                               break;
                                           case 2:
                                               if (bitmap == null) {
                                                   mImgSwitcher3.postDelayed(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           mImgSwitcher3.setImageResource(R.mipmap.ic_launcher);
                                                       }
                                                   }, 200);
                                               } else {
                                                   mImgSwitcher3.postDelayed(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           mImgSwitcher3.setImageDrawable(new BitmapDrawable(getResources(),
                                                                   bitmap));
                                                       }
                                                   }, 200);
                                               }
                                              mTextSwitcher3.postDelayed(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       mTextSwitcher3.setText(mSparseArray.valueAt(finalRandom3).title);
                                                   }
                                               }, 200);
                                               break;
                                       }
                                   }

                               }
                           }

                );
        mCompositeSubscription.add(subscription);
    }


    /**
     * download img url 2 Bitmap
     * @return Bitmap
     */
    private Bitmap getUrlBitmap(String url){
        try {
              return Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .centerCrop()
                    .dontAnimate()
                    .into(300, 300)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        if (mCompositeSubscription!=null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
