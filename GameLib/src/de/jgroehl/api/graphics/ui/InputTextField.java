package de.jgroehl.api.graphics.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.inputmethod.InputMethodManager;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.control.interfaces.KeyEventListener;

/**
 * 
 * @author Janek Gröhl
 *
 */
public class InputTextField extends AbstractClickableElement
{

	private String text;
	private final int maximumCharacters;
	private final Label label;
	private RectF fieldRect;
	private final Paint rectPaint = new Paint();
	private final float width;
	private final float height;

	public InputTextField(float xPosition, float yPosition, float width, float height, int maximumCharacters,
			Context context)
	{
		super(xPosition, yPosition, width, height, null, context);
		EventCallback eventCallback = new EventCallback()
		{
			InputMethodManager keyboard;
			private BaseGameHandler gameHandler;

			private KeyEventListener textEditingListener = new KeyEventListener()
			{

				@Override
				public void completedInput()
				{
					keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
					gameHandler.getGamePanel().removeKeyEventListener(this);
				}

				@Override
				public void charEntered(char c)
				{
					if (InputTextField.this.getText().length() < InputTextField.this.getMaximumCharacters())
						InputTextField.this.setText(InputTextField.this.getText() + String.valueOf(c));
				}

				@Override
				public void charDeleted()
				{
					if (InputTextField.this.getText().length() > 0)
						InputTextField.this.setText(InputTextField.this.getText().substring(0,
								InputTextField.this.getText().length() - 1));
				}
			};

			@Override
			public void action(BaseGameHandler gameHandler)
			{
				if (keyboard == null)
				{
					keyboard = (InputMethodManager) gameHandler.getContext().getSystemService(
							Context.INPUT_METHOD_SERVICE);
				}
				keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
				if (this.gameHandler == null)
				{
					this.gameHandler = gameHandler;
				}
				this.gameHandler.getGamePanel().setKeyEventListener(textEditingListener);
			}
		};

		setCallback(eventCallback);

		this.maximumCharacters = maximumCharacters;
		this.width = width;
		this.height = height;
		text = "";
		rectPaint.setStyle(Style.FILL_AND_STROKE);
		rectPaint.setColor(Color.rgb(20, 20, 20));
		label = new Label(text, xPosition, yPosition + height * 0.8f, context);
		label.setTextHeight(height * 0.8f);
	}

	protected int getMaximumCharacters()
	{
		return maximumCharacters;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	@Override
	public void draw(Canvas c)
	{
		if (fieldRect == null)
		{
			fieldRect = new RectF(xPosition * c.getWidth(), yPosition * c.getHeight(), (xPosition + width)
					* c.getWidth(), (yPosition + height) * c.getHeight());
		}
		c.drawRect(fieldRect, rectPaint);
		label.setText(text);
		label.draw(c);
	}

}
