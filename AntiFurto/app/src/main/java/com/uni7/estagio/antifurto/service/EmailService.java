package com.uni7.estagio.antifurto.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Breno Luan on 21/05/2017.
 */

public class EmailService extends Service {

//    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    Properties props = null;

    String rec, subject, textMessage;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        rec = "leidianejuliahellen@gmail.com";
        subject = "Teste Envio de Email";
        textMessage = "Lilica cara de onça";
        String anexo = intent.getStringExtra("anexo");



//         Session session = Session.getInstance(props, auth);



//        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);


        RetreiveFeedTask task = new RetreiveFeedTask(getSession(), rec, subject, textMessage, anexo);
        task.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public Session getSession(){
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("recoveryphoneapp@gmail.com", "apprecoveryphone");
            }
        });
        session.setDebug(true);
        return session;

    }

    class RetreiveFeedTask extends Thread {
        Session session = null;
        String receptor = null;
        String sub = null;
        String text = null;
        String anexo = null;
        public RetreiveFeedTask(Session session, String receptor, String sub, String text, String anexo){
            this.session = session;
            this.receptor = receptor;
            this.sub = sub;
            this.text = text;
            this.anexo = anexo;
        }
        public void run(){
            Log.i("Thread", "Entrando");
//            Log.i("Auth", session.get)

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("recoveryphoneapp@gmail.com"));
//                leidianejuliahellen@gmail.com
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
                message.setSubject(sub);
               // message.setContent(text, "text/html; charset=utf-8");
                Multipart multipart = new MimeMultipart();
                // Texto
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(text, "text/plain");
                multipart.addBodyPart(messageBodyPart);
                //Anexo
                File file = new File(anexo);
                DataSource ds = new FileDataSource(file) {
                    public String getContentType() {
                        return "application/octet-stream";
                    }
                };
                BodyPart mbp = new MimeBodyPart();
                mbp.setDataHandler(new DataHandler(ds));
                mbp.setFileName(file.getName());
                mbp.setDisposition(Part.ATTACHMENT);
                multipart.addBodyPart(mbp);
                message.setContent(multipart);
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            stopSelf();
        }

    }
}
