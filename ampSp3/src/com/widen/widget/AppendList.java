package com.widen.widget;

import com.widen.R;
import com.widen.util.MapForImg;
import com.widen.util.ShowCatCallBack;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;



/**
 * @author Aiven
 * @date 2012/07/27 @ 上下加载ListView
 * */
public class AppendList extends ListView implements OnScrollListener{

	private static final int STATE_RELEASE_FRESH = 1001;// 释放刷新�?

	private static final int STATE_INTO_RELESE = 1002;// 未进入释放刷新�?

	private static final int STATE_REFRESHING = 1003;// 正在刷新�?

	private static final int STATE_DONE = 1004;// 初始�?

	public static final int DERECTION_UP = 1005;// 上加�?

	public static final int DERECTION_DOWN = 1006;// 底部加载

	public boolean isHiddenFooter;

	private int mState;

	private View mHeadView;

	private View mFootView;

	private int mHeadHeight;

	private int mFootHeight;

	private int mHeadResouceId;

	private int mFootResouceId;

	// *** 记录ListView 当前显示信息 **/////////
	private int mDataTotal;

	private int mDataFirstIndex;

	private int mDataViewCount;

	private static final float RATIO = 2.8f;

	private static final float RATIO2 = 1.4f;

	private int mStartY;

	private boolean mHeadRecard;

	private boolean mFootRecard;

	private boolean isBack;

	private View mHeadAnimationView;

	private View mFootAnimationView;

	private Animation mHeadStartAnimation;

	private Animation mHeadBackAnimation;

	private Animation mFootStartAnimation;

	private Animation mFootBackAnimation;

	private OnLoadingListener mLoadingListener;

	private View mClickView;

	private int mClickViewId;

	private MyClickListener myClickListener;

	private OnAppItemClickListener mAppClickLitener;

	private ItemClickLener mItemClickListener;

	private int mListDataCount;// 数据总数

	private int mListDataIndex;// 数据第一条索�?
	
	public boolean cannotPull;
	

	
	private static boolean isToRight;

	/***
	 * 属�?说明�?
	 * 
	 * @headView：ListView的HeaderView对应的布�?���?
	 * @footView：ListView的FooterView对应的布�?���?
	 * @isHiddenFooter:boolean类型，默认为ture，是否隐藏底部FooterView
	 * @mClickViewId:当isHiddenFooter值为false的情况下，指定FooterView�?��层布�?��Id 
	 *                                                               作用是：isHiddenFooter为false的情况下只能点击加载
	 *                                                               ，拖动不能加载，
	 *                                                               通腾讯微博列�?
	 * 
	 * @param context
	 * @param attrs
	 */
	public AppendList(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initialize();
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.APPList);
		mHeadResouceId = a.getResourceId(R.styleable.APPList_headView, 0);
		mFootResouceId = a.getResourceId(R.styleable.APPList_footView, 0);
		isHiddenFooter = a.getBoolean(R.styleable.APPList_hiddenFooter, true);
		mClickViewId = a.getResourceId(R.styleable.APPList_footerClickId, 0);
		initData(context);
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mHeadView = null;
		mFootView = null;
		mHeadHeight = -1;
		mFootHeight = -1;
		mHeadResouceId = 0;
		mFootResouceId = 0;
		mState = STATE_DONE;
		mHeadRecard = false;
		mFootRecard = false;
		mHeadAnimationView = null;
		mFootAnimationView = null;
		mClickView = null;
	}

	/**
	 * 初始化数�?
	 * 
	 * @param context
	 */
	private void initData(Context context) {
		// TODO Auto-generated method stub
		if (mHeadResouceId != 0) {
			mHeadView = LayoutInflater.from(context).inflate(mHeadResouceId,
					null);
			if (mHeadView != null) {
				addHeaderView(mHeadView);
				measureView(mHeadView);
				mHeadHeight = mHeadView.getMeasuredHeight();
				setPaddingTop(mHeadView, -1 * mHeadHeight);
			}
		}
		if (mFootResouceId != 0) {
			mFootView = LayoutInflater.from(context).inflate(mFootResouceId,
					null);
			if (mFootView != null) {
				addFooterView(mFootView);
				measureView(mFootView);
				mFootHeight = mFootView.getMeasuredHeight();
				if (!isHiddenFooter) {
					// 添加点击事件
					showView(mFootView);
					if (mClickViewId != 0) {
						mClickView = (View) findViewById(mClickViewId);
						mClickView.setClickable(true);
						myClickListener = new MyClickListener();
						mClickView.setOnClickListener(myClickListener);
					}

				} else {
					setPaddingTop(mFootView, -1 * mFootHeight);
				}
			}
		}
		setOnScrollListener(this);
		mItemClickListener = new ItemClickLener();
		setOnItemClickListener(mItemClickListener);
	}
	

	private void setPaddingTop(View view, int top) {
		view.setPadding(0, top, 0, 0);
	}

	private void setPadddingBottom(View view, int bottom) {
		view.setPadding(0, 0, 0, bottom);
	}

	private void showView(View view) {
		view.setPadding(0, 0, 0, 0);
	}
	


	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub		
		if(!cannotPull){
			if (mHeadView == null && mFootView == null)
				return super.onTouchEvent(ev);
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!mHeadRecard && mDataFirstIndex == 0 && !mFootRecard) {
					mHeadRecard = true;
					mFootRecard = false;
					mStartY = (int) ev.getY();
				}
				if (!mFootRecard && mDataFirstIndex == mDataTotal - mDataViewCount
						&& !mHeadRecard) {
					mFootRecard = true;
					mHeadRecard = false;
					mStartY = (int) ev.getY();
				}
				break;
			case MotionEvent.ACTION_UP:
				// 如果不在刷新的时�?
				if (mState != STATE_REFRESHING) {
					// 未达到刷�?
					if (mState == STATE_INTO_RELESE) {
						// 修改状�?为初始�?
						mState = STATE_DONE;
						if (mHeadRecard)
							changeHeadView();
						else if (mFootRecard) {
							changeFootView();
						}
					}
					// 进入刷新�?
					if (mState == STATE_RELEASE_FRESH) {
						// 修改状�?为刷新�?
						mState = STATE_REFRESHING;
						if (mHeadRecard)
							changeHeadView();
						else if (mFootRecard) {
							changeFootView();
						}
					} else {
					}
					mHeadRecard = false;
					mFootRecard = false;
				} else {
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int tempY = (int) ev.getY();
				if (!mHeadRecard && mDataFirstIndex == 0 && !mFootRecard) {
					mHeadRecard = true;
					mFootRecard = false;
					mStartY = tempY;
				}
	
				if (!mFootRecard && mDataFirstIndex == mDataTotal - mDataViewCount
						&& !mHeadRecard) {
					mFootRecard = true;
					mHeadRecard = false;
					mStartY = tempY;
				}
				if (mHeadRecard && mHeadView != null) {
					if (mState != STATE_REFRESHING) {
						// 松手可以刷新�?
						if (mState == STATE_RELEASE_FRESH) {
							setSelection(0);
							// �?��推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
							if (((tempY - mStartY) / RATIO < mHeadHeight)
									&& (tempY - mStartY) > 0) {
								mState = STATE_INTO_RELESE;
								changeHeadView();
							}
							// �?��子推到顶�?
							else if (tempY - mStartY <= 0) {
								mState = STATE_DONE;
								changeHeadView();
							} else {
							}
						}
						if (mState == STATE_INTO_RELESE) {
							setSelection(0);
							// 下拉到可以进入RELEASE_TO_REFRESH的状�?
							if ((tempY - mStartY) / RATIO >= mHeadHeight) {
								mState = STATE_RELEASE_FRESH;
								isBack = true;
								changeHeadView();
							}
							// 上推到顶�?
							else if (tempY - mStartY <= 0) {
								mState = STATE_DONE;
								changeHeadView();
							}
						}
	
						if (mState == STATE_DONE) {
							if (tempY - mStartY > 0) {
								mState = STATE_INTO_RELESE;
								changeHeadView();
							}
						}
	
						if (mState == STATE_INTO_RELESE) {
							setPaddingTop(mHeadView,
									(int) (-1 * mHeadHeight + (tempY - mStartY)
											/ RATIO));
						}
	
						// 更新headView的paddingTop
						if (mState == STATE_RELEASE_FRESH) {
							setPaddingTop(mHeadView, (int) ((tempY - mStartY)
									/ RATIO - mHeadHeight));
						}
					}
	
				} else if (mFootRecard && isHiddenFooter && mFootView != null) {
					/*if (mState != STATE_REFRESHING) {
						// 松手可以刷新�?
						if (mState == STATE_RELEASE_FRESH) {
							// �?��推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
							if (((mStartY - tempY) / RATIO < mFootHeight)
									&& (tempY - mStartY) < 0) {
								mState = STATE_INTO_RELESE;
								changeFootView();
							}
							// �?��子推到顶�?
							else if (tempY - mStartY >= 0) {
								mState = STATE_DONE;
								changeFootView();
							} else {
							}
						}
						if (mState == STATE_INTO_RELESE) {
							// 下拉到可以进入RELEASE_TO_REFRESH的状�?
							if ((mStartY - tempY) / RATIO >= mFootHeight) {
								mState = STATE_RELEASE_FRESH;
								isBack = true;
								changeFootView();
							}
							// 上推到顶�?
							else if (tempY - mStartY >= 0) {
								mState = STATE_DONE;
								changeFootView();
							}
						}
						if (mState == STATE_DONE) {
							if (tempY - mStartY < 0) {
								mState = STATE_INTO_RELESE;
								changeFootView();
							}
						}
						if (mState == STATE_INTO_RELESE) {
							setPadddingBottom(mFootView,
									(int) (-1 * mFootHeight + (mStartY - tempY)
											/ RATIO2));
						}
						// 更新headView的paddingTop
						if (mState == STATE_RELEASE_FRESH) {
							setPadddingBottom(mFootView, (int) ((mStartY - tempY)
									/ RATIO2 - mFootHeight));
						}
					}*/
				}
	
				break;
			}
		
		}

		return super.onTouchEvent(ev);
	}

	private void changeHeadView() {
		switch (mState) {
		case STATE_DONE:
			setPaddingTop(mHeadView, -1 * mHeadHeight);
			if (mHeadAnimationView != null)
				mHeadAnimationView.clearAnimation();
			break;
		case STATE_REFRESHING:
			showView(mHeadView);
			if (mHeadAnimationView != null) {
				mHeadAnimationView.clearAnimation();
				mHeadAnimationView.setVisibility(View.GONE);
			}
			if (mLoadingListener != null)
				mLoadingListener.Loading(mHeadView, DERECTION_UP,
						mListDataIndex);
			break;
		case STATE_INTO_RELESE:
			if (mHeadAnimationView != null)
				mHeadAnimationView.clearAnimation();
			if (isBack) {
				// 返回动画
				isBack = false;
				if (mHeadAnimationView != null) {
					mHeadAnimationView.clearAnimation();
					mHeadAnimationView.startAnimation(mHeadBackAnimation);
				}
				if (mLoadingListener != null) {
					mLoadingListener.cancel(mHeadView, DERECTION_UP);
				}
			}
			break;
		case STATE_RELEASE_FRESH:
			// �?��翻转动画
			if (mLoadingListener != null) {
				mLoadingListener.prepareLoadding(mHeadView, DERECTION_UP);
			}
			if (mHeadAnimationView != null) {
				mHeadAnimationView.setVisibility(View.VISIBLE);
				mHeadAnimationView.clearAnimation();
				mHeadAnimationView.startAnimation(mHeadStartAnimation);
			}
			break;
		}
	}

	private void changeFootView() {
		switch (mState) {
		case STATE_DONE:
			if (isHiddenFooter)
				setPaddingTop(mFootView, -1 * mFootHeight);
			if (mFootAnimationView != null)
				mFootAnimationView.clearAnimation();
			break;
		case STATE_REFRESHING:
			showView(mFootView);
			if (mFootAnimationView != null) {
				mFootAnimationView.clearAnimation();
				mFootAnimationView.setVisibility(View.GONE);
			}
			if (mLoadingListener != null && isHiddenFooter)
				mLoadingListener.Loading(mFootView, DERECTION_DOWN,
						mListDataIndex + getListCount());
			break;
		case STATE_INTO_RELESE:
			if (mFootAnimationView != null)
				mFootAnimationView.clearAnimation();
			if (isBack) {
				// 返回动画
				isBack = false;
				if (mFootAnimationView != null) {
					mFootAnimationView.clearAnimation();
					mFootAnimationView.startAnimation(mFootBackAnimation);
				}
				if (mLoadingListener != null) {
					mLoadingListener.cancel(mFootView, DERECTION_DOWN);
				}
			}
			break;
		case STATE_RELEASE_FRESH:
			// �?��翻转动画
			if (mLoadingListener != null) {
				mLoadingListener.prepareLoadding(mFootView, DERECTION_DOWN);
			}
			if (mFootAnimationView != null && mFootStartAnimation != null) {
				mFootAnimationView.setVisibility(View.VISIBLE);
				mFootAnimationView.clearAnimation();
				mFootAnimationView.startAnimation(mFootStartAnimation);
			}
			break;
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		mDataTotal = totalItemCount;
		mDataFirstIndex = firstVisibleItem;
		mDataViewCount = visibleItemCount;
	}

	/**
	 * 设置顶部加载动画
	 * 
	 * @param startAnimation
	 *            拖拽�?��加载动画
	 * @param backAnimation
	 *            放弃加载动画
	 * @param viewId
	 */
	public void setTopAniamtionInfo(Animation startAnimation,
			Animation backAnimation, int viewId) {
		if (mHeadView != null) {
			this.mHeadStartAnimation = startAnimation;
			this.mHeadBackAnimation = backAnimation;
			mHeadAnimationView = mHeadView.findViewById(viewId);
		}
	}

	/**
	 * 设置底部加载动画
	 * 
	 * @param startAnimation
	 *            拖拽�?��加载动画
	 * @param backAnimation
	 *            放弃加载动画
	 * @param viewId
	 */
	public void setBottomAnimationInfo(Animation startAnimation,
			Animation backAnimation, int viewId) {
		if (mFootView != null) {
			this.mFootBackAnimation = backAnimation;
			this.mFootStartAnimation = startAnimation;
			mFootAnimationView = mFootView.findViewById(viewId);
		}
	}

	/**
	 * 加载完毕后调用，这个函数在数据加载完毕后必须手动调用，否则ListView出问�?
	 * 
	 * @param derection
	 */
	public void compateRefresh(int derection) {
		mState = STATE_DONE;
		mHeadRecard = false;
		mFootRecard = false;
		if (derection == DERECTION_UP)
			changeHeadView();
		else if (derection == DERECTION_DOWN) {
			changeFootView();
		}
		if (mLoadingListener != null) {
			mLoadingListener.LoadOver(mHeadView, derection);
		}
	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mLoadingListener != null) {
				mLoadingListener.Loading(mFootView, DERECTION_DOWN,
						mListDataIndex + getListCount());
			}
		}

	}

	class ItemClickLener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (mAppClickLitener == null) {
				return;
			}
			if (!isHiddenFooter && position == getAdapter().getCount() - 1
					&& mFootView != null) {// 尾部不隐�?
				return;
			} else if (isHiddenFooter
					&& position == getAdapter().getCount() - 1
					&& mFootView != null) {// 尾部隐藏
				return;
			} else {
				if (mState == STATE_REFRESHING)
					return;
				else {
					if (mHeadView != null && position != 0)
						mAppClickLitener.onItemClick(parent, view,
								position - 1, id - 1);
					else if (mHeadView == null) {
						mAppClickLitener
								.onItemClick(parent, view, position, id);
					}
				}
			}
		}

	}

	private int getListCount() {
		if (mHeadView != null && mFootView != null)
			return getAdapter().getCount() - 3;
		else if (mHeadView != null && mFootView == null) {
			return getAdapter().getCount() - 2;
		} else if (mHeadView == null && mFootView != null) {
			return getAdapter().getCount() - 2;
		} else if (mHeadView == null && mFootView == null) {
			return getAdapter().getCount() - 1;
		}
		return 0;
	}

	/**
	 * 设置加载监听�?
	 * 
	 * @param listener
	 */
	public void setLoadingListener(OnLoadingListener listener) {
		this.mLoadingListener = listener;
	}

	/**
	 * 设置 ListView Item单击Click事件
	 * 
	 * @param ItemClickListener
	 */
	public void SetOnAppItemClickListener(
			OnAppItemClickListener ItemClickListener) {
		this.mAppClickLitener = ItemClickListener;
	}

	/**
	 * ListView Item单击事件
	 * 
	 * @author Aiven
	 * 
	 */
	public interface OnAppItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id);
	}

	/**
	 * 获取设置的ListView可放数据条数，一遍情况下不起作用
	 * 
	 * @return
	 */
	public int getmListDataCount() {
		return mListDataCount;
	}

	/**
	 * 设置总的数据条数，可以不设置
	 * 
	 * @param mListDataIndex
	 */
	public void setListDataCount(int listDataCount) {
		this.mListDataCount = listDataCount;
	}

	public int getListDataIndex() {
		return mListDataIndex;
	}

	/**
	 * 设置给定数据第一条索引，�?��情况不涉及到
	 * 
	 * @return
	 */
	public void setListDataIndex(int mListDataIndex) {
		this.mListDataIndex = mListDataIndex;
	}

	/**
	 * 数据加载监听接口
	 * 
	 * @author Aiven
	 * 
	 */
	public interface OnLoadingListener {
		/**
		 * 拖拽到松手可加载时调�?
		 * 
		 * @param parentView
		 * @param derection
		 */
		public void prepareLoadding(View parentView, int derection);

		/**
		 * �?��加载数据
		 * 
		 * @param parentView
		 * @param derection
		 *            加载方向 向上：DERECTION_UP 向下�?DERECTION_DOWN
		 * @param dataIndex
		 *            数据加载索引，一般用不到
		 */
		public void Loading(View parentView, int derection, int dataIndex);

		/**
		 * 加载完毕后调�?
		 * 
		 * @param parentView
		 * @param derection
		 */
		public void LoadOver(View parentView, int derection);

		/**
		 * 手势滑动�?取消松手加载 的情况下调用
		 * 
		 * @param parentView
		 * @param derection
		 */
		public void cancel(View parentView, int derection);

	}

	

}
