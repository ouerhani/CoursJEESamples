package converters;

import entities.Sites;
import sessionBeans.SitesFacade;
import managedBeans.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
public class SitesConverter implements Converter
    {
    @EJB
    private SitesFacade ejbFacade;
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value)
        {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value))
            {
            return null;
            }
        return this.ejbFacade.find(getKey(value));
        }
    java.lang.Integer getKey(String value)
        {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
        }
    String getStringKey(java.lang.Integer value)
        {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
        }
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object)
        {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0))
            {
            return null;
            }
        if (object instanceof Sites)
            {
            Sites o = (Sites) object;
            return getStringKey(o.getSiteID());
            }
        else
            {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]
                {
                object, object.getClass().getName(), Sites.class.getName()
                });
            return null;
            }
        }
    }
