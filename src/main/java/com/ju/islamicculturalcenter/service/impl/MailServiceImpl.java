package com.ju.islamicculturalcenter.service.impl;

import com.ju.islamicculturalcenter.service.iservice.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendPasswordEmail(String firstName, String emailTo, String password) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailTo);
        message.setSubject("Islamic-Cultural-Center Account Password");
        message.setText("Hello, " + firstName + "!\n"
                + "Your Password Is :" + password + "\n"
                + "Best Regards, Islamic-cultural-center!");

        try {
            javaMailSender.send(message);
            log.info("Email Sent Successfully to : {}", emailTo);
        } catch (MailException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void sendPasswordEmailWithHtml(String firstName, String emailTo, String password) { //TODO

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("islamic.cultural.center.ju@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, emailTo);
            message.setSubject("Your Account Password");
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        String htmlContent = "<!DOCTYPE html>\n" +
                "<html\n" +
                "  xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "  lang=\"en\"\n" +
                ">\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <!--[if mso\n" +
                "      ]><xml\n" +
                "        ><o:OfficeDocumentSettings\n" +
                "          ><o:PixelsPerInch>96</o:PixelsPerInch\n" +
                "          ><o:AllowPNG /></o:OfficeDocumentSettings></xml\n" +
                "    ><![endif]-->\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <link\n" +
                "      href=\"https://fonts.googleapis.com/css?family=Open+Sans\"\n" +
                "      rel=\"stylesheet\"\n" +
                "      type=\"text/css\"\n" +
                "    />\n" +
                "    <link\n" +
                "      href=\"https://fonts.googleapis.com/css?family=Cabin\"\n" +
                "      rel=\"stylesheet\"\n" +
                "      type=\"text/css\"\n" +
                "    />\n" +
                "    <!--<![endif]-->\n" +
                "    <style>\n" +
                "      * {\n" +
                "        box-sizing: border-box;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      a[x-apple-data-detectors] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      #MessageViewBody a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: none;\n" +
                "      }\n" +
                "\n" +
                "      p {\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      .desktop_hide,\n" +
                "      .desktop_hide table {\n" +
                "        mso-hide: all;\n" +
                "        display: none;\n" +
                "        max-height: 0px;\n" +
                "        overflow: hidden;\n" +
                "      }\n" +
                "\n" +
                "      @media (max-width: 620px) {\n" +
                "        .desktop_hide table.icons-inner {\n" +
                "          display: inline-block !important;\n" +
                "        }\n" +
                "\n" +
                "        .icons-inner {\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .icons-inner td {\n" +
                "          margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "        .image_block img.big,\n" +
                "        .row-content {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .mobile_hide {\n" +
                "          display: none;\n" +
                "        }\n" +
                "\n" +
                "        .stack .column {\n" +
                "          width: 100%;\n" +
                "          display: block;\n" +
                "        }\n" +
                "\n" +
                "        .mobile_hide {\n" +
                "          min-height: 0;\n" +
                "          max-height: 0;\n" +
                "          max-width: 0;\n" +
                "          overflow: hidden;\n" +
                "          font-size: 0px;\n" +
                "        }\n" +
                "\n" +
                "        .desktop_hide,\n" +
                "        .desktop_hide table {\n" +
                "          display: table !important;\n" +
                "          max-height: none !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      background-color: #d9dffa;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -webkit-text-size-adjust: none;\n" +
                "      text-size-adjust: none;\n" +
                "    \"\n" +
                "  >\n" +
                "    <table\n" +
                "      class=\"nl-container\"\n" +
                "      width=\"100%\"\n" +
                "      border=\"0\"\n" +
                "      cellpadding=\"0\"\n" +
                "      cellspacing=\"0\"\n" +
                "      role=\"presentation\"\n" +
                "      style=\"\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "        background-color: #d9dffa;\n" +
                "      \"\n" +
                "    >\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table\n" +
                "              class=\"row row-1\"\n" +
                "              align=\"center\"\n" +
                "              width=\"100%\"\n" +
                "              border=\"0\"\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              role=\"presentation\"\n" +
                "              style=\"\n" +
                "                mso-table-lspace: 0pt;\n" +
                "                mso-table-rspace: 0pt;\n" +
                "                background-color: #cfd6f4;\n" +
                "              \"\n" +
                "            >\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td>\n" +
                "                    <table\n" +
                "                      class=\"row-content stack\"\n" +
                "                      align=\"center\"\n" +
                "                      border=\"0\"\n" +
                "                      cellpadding=\"0\"\n" +
                "                      cellspacing=\"0\"\n" +
                "                      role=\"presentation\"\n" +
                "                      style=\"\n" +
                "                        mso-table-lspace: 0pt;\n" +
                "                        mso-table-rspace: 0pt;\n" +
                "                        color: #000000;\n" +
                "                        width: 600px;\n" +
                "                      \"\n" +
                "                      width=\"600\"\n" +
                "                    >\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td\n" +
                "                            class=\"column column-1\"\n" +
                "                            width=\"100%\"\n" +
                "                            style=\"\n" +
                "                              mso-table-lspace: 0pt;\n" +
                "                              mso-table-rspace: 0pt;\n" +
                "                              font-weight: 400;\n" +
                "                              text-align: left;\n" +
                "                              vertical-align: top;\n" +
                "                              padding-top: 20px;\n" +
                "                              padding-bottom: 0px;\n" +
                "                              border-top: 0px;\n" +
                "                              border-right: 0px;\n" +
                "                              border-bottom: 0px;\n" +
                "                              border-left: 0px;\n" +
                "                            \"\n" +
                "                          >\n" +
                "                            <table\n" +
                "                              class=\"icons_block block-1\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"0\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td\n" +
                "                                  class=\"pad\"\n" +
                "                                  style=\"\n" +
                "                                    vertical-align: middle;\n" +
                "                                    color: #000000;\n" +
                "                                    font-family: inherit;\n" +
                "                                    font-size: 14px;\n" +
                "                                    text-align: center;\n" +
                "                                  \"\n" +
                "                                >\n" +
                "                                  <table\n" +
                "                                    class=\"alignment\"\n" +
                "                                    cellpadding=\"0\"\n" +
                "                                    cellspacing=\"0\"\n" +
                "                                    role=\"presentation\"\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"\n" +
                "                                      mso-table-lspace: 0pt;\n" +
                "                                      mso-table-rspace: 0pt;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <tr>\n" +
                "                                      <td\n" +
                "                                        style=\"\n" +
                "                                          vertical-align: middle;\n" +
                "                                          text-align: center;\n" +
                "                                          padding-top: 5px;\n" +
                "                                          padding-bottom: 5px;\n" +
                "                                          padding-left: 5px;\n" +
                "                                          padding-right: 5px;\n" +
                "                                        \"\n" +
                "                                      >\n" +
                "                                        <img\n" +
                "                                          class=\"icon\"\n" +
                "                                          src=\"https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/920290_904650/logo-removebg-preview.png\"\n" +
                "                                          alt\n" +
                "                                          height=\"128\"\n" +
                "                                          width=\"95\"\n" +
                "                                          align=\"center\"\n" +
                "                                          style=\"\n" +
                "                                            display: block;\n" +
                "                                            height: auto;\n" +
                "                                            margin: 0 auto;\n" +
                "                                            border: 0;\n" +
                "                                          \"\n" +
                "                                        />\n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </table>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            <table\n" +
                "                              class=\"image_block block-2\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"0\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td\n" +
                "                                  class=\"pad\"\n" +
                "                                  style=\"\n" +
                "                                    width: 100%;\n" +
                "                                    padding-right: 0px;\n" +
                "                                    padding-left: 0px;\n" +
                "                                  \"\n" +
                "                                >\n" +
                "                                  <div\n" +
                "                                    class=\"alignment\"\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"line-height: 10px\"\n" +
                "                                  >\n" +
                "                                    <img\n" +
                "                                      class=\"big\"\n" +
                "                                      src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3991/animated_header.gif\"\n" +
                "                                      style=\"\n" +
                "                                        display: block;\n" +
                "                                        height: auto;\n" +
                "                                        border: 0;\n" +
                "                                        width: 600px;\n" +
                "                                        max-width: 100%;\n" +
                "                                      \"\n" +
                "                                      width=\"600\"\n" +
                "                                      alt=\"Card Header with Border and Shadow Animated\"\n" +
                "                                      title=\"Card Header with Border and Shadow Animated\"\n" +
                "                                    />\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table\n" +
                "              class=\"row row-2\"\n" +
                "              align=\"center\"\n" +
                "              width=\"100%\"\n" +
                "              border=\"0\"\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              role=\"presentation\"\n" +
                "              style=\"\n" +
                "                mso-table-lspace: 0pt;\n" +
                "                mso-table-rspace: 0pt;\n" +
                "                background-color: #d9dffa;\n" +
                "                background-image: url('https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3991/body_background_2.png');\n" +
                "                background-position: top center;\n" +
                "                background-repeat: repeat;\n" +
                "                background-size: auto;\n" +
                "              \"\n" +
                "            >\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td>\n" +
                "                    <table\n" +
                "                      class=\"row-content stack\"\n" +
                "                      align=\"center\"\n" +
                "                      border=\"0\"\n" +
                "                      cellpadding=\"0\"\n" +
                "                      cellspacing=\"0\"\n" +
                "                      role=\"presentation\"\n" +
                "                      style=\"\n" +
                "                        mso-table-lspace: 0pt;\n" +
                "                        mso-table-rspace: 0pt;\n" +
                "                        background-size: auto;\n" +
                "                        border-radius: 0;\n" +
                "                        color: #000000;\n" +
                "                        width: 600px;\n" +
                "                      \"\n" +
                "                      width=\"600\"\n" +
                "                    >\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td\n" +
                "                            class=\"column column-1\"\n" +
                "                            width=\"100%\"\n" +
                "                            style=\"\n" +
                "                              mso-table-lspace: 0pt;\n" +
                "                              mso-table-rspace: 0pt;\n" +
                "                              font-weight: 400;\n" +
                "                              text-align: left;\n" +
                "                              padding-left: 50px;\n" +
                "                              padding-right: 50px;\n" +
                "                              vertical-align: top;\n" +
                "                              padding-top: 15px;\n" +
                "                              padding-bottom: 15px;\n" +
                "                              border-top: 0px;\n" +
                "                              border-right: 0px;\n" +
                "                              border-bottom: 0px;\n" +
                "                              border-left: 0px;\n" +
                "                            \"\n" +
                "                          >\n" +
                "                            <table\n" +
                "                              class=\"text_block block-1\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"10\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                                word-break: break-word;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td class=\"pad\">\n" +
                "                                  <div style=\"font-family: sans-serif\">\n" +
                "                                    <div\n" +
                "                                      class\n" +
                "                                      style=\"\n" +
                "                                        font-size: 14px;\n" +
                "                                        mso-line-height-alt: 16.8px;\n" +
                "                                        color: #506bec;\n" +
                "                                        line-height: 1.2;\n" +
                "                                        font-family: Helvetica Neue, Helvetica,\n" +
                "                                          Arial, sans-serif;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <p\n" +
                "                                        style=\"\n" +
                "                                          margin: 0;\n" +
                "                                          font-size: 14px;\n" +
                "                                          text-align: right;\n" +
                "                                          mso-line-height-alt: 16.8px;\n" +
                "                                        \"\n" +
                "                                      >\n" +
                "                                        <strong\n" +
                "                                          ><span style=\"font-size: 38px\"\n" +
                "                                            >:كلمة المرور لحسابك هي</span\n" +
                "                                          ></strong\n" +
                "                                        >\n" +
                "                                      </p>\n" +
                "                                    </div>\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            <table\n" +
                "                              class=\"divider_block block-2\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"10\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td class=\"pad\">\n" +
                "                                  <div class=\"alignment\" align=\"center\">\n" +
                "                                    <table\n" +
                "                                      border=\"0\"\n" +
                "                                      cellpadding=\"0\"\n" +
                "                                      cellspacing=\"0\"\n" +
                "                                      role=\"presentation\"\n" +
                "                                      width=\"100%\"\n" +
                "                                      style=\"\n" +
                "                                        mso-table-lspace: 0pt;\n" +
                "                                        mso-table-rspace: 0pt;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <tr>\n" +
                "                                        <td\n" +
                "                                          class=\"divider_inner\"\n" +
                "                                          style=\"\n" +
                "                                            font-size: 1px;\n" +
                "                                            line-height: 1px;\n" +
                "                                            border-top: 2px dashed #8a3c90;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <span>&#8202;</span>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            <table\n" +
                "                              class=\"heading_block block-3\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"0\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td\n" +
                "                                  class=\"pad\"\n" +
                "                                  style=\"width: 100%; text-align: center\"\n" +
                "                                >\n" +
                "                                  <h1\n" +
                "                                    style=\"\n" +
                "                                      margin: 0;\n" +
                "                                      color: #506bec;\n" +
                "                                      font-size: 38px;\n" +
                "                                      font-family: Helvetica Neue, Helvetica,\n" +
                "                                        Arial, sans-serif;\n" +
                "                                      line-height: 120%;\n" +
                "                                      text-align: center;\n" +
                "                                      direction: ltr;\n" +
                "                                      font-weight: 700;\n" +
                "                                      letter-spacing: normal;\n" +
                "                                      margin-top: 0;\n" +
                "                                      margin-bottom: 0;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <span class=\"tinyMce-placeholder\"\n" +
                "                                      >" + password +"</span\n" +
                "                                    >\n" +
                "                                  </h1>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            <table\n" +
                "                              class=\"divider_block block-4\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"10\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td class=\"pad\">\n" +
                "                                  <div class=\"alignment\" align=\"center\">\n" +
                "                                    <table\n" +
                "                                      border=\"0\"\n" +
                "                                      cellpadding=\"0\"\n" +
                "                                      cellspacing=\"0\"\n" +
                "                                      role=\"presentation\"\n" +
                "                                      width=\"100%\"\n" +
                "                                      style=\"\n" +
                "                                        mso-table-lspace: 0pt;\n" +
                "                                        mso-table-rspace: 0pt;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <tr>\n" +
                "                                        <td\n" +
                "                                          class=\"divider_inner\"\n" +
                "                                          style=\"\n" +
                "                                            font-size: 1px;\n" +
                "                                            line-height: 1px;\n" +
                "                                            border-top: 2px dashed #8a3c90;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <span>&#8202;</span>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            <table\n" +
                "                              class=\"paragraph_block block-5\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"0\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                                word-break: break-word;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td class=\"pad\">\n" +
                "                                  <div\n" +
                "                                    style=\"\n" +
                "                                      color: #101112;\n" +
                "                                      font-size: 16px;\n" +
                "                                      font-family: Helvetica Neue, Helvetica,\n" +
                "                                        Arial, sans-serif;\n" +
                "                                      font-weight: 400;\n" +
                "                                      line-height: 120%;\n" +
                "                                      text-align: left;\n" +
                "                                      direction: ltr;\n" +
                "                                      letter-spacing: 0px;\n" +
                "                                      mso-line-height-alt: 19.2px;\n" +
                "                                    \"\n" +
                "                                  ></div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table\n" +
                "              class=\"row row-3\"\n" +
                "              align=\"center\"\n" +
                "              width=\"100%\"\n" +
                "              border=\"0\"\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              role=\"presentation\"\n" +
                "              style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt\"\n" +
                "            >\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td>\n" +
                "                    <table\n" +
                "                      class=\"row-content stack\"\n" +
                "                      align=\"center\"\n" +
                "                      border=\"0\"\n" +
                "                      cellpadding=\"0\"\n" +
                "                      cellspacing=\"0\"\n" +
                "                      role=\"presentation\"\n" +
                "                      style=\"\n" +
                "                        mso-table-lspace: 0pt;\n" +
                "                        mso-table-rspace: 0pt;\n" +
                "                        color: #000000;\n" +
                "                        width: 600px;\n" +
                "                      \"\n" +
                "                      width=\"600\"\n" +
                "                    >\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td\n" +
                "                            class=\"column column-1\"\n" +
                "                            width=\"100%\"\n" +
                "                            style=\"\n" +
                "                              mso-table-lspace: 0pt;\n" +
                "                              mso-table-rspace: 0pt;\n" +
                "                              font-weight: 400;\n" +
                "                              text-align: left;\n" +
                "                              vertical-align: top;\n" +
                "                              padding-top: 0px;\n" +
                "                              padding-bottom: 5px;\n" +
                "                              border-top: 0px;\n" +
                "                              border-right: 0px;\n" +
                "                              border-bottom: 0px;\n" +
                "                              border-left: 0px;\n" +
                "                            \"\n" +
                "                          >\n" +
                "                            <table\n" +
                "                              class=\"image_block block-1\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"0\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td\n" +
                "                                  class=\"pad\"\n" +
                "                                  style=\"\n" +
                "                                    width: 100%;\n" +
                "                                    padding-right: 0px;\n" +
                "                                    padding-left: 0px;\n" +
                "                                  \"\n" +
                "                                >\n" +
                "                                  <div\n" +
                "                                    class=\"alignment\"\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"line-height: 10px\"\n" +
                "                                  >\n" +
                "                                    <img\n" +
                "                                      class=\"big\"\n" +
                "                                      src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3991/bottom_img.png\"\n" +
                "                                      style=\"\n" +
                "                                        display: block;\n" +
                "                                        height: auto;\n" +
                "                                        border: 0;\n" +
                "                                        width: 600px;\n" +
                "                                        max-width: 100%;\n" +
                "                                      \"\n" +
                "                                      width=\"600\"\n" +
                "                                      alt=\"Card Bottom with Border and Shadow Image\"\n" +
                "                                      title=\"Card Bottom with Border and Shadow Image\"\n" +
                "                                    />\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table\n" +
                "              class=\"row row-4\"\n" +
                "              align=\"center\"\n" +
                "              width=\"100%\"\n" +
                "              border=\"0\"\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              role=\"presentation\"\n" +
                "              style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt\"\n" +
                "            >\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td>\n" +
                "                    <table\n" +
                "                      class=\"row-content stack\"\n" +
                "                      align=\"center\"\n" +
                "                      border=\"0\"\n" +
                "                      cellpadding=\"0\"\n" +
                "                      cellspacing=\"0\"\n" +
                "                      role=\"presentation\"\n" +
                "                      style=\"\n" +
                "                        mso-table-lspace: 0pt;\n" +
                "                        mso-table-rspace: 0pt;\n" +
                "                        color: #000000;\n" +
                "                        width: 600px;\n" +
                "                      \"\n" +
                "                      width=\"600\"\n" +
                "                    >\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td\n" +
                "                            class=\"column column-1\"\n" +
                "                            width=\"100%\"\n" +
                "                            style=\"\n" +
                "                              mso-table-lspace: 0pt;\n" +
                "                              mso-table-rspace: 0pt;\n" +
                "                              font-weight: 400;\n" +
                "                              text-align: left;\n" +
                "                              padding-left: 10px;\n" +
                "                              padding-right: 10px;\n" +
                "                              vertical-align: top;\n" +
                "                              padding-top: 10px;\n" +
                "                              padding-bottom: 20px;\n" +
                "                              border-top: 0px;\n" +
                "                              border-right: 0px;\n" +
                "                              border-bottom: 0px;\n" +
                "                              border-left: 0px;\n" +
                "                            \"\n" +
                "                          >\n" +
                "                            <table\n" +
                "                              class=\"text_block block-1\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"10\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                                word-break: break-word;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td class=\"pad\">\n" +
                "                                  <div style=\"font-family: sans-serif\">\n" +
                "                                    <div\n" +
                "                                      class\n" +
                "                                      style=\"\n" +
                "                                        font-size: 14px;\n" +
                "                                        mso-line-height-alt: 16.8px;\n" +
                "                                        color: #97a2da;\n" +
                "                                        line-height: 1.2;\n" +
                "                                        font-family: Helvetica Neue, Helvetica,\n" +
                "                                          Arial, sans-serif;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <p\n" +
                "                                        style=\"\n" +
                "                                          margin: 0;\n" +
                "                                          font-size: 14px;\n" +
                "                                          text-align: center;\n" +
                "                                          mso-line-height-alt: 16.8px;\n" +
                "                                        \"\n" +
                "                                      >\n" +
                "                                        ستنتهي صلاحية هذا الرابط خلال الـ 24\n" +
                "                                        ساعة القادمة.\n" +
                "                                      </p>\n" +
                "                                    </div>\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            <table\n" +
                "                              class=\"text_block block-2\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"10\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                                word-break: break-word;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td class=\"pad\">\n" +
                "                                  <div style=\"font-family: sans-serif\">\n" +
                "                                    <div\n" +
                "                                      class\n" +
                "                                      style=\"\n" +
                "                                        font-size: 14px;\n" +
                "                                        mso-line-height-alt: 16.8px;\n" +
                "                                        color: #97a2da;\n" +
                "                                        line-height: 1.2;\n" +
                "                                        font-family: Helvetica Neue, Helvetica,\n" +
                "                                          Arial, sans-serif;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <p\n" +
                "                                        style=\"\n" +
                "                                          margin: 0;\n" +
                "                                          text-align: center;\n" +
                "                                          font-size: 12px;\n" +
                "                                          mso-line-height-alt: 14.399999999999999px;\n" +
                "                                        \"\n" +
                "                                      >\n" +
                "                                        <span style=\"font-size: 12px\"\n" +
                "                                          >Copyright© 2022 Islamic culture\n" +
                "                                          center.</span\n" +
                "                                        >\n" +
                "                                      </p>\n" +
                "                                    </div>\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table\n" +
                "              class=\"row row-5\"\n" +
                "              align=\"center\"\n" +
                "              width=\"100%\"\n" +
                "              border=\"0\"\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              role=\"presentation\"\n" +
                "              style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt\"\n" +
                "            >\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td>\n" +
                "                    <table\n" +
                "                      class=\"row-content stack\"\n" +
                "                      align=\"center\"\n" +
                "                      border=\"0\"\n" +
                "                      cellpadding=\"0\"\n" +
                "                      cellspacing=\"0\"\n" +
                "                      role=\"presentation\"\n" +
                "                      style=\"\n" +
                "                        mso-table-lspace: 0pt;\n" +
                "                        mso-table-rspace: 0pt;\n" +
                "                        color: #000000;\n" +
                "                        width: 600px;\n" +
                "                      \"\n" +
                "                      width=\"600\"\n" +
                "                    >\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td\n" +
                "                            class=\"column column-1\"\n" +
                "                            width=\"100%\"\n" +
                "                            style=\"\n" +
                "                              mso-table-lspace: 0pt;\n" +
                "                              mso-table-rspace: 0pt;\n" +
                "                              font-weight: 400;\n" +
                "                              text-align: left;\n" +
                "                              vertical-align: top;\n" +
                "                              padding-top: 5px;\n" +
                "                              padding-bottom: 5px;\n" +
                "                              border-top: 0px;\n" +
                "                              border-right: 0px;\n" +
                "                              border-bottom: 0px;\n" +
                "                              border-left: 0px;\n" +
                "                            \"\n" +
                "                          >\n" +
                "                            <table\n" +
                "                              class=\"icons_block block-1\"\n" +
                "                              width=\"100%\"\n" +
                "                              border=\"0\"\n" +
                "                              cellpadding=\"0\"\n" +
                "                              cellspacing=\"0\"\n" +
                "                              role=\"presentation\"\n" +
                "                              style=\"\n" +
                "                                mso-table-lspace: 0pt;\n" +
                "                                mso-table-rspace: 0pt;\n" +
                "                              \"\n" +
                "                            >\n" +
                "                              <tr>\n" +
                "                                <td\n" +
                "                                  class=\"pad\"\n" +
                "                                  style=\"\n" +
                "                                    vertical-align: middle;\n" +
                "                                    color: #9d9d9d;\n" +
                "                                    font-family: inherit;\n" +
                "                                    font-size: 15px;\n" +
                "                                    padding-bottom: 5px;\n" +
                "                                    padding-top: 5px;\n" +
                "                                    text-align: center;\n" +
                "                                  \"\n" +
                "                                >\n" +
                "                                  <table\n" +
                "                                    width=\"100%\"\n" +
                "                                    cellpadding=\"0\"\n" +
                "                                    cellspacing=\"0\"\n" +
                "                                    role=\"presentation\"\n" +
                "                                    style=\"\n" +
                "                                      mso-table-lspace: 0pt;\n" +
                "                                      mso-table-rspace: 0pt;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <tr>\n" +
                "                                      <td\n" +
                "                                        class=\"alignment\"\n" +
                "                                        style=\"\n" +
                "                                          vertical-align: middle;\n" +
                "                                          text-align: center;\n" +
                "                                        \"\n" +
                "                                      >\n" +
                "                                        <!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\n" +
                "                                        <!--[if !vml]><!-->\n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </table>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "    <!-- End -->\n" +
                "  </body>\n" +
                "</html>";

        try {
            message.setContent(htmlContent, "text/html; charset=utf-8");
            javaMailSender.send(message);
            log.info("Email Sent Successfully to : {}", emailTo);
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
