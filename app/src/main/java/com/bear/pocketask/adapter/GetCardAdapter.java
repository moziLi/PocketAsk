package com.bear.pocketask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bear.pocketask.R;
import com.bear.pocketask.adapter.base.IBaseAdapter;
import com.bear.pocketask.info.CardItemInfo;
import com.bear.pocketask.info.SelectorInfo;
import com.bear.pocketask.tools.observable.EventObservable;
import com.bear.pocketask.widget.inputview.ITextView;
import com.bear.pocketask.widget.record.RecordObservable;
import com.bear.pocketask.widget.record.RecordView;
import com.bear.pocketask.widget.selectorbutton.SelectorAdapterView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 卡片信息适配器
 * Created by bear on 16/10/7.
 */

public class GetCardAdapter extends IBaseAdapter implements View.OnClickListener {
    private static final String TAG = "GetCardAdapter";
    private CardItemInfo info;

    public GetCardAdapter(Context context, List<CardItemInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getList().size() < 1)
            return null;

        if (convertView == null) {
            convertView = getInflater().inflate(R.layout.get_card_item, null);

            viewHolder = new ViewHolder();
            viewHolder.ivDetailPic = (ImageView) convertView.findViewById(R.id.card_item_detail_pic);
            viewHolder.ivHeadPic = (ImageView) convertView.findViewById(R.id.card_item_head_portrait);
            viewHolder.tvQuestion = (TextView) convertView.findViewById(R.id.card_item_questions_text);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.card_item_user_name);

            viewHolder.leReport = convertView.findViewById(R.id.card_item_report);
            viewHolder.rvRecordView = (RecordView) convertView.findViewById(R.id.card_item_questions_audio);
//            viewHolder.llSelectorLayout = convertView.findViewById(R.id.card_item_select_list_layout);
            viewHolder.lvSelectorList = (SelectorAdapterView) convertView.findViewById(R.id.card_item_select_list);
            viewHolder.etInputView = (ITextView) convertView.findViewById(R.id.card_item_input);
            viewHolder.btSend = convertView.findViewById(R.id.card_item_send);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        //设置值
        info = (CardItemInfo) getItem(position);

        //显示圆形的图像
        ImageLoader.getInstance().displayImage(info.getHeadPic(), viewHolder.ivHeadPic);
        //昵称
        viewHolder.tvUserName.setText(info.getUserName());


        viewHolder.tvQuestion.setText(info.getQuestions());
        //设置id
        viewHolder.rvRecordView.setRecordId(info.getQuestionId());
        RecordObservable.getInstance().addObserver(viewHolder.rvRecordView);
        viewHolder.etInputView.setQuestionId(info.getQuestionId());
        viewHolder.etInputView.setText(info.getInputText());
        EventObservable.getInstance().addObserver(viewHolder.etInputView);


        initView(viewHolder, info);


        return convertView;
    }

    /**
     * 初始化view
     *
     * @param viewHolder
     * @param info
     */
    private void initView(ViewHolder viewHolder, CardItemInfo info) {
        switch (info.getCardMode()) {
            case TopAudioBottomImage: {
                viewHolder.lvSelectorList.setVisibility(View.GONE);
                viewHolder.tvQuestion.setVisibility(View.GONE);

                viewHolder.ivDetailPic.setVisibility(View.VISIBLE);
                viewHolder.rvRecordView.setVisibility(View.VISIBLE);

                //显示普通的矩形详情图片
                ImageLoader.getInstance().displayImage(info.getDetailPic(), viewHolder.ivDetailPic);
                //设置语音
                viewHolder.rvRecordView.setClickRf();
                break;
            }
            case TopAudioBottomSelector: {
                viewHolder.lvSelectorList.setVisibility(View.VISIBLE);
                viewHolder.tvQuestion.setVisibility(View.GONE);

                viewHolder.ivDetailPic.setVisibility(View.GONE);
                viewHolder.rvRecordView.setVisibility(View.VISIBLE);

                //
                viewHolder.rvRecordView.setClickRf();
                initList(viewHolder.lvSelectorList, info.getSelectorList());
                break;
            }
            case TopTextBottomImage: {
                viewHolder.lvSelectorList.setVisibility(View.GONE);
                viewHolder.tvQuestion.setVisibility(View.VISIBLE);

                viewHolder.ivDetailPic.setVisibility(View.VISIBLE);
                viewHolder.rvRecordView.setVisibility(View.GONE);

                //
                viewHolder.tvQuestion.setText(info.getQuestions());
                ImageLoader.getInstance().displayImage(info.getDetailPic(), viewHolder.ivDetailPic);
                break;
            }
            case TopTextBottomSelector: {
                viewHolder.lvSelectorList.setVisibility(View.VISIBLE);
                viewHolder.tvQuestion.setVisibility(View.VISIBLE);

                viewHolder.ivDetailPic.setVisibility(View.GONE);
                viewHolder.rvRecordView.setVisibility(View.GONE);

                //
                viewHolder.tvQuestion.setText(info.getQuestions());
                initList(viewHolder.lvSelectorList, info.getSelectorList());
                break;
            }
        }

        //添加点击事件
        viewHolder.leReport.setOnClickListener(this);
        viewHolder.ivHeadPic.setOnClickListener(this);
        viewHolder.btSend.setOnClickListener(this);
        viewHolder.etInputView.setOnClickListener(this);

        viewHolder.ivDetailPic.setOnClickListener(this);

    }

    /**
     * 设置选项数据
     *
     * @param listView
     * @param selectorList
     */
    private void initList(AdapterView listView, List<SelectorInfo> selectorList) {

        SelectorAdapter selectorAdapter = new SelectorAdapter(getmContext(), selectorList);
        listView.setAdapter(selectorAdapter);

    }

    private ViewHolder viewHolder;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_item_head_portrait: {
                if (cardItemClickListener != null)
                    cardItemClickListener.onClickedObject(info.getQuestionId(), CardItemClickMode.HEAD_PIC);
                Log.i(TAG, "onTouch: down head pic");
                break;
            }
            case R.id.card_item_detail_pic: {
                if (cardItemClickListener != null)
                    cardItemClickListener.onClickedObject(info.getQuestionId(), CardItemClickMode.DETAIL_PIC);
                Log.i(TAG, "onTouch: down detail pic");
                break;

            }
            case R.id.card_item_report: {
                if (cardItemClickListener != null)
                    cardItemClickListener.onClickedObject(info.getQuestionId(), CardItemClickMode.Report_BUTTON);
                Log.i(TAG, "onClick: leReport");
                break;

            }
            case R.id.card_item_send: {
                if (cardItemClickListener != null)
                    cardItemClickListener.onClickedObject(info.getQuestionId(), CardItemClickMode.SEND_BUTTON);
                Log.i(TAG, "onClick: send");
                break;
            }
            case R.id.card_item_input: {
                if (cardItemClickListener != null)
                    cardItemClickListener.onClickedObject(info.getQuestionId(), CardItemClickMode.INPUT_BUTTON);
                Log.i(TAG, "onClick: record");
                break;
            }
        }
    }

    private class ViewHolder {
        ImageView ivHeadPic;
        TextView tvQuestion;
        TextView tvUserName;
        ImageView ivDetailPic;
        View btSend;
        View leReport;
        ITextView etInputView;
        RecordView rvRecordView;
        //        View llSelectorLayout;
        SelectorAdapterView lvSelectorList;
    }

    private CardItemClickListener cardItemClickListener;

    public interface CardItemClickListener {
        void onClickedObject(int questionId, CardItemClickMode clickMode);
    }

    public void setCardItemClickListener(CardItemClickListener cardItemClickListener) {
        this.cardItemClickListener = cardItemClickListener;
    }

    public enum CardItemClickMode {
        //头像
        HEAD_PIC,

        //问题图片详情
        DETAIL_PIC,

        ///举报按钮
        Report_BUTTON,

        //发送按钮
        SEND_BUTTON,

        //输入框
        INPUT_BUTTON,

    }

}
