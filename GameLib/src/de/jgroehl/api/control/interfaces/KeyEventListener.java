package de.jgroehl.api.control.interfaces;

public interface KeyEventListener
{

	void charEntered(char c);

	void charDeleted();

	void completedInput();

}
