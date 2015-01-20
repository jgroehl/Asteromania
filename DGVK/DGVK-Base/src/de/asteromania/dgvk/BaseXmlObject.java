package de.asteromania.dgvk;

public abstract class BaseXmlObject
{

	/**
	 * This constructor may be called to initialize a {@link BaseXmlObject}
	 * without having an underlying Xml-Structure. <br />
	 * <br />
	 * May especially be useful for creating a new {@link BaseXmlObject} in oder
	 * to serialize it via the {@link #toXml()} Method.
	 */
	protected BaseXmlObject()
	{

	};

	/**
	 * This constructor calls the {@link #initialize(String)} method to create a
	 * {@link BaseXmlObject} from the given information of the xml string.
	 * 
	 * @param xml
	 *            the xml given to create this object from.
	 */
	protected BaseXmlObject(String xml)
	{
		initialize(xml);
	};

	public abstract String toXml();

	protected abstract void initialize(String xml);

}
