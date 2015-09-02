package com.studio.application;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SectionIndexer;

import com.studio.application.request.GetLyric;
import com.studio.jframework.network.Task;
import com.studio.jframework.widget.listview.IndexableListView;
import com.studio.jframework.widget.listview.StringMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;


public class IndexableListViewActivity extends ActionBarActivity {

    public static final String TAG = "IndexableListViewActivity";

	private ArrayList<String> mItems;
	private IndexableListView mListView;

    private EditText mInput;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mItems = new ArrayList<String>();
        mItems.add("阿斯达克");
        mItems.add("沃尔夫");
        mItems.add("阿斯蒂芬");
        mItems.add("还挺热");
        mItems.add("v不是");
        mItems.add("123萨芬");
        mItems.add("和人");
        mItems.add("完全");
        mItems.add("保存");
        mItems.add("就没有");
        mItems.add("破");
        mItems.add("偶");
        mItems.add("玫瑰花");
        mItems.add("格式");
        mItems.add("兔兔");
        mItems.add("而");
        mItems.add("水电费");
        mItems.add("需");
        mItems.add("持续");
        mItems.add("阿斯蒂芬");
        mItems.add("玩儿");
        mItems.add("太热");
        mItems.add("羊肉汤");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
        Collections.sort(mItems);

        ContentAdapter adapter = new ContentAdapter(this, android.R.layout.simple_list_item_1, mItems);
        
        mListView = (IndexableListView) findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);
        mInput = (EditText) findViewById(R.id.input);
        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String url = "http://www.baidu.com";
                Task task = new GetLyric(IndexableListViewActivity.this, url, TAG, null, 0, false);
                EventBus.getDefault().post(task);
            }
        });
    }
    
    private class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {
    	
    	private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	
		public ContentAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public int getPositionForSection(int section) {
			// If there is no item for current section, previous section will be selected
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						// For numeric section
						for (int k = 0; k <= 9; k++) {
							if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}
    }
}