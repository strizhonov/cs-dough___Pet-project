package by.training.jspsupport;

import by.training.constant.ValuesContainer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDefaultFormatTagSupport extends TagSupport {

    private static final String DATE_FORMAT = ValuesContainer.DEFAULT_DATE_FORMAT;
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
            String dayTime = dateFormatter.format(date);
            out.print(dayTime);
        } catch(IOException ioe) {
            throw new JspException("Error in jsp: " + ioe);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return SKIP_PAGE;
    }
}
