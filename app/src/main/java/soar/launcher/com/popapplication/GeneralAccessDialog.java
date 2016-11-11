package soar.launcher.com.popapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GeneralAccessDialog extends PopupWindow {

	private final static String TAG = "GeneralAccessDialog";


	protected Context mContext;
	protected LinearLayout vLineLyContentArea;
	protected LinearLayout vLineLyButtonGroup;
	protected LinearLayout vLineLyMessageContainer;
	protected Button vBtnPositive;
	protected Button vBtnNegative;
	protected TextView vTxtTitle;
	protected ImageView vTitleImage;
	protected TextView vTextMessage;
	protected RelativeLayout realLyRoot;
	private View mParentView;

	private DialogDismissLisenter dialogDismissLisenter;



	public GeneralAccessDialog(Context context ,int width, int height) {
		super(width, height);
		this.mContext = context;
		setContentView(initBaseView());
	}

	public GeneralAccessDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	public GeneralAccessDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initBaseView();
	}


	@Override
	public void setContentView(View contentView) {
		super.setContentView(mParentView);
	}

	private View initBaseView(){
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		mParentView = layoutInflater.inflate(R.layout.dialog_general_acess ,null);
		realLyRoot = (RelativeLayout) mParentView.findViewById(R.id.root);
		vBtnPositive = (Button) mParentView.findViewById(R.id.btn_positive);
		vBtnNegative = (Button) mParentView.findViewById(R.id.btn_negative);
		vTxtTitle = (TextView) mParentView.findViewById(R.id.txt_title);
		vTitleImage = (ImageView) mParentView.findViewById(R.id.dialog_access_image);
		vTextMessage = (TextView)mParentView.findViewById(R.id.default_text_message);
		vLineLyContentArea = (LinearLayout) mParentView.findViewById(R.id.vcontent_area);
		vLineLyButtonGroup = (LinearLayout) mParentView.findViewById(R.id.vgroup_btns);
		vLineLyMessageContainer = (LinearLayout) mParentView.findViewById(R.id.vmessage_container);
		return mParentView;
	}


	public GeneralAccessDialog show(){
		showAtLocation(mParentView, Gravity.BOTTOM, 0, 0);
		showAnimation(mParentView);
		return this;
	}


	@Override
	public void dismiss() {
		dismissAnimation(mParentView);
		if(dialogDismissLisenter!= null) {
			dialogDismissLisenter.onDismiss();
		}
	}


	/**
	 * set dialog dismiss listener
	 * @param dialogDismissLisenter
     */
	private void setDialogDismissLisenter(DialogDismissLisenter dialogDismissLisenter){
		this.dialogDismissLisenter = dialogDismissLisenter;
	}

	/**
	 *
	 * @param viewBack
	 */
	private void showAnimation(View viewBack) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(viewBack, "alpha", 0.0f, 1.0f);
			animatorAlpha.setInterpolator(new AccelerateDecelerateInterpolator());
			animatorAlpha.setDuration(300);
			animatorAlpha.start();
		}

	}




	/**
	 *
	 * @param viewBack
	 */
	private void dismissAnimation(View viewBack) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(viewBack, "alpha", 1.0f, 0.0f);
			animatorAlpha.setInterpolator(new DecelerateInterpolator());
			animatorAlpha.setDuration(300);
			animatorAlpha.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					GeneralAccessDialog.super.dismiss();
				}

				@Override
				public void onAnimationCancel(Animator animation) {

				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}
			});
			animatorAlpha.start();
		}
	}


	public LinearLayout getMessageContainer() {
		return vLineLyMessageContainer;
	}

	public Button getPositiveButton() {
		return vBtnPositive;
	}

	public Button getNegativeButton() {
		return vBtnNegative;
	}

	public TextView getTitleTextView() {
		return vTxtTitle;
	}

	public ImageView getTitleImageView() {
		return vTitleImage;
	}

	public TextView getMessageTextView() {
		return vTextMessage;
	}

	public RelativeLayout getRootView() {
		return realLyRoot;
	}

	/**
	 *  Builder ------------
	 */

	public static class Builder{

		private GeneralAccessDialog generalAccessDialog;
		private Context context;

		public Builder(Context context) {
			this.context = context;
			init();
		}


		private void init(){
			generalAccessDialog = new GeneralAccessDialog(context , ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			generalAccessDialog.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			generalAccessDialog.setFocusable(true);
			generalAccessDialog.mParentView.setFocusableInTouchMode(true);
			generalAccessDialog.mParentView.setFocusable(true);
//        popWindow.setBackgroundDrawable(new BitmapDrawable());
			generalAccessDialog.setOutsideTouchable(true);
//        popWindow.setAnimationStyle(R.style.PopupAnimation);
			generalAccessDialog.mParentView.setOnKeyListener(new View.OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
						generalAccessDialog.dismiss();
						return true;
					}
					return false;
				}
			});
			generalAccessDialog.realLyRoot.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					generalAccessDialog.dismiss();
				}
			});
		}


		/**
		 * settitle
		 * @param title
         */
		public Builder setTitle(String title){
			generalAccessDialog.vTxtTitle.setVisibility(View.VISIBLE);
			generalAccessDialog.vTxtTitle.setText(title);
			return this;
		}

		/**
		 * settitle
		 * @param titleRes
         */
		public Builder setTitle(int titleRes){
			generalAccessDialog.vTxtTitle.setVisibility(View.VISIBLE);
			generalAccessDialog.vTxtTitle.setText(context.getString(titleRes));
			return this;
		}


		/**
		 * set title color
		 * @param colorRes
         */
		public Builder setTitleColor(int colorRes){
			generalAccessDialog.vTxtTitle.setTextColor(context.getResources().getColor(colorRes));
			return this;
		}

		/**
		 *  set title size
		 * @param sizeRes
         */
		public Builder setTitleSize(int sizeRes){
			generalAccessDialog.vTxtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP ,context.getResources().getDimensionPixelSize(sizeRes));
			return this;
		}

		/**
		 * set titile visible
		 * @param visible
         */
		public Builder setTitleVisible(boolean visible){
			generalAccessDialog.vTxtTitle.setVisibility(visible ? View.VISIBLE : View.GONE);
			return this;
		}



		/**
		 * set image
		 * @param imageRes
         */
		public Builder setTitleImage(int imageRes){
			generalAccessDialog.vTitleImage.setVisibility(View.VISIBLE);
			generalAccessDialog.vTitleImage.setImageResource(imageRes);
			return this;
		}


		/**
		 * set image
		 * @param bitmap
         */
		public Builder setTitleImage(Bitmap bitmap){
			generalAccessDialog.vTitleImage.setVisibility(View.VISIBLE);
			generalAccessDialog.vTitleImage.setImageBitmap(bitmap);
			return this;
		}


		/**
		 * set image
		 * @param drawable
         */
		public Builder setTitleImage(Drawable drawable){
			generalAccessDialog.vTitleImage.setVisibility(View.VISIBLE);
			generalAccessDialog.vTitleImage.setImageDrawable(drawable);
			return this;
		}


		/**
		 * set image visible
		 * @param visible
         */
		public Builder setTitleImageVisible(boolean visible){
			generalAccessDialog.vTitleImage.setVisibility(visible ? View.VISIBLE : View.GONE);
			return this;
		}


		/**
		 * set message
		 * @param message
         */
		public Builder setMessage(String message){
			generalAccessDialog.vTextMessage.setVisibility(View.VISIBLE);
			generalAccessDialog.vTextMessage.setText(message);
			return this;
		}

		/**
		 * set message
		 * @param messageRes
         */
		public Builder setMessage(int messageRes){
			generalAccessDialog.vTextMessage.setVisibility(View.VISIBLE);
			generalAccessDialog.vTextMessage.setText(context.getString(messageRes));
			return this;
		}



		/**
		 * set title color
		 * @param colorRes
		 */
		public Builder setMessageColor(int colorRes){
			generalAccessDialog.vTextMessage.setTextColor(context.getResources().getColor(colorRes));
			return this;
		}

		/**
		 *  set title size
		 * @param sizeRes
		 */
		public Builder setMessageSize(int sizeRes){
			generalAccessDialog.vTextMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP ,context.getResources().getDimensionPixelSize(sizeRes));
			return this;
		}

		/**
		 * set titile visible
		 * @param visible
		 */
		public Builder setMessageVisible(boolean visible){
			generalAccessDialog.vTextMessage.setVisibility(visible ? View.VISIBLE : View.GONE);
			return this;
		}


		/**
		 * set message custom view
		 * @param view
         */
		public Builder setMessageView(View view){
			try {
				generalAccessDialog.vLineLyMessageContainer.removeAllViews();
				generalAccessDialog.vLineLyMessageContainer.addView(view);
			}catch (Exception e){
				Log.d(TAG , "Your view has parent ?");
			}
			return this;
		}


		/**
		 * set message container size
		 * @param wid
		 * @param hei
         */
		public Builder setMessageContainerSize(int wid , int hei){
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) generalAccessDialog.vLineLyMessageContainer.getLayoutParams();
			layoutParams.width = wid;
			layoutParams.height = hei;
			generalAccessDialog.vLineLyMessageContainer.setLayoutParams(layoutParams);
			return this;
		}


		/**
		 * set message container size
		 * @param wid
         * @param hei
         */
		public Builder setMessageContainerSize(float wid , float hei){
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) generalAccessDialog.vLineLyMessageContainer.getLayoutParams();
			layoutParams.width = (int)wid;
			layoutParams.height = (int)hei;
			generalAccessDialog.vLineLyMessageContainer.setLayoutParams(layoutParams);
			return this;
		}


		/**
		 * setMessageContainerVisible
		 * @param visible
         */
		public Builder setMessageContainerVisible(boolean visible){
			generalAccessDialog.vLineLyMessageContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
			return this;
		}


		/**
		 * set button
		 * @param buttonStr
		 * @param onClickListener
         */
		public Builder setNegativeButton(String buttonStr , View.OnClickListener onClickListener){
			generalAccessDialog.vBtnNegative.setVisibility(View.VISIBLE);
			generalAccessDialog.vBtnNegative.setText(buttonStr);
			generalAccessDialog.vBtnNegative.setOnClickListener(onClickListener);
			return this;
		}

		/**
		 * setNegativeButton
		 * @param buttonStrRes
		 * @param onClickListener
         */
		public Builder setNegativeButton(int buttonStrRes , View.OnClickListener onClickListener){
			generalAccessDialog.vBtnNegative.setVisibility(View.VISIBLE);
			generalAccessDialog.vBtnNegative.setText(context.getString(buttonStrRes));
			generalAccessDialog.vBtnNegative.setOnClickListener(onClickListener);
			return this;
		}


		/**
		 * setNegativeButtonBackgroud
		 * @param drawRes
         */
		public Builder setNegativeButtonBackgroud(int drawRes){
			generalAccessDialog.vBtnNegative.setBackgroundResource(drawRes);
			return this;
		}


		/**
		 * setNegativeButtonVisible
		 * @param visible
         */
		public Builder setNegativeButtonVisible(boolean visible){
			generalAccessDialog.vBtnNegative.setVisibility(visible ? View.VISIBLE : View.GONE);
			return this;
		}


		/**
		 *
		 * @param buttonStr
		 * @param onClickListener
         */
		public Builder setPositiveButton(String buttonStr , View.OnClickListener onClickListener){
			generalAccessDialog.vBtnPositive.setVisibility(View.VISIBLE);
			generalAccessDialog.vBtnPositive.setText(buttonStr);
			generalAccessDialog.vBtnPositive.setOnClickListener(onClickListener);
			return this;
		}

		/**
		 *
		 * @param buttonStrRes
		 * @param onClickListener
         */
		public Builder setPositiveButton(int buttonStrRes , View.OnClickListener onClickListener){
			generalAccessDialog.vBtnPositive.setVisibility(View.VISIBLE);
			generalAccessDialog.vBtnPositive.setText(context.getString(buttonStrRes));
			generalAccessDialog.vBtnPositive.setOnClickListener(onClickListener);
			return this;
		}


		/**
		 *
		 * @param drawRes
         */
		public Builder setPositiveButtonBackgroud(int drawRes){
			generalAccessDialog.vBtnPositive.setBackgroundResource(drawRes);
			return this;
		}


		/**
		 *
		 * @param visible
         */
		public Builder setPositiveButtonVisible(boolean visible){
			generalAccessDialog.vBtnPositive.setVisibility(visible ? View.VISIBLE : View.GONE);
			return this;
		}


		/**
		 * set dialog size
		 * @param wid
         * @param hei
         */
		public Builder setDialogSize(int wid , int hei){
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) generalAccessDialog.vLineLyContentArea.getLayoutParams();
			layoutParams.width = wid;
			layoutParams.height = hei;
			generalAccessDialog.vLineLyContentArea.setLayoutParams(layoutParams);
			return this;
		}


		/**
		 * set dialog size
		 * @param wid
         * @param hei
         */
		public Builder setDialogSize(float wid , float hei){
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) generalAccessDialog.vLineLyContentArea.getLayoutParams();
			layoutParams.width = (int)wid;
			layoutParams.height =(int)hei;
			generalAccessDialog.vLineLyContentArea.setLayoutParams(layoutParams);
			return this;
		}


		/**
		 * set dialog out back color
		 * @param color
         */
		public Builder setDialogOutBackColor(int color){
			generalAccessDialog.realLyRoot.setBackgroundColor(color);
			return this;
		}

		/**
		 * set dialog back color
		 * @param color
         */
		public Builder setDialogBackColor(int color){
			generalAccessDialog.vLineLyContentArea.setBackgroundColor(color);
			return this;
		}


		/**
		 * set dialog dismiss listener
		 * @param dialogDismissLisenter
		 */
		public Builder setDialogDismissLisenter(DialogDismissLisenter dialogDismissLisenter){
			generalAccessDialog.setDialogDismissLisenter(dialogDismissLisenter);
			return this;
		}


		/**
		 * build
		 * @return
         */
		public GeneralAccessDialog build(){
			return generalAccessDialog;
		}

	}


    /**
	 * dialog dismiss lisenter
	 * if you do something after dismissing dialog
	 * you can set this lisenter
	 */
	public interface DialogDismissLisenter{
		void onDismiss();
	}


}
