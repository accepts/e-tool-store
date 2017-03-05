package ua.kiev.toolstore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import ua.kiev.toolstore.model.LineItem;
import ua.kiev.toolstore.model.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class MailUtil {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(MailUtil.class);

    @Autowired
    private MailSender mailSender;

    @Value("${email.login}")
    private String emailName;

    @Value("${email.domain}")
    private String emailDomain;


    public void sendMail(String from, String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }


    public void sendMailAboutConfirmedOrder(Order order){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getEmailFrom(order));
        message.setTo(getEmailTo());
        message.setSubject(getSubject(order));
        message.setText(getContent(order));
        mailSender.send(message);
        LOG.debug("<---Order confirmed, email is sanded");
    }


    // ------------------ Private method ------------------------------------------

    private String getTime(){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    private String getSubject(Order order){
        return "New Order At ( " + getTime() + " )" + "from ( " + getEmailFrom(order) + " )";
    }

    private String  getEmailTo(){
        return emailName + emailDomain;
    }

    private String getEmailFrom(Order order){
        return order.getUser().getEmail();
    }


    private String getContent(Order order){

        String content =
                "================ Main Order's characteristic==================="
                + "\n"
                + "Date: \t\t" + getTime()
                + "\n"
                + "Name: \t\t" + order.getUser().getFirstName() + " " + order.getUser().getLastName()
                + "\n"
                + "E-mail: \t" + order.getUser().getEmail()
                + "\n"
                + "Phone: \t\t" + order.getUser().getPhone()
                + "\n"
                + "Address: \t" + order.getUser().getAddress().getCountry()
                                + ", " +  order.getUser().getAddress().getState()
                                + ", " +  order.getUser().getAddress().getCity() + " ;"
                + "\n"
                + "\n"
                + "---------------------Items in Order--------------------------"
                + "\n"
                ;

        for (LineItem lineItem : order.getLineItems()) {
            content = content + " ~ " + lineItem.getAmount() + " x " + lineItem.getPrice().toString() + " \t | "
                    + lineItem.getProduct().getManufacturer() + " " + lineItem.getProduct().getName() + " ;"
                    + "\n";
        }

        content = content
                + "-------------------------------------------------------------"
                + "\n"
                + "Total items amount: \t" + String.valueOf(order.getItemsAmount()) + " ;"
                + "\n"
                + "Total items sum: \t" + order.getSumOfItems().toString() + " USD;"
                + "\n"
                + "-------------------------------------------------------------"
                + "\n"
                + "\n"
                + "Comment to Order:"
                + "\n"
        ;

        if (order.getComment() != null){
            content = content
                    + order.getComment()
                    + "\n";
        } else {
            content = content
                    + " < No comment for this Order. > "
                    + "\n";
        }

                content = content
                + "=============================================================="
                + "\n"
                ;

        return content;
    }


}
