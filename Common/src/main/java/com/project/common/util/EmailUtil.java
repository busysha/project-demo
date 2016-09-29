package com.project.common.util;


import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class EmailUtil {
    private String username;
    private String password;
    private String smtpHost;
    private String subject;
    private String senders;
    private String sendersname;
    private Map<String, String> recipients;
    private String content;
    private String contentEncoding;
    private int contentType;
    private File[] attaches;
    private String fileNameEncoding;
    private Session session;
    private static Logger log = Logger.getLogger(EmailUtil.class.getName());
    public static final int CONTENT_TYPE_TEXT = 0;
    public static final int CONTENT_TYPE_HTML = 1;

    public EmailUtil(String smtpHost, String username, String password, String senders, String sendersname, Map<String, String> recipients, String subject, String content, String contentEncoding, int contentType, File[] files, String fileNameEncoding) {
        this.smtpHost = smtpHost;
        this.username = username;
        this.password = password;
        this.senders = senders;
        this.recipients = recipients;
        this.subject = subject;
        this.content = content;
        this.contentEncoding = contentEncoding;
        this.contentType = contentType;
        this.attaches = files;
        this.fileNameEncoding = fileNameEncoding;
        this.sendersname = sendersname;
    }

    public boolean send() throws MessagingException, UnsupportedEncodingException {
        Transport trans = null;
        try {
            if (this.smtpHost.isEmpty() || this.username.isEmpty() || this.password.isEmpty() || this.senders.isEmpty() || recipients == null || recipients.size() == 0) {
                log.error("参数异常！！！");
                return false;
            }
            Properties p = new Properties();
            p.put("mail.smtp.host", this.smtpHost);
            p.put("mail.smtp.auth", "true");
            this.session = Session.getDefaultInstance(p);
            trans = this.session.getTransport("smtp");
            trans.connect(this.username, this.password);
            MimeMultipart multiPart = new MimeMultipart();
            MimeBodyPart mainBodyPart = new MimeBodyPart();

            /*if (this.contentEncoding == null) {
                this.content = new String(this.content.getBytes(),"ISO-8859-1");
            } else {
                this.content = new String(this.content.getBytes(this.contentEncoding), "ISO-8859-1");
            }*/

            if (this.contentType == 1) {
                mainBodyPart.setContent(this.content, "text/html;charset=UTF-8");
            } else {
                mainBodyPart.setContent(this.content, "text/plain;charset=UTF-8");
            }
            multiPart.addBodyPart(mainBodyPart);

            if ((this.attaches != null) && (this.attaches.length > 0)) {
                for (int i = 0; i < this.attaches.length; i++) {
                    MimeBodyPart tmpBody = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(this.attaches[i]);
                    tmpBody.setDataHandler(new DataHandler(fds));
                    String fileName = this.attaches[i].getName();

                    /*if (this.fileNameEncoding == null) {
                        fileName = new String(fileName.getBytes(), "ISO-8859-1");
                    } else {
                        fileName = new String(fileName.getBytes(this.fileNameEncoding), "ISO-8859-1");
                    }*/
                    tmpBody.setFileName(fileName);
                    multiPart.addBodyPart(tmpBody);
                }
            }

            MimeMessage msg = new MimeMessage(this.session);
            msg.setContent(multiPart);
            msg.setSubject(this.subject, "UTF-8");
            Address senderAddress = new InternetAddress(this.senders, this.sendersname != null ? this.sendersname : "", "UTF-8");
            msg.setFrom(senderAddress);

            InternetAddress[] acceptAddress = new InternetAddress[this.recipients.size()];
            Set<String> sendersKey = this.recipients.keySet();
            int i = 0;
            for (Iterator<String> it = sendersKey.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                acceptAddress[i] = new InternetAddress(s, this.recipients.get(s), "UTF-8");
                i++;
            }
            msg.setRecipients(Message.RecipientType.TO, acceptAddress);
            trans.sendMessage(msg, acceptAddress);
            log.debug("邮件发送完毕");
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        } finally {
            if (trans != null) {
                trans.close();
            }
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpHost() {
        return this.smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSenders() {
        return this.senders;
    }

    public void setSenders(String senders) {
        this.senders = senders;
    }

    public Map<String, String> getRecipients() {
        return recipients;
    }

    public void setRecipients(Map<String, String> recipients) {
        this.recipients = recipients;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentType() {
        return this.contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public File[] getAttaches() {
        return this.attaches;
    }

    public void setAttaches(File[] attaches) {
        this.attaches = attaches;
    }

    public String getFileNameEncoding() {
        return this.fileNameEncoding;
    }

    public void setFileNameEncoding(String fileNameEncoding) {
        this.fileNameEncoding = fileNameEncoding;
    }
}
