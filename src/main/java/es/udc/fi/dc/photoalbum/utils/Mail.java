package es.udc.fi.dc.photoalbum.utils;

import java.util.Locale;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Utility for sending mails
 */
public class Mail {

    private static final String HOST_NAME = "s246.sam-solutions.net";
    private static final int SMTP_PORT = 25;
    private static final String EMAIL = "";
    private static final String LOGIN = "ssav";
    private static final String PASSWORD = "";
    private static final String SUBJECT_REG_EN = "Registration at \"Photo Albums\"";
    private static final String SUBJECT_PASS_EN = "Password recovery";
    private static final String MESSAGE_REG_EN = "Dear User, thanks for registration. Enjoy!";
    private static final String MESSAGE_PASS_EN = "Dear User, you can login with new password "
            + "and change";
    private static final String SUBJECT_REG_ES = "Registro en \"Photo Albums\"";
    private static final String SUBJECT_PASS_ES = "Recuperar la contraseña";
    private static final String MESSAGE_REG_ES = "Estimado usuario, gracias por inscripción. Disfrute!";
    private static final String MESSAGE_PASS_ES = "Estimado usuario, puedes iniciar sesión con la nueva "
            + "contraseña y cambiarla en Perfil. Nueva contraseña es:";
    private Email email = new SimpleEmail();

    /**
     * @param emailTo
     *            recepient's email
     * @throws EmailException
     */
    public Mail(String emailTo) throws EmailException {
        this.email.setHostName(HOST_NAME);
        this.email.setSmtpPort(SMTP_PORT);
        this.email.setFrom(EMAIL);
        this.email.setAuthenticator(new DefaultAuthenticator(LOGIN,
                PASSWORD));
        this.email.addTo(emailTo);
    }

    /**
     * sends mail on registration
     * 
     * @throws EmailException
     */
    public void sendRegister(Locale locale) throws EmailException {
        if (locale.equals(Locale.US)) {
            this.email.setSubject(SUBJECT_REG_EN);
            this.email.setMsg(MESSAGE_REG_EN);
        } else {
            this.email.setSubject(SUBJECT_REG_ES);
            this.email.setMsg(MESSAGE_REG_ES);
        }
        this.email.send();
    }

    /**
     * sends mail on password forget
     * 
     * @throws EmailException
     */
    public void sendPass(String password, Locale locale)
            throws EmailException {
        if (locale.equals(Locale.US)) {
            this.email.setSubject(SUBJECT_PASS_EN);
            this.email.setMsg(MESSAGE_PASS_EN + password);
        } else {
            this.email.setSubject(SUBJECT_PASS_ES);
            this.email.setMsg(MESSAGE_PASS_ES + password);
        }
        this.email.send();
    }
}
